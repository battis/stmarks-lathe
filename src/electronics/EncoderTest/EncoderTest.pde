char ARDUINO_HANDSHAKE = '@';

void setup ()
{
  Serial.begin (9600);

  /* send handshake to computer */
  Serial.print (ARDUINO_HANDSHAKE);
  
  pinMode (13, OUTPUT);
}

void loop()
{
  int irvalue = digitalRead (0);
  if (irvalue == HIGH)
  {
    digitalWrite (13, HIGH);
    Serial.println (millis() + " read high on input pin");
  }
  else
  {
    digitalWrite (13, LOW);
    Serial.println (millis() + " didn't read diddly on input pin");
  }
}