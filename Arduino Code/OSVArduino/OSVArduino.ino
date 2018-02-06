typedef struct{
  float x,y,theta;
} Pose;
typedef struct{
  int left,right;
  float theta;
} SensorData;
Pose pose;
SensorData sensors;
int leftPower,rightPower;
int locationUpdateState;
long start;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  Serial.setTimeout(5);
}
void loop() {
  handleIncomingMessages();
  updateData();
  controlHardware();
}

//SEND DATA TO PI
void updateData() {
  locUpdateSim();
  if (locationUpdateState == 3) {
    sendLocationUpdate();
    reqLocSim();
  }
  sendSensorUpdate();
}
void sendLocationUpdate() {
  String flag = "POS";
  Serial.println(flag + pose.x + ":" + pose.y + ":" + pose.theta);
}
void sendSensorUpdate() {
  String flag = "ENC";
  Serial.println(flag + sensors.left + ":" + sensors.right + ":" + sensors.theta);
}

//HANDLE RECIEVED DATA
void handleIncomingMessages() {
  if (Serial.available() > 0) {
    String s = Serial.readStringUntil("\n");
    if (s.startsWith("SET")) {
      handleMotorMessage(s);
    }
  }
}
void handleMotorMessage(String msg) {
  msg.replace("SET", "");
  int lRev = (msg.substring(0,1).toInt() * 2) - 1;
  int rRev = (msg.substring(1,2).toInt( ) * 2) - 1;
  msg = msg.substring(2);
  byte pVals[2];
  msg.getBytes(pVals,6);
  leftPower = lRev*pVals[0];
  rightPower = rRev*pVals[1];
  /*String out = "motor vals: ";
  Serial.println(out+lRev+","+rRev);*/
}

//CONTROL HARDWARE
void controlHardware(){
  //set left motor
  //set right motor
}

//SIM!
void reqLocSim() {
  start = millis();
  locationUpdateState = 0;
}

void locUpdateSim() {
  long v = millis() - start;
  if (locationUpdateState == 2 && millis() - start > 210) {
    pose.theta = random(0, 360000) / 1000.0;
    locationUpdateState++;
  } else if (locationUpdateState == 1 && millis() - start > 140) {
    pose.y = random(0, 2000) / 1000.0;
    locationUpdateState++;
  } else if (locationUpdateState == 0 && millis() - start > 70) {
    pose.x = random(0, 4000) / 1000.0;
    locationUpdateState++;
  }
}

