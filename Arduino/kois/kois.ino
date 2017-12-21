#include <stdint.h>
#include "SparkFunBME280.h"
#include "Wire.h"
#include "SPI.h"
#include <LiquidCrystal.h>  /* LCD kullanabilmek için LiquidCrystal sınıfını import ettik */

LiquidCrystal lcd(8, 9, 4, 5, 6, 7);
BME280 mySensor;

int redLed = 22;
int greenLed = 24;
int yellowLed = 26;
int airSensorA8 = A8;
int flameSensor28 = 28;
int buzzer = 53;
int klimaSoguk = 50;
int klimaSicak =51;
String serialRead ="0";
int sira = 0;
int buzzerPin = 53;

void setup()
{
  lcd.begin(16,2);
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("KOIS");
	mySensor.settings.commInterface = I2C_MODE;
	mySensor.settings.I2CAddress = 0x77;
	mySensor.settings.runMode = 3; //Normal mode
	mySensor.settings.tStandby = 0;
	mySensor.settings.filter = 0;
	mySensor.settings.tempOverSample = 1;
  mySensor.settings.pressOverSample = 1;
	mySensor.settings.humidOverSample = 1;
  pinMode(redLed, OUTPUT);
  pinMode(greenLed, OUTPUT);
  pinMode(yellowLed, OUTPUT);
  pinMode(klimaSoguk, OUTPUT);
  pinMode(klimaSicak, OUTPUT);
  pinMode(airSensorA8, INPUT);
  pinMode(flameSensor28, INPUT);
  pinMode(buzzerPin,OUTPUT);
	Serial.begin(57600);
	delay(10);  //Make sure sensor had enough time to turn on. BME280 requires 2ms to start up.
  mySensor.begin(), HEX;
	uint8_t memCounter = 0x80;
	uint8_t tempReadData;
	
}
void loop()
{
	Serial.print(mySensor.readTempC(), 2);
	//Serial.println(" degrees C");
  Serial.print(";");
	//Serial.print("Temperature: ");
	Serial.print(mySensor.readTempF(), 2);
	//Serial.println(" degrees F");
  Serial.print(";");
	//Serial.print("Pressure: ");
	Serial.print(mySensor.readFloatPressure(), 2);
	//Serial.println(" Pa");
  Serial.print(";");
	//Serial.print("Altitude: ");
	Serial.print(mySensor.readFloatAltitudeMeters(), 2);
	//Serial.println("m");
  Serial.print(";");
	//Serial.print("Altitude: ");
	Serial.print(mySensor.readFloatAltitudeFeet(), 2);
	//Serial.println("ft");	
  Serial.print(";");
	//Serial.print("%RH: ");
	Serial.print(mySensor.readFloatHumidity(), 2);
	//Serial.println(" %");
  Serial.print(";");
  int airSensor = analogRead(airSensorA8);
  int flameSensor = digitalRead(flameSensor28);
  
  //Serial.print("Air Quality : ");
  Serial.print(airSensor);
  Serial.print(";");
  //Serial.print("Flame       : ");
  Serial.print(!flameSensor);
  Serial.print("\n");
  serialRead=Serial.readString();
  //lcd.print(serialRead);

  if(flameSensor == 0)
    tone(buzzerPin,1000,200);
  else
    noTone(buzzerPin);


  lcd.clear();
  lcd.setCursor(0,0);
  switch(sira) {
    case 0:
      lcd.print("SICAKLIK (C)");
      lcd.setCursor(0,1);
      lcd.print(mySensor.readTempC(), 2);
    break;
    case 1:
      lcd.print("SICAKLIK (F)");
      lcd.setCursor(0,1);
      lcd.print(mySensor.readTempF(), 2);
    break;
    case 2:
        if(serialRead == "1"){
        digitalWrite(klimaSicak, HIGH);
        digitalWrite(klimaSoguk, LOW);
        lcd.clear();
        lcd.setCursor(0,0);
        lcd.print("KLIMA SICAK");
        lcd.setCursor(0,1);
        lcd.print("AYARLANDI");
        //serialRead = "0";
        //delay(1000);
      }
      else if(serialRead == "2"){
        digitalWrite(klimaSoguk, HIGH);
        digitalWrite(klimaSicak, LOW);
        lcd.clear();
        lcd.setCursor(0,0);
        lcd.print("KLIMA SOGUK");
        lcd.setCursor(0,1);
        lcd.print("AYARLANDI");
        //serialRead = "0";
        //delay(1000);
      }
    break;
    case 3:
      lcd.print("BASINC (BAR)");
      lcd.setCursor(0,1);
      lcd.print(mySensor.readFloatPressure(), 2);
    break;
    case 4:
      lcd.print("RAKIM (MT)");
      lcd.setCursor(0,1);
      lcd.print(mySensor.readFloatAltitudeMeters(), 2);
    break;
    case 5:
      lcd.print("RAKIM (FT)");
      lcd.setCursor(0,1);
      lcd.print(mySensor.readFloatAltitudeFeet(), 2);
    break;
    case 6:
      lcd.print("NEM (RH)");
      lcd.setCursor(0,1);
      lcd.print(mySensor.readFloatHumidity(), 2);
    break;
    case 7:
      lcd.print("HAVA KALITESI");
      lcd.setCursor(0,1);
      lcd.print(airSensor);
    break;
    case 8:
      lcd.print("YANGIN ");
      //lcd.setCursor(0,1);
      if(flameSensor == 0)
        lcd.print("VAR");
      else
          lcd.print("YOK");
      sira = -1;
    break;
  }
  
  
  if (airSensor > 400)
  {
    digitalWrite(redLed, HIGH);
    digitalWrite(greenLed, LOW);
  }
  else
  {
    digitalWrite(redLed, LOW);
    digitalWrite(greenLed, HIGH);
  }
  if (!flameSensor)
  {
    digitalWrite(yellowLed, HIGH);
  }
  else
  {
    digitalWrite(yellowLed, LOW);
  }
  
  sira++;
	delay(1000);

}
