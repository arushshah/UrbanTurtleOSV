package org.uturtle.main;

import org.uturtle.serial.ArduinoComm;

public class Main {
	public static void main(String[] args) {
		ArduinoComm comm = ArduinoComm.getInstance();
		comm.init();
		comm.request();
	}
}
