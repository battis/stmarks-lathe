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
			if (val == 'A')
			{
				horizontalClockwise (255, 500);
			}
			if (val == 'B')
			{
				horizontalCounterClockwise (255, 500);
			}
			char val = Serial.read ();
			if (val == 'C')
			{
			veritcalClockwise (255,500);
			}
			char val = Serial.read ();
			if (val == 'D')
			{
			verticalCounterClockwise (255,500)
			}
		}
	}

	void RadiusClockwise (int power, int duration)
	{
		analogWrite (RPWM, power);
		digitalWrite (RCW, HIGH);
		delay (duration);
		digitalWrite (RCW, LOW);
		digitalWrite (RPWM, LOW);
	}

	void RadiusCounterClockwise (int power, int duration)
	{
		analogWrite (RPWM, power);
		digitalWrite (RCCW, HIGH);
		delay (500);
		digitalWrite (RCCW, LOW);
		digitalWrite (RPWM, LOW);
	}
