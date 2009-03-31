//developed primarily by P.Lim and B. Garey;
	int RCW = 3; // enable clockwise radius motor control
	int RPWM = 5; // control radius motor power level (PWM)
	int RCCW = 9; // enable counter-clockwise radius motor control
	int XCW = 10;
	int XPWM = 6;
	int XCCW = 11;
	int EM = 13;
	char ARDUINO_HANDSHAKE = '@';

	void setup ()
	{
		Serial.begin (9600);

		/* send handshake to computer */
		Serial.print (ARDUINO_HANDSHAKE);

		pinMode (RCW, OUTPUT);
		pinMode (RCCW, OUTPUT);
		pinMode (RPWM, OUTPUT);
		pinMode (XCW, OUTPUT);
		pinMOde (XCCW, OUTPUT);
		pinMode (XPWM, OUTPUT);
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
                HorizontalLeft (turns, power);
                Serial.print (turns, DEC);
                Serial.print (power, DEC);
			}
			if (val == 2)
			{
				int turns = Serial.read();
                int power = Serial.read();
				HorizontalRight (turns,power);
				Serial.print (turns, DEC);
                Serial.print (power, DEC);
			}
			if (val == 3)//useful?
			{
			    Serial.print (111);   
			}
			if (val == 4)
			{
				int turns = Serial.read();
                int power = Serial.read();
				VerticalIn (turns, power);
				Serial.print (turns, DEC);
                Serial.print (power, DEC);
			}
			if (val == 5)
			{
				int turns = Serial.read();
                int power = Serial.read();
				HorizontalLeft (turns, power);
				VerticalIn (turns, power);
				Serial.print (turns, DEC);
                Serial.print (power, DEC);
			}
			if (val == 6)
			{
				int turns = Serial.read();
                int power = Serial.read();
				HorizontalRight (turns, power);
				VerticalIn (turns, power);
				Serial.print (turns, DEC);
                Serial.print (power, DEC);
			}
			if (val == 7);
			{
				int turns = Serial.read();
                int power = Serial.read();
                Serial.print (turns, DEC);
                Serial.print (power, DEC);
			}
			if (val == 8)
			{	
				int turns = Serial.read();
                int power = Serial.read();
				VerticalOut (turns, power);
				Serial.print (turns, DEC);
                Serial.print (power, DEC);
			}
			if (val == 9)
			{
				int turns = Serial.read();
                int power = Serial.read();
				HorizontalLeft (turns, power);
				VerticalOut (turns, power);
				Serial.print (turns, DEC);
                Serial.print (power, DEC);
			}
			if (val == 10)
			{
				int turns = Serial.read();
                int power = Serial.read();
				HorizontalRight (turns, power);
				VerticalOut (turns, power);
				Serial.print (turns, DEC);
                Serial.print (power, DEC);
			}
			if (val == 11)
			{
				int turns = Serial.read();
                int power = Serial.read();
                Serial.print (turns, DEC);
                Serial.print (power, DEC);
			}	
			if (val == 16)
			{
				
			}	
			if (val == 32)
			{
				
			}	
			if (val == 64)
			{
				
			}	
			if (val >= 128)
			{
				Serial.print ("Stop");
			}		
		}
	}

void VerticalIn (int turns, int power)
	{
		analogWrite (RPWM, power);
		digitalWrite (RCW, HIGH);

                /* this is a totally arbitrary delay time -- in reality, 
                we're going to be reading inputs from the encoder pins, 
                and counting the number of turns that the motor makes. 
                If we don't see the motor turn within a "reasonable" period
                of time, we will abort and send a message to the computer. */
		delay (turns * 100);
		digitalWrite (RCW, LOW);
		digitalWrite (RPWM, LOW);

                /* we need to think about what kind of information that
                we're sending back to the computer. We need to send error
                messages if we fail to turn the motor the desired number of
                turns. We need to send some sort of confirmation/update if
                we did turn the motor the right number of times. Perhaps
                it would be useful for the program to know how long it took
                us to turn the motor that number of turns? */
	}
	void VerticalOut (int power, int duration)
	{
		analogWrite (RPWM, power);
		digitalWrite (RCCW, HIGH);
		delay (turns * 100);
		digitalWrite (RCCW, LOW);
		digitalWrite (RPWM, LOW);
	}
	void HorizontalRight (int power, int duration)
	{
		analogWrite (XPWM, power);
		digitalWrite (XCCW, HIGH);
		delay (turns * 100);
		digitalWrite (XCCW, LOW);
		digitalWrite (XPWM, LOW);
	}
	void HorizontalLeft (int power, int duration)
	{
		analogWrite (XPWM, power);
		digitalWrite (XCW, HIGH);
		delay (turns * 100);
		digitalWrite (XCW, LOW);
		digitalWrite (XPWM, LOW);
	}
	void Stop ()
	{
		digitalWrite (EM, HIGH);
		digitalWrite (XCW, LOW);
		digitalWrite (XCCW, LOW);
		digitalWrite (RCW, LOW);
		digitalWrite (RCCW, LOW);
		digitialWrite (EM, LOW);//?
	}