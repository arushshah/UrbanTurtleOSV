package org.uturtle.serial;

import java.util.Arrays;
import java.util.function.Consumer;

import org.uturtle.serial.IncomingMessageFlag.DestinationResponse;
import org.uturtle.serial.IncomingMessageFlag.PositionUpdate;

public class ArduinoDataHandler {
	// Singleton groundwork
	private static final ArduinoDataHandler INSTANCE = new ArduinoDataHandler();

	private ArduinoDataHandler() {
	}

	public static ArduinoDataHandler getInstance() {
		return INSTANCE;
	}

	private Consumer<PositionUpdate> handlePositionUpdate;
	private Consumer<DestinationResponse> handleDestinationResponse;

	public void handle(String message) {
		if (message.contains(IncomingMessageFlag.DESTINATION_RESPONSE.flag) && handleDestinationResponse != null) {
			handleDestinationResponse.accept(parseDestinationResponse(message));
		} else if (message.contains(IncomingMessageFlag.POSITION_UPDATE.flag) && handlePositionUpdate != null) {
			handlePositionUpdate.accept(parsePositionUpdate(message));
		} else {
			System.err.println(message);
		}
	}

	private static DestinationResponse parseDestinationResponse(String message) {
		message = message.replace(IncomingMessageFlag.DESTINATION_RESPONSE.flag, "");
		String[] vals = message.split(":");
		Float[] floatVals = Arrays.stream(vals).map(s -> Float.parseFloat(s)).toArray(i -> new Float[i]);
		return new DestinationResponse(floatVals[0], floatVals[1], floatVals[2]);
	}

	private static PositionUpdate parsePositionUpdate(String message) {
		message = message.replace(IncomingMessageFlag.POSITION_UPDATE.flag, "");
		String[] vals = message.split(":");
		Float[] floatVals = Arrays.stream(vals).map(s -> Float.parseFloat(s)).toArray(i -> new Float[i]);
		return new PositionUpdate(floatVals[0], floatVals[1], floatVals[2]);
	}

	public void onPositionUpdate(Consumer<PositionUpdate> handlePositionUpdate) {
		this.handlePositionUpdate = handlePositionUpdate;
	}

	public void onDestinationResponse(Consumer<DestinationResponse> handleDestinationResponse) {
		this.handleDestinationResponse = handleDestinationResponse;
	}

}
