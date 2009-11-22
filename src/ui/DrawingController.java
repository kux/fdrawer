package ui;

import java.util.List;

public interface DrawingController {

	void drawFunctions(List<String> functions);

	void zoomIn(double percentage);

	void zoomOut(double percentage);
	
	void move();

}
