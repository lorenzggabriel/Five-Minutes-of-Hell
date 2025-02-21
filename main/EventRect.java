package main;

import java.awt.Rectangle;

// Declaration of the EventRect class, which extends the Rectangle class
public class EventRect extends Rectangle {
	// Fields to store the default X and Y coordinates of the event rectangle
	int eventRectDefaultX, eventRectDefaultY;
	boolean eventDone = false; // Boolean flag to indicate whether the event associated with the rectangle is
								// done
}
