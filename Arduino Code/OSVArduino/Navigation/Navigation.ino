#include "Enes100.h"
#include <dfr_tank.h>


/* Create a new Enes100 object
 * Parameters:
 *  string teamName
 *  int teamType
 *  int markerId
 *  int rxPin
 *  int txPin
 */
Enes100 enes("UrbanTurtle", WATER, 112, 8, 9);
DFRTank tank;
void forward(int power) {
  tank.setLeftMotorPWM(power);
  tank.setRightMotorPWM(power);
}

void backward(int power) {
  tank.setLeftMotorPWM(-power);
  tank.setRightMotorPWM(-power);
}

void left(int power) {
  tank.setLeftMotorPWM(-power);
  tank.setRightMotorPWM(power);
}

void right(int power) {
  tank.setLeftMotorPWM(power);
  tank.setRightMotorPWM(-power);
}

void stopAll() {
  tank.turnOffMotors();
}

void setup() {
    Serial.begin(9600);
    tank.init();

    // Retrieve the destination
    while (!enes.retrieveDestination()) {
        enes.println("Unable to retrieve location");
    }
    pinMode(5,OUTPUT);
    pinMode(6,OUTPUT);
    enes.print("My destination is at ");
    enes.print(enes.destination.x);
    enes.print(",");
    enes.println(enes.destination.y);
}

void loop() {
    // Update the OSV's current location
    /*if (enes.updateLocation()) {
        enes.println("Huzzah! Location updated!");
        enes.print("My x coordinate is ");
        enes.println(enes.location.x);
        enes.print("My y coordinate is ");
        enes.println(enes.location.y);
        enes.print("My theta is ");
        enes.println(enes.location.theta);
        Serial.println("Theta:");
        Serial.println(enes.location.theta);
        Serial.println("Not turning");
        Serial.println(enes.location.x);

        // Keep turning until the OSV faces the rough terrain
        while (abs(enes.location.theta - 6.28) > 0.1 && abs(enes.location.theta) > 0.1) {
          right(255);
          enes.updateLocation();
          Serial.println("Turning");
          Serial.println("Theta:");
          Serial.println(enes.location.theta);
        }

        // Move forward once orientation is determined
        forward(200);
        if (enes.location.x > 1.46) {
          stopAll();
          // Stop once terrain has been crossed
          while (true);
        }
    }
    else {
        enes.println("Sad trombone... I couldn't update my location");
    }*/
    digitalWrite(5,200);
}
