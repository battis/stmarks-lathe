/* digital I/O pin assignments */
int X1 =	0; // X-axis encoder #1 (input)
int X2 =	1; // X-axis encoder #2 (input)
int Y1 =	2; // Y-axis encoder #1 (input)
int XCW =	3; // X-axis clockwise PWM motor control (output)
int Y2 =	4; // Y-axis encoder #2 (input)
int YCW =	5; // Y-axis clockwise PWM motor control (output)
// int ZCW =	6; // Z-axis clockwise PWM encoder (output)
// int Z1 =	7; // Z-axis encoder #1 (input)
// int Z2 =	8; // Z-axis encoder #2 (input)
int XCCW =	9; // X-axis counter-clockwise PWM motor control (output)
int YCCW =	10; // Y-axis counter-clockwise PWM motor control (output)
// int ZCCW =	11; // Z-axis counter-clockwise PWM motor control (output)
int OP =	12; // Operator feedback LED
int EM =	13; // Emergency drive motor shut-off relay

/* analog input pins */
int XSENS =		0; // X-axis motor sensor
int YSENS =		1; // Y-axis motor sensor
// int ZSENS =		2; // Z-axis motor sensor
int ANLG3 =		3;
int ANLG4 =		4;
int EMSENS =	5; // Emergency drive shut-off sensor

char ARDUINO_HANDSHAKE = '@';

void setup()
{
	Serial.begin(9600);
 
	/* send handshake to computer */
	Serial.print (ARDUINO_HANDSHAKE);

	pinMode (X1, INPUT);
	pinMode (X2, INPUT);
	pinMode (XCW, OUTPUT);
	pinMode (XCCW, OUTPUT);
  
	pinMode (Y1, INPUT);
	pinMode (Y2, INPUT);
	pinMode (YCW, OUTPUT);
	pinMode (YCCW, OUTPUT);
	
	pinMode (OP, OUTPUT);
	pinMode (EM, OUTPUT);
}

void loop()
{
  // body of the program
}
