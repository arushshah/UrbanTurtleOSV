package org.uturtle.serial;

public enum OutgoingMessageFlag {
	MOTOR_OUTPUT("SET"), DESTINATION_REQUEST("DEST");
	public final String flag;

	private OutgoingMessageFlag(String flag) {
		this.flag = flag;
	}

	public static class MotorOutput implements Sendable {
		public final byte left, right;
		boolean leftReverse, rightReverse;

		public MotorOutput(byte left, byte right, boolean leftReverse, boolean rightReverse) {
			this.left = left;
			this.right = right;
			this.leftReverse = leftReverse;
			this.rightReverse = rightReverse;
		}

		@Override
		public String getSendableString() {
			int l = leftReverse ? 0 : 1;
			int r = rightReverse ? 0 : 1;
			return MOTOR_OUTPUT.flag + l + r + new String(new byte[] { left, right }) + "\n";
		}
	}
}
