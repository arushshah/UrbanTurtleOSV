float x,y,theta;
void setup() {
  // put your setup code here, to run once:
Serial.begin(9600);
}
void loop() {
  handleIncomingMessages();
  /*if(Serial.findUntil("FLOAT","\n")){
      String s = Serial.readStringUntil("\n");
      float f = s.toFloat();
      String msg = "f: ";
      Serial.println(msg+f);
  }*/
}

void handleIncomingMessages(){
  String s = Serial.readStringUntil("\n");
  if(s.startsWith("POS")){
    sendPosition();
  }
}

void sendPosition(){
  String flag = "POS";
  Serial.println(flag+x+":"+y+":"+theta);
}

