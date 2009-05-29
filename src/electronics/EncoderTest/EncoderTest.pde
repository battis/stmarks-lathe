int XENC1 = 5;
int XENC2 = 6;

char ARDUINO_HANDSHAKE = '@';

void setup ()
{
  Serial.begin (9600);

  /* send handshake to computer */
  Serial.print (ARDUINO_HANDSHAKE);
  
  pinMode (XENC1, INPUT);
  pinMode (XENC2, INPUT);
}

void loop()
{
  Serial.print (digitalRead (XENC1), DEC);
  Serial.println (digitalRead (XENC2), DEC);
}
