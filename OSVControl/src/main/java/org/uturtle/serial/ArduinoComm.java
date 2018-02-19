package org.uturtle.serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * 
 * @author Ari Main class responsible for communication with the arduino.
 *         Singleton pattern makes it available from anywhere in the code, and
 *         the class offers communication functionality
 */
public class ArduinoComm implements SerialPortDataListener {

	// Singleton groundwork
	private static final ArduinoComm INSTANCE = new ArduinoComm();

	private ArduinoComm() {
	}

	public static ArduinoComm getInstance() {
		return INSTANCE;
	}

	// Constants
	public static final boolean RUN_ON_PI = false;
	public static final int BAUD_RATE = 9600;
	public static final String PORT = RUN_ON_PI ? "/dev/ttyACM0" : "COM11";
	public static final int TIME_OUT = 1000;

	// Instance
	private SerialPort serialPort;
	private OutputStream output;
	private BufferedReader input;
	private boolean initialized;

	private boolean initialize() throws Exception {
		if (RUN_ON_PI) {
			System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
		}
		Arrays.stream(SerialPort.getCommPorts()).forEach(System.out::println);
		SerialPort portId = SerialPort.getCommPorts()[0];
		if (!portId.isOpen()) {
			System.err.println("Port in use: " + portId.getDescriptivePortName());
			return false;
		}
		// open serial port, and use class name for the appName.
		if (portId.openPort()) {
			initialized = true;
			serialPort = portId;
		}
		;

		// set port parameters
		serialPort.setComPortParameters(BAUD_RATE, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

		// open the streams
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = serialPort.getOutputStream();
		// add event listeners
		serialPort.addDataListener(this);
		return true;
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
			try {
				String inputLine;
				while (input.ready() && (inputLine = input.readLine()) != null) {
					ArduinoDataHandler.getInstance().handle(inputLine);
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}

	}

	public void send(Sendable message) {
		try {
			output.write(message.getSendableString().getBytes());
			// output.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * initializes the communication routine on the port specified at {@link #PORT}
	 * 
	 * @return whether communication was successfully initiated
	 */
	public boolean init() {
		try {
			initialized = initialize();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			initialized = false;
		}
		return initialized;
	}

	public boolean isOpen() {
		return initialized;
	}

	/**
	 * This should be called when you stop using the port. This will prevent port
	 * locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeDataListener();
			serialPort.closePort();
		}
		initialized = false;
	}

	@Override
	public int getListeningEvents() {
		return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
	}

}
