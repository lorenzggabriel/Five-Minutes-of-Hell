package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// This class is named UtilityTool and is part of the "main" package.
public class UtilityTool {

	// This method takes an original BufferedImage, along with width and height
	// parameters, and returns a scaled version of the original image with the
	// specified dimensions.
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {

		// Create a new BufferedImage with the specified width, height, and the same
		// image type as the original.
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());

		// Create a Graphics2D object to draw on the scaledImage.
		Graphics2D g2 = scaledImage.createGraphics();

		// Draw the original image onto the scaledImage, scaling it to the specified
		// width and height.
		g2.drawImage(original, 0, 0, width, height, null);

		// Dispose of the Graphics2D object to free up system resources.
		g2.dispose();

		// Return the scaledImage.
		return scaledImage;
	}
}
