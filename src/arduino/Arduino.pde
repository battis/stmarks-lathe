	int RCW = 3; // enable clockwise radius motor control
	int RPWM = 5; // control radius motor power level (PWM)
	int RCCW = 9; // enable counter-clockwise radius motor control

	char ARDUINO_HANDSHAKE = '@';

	void setup ()
	{
		Serial.begin (9600);

		/* send handshake to computer */
		Serial.print (ARDUINO_HANDSHAKE);

		pinMode (RCW, OUTPUT);
		pinMode (RCCW, OUTPUT);
		pinMode (RPWM, OUTPUT);
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
			if (val == '1')
			{
                                int turns = Serial.read();
                                int power = Serial.read();
				VerticalIn (turns, power);
			}
			if (val == '2')
			{
				VerticalOut (255, 500);
			}
			if (val == '3')
			{
			HorizontalRight (255,500);
			}
			if (val == '4')
			{
			HorizontalLeft (255,500)
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
		delay (500);
		digitalWrite (RCCW, LOW);
		digitalWrite (RPWM, LOW);
	}
	void HorizontalRight (int power, int duration)
	{
		analogWrite (RPWM, power);
		digitalWrite (RCCW, HIGH);
		delay (500);
		digitalWrite (RCCW, LOW);
		digitalWrite (RPWM, LOW);
	}
	void HorizontalLeft (int power, int duration)
		{
		analogWrite (RPWM, power);
		digitalWrite (RCCW, HIGH);
		delay (500);
		digitalWrite (RCCW, LOW);
		digitalWrite (RPWM, LOW);
	}
