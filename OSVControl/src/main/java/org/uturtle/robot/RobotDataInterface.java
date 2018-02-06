package org.uturtle.robot;

import org.uturtle.serial.IncomingMessageFlag.PositionUpdate;
import org.uturtle.serial.IncomingMessageFlag.SensorUpdate;
import org.uturtle.serial.OutgoingMessageFlag.MotorOutput;

public interface RobotDataInterface {
	public double getLeftVoltage();

	public double getRightVoltage();

	public void updatePosition(float x, float y, float theta);

	public void updateSensors(int leftEncoder, int rightEncoder, float gyro);

	public void init();

	public void update(int dt);

	public default byte getLeftPWM() {
		return (byte) Math.abs(getLeftVoltage() * 255);
	}

	public default byte getRightPWM() {
		return (byte) Math.abs(getLeftVoltage() * 255);
	}

	public default boolean getLeftDirection() {
		return getLeftVoltage() < 0;
	}

	public default boolean getRightDirection() {
		return getRightVoltage() < 0;
	}

	public default void consumePositionUpdate(PositionUpdate pos) {
		updatePosition(pos.x, pos.y, pos.theta);
	}

	public default void consumeSensorUpdate(SensorUpdate sensors) {
		updateSensors(sensors.left, sensors.right, sensors.theta);
	}

	public default MotorOutput generateMotorCommand() {
		return new MotorOutput(getLeftPWM(), getRightPWM(), getLeftDirection(), getRightDirection());
	}
}
