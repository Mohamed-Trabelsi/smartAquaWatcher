#include "dht11.h"
#include "PubSubClient.h"
#include <WiFi.h>
#include "OneWire.h"
#include "DallasTemperature.h"
/*const char* ssid = "HUAWEIY92019";                 
const char* wifi_password = "130599040500"; */
const char* ssid = "Airbox-FC4A";                 
const char* wifi_password = "3h3H2n5C"; 
// MQTT
const char* mqtt_server = "broker.emqx.io";  
const char* temperature_topic = "water/temperature";
const char* ph_topic = "water/ph";
const char* humidity_topic = "water/humidity";
const char* turbidity_topic = "water/turbidity";
const char* adress_topic = "macAdress/esp32";
const char* mqtt_username = "pi"; 
const char* mqtt_password = "pi"; 
const char* clientID = "client_livingroom"; 
WiFiClient wifiClient;
PubSubClient client(mqtt_server, 1883, wifiClient); 
unsigned long previousMillis = 0;
const long interval = /*6 * 60 * 60 **/ 10000;
const int critical= 1000*60*30;

void setup() {
  Serial.begin(9600); 
}

float round_to_dp( float in_value, int decimal_place )
{
  float multiplier = powf( 10.0f, decimal_place );
  in_value = roundf( in_value * multiplier ) / multiplier;
  return in_value;
}

void temperature()
{
OneWire oneWire(5);
DallasTemperature sensors(&oneWire);
sensors.requestTemperatures(); 
  Serial.print("Temperature: ");
  float t =sensors.getTempCByIndex(0);
  Serial.print(t);
  Serial.println("C");
    if (client.publish(temperature_topic, String(t).c_str())) {
   Serial.println("Temperature sent!");
  }
  else {
    Serial.println("Temperature failed to send. Reconnecting to MQTT Broker and trying again");
    client.connect(clientID, mqtt_username, mqtt_password);
    delay(10); 
    client.publish(temperature_topic, String(t).c_str());
  }
  
}

void humidite()
{
dht11 DHT11;
int chk = DHT11.read(26);
float humidity = DHT11.humidity;
Serial.print("Humidity (%): ");   
Serial.println(humidity, 2);

if (client.publish(humidity_topic, String(humidity).c_str())) {
    Serial.println("humidity sent!");
  }
  else {
    Serial.println("humidity failed to send. Reconnecting to MQTT Broker and trying again");
    client.connect(clientID, mqtt_username, mqtt_password);
    delay(10); 
    client.publish(humidity_topic, String(humidity).c_str());
  }
}
float phvalue()
{
float b;
int buf[10],temp;
unsigned long int avgValue;
  for(int i=0;i<10;i++)       //Get 10 sample value from the sensor for smooth the value
  { 
    buf[i]=analogRead(35);
    delay(10);
  }
  for(int i=0;i<9;i++)        //sort the analog from small to large
  {
    for(int j=i+1;j<10;j++)
    {
      if(buf[i]>buf[j])
      {
        temp=buf[i];
        buf[i]=buf[j];
        buf[j]=temp;
      }
    }
  }
  avgValue=0;
  for(int i=2;i<8;i++)                      //take the average value of 6 center sample
    avgValue+=buf[i];
  float phValue=(float)avgValue*5.0/1024/6; //convert the analog into millivolt
  phValue=(3.5*phValue)/6.85;                      //convert the millivolt into pH value
  return phValue;
}
void ph()
{
 float phv = phvalue();
   Serial.print("pH:");  
  Serial.println(phv);
  if (client.publish(ph_topic, String(phv).c_str())) {
    Serial.println("PH sent!");   
  }
  else {
    Serial.println("PH failed to send. Reconnecting to MQTT Broker and trying again");
    client.connect(clientID, mqtt_username, mqtt_password);
    delay(10); 
    client.publish(ph_topic, String(phv).c_str());
  }
}

void turbidite()
{
float volt = 0;
float ntu;
    for(int i=0; i<800; i++)
    {
        volt += ((float)analogRead(34)/1023)*5;
    }
    volt = volt/800;
    volt = round_to_dp(volt,2);
    if(volt < 2.5){
      ntu = 3000;
    }else{
      ntu = -1120.4*sq(volt)+5742.3*volt-4353.9; 
    }
Serial.print("turbidity= ");
Serial.println(ntu);
if (client.publish(turbidity_topic, String(ntu).c_str())) {
    Serial.println("turbidity sent!");
  }
else {
    Serial.println("turbidity failed to send. Reconnecting to MQTT Broker and trying again");
    client.connect(clientID, mqtt_username, mqtt_password);
    delay(10); 
    client.publish(turbidity_topic, String(ntu).c_str());
  }
}

void macAdress()
{
  String macAdress = WiFi.macAddress();
  if (client.publish(adress_topic, String(macAdress).c_str())) {
    Serial.println("adress sent!");
    
  }
  else {
    Serial.println("adress mac failed to send. Reconnecting to MQTT Broker and trying again");
    client.connect(clientID, mqtt_username, mqtt_password);
    delay(10); 
    client.publish(adress_topic, String(macAdress).c_str());
  }
}

void connect_MQTT(){
  Serial.print("Connecting to ");
  Serial.println(ssid);

  // Connect to the WiFi
  WiFi.begin(ssid, wifi_password);

  // Wait until the connection has been confirmed before continuing
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("WiFi connected");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

    if (client.connect(clientID, mqtt_username, mqtt_password)) {
    Serial.println("Connected to MQTT Broker!");
  }
  else {
    Serial.println("Connection to MQTT Broker failed...");
  }
}
void loop() {

unsigned long currentMillis = millis();
 if (currentMillis - previousMillis >= interval) {
    previousMillis = currentMillis;
  connect_MQTT();
  macAdress();
  temperature();
  ph(); 
  humidite();
  turbidite();
  client.disconnect();
  delay(5000);
  }
  if ((phvalue()<6.5)&&(phvalue()>9.5))
  {
connect_MQTT();
  macAdress();
  temperature();
  ph(); 
  humidite();
  turbidite();
  client.disconnect();
  }
}
  
