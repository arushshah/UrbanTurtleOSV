package org.uturtle.robot;

public abstract class Robot implements RobotDataInterface {
	private Motor left, right;
	private float gyro, gyroOffset;

	@Override
	public double getLeftVoltage() {
		return left.getVoltage();
	}

	@Override
	public double getRightVoltage() {
		return right.getVoltage();
	}

	@Override
	public void updateSensors(int leftEncoder, int rightEncoder, float gyro) {
		left.position = leftEncoder;
		right.position = rightEncoder;
		this.gyro = gyro;
	}

	public void zeroEncoders() {
		left.zeroEncoder();
		right.zeroEncoder();
	}

	public float getGyroAngle() {
		return gyro - gyroOffset;
	}

	public void zeroGyro() {
		gyroOffset = gyro;
	}

	public class Motor {
		private double voltage;
		private boolean inverted;
		private int position, encoderOffset;

		public double getVoltage() {
			return inverted ? -1 : 1 * voltage;
		}

		public void setInverted(boolean inverted) {
			this.inverted = inverted;
		}

		public void zeroEncoder() {
			encoderOffset = position;
		}

		public void setVoltage(double val) {
			voltage = Math.max(Math.min(val, 1), -1);
		}

		public int getPosition() {
			return position - encoderOffset;
		}
	}
}
