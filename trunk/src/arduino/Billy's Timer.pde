void setup()
{
  Serial.begin(9600);
  Serial.print (turns, DEC);
  Serial.print (power, DEC);
}

void loop ()
{
  Serial.print("Time: ");
  time = millis();
  //prints time since program started
  Serial.println(time);
  delay(1000);
  int elapsed = time-millis();
  if (elapsed > 20)
  {
    Serial.print(elapsed);
    
digitalWrite (XEn, DISABLE);

  }
}