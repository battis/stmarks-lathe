	int RCW = 3; // enable clockwise radius motor control
	int RCWPWM = 5; // control radius motor power level (PWM)
	int RCCW = 9; // enable counter-clockwise radius motor control
	int RCCWPWM = 10;

	char ARDUINO_HANDSHAKE = '@';

	void setup ()
	{
		Serial.begin (9600);

		/* send handshake to computer */
		Serial.print (ARDUINO_HANDSHAKE);

		pinMode (RCW, OUTPUT);
		pinMode (RCCW, OUTPUT);
		pinMode (RCWPWM, OUTPUT);
		pinMode (RCCWPWM, OUTPUT);
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
		}
	}

	void RadiusClockwise (int power, int duration)
	{
		digitalWrite (RCW, HIGH);
		for (int i = 0; i < 26; i++ )
		{
			analogWrite (RCWPWM, i * 10);
			delay (50);
		}
		digitalWrite (RCW, LOW);
		digitalWrite (RCWPWM, LOW);
	}

	void RadiusCounterClockwise (int power, int duration)
	{

		digitalWrite (RCCW, HIGH);
		for (int i = 0; i < 26; i++ )
		{
			analogWrite (RCCWPWM, i * 10);
			delay (50);
		}
		digitalWrite (RCCW, LOW);
		digitalWrite (RCCWPWM, LOW);
	}
