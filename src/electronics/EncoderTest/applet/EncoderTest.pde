int XENC1 = 0;
int LED = 13;

char ARDUINO_HANDSHAKE = '@';

void setup ()
{
  Serial.begin (9600);

  /* send handshake to computer */
  Serial.print (ARDUINO_HANDSHAKE);
  
  pinMode (XENC1, OUTPUT);
  digitalWrite (XENC1, LOW);
  pinMode (XENC1, INPUT);
  pinMode (LED, OUTPUT);
}

void loop()
{
  int irvalue = digitalRead (XENC1);
  Serial.print (millis(), DEC);
  if (irvalue == HIGH)
  {
    digitalWrite (LED, HIGH);
    Serial.println ("ms: read high on input pin");
  }
  else
  {
    digitalWrite (LED, LOW);
    Serial.println ("ms: didn't read diddly on input pin");
  }
}
