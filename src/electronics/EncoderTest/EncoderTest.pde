int XENC1 = 0;
int LED = 13;

char ARDUINO_HANDSHAKE = '@';

void setup ()
{
  Serial.begin (9600);

  /* send handshake to computer */
  Serial.print (ARDUINO_HANDSHAKE);
  
  pinMode (XENC1, INPUT);
  pinMode (LED, OUTPUT);
}

void loop()
{
  int irvalue = analogRead (XENC1);
  Serial.print (millis(), DEC);
  Serial.print ("ms: ");
  Serial.print (irvalue, DEC);
  Serial.println ("/1024 * 5V");
}
