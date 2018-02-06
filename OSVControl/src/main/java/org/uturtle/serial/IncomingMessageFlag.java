package org.uturtle.serial;

public enum IncomingMessageFlag {
	POSITION_UPDATE("POS"), DESTINATION_RESPONSE("DEST"), NAVIGATED("NAV"), STATE_SENSORS_UPDATE("ENC");
	public final String flag;

	private IncomingMessageFlag(String flag) {
		this.flag = flag;
	}

	public static class PositionUpdate {
		public final float x, y, theta;

		public PositionUpdate(float x, float y, float theta) {
			this.x = x;
			this.y = y;
			this.theta = theta;
		}

		public String toString() {
			return POSITION_UPDATE.flag + "[" + x + ", " + y + ", " + theta + "]";
		}
	}

	public static class DestinationResponse {
		public final float x, y, theta;

		public DestinationResponse(float x, float y, float theta) {
			this.x = x;
			this.y = y;
			this.theta = theta;
		}

		public String toString() {
			return DESTINATION_RESPONSE.flag + "[" + x + ", " + y + ", " + theta + "]";
		}
	}

	public static class StateSensorsUpdate {
		public final int left, right;
		public final float theta;

		public StateSensorsUpdate(int left, int right, float theta) {
			this.left = left;
			this.right = right;
			this.theta = theta;
		}

		public String toString() {
			return STATE_SENSORS_UPDATE.flag + "[" + left + ", " + right + ", " + theta + "]";
		}
	}
}
