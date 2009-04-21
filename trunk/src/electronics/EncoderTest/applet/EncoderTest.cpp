#include "WProgram.h"
void setup ();
void loop();
int XENC1 = 0;
int LED = 13;

char ARDUINO_HANDSHAKE = '@';

void setup ()
{
  Serial.begin (9600);

  /* send handshake to computer */
  Serial.print (ARDUINO_HANDSHAKE);
  
  pinMode (XENC1, INPUT);
  pinMode (LED, OUTPUT);
}

void loop()
{
  int irvalue = digitalRead (XENC1);
  digitalWrite (LED, irvalue);
  Serial.println (irvalue, DEC);
}

int main(void)
{
	init();

	setup();
    
	for (;;)
		loop();
        
	return 0;
}

