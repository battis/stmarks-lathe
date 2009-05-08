// I/O pin numbers
int REn = 3; // enable clockwise radius motor control - 3 on arduino to pin 1 on h-bridge
int RDir = 5; // control radius motor power level (PWM) - 5 on arduino inverted to pin 2 and actual to 7 on h-bridge
int XEn = 9; // enable counter-clockwise radius motor control - 9 on arduino to pin 9 on h-bridge
int XDir = 10; // 10 on arduino inverted to pin 15 and actual to 10 on h-bridge
int EM = 13;

// voltage levels
int ENABLE = HIGH;
int DISABLE = LOW;
int CLOCKWISE = LOW;
int COUNTER_CLOCKWISE = HIGH;    

//Turn counting
int CHANGE_COUNTER = 0;
int XEn1_Val = 0;
int XEn1_OldVal = 0; 
int REn1_Val = 0;
int REn1_OldVal = 0;     

// Message (msg) bit indices
int msg_XCW = 1;
int msg_XCCW = 2;
int msg_RCW = 4;
int msg_RCCW = 8;
int OK_BIT = 16;
int OK = 3;
int CHICKEN_OUT_WAIT_TIME = 20;
int CHICKEN_OUT_BIT = 32;
int CHICKEN_OUT = 2;
int TIME_OUT_BIT = 64;
int TIME_OUT = 1;
int msg = 0;
bool PARAMS_USED = new bool [10];
int PARAMS = new int [10];
char ARDUINO_HANDSHAKE = '@';

void setup ()
{
  Serial.begin (9600);

  /* send handshake to computer */
  Serial.print (ARDUINO_HANDSHAKE);

  pinMode (REn, OUTPUT);
  pinMode (RDir, OUTPUT);
  pinMode (XEn, OUTPUT);
  pinMOde (XDir, OUTPUT);
  pinMode (EM, OUTPUT);
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
    if (val == 1)
    {
      int turns = Serial.read();
      int power = Serial.read();
      XCW (turns, power);
      Serial.print (turns, DEC);
      Serial.print (power, DEC);
    }
    if (val == 2)
    {
      int turns = Serial.read();
      int power = Serial.read();
      XCCW (turns,power);
      Serial.print (turns, DEC);
      Serial.print (power, DEC);
    }
    if (val == 3)//useful?
    {
      Serial.print (31);   
    }
    if (val == 4)
    {
      int turns = Serial.read();
      int power = Serial.read();
      RCW(turns, power);
      Serial.print (turns, DEC);
      Serial.print (power, DEC);
    }
    if (val == 5)
    {
      int turns = Serial.read();
      int power = Serial.read();
      XCW (turns, power);
      RCW (turns, power);
      Serial.print (turns, DEC);
      Serial.print (power, DEC);
    }
    if (val == 6)
    {
      int turns = Serial.read();
      int power = Serial.read();
      XCCW (turns, power);
      RCW (turns, power);
      Serial.print (turns, DEC);
      Serial.print (power, DEC);
    }
    if (val == 7);
    {
      Serial.print (31);   
    }
    if (val == 8)
    {	
      int turns = Serial.read();
      int power = Serial.read();
      RCCW (turns, power);
      Serial.print (turns, DEC);
      Serial.print (power, DEC);
    }
    if (val == 9)
    {
      int turns = Serial.read();
      int power = Serial.read();
      XCW (turns, power);
      RCCW (turns, power);
      Serial.print (turns, DEC);
      Serial.print (power, DEC);
    }
    if (val == 10)
    {
      int turns = Serial.read();
      int power = Serial.read();
      XCCW (turns, power);
      RCCW (turns, power);
      Serial.print (turns, DEC);
      Serial.print (power, DEC);
    }
    if (val == 11)
    {
      Serial.print (31);
    }	

    if (val >= 128)
    {
      Serial.print ("Stop");
    }	
    Serial.println (msg);
    for (int i = 0; i < 10; i ++)
    {
      Serial.write (PARAMS[i]);
      PARAMS_USED [i] = false;
    }							
  }
}

void RCW (int turns, int reqTurns)
{
  analogWrite (RDir, CLOCKWISE);
  int time = millis();
  digitalWrite (REn, ENABLE);
  while (turns != reqTurns && PARAMS_USED[CHICKEN_OUT] != true)
  {
    int elapsed = time-millis();
    if (elapsed > CHICKEN_OUT_WAIT_TIME)
    {
      digitalWrite (REn, DISABLE);
      //PARAMS[CHICKEN_OUT] = elapsed;
      PARAMS_USED[CHICKEN_OUT];
      msg += CHICKEN_OUT_BIT;
    }
    else
    {
      REn1_OldVal = REn1_Val;
      REn1_Val = digitalRead (REn1);
      if (REn1_Val ! = Ren _OldVal)
      {
        change_counter ++;
      } 
      if (change_counter == 4)
      {	
        turns ++;
        change_counter == 0;
      }  //
    }
    digitalWrite (REn, DISABLE);

    if (!PARAMS_USED[TIME_OUT]||!PARAMS_USED[CHICKEN_OUT])
    {
      //PARAMS[OK] = elapsed;
      PARAMS_USED[OK];
      msg+= OK_BIT;
    }

  }
}

/* this is a totally arbitrary delay time -- in reality, 
 we're going to be reading inputs from the encoder pins, 
 and counting the number of turns that the motor makes. 
 If we don't see the motor turn within a "reasonable" period
 of time, we will abort and send a message to the computer. 
 delay (turns * 100);*/

/* we need to think about what kind of information that
 we're sending back to the computer. We need to send error
 messages if we fail to turn the motor the desired number of
 turns. We need to send some sort of confirmation/update if
 we did turn the motor the right number of times. Perhaps
 it would be useful for the program to know how long it took
 us to turn the motor that number of turns? */

void RCCW (int power, int reqTurns)
{
  analogWrite (RDir, COUNTER_CLOCKWISE);
  int time = millis();
  digitalWrite (REn, ENABLE);
  while (turns != reqTurns && PARAMS_USED[CHICKEN_OUT] != true)
  {
    int elapsed = time-millis();
    if (elapsed > CHICKEN_OUT_WAIT_TIME)
    {
      digitalWrite (REn, DISABLE);
      PARAMS[CHICKEN_OUT] = elapsed;
      PARAMS_USED[CHICKEN_OUT];
      msg += CHICKEN_OUT_BIT;
    }
    digitalWrite (REn, DISABLE);
  }
  if (!PARAMS_USED[TIME_OUT]||!PARAMS_USED[CHICKEN_OUT])
  {
    PARAMS[OK] = elapsed;
    PARAMS_USED[OK];
    msg += OK_BIT;
  }
}

void XCCW(int power, int reqTurns)
{
  analogWrite (XDir, COUNTER_CLOCKWISE);
  int time = millis();
  digitalWrite (XEn, ENABLE);
  while (turns != reqTurns && PARAMS_USED[CHICKEN_OUT] != true)
  {
    int elapsed = time-millis();
    if (elapsed > CHICKEN_OUT_WAIT_TIME)
    {
      digitalWrite (XEn, DISABLE);
      PARAMS[CHICKEN_OUT] = elapsed;
      PARAMS_USED[CHICKEN_OUT];
      msg += CHICKEN_OUT_BIT;
    }
    digitalWrite (XEn, DISABLE);
  }
  if (!PARAMS_USED[TIME_OUT]||!PARAMS_USED[CHICKEN_OUT])
  {
    PARAMS[OK] = elapsed;
    PARAMS_USED[OK];
    msg+= OK_BIT;
  }
}

void XCW (int power, int reqTurns)// "model" motor controller -- awaiting encoder goodness
{
  analogWrite (XDir, reqTurns);
  int time = millis();
  digitalWrite (XEn, ENABLE);
  while (turns != reqTurns && PARAMS_USED[CHICKEN_OUT] != true)
  {
    int elapsed = time-millis();
    if (elapsed > CHICKEN_OUT_WAIT_TIME)
    {
      digitalWrite (XEn, DISABLE);
      PARAMS[CHICKEN_OUT] = elapsed;
      PARAMS_USED[CHICKEN_OUT];
      msg += CHICKEN_OUT_BIT;
    }
    digitalWrite (XEn, DISABLE);
  }
  if (!PARAMS_USED[TIME_OUT]||!PARAMS_USED[CHICKEN_OUT])
  {
    PARAMS[OK] = elapsed;
    PARAMS_USED[OK];
    msg+= OK_BIT;
  }
}

void Stop ()
{
  digitalWrite (EM, ENABLE);
  digitalWrite (XEn, DISABLE);
  digitalWrite (REn, DISABLE);
  digitalWrite (EM, DISABLE);//?
}
