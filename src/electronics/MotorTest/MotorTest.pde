int REn = 3; // enable clockwise radius motor control - 3 on arduino to pin 1 on h-bridge
int RDir = 5; // control radius motor power level (PWM) - 5 on arduino inverted to pin 2 and actual to 7 on h-bridge
int XEn = 9; // enable counter-clockwise radius motor control - 9 on arduino to pin 9 on h-bridge
int XDir = 10; // 10 on arduino inverted to pin 15 and actual to 10 on h-bridge

int DELAY = 2000; // milliseconds

int ENABLE = HIGH;
int DISABLE = LOW;

int CLOCKWISE = LOW;
int COUNTER_CLOCKWISE = HIGH;        


char ARDUINO_HANDSHAKE = '@';

void setup ()
{
  Serial.begin (9600);

  /* send handshake to computer */
  Serial.print (ARDUINO_HANDSHAKE);

  pinMode (REn, OUTPUT);
  pinMode (XEn, OUTPUT);
  pinMode (RDir, OUTPUT);
  pinMode (XDir, OUTPUT);
}

void loop ()
{
  // body of the program
  // collect input from the encoders -- it will need to store some of
  // this until the next time it surfaces to communicate
  // pass instructions to the gear motors
  // communicate back and forth with the computer

  if (Serial.available ())
  {
    char val = Serial.read ();
    if (val == 'A')
    {
      RadiusClockwise (128, 500);
    }
    if (val == 'B')
    {
      RadiusCounterClockwise (128, 500);
    }
    if (val == 'D')
    {
      XClockwise (128, 500);
    }
    if (val == 'E')
    {
      XCounterClockwise (128, 500);
    }
    if (val == 'G')
    {
      SetBothRunClockwise (128, 500);
    }
    if (val == 'H')
    {
      SetBothRunCounterClockwise (128, 500);
    }
  }

  /* send all pins low */
  for (int i = 0; i < 14; i++)
  {
    digitalWrite (i, LOW);
  }
}

void SetBothZeroPower()
{
  digitalWrite (RDir, CLOCKWISE);
  digitalWrite (XDir, CLOCKWISE);
  digitalWrite (REn, ENABLE);
  digitalWrite (XEn, ENABLE);
  delay (DELAY);
  digitalWrite (REn, DISABLE);
  digitalWrite (XEn, DISABLE);
}

void RadiusClockwise (int power, int duration)
{
  digitalWrite (RDir, CLOCKWISE);
  digitalWrite (REn, ENABLE);
  delay (DELAY);
  digitalWrite (REn, DISABLE);
}

void XClockwise (int power, int duration)
{
  digitalWrite (XDir, CLOCKWISE);
  digitalWrite (XEn, ENABLE);
  delay (DELAY);
  digitalWrite (XEn, DISABLE);
}  

void RadiusCounterClockwise (int power, int duration)
{
  digitalWrite (RDir, COUNTER_CLOCKWISE);
  digitalWrite (REn, ENABLE);
  delay (DELAY);
  digitalWrite (REn, DISABLE);
}

void XCounterClockwise (int power, int duration)
{
  digitalWrite (XDir, COUNTER_CLOCKWISE);
  digitalWrite (XEn, ENABLE);
  delay (DELAY);
  digitalWrite (XEn, DISABLE);
}

void SetBothRunClockwise (int power, int duration)
{
  digitalWrite (RDir, CLOCKWISE);
  digitalWrite (XDir, CLOCKWISE);
  digitalWrite (REn, ENABLE);
  digitalWrite (XEn, ENABLE);
  delay (DELAY);
  digitalWrite (REn, DISABLE);
  digitalWrite (XEn, DISABLE);
}

void SetBothRunCounterClockwise (int power, int duration)
{
  digitalWrite (RDir, COUNTER_CLOCKWISE);
  digitalWrite (XDir, COUNTER_CLOCKWISE);
  digitalWrite (REn, ENABLE);
  digitalWrite (XEn, ENABLE);
  delay (DELAY);
  digitalWrite (REn, DISABLE);
  digitalWrite (XEn, DISABLE);
}
