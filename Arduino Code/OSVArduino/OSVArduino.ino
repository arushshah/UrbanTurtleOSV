void setup() {
  // put your setup code here, to run once:
Serial.begin(9600);
Serial.setTimeout(5);
}
byte val[4];
void loop() {    
  if(Serial.findUntil("FLOAT","\n")){
      String s = Serial.readStringUntil("\n");
      float f = s.toFloat();
      String msg = "f: ";
      Serial.println(msg+f);
  }
}
