package org.uturtle.main;

import org.uturtle.serial.ArduinoComm;
import org.uturtle.serial.ArduinoDataHandler;
import org.uturtle.serial.OutgoingMessageFlag.MotorOutput;

public class Main {
	static long start;

	public static void main(String[] args) throws InterruptedException {
		ArduinoComm comm = ArduinoComm.getInstance();
		comm.init();
		start = System.currentTimeMillis();
		ArduinoDataHandler.getInstance().onPositionUpdate(u -> {
			System.out.println(u + " -- " + (System.currentTimeMillis() - start));
			trig();
		});
		ArduinoDataHandler.getInstance().onSensorUpdate(u -> {
			System.out.println(u);
		});
		while (true) {
			comm.send(new MotorOutput((byte) 40, (byte) 20, false, true));
			Thread.sleep(20);
		}
	}

	private static void trig() {
		start = System.currentTimeMillis();
	}
}
