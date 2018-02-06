package org.uturtle.main;

import org.uturtle.serial.ArduinoComm;
import org.uturtle.serial.ArduinoDataHandler;

public class Main {
	static long start;

	public static void main(String[] args) {
		ArduinoComm comm = ArduinoComm.getInstance();
		comm.init();
		comm.request();
		start = System.currentTimeMillis();
		ArduinoDataHandler.getInstance().onPositionUpdate(u -> {
			System.out.println(u + " -- " + (System.currentTimeMillis() - start));
			trig();
		});
	}

	private static void trig() {
		start = System.currentTimeMillis();
	}
}
