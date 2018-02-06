package org.uturtle.robot;

public interface RobotDataInterface {
	public double getLeftVoltage();

	public double getRightVoltage();

	public void updatePosition(float x, float y, float theta);

	public void updateSensors(int leftEncoder, int rightEncoder, float gyro);

	public void init();

	public void update(int dt);

	public default int getLeftPWM() {
		return (int) Math.abs(getLeftVoltage() * 255);
	}

	public default int getRightPWM() {
		return (int) Math.abs(getLeftVoltage() * 255);
	}

	public default boolean getLeftDirection() {
		return getLeftVoltage() < 0;
	}

	public default boolean getRightDirection() {
		return getRightVoltage() < 0;
	}
}
