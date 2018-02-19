package org.uturtle.main;

import org.uturtle.robot.OSVRobot;
import org.uturtle.robot.RobotDataInterface;
import org.uturtle.serial.ArduinoComm;
import org.uturtle.serial.ArduinoDataHandler;

public class Main {
	static long start;

	public static void main(String[] args) throws InterruptedException {
		ArduinoComm comm = ArduinoComm.getInstance();
		ArduinoDataHandler data = ArduinoDataHandler.getInstance();
		comm.init();
		RobotDataInterface robot = new OSVRobot();
		data.onPositionUpdate(robot::consumePositionUpdate);
		data.onSensorUpdate(robot::consumeSensorUpdate);
		robot.init();
		while (comm.isOpen()) {
			robot.update(0);
			comm.send(robot.generateMotorCommand());
		}
	}

}
