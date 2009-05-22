// I/O pin numbers
int REn1 = 3; // enable clockwise radius motor control - 3 on arduino to pin 1 on h-bridge
int RDir = 5; // control radius motor power level (PWM) - 5 on arduino inverted to pin 2 and actual to 7 on h-bridge
int XEn1 = 9; // enable counter-clockwise radius motor control - 9 on arduino to pin 9 on h-bridge
int XDir = 10; // 10 on arduino inverted to pin 15 and actual to 10 on h-bridge
int EM = 13;

// voltage levels
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
bool PARAMS_USED[10];
int PARAMS[10];
char ARDUINO_HANDSHAKE = '@';


void setup ()
{
  Serial.begin (9600);

  /* send handshake to computer */
  Serial.print (ARDUINO_HANDSHAKE);

  pinMode (REn1, OUTPUT);
  pinMode (RDir, OUTPUT);
  pinMode (XEn1, OUTPUT);
  pinMode (XDir, OUTPUT);
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
    if (val == 1)//commands - when # received from computer it calls the move command that corresponds to the #
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

    if (val >= 128)//if the input # is too great or the user wishes to end the command, it ends the program
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

void RCW (int reqTurns, int powerLevel)//right clockwise command - directional for lathe
{
  int turns = 0;  
  analogWrite (RDir, CLOCKWISE);
  int time = millis();
  analogWrite (REn1, powerLevel);
  while (turns != reqTurns && PARAMS_USED[CHICKEN_OUT] != true)
  {
    int elapsed = time-millis();
    if (elapsed > CHICKEN_OUT_WAIT_TIME)//in case the process is taking too much time, we disable it
    {
      digitalWrite (REn1, DISABLE);
      PARAMS[CHICKEN_OUT] = elapsed;
      PARAMS_USED[CHICKEN_OUT];
      msg += CHICKEN_OUT_BIT;//message to tell that we have chickened out and stopped
    }
    else
    {
      REn1_OldVal = REn1_Val;
      REn1_Val = digitalRead (REn1);
      if (REn1_Val != REn1_OldVal)
      {
        CHANGE_COUNTER ++;
      } 
      if (CHANGE_COUNTER == 4)
      {	
        turns ++;
        CHANGE_COUNTER == 0;
      }  
    }
  }
  digitalWrite (REn1, DISABLE);


  if (!PARAMS_USED[TIME_OUT]||!PARAMS_USED[CHICKEN_OUT])//has the computer check for problems and if there are no problems then run it
  {
    //PARAMS[OK] = elapsed;
    PARAMS_USED[OK];
    msg+= OK_BIT;
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

void RCCW (int reqTurns, int powerLevel)
{
  int turns = 0;
  int elapsed;
  analogWrite (RDir, COUNTER_CLOCKWISE);
  int time = millis();
  analogWrite (REn1, powerLevel);
  while (turns != reqTurns && PARAMS_USED[CHICKEN_OUT] != true)
  {
    elapsed = time-millis();

    if (elapsed > CHICKEN_OUT_WAIT_TIME)
    {
      digitalWrite (REn1, DISABLE);
      PARAMS[CHICKEN_OUT] = elapsed;
      PARAMS_USED[CHICKEN_OUT];
      msg += CHICKEN_OUT_BIT;
    }
    digitalWrite (REn1, DISABLE);
  }
  if (!PARAMS_USED[TIME_OUT]||!PARAMS_USED[CHICKEN_OUT])
  {
    PARAMS[OK] = elapsed;
    PARAMS_USED[OK];
    msg += OK_BIT;
  }
  else
  {
    REn1_OldVal = REn1_Val;
    REn1_Val = digitalRead (REn1);
    if (REn1_Val != REn1_OldVal)
    {
      CHANGE_COUNTER ++;
    } 
    if (CHANGE_COUNTER == 4)
    {	
      turns ++;
      CHANGE_COUNTER == 0;
    }  
  }
  digitalWrite (REn1, DISABLE);
  if (!PARAMS_USED[TIME_OUT]||!PARAMS_USED[CHICKEN_OUT])
{
  //PARAMS[OK] = elapsed;
  PARAMS_USED[OK];
  msg+= OK_BIT;
}
}




void XCCW(int reqTurns, int powerLevel)
{
  int turns = 0;
  int elapsed;
  analogWrite (XDir, COUNTER_CLOCKWISE);
  int time = millis();
  analogWrite (XEn1, powerLevel);	
  while (turns != reqTurns && PARAMS_USED[CHICKEN_OUT] != true)
  {
   elapsed = time-millis();
    if (elapsed > CHICKEN_OUT_WAIT_TIME)
    {
      digitalWrite (XEn1, DISABLE);
      PARAMS[CHICKEN_OUT] = elapsed;
      PARAMS_USED[CHICKEN_OUT];
      msg += CHICKEN_OUT_BIT;
    }
    digitalWrite (XEn1, DISABLE);
  }
  if (!PARAMS_USED[TIME_OUT]||!PARAMS_USED[CHICKEN_OUT])
  {
    PARAMS[OK] = elapsed;
    PARAMS_USED[OK];
    msg+= OK_BIT;
  }
  else
  {
    XEn1_OldVal = XEn1_Val;
    XEn1_Val = digitalRead (XEn1);
    if (XEn1_Val != XEn1_OldVal)
    {
      CHANGE_COUNTER ++;
    } 
    if (CHANGE_COUNTER == 4)
    {	
      turns ++;
      CHANGE_COUNTER == 0;
    }  
  }
  digitalWrite (XEn1, DISABLE);

  if (!PARAMS_USED[TIME_OUT]||!PARAMS_USED[CHICKEN_OUT])
  {
    //PARAMS[OK] = elapsed;
    PARAMS_USED[OK];
    msg+= OK_BIT;
  }

}



void XCW (int reqTurns, int powerLevel)// "model" motor controller -- awaiting encoder goodness
{
  int turns = 0;
  int elapsed;
  analogWrite (XDir, CLOCKWISE);
  int time = millis();
  analogWrite (XEn1, powerLevel);
  while (turns != reqTurns && PARAMS_USED[CHICKEN_OUT] != true)
  {
    elapsed = time-millis();
    if (elapsed > CHICKEN_OUT_WAIT_TIME)
    {
      digitalWrite (XEn1, DISABLE);
      PARAMS[CHICKEN_OUT] = elapsed;
      PARAMS_USED[CHICKEN_OUT];
      msg += CHICKEN_OUT_BIT;
    }
    else
    {
      XEn1_OldVal = XEn1_Val;
      XEn1_Val = digitalRead (REn1);
      if (XEn1_Val != XEn1_OldVal)
      {
        CHANGE_COUNTER ++;
      } 
      if (CHANGE_COUNTER == 4)
      {	
        turns ++;
        CHANGE_COUNTER == 0;
      }  
    }
  }
  digitalWrite (XEn1, DISABLE);

  if (!PARAMS_USED[TIME_OUT]||!PARAMS_USED[CHICKEN_OUT])
  {
    PARAMS[OK] = elapsed;
    PARAMS_USED[OK];
    msg+= OK_BIT;
  }

}

void Stop ()
{
  digitalWrite (EM, HIGH);
  digitalWrite (XEn1, DISABLE);
  digitalWrite (REn1, DISABLE);
  digitalWrite (EM, DISABLE);//?
}
