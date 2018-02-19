package org.uturtle.test;

import org.uturtle.serial.ArduinoComm;
import org.uturtle.serial.ArduinoDataHandler;

public class TestSerialLink {
	public static void main(String[] args) {
		ArduinoComm comm = ArduinoComm.getInstance();
		ArduinoDataHandler data = ArduinoDataHandler.getInstance();
		comm.init();
		data.onUnknownMessage(System.out::println);
	}
}
