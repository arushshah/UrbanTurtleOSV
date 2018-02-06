float x, y, theta;
int locationUpdateState;
long start;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}
void loop() {
  handleIncomingMessages();
  updateData();
  /*if(Serial.findUntil("FLOAT","\n")){
      String s = Serial.readStringUntil("\n");
      float f = s.toFloat();
      String msg = "f: ";
      Serial.println(msg+f);
    }*/
}
void updateData() {
  locUpdateSim();
  if (locationUpdateState == 3) {
    sendLocationUpdate();
    reqLocSim();
  }
}
void sendLocationUpdate() {
  String flag = "POS";
  Serial.println(flag + x + ":" + y + ":" + theta);
}
void handleIncomingMessages() {
  if (Serial.available() > 0) {
    String s = Serial.readStringUntil("\n");
  }
}

void reqLocSim() {
  start = millis();
  locationUpdateState = 0;
}

void locUpdateSim() {
  long v = millis() - start;
  if (locationUpdateState == 2 && millis() - start > 210) {
    theta = random(0, 360000) / 1000.0;
    locationUpdateState++;
  } else if (locationUpdateState == 1 && millis() - start > 140) {
    y = random(0, 2000) / 1000.0;
    locationUpdateState++;
  } else if (locationUpdateState == 0 && millis() - start > 70) {
    x = random(0, 4000) / 1000.0;
    locationUpdateState++;
  }
}

