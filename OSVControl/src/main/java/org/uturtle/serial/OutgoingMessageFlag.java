package org.uturtle.serial;

public enum OutgoingMessageFlag {
	MOTOR_OUTPUT("SET"), DESTINATION_REQUEST("DEST");
	public final String flag;

	private OutgoingMessageFlag(String flag) {
		this.flag = flag;
	}

	public static class MotorOutput {
		public final float left, right;

		public MotorOutput(float left, float right) {
			this.left = left;
			this.right = right;
		}
	}
}
