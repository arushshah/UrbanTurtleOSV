package org.uturtle.serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * 
 * @author Ari Main class responsible for communication with the arduino.
 *         Singleton pattern makes it available from anywhere in the code, and
 *         the class offers communication functionality
 */
public class ArduinoComm implements SerialPortEventListener {

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
	public static final String PORT = RUN_ON_PI ? "/dev/ttyACM0" : "COM3";
	public static final int TIME_OUT = 1000;

	// Instance
	private SerialPort serialPort;
	private CancellableBufferedOutputStream output;
	private BufferedReader input;
	private boolean initialized;

	private boolean initialize() throws Exception {
		if (RUN_ON_PI) {
			System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
		}
		CommPortIdentifier portId = null;
		try {
			portId = CommPortIdentifier.getPortIdentifier(PORT);
		} catch (NoSuchPortException e1) {
			System.err.println("invalid port: " + PORT);
			return false;
		}
		if (portId.isCurrentlyOwned()) {
			System.err.println("Port in use: " + portId.getCurrentOwner());
			return false;
		}
		// open serial port, and use class name for the appName.
		serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

		// set port parameters
		serialPort.setSerialPortParams(BAUD_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

		// open the streams
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = new CancellableBufferedOutputStream(serialPort.getOutputStream());
		// add event listeners
		serialPort.addEventListener(this);
		serialPort.notifyOnDataAvailable(true);
		return true;
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine;
				while (input.ready() && (inputLine = input.readLine()) != null) {
					System.out.println(inputLine);
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}

	}

	public void request() {
		try {
			output.write(new String("POS?").getBytes());
			output.flush();
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

	/**
	 * This should be called when you stop using the port. This will prevent port
	 * locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
		initialized = false;
	}

}
