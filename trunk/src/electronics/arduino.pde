	int REn = 3; // enable clockwise radius motor control - 3 on arduino to pin 1 on h-bridge
	int RDir = 5; // control radius motor power level (PWM) - 5 on arduino inverted to pin 2 and actual to 7 on h-bridge
	int XEn = 9; // enable counter-clockwise radius motor control - 9 on arduino to pin 9 on h-bridge
	int XDir = 10; // 10 on arduino inverted to pin 15 and actual to 10 on h-bridge

        
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
				RadiusClockwiseR (128, 500);
			}
			if (val == 'B')
			{
				RadiusCounterClockwiseR (128, 500);
			}
			if (val == 'C')
			{
				TestZeroPowerR();
			}
                        if (val == 'D')
                        {
                                RadiusClockwiseX (128, 500);
                        }
                        if (val == 'E')
			{
				RadiusCounterClockwiseX (128, 500);
			}
                        if (val == 'F')
			{
				TestZeroPowerX();
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
	
	void TestZeroPowerR()
	{
		digitalWrite (REn, HIGH);
		analogWrite (RDir, 0);
		delay (1000);
		digitalWrite (REn, LOW);
		digitalWrite (RDir, LOW);
	}
        void TestZeroPowerX()
        {
                digitalWrite (XEn, HIGH);
                analogWrite (XDir, 0);
                delay (1000);
                digitalWrite (XEn, LOW);
                digitalWrite (XDir, LOW);
        }
	void RadiusClockwiseR (int power, int duration)
	{
		digitalWrite (REn, HIGH);
		/*for (int i = 0; i < 5; i++ )
		{
			analogWrite (RCWPWM, i * 50);
			delay (500);
		}*/
                digitalWrite (RDir, LOW);
                delay (4000);
                digitalWrite (REn, LOW);
		digitalWrite (RDir, HIGH);
	}

        void RadiusClockwiseX (int power, int duration)
        {
                digitalWrite (XEn, HIGH);
                //see commented out area in above method, add or no?
                digitalWrite (XDir, LOW);
                delay (4000);
                digitalWrite (XEn, LOW);
                digitalWrite (XDir, HIGH);
        }  
	void RadiusCounterClockwiseR (int power, int duration)
	{
                digitalWrite (REn, HIGH);
		/*for (int i = 0; i < 5; i++ )
		{
			analogWrite (RCWPWM, i * 50);
			delay (500);
		}*/
                digitalWrite (RDir, HIGH);
                delay (4000);
                digitalWrite (REn, LOW);
		digitalWrite (RDir, HIGH);
		/*digitalWrite (XEn, HIGH);
		for (int i = 0; i < 5; i++ )
		{
			analogWrite (XDir, i * 50);
			delay (500);
		}
		digitalWrite (XEn, LOW);
		digitalWrite (XDir, LOW);*/
	}

        void RadiusCounterClockwiseX (int power, int duration)
        {
                digitalWrite (XEn, HIGH);
                //see commented out area in above method, add or no?
                digitalWrite (XDir, HIGH);
                delay (4000);
                digitalWrite (XEn, LOW);
                digitalWrite (XDir, HIGH);
                //see commented out area in above method, add or no?
        }
        
        void SetBothRunClockwise (int power, int duration)
        {
                digitalWrite (REn, HIGH);
                digitalWrite (XEn, HIGH);
                digitalWrite (RDir, LOW);
                digitalWrite (XDir, LOW);
                delay (4000);
                digitalWrite (REn, LOW);
                digitalWrite (XEn, LOW);
                digitalWrite (RDir, HIGH);
                digitalWrite (XDir, HIGH);
        }
        
        void SetBothRunCounterClockwise (int power, int duration)
        {
                digitalWrite (REn, HIGH);
                digitalWrite (XEn, HIGH);
                digitalWrite (RDir, HIGH);
                digitalWrite (XDir, HIGH);
                delay (4000);
                digitalWrite (REn, LOW);
                digitalWrite (XEn, LOW);
		digitalWrite (RDir, HIGH);
                digitalWrite (XDir, HIGH);
        }

