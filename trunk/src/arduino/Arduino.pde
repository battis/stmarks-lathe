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
				RadiusClockwise (255, 500);
			}
			if (val == 'B')
			{
				RadiusCounterClockwise (255, 500);
			}
		}
	}

	void RadiusClockwise (int power, int duration)
	{
		digitalWrite (RCW, HIGH);
		analogWrite (RPWM, power);
		delay (duration);
		analogWrite (RPWM, 0);
		digitalWrite (RCW, LOW);
	}

	void RadiusCounterClockwise (int power, int duration)
	{
		digitalWrite (RCCW, HIGH);
		analogWrite (RPWM, 255);
		delay (500);
		analogWrite (RPWM, 0);
		digitalWrite (RCCW, LOW);
	}