package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

public class TextGUI {

	String text = "";
	static final int FONT_COLS = 8; // columns in font file texture atlas
	static final int FONT_ROWS = 8;
	static final String fileName = "Font";
	GUIModel model;
	
	public void addText(String text) {
		
		//get image width and height
		// load image data
		BufferedImage imageBuffer = null;
		try {
			imageBuffer = ImageIO.read(new File("fonts/" + fileName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//generate mesh
		model = buildTextModel(FONT_COLS, FONT_ROWS, imageBuffer.getWidth(), imageBuffer.getHeight());
	}
	
	public GUIModel getModel() {
		return model;
	}

	public GUIModel buildTextModel(int cols, int rows, int width, int height) {
		
		//load text data
		byte[] chars = text.getBytes(Charset.forName("UTF-8"));
		int numChars = chars.length;
		
		
		float[] vertexCoords = new float[numChars * 12];
		float[] textureCoords = new float[numChars * 8];
		int[] indices = new int[numChars * 6];
		
		float tileWidth = (float) width / (float)cols;
		float tileHeight = (float) height / (float)rows;
		
		//create textured quad, assigning texture coords to specific character at specific column/row
		
		
		
		for (int i=0; i<numChars; i++) {
			//each character needs 4 vertices, 4 texture coords and index data
			
			byte currChar = chars[i];
			int col = currChar % cols;
			int row = currChar / cols;
			
			//order - top left, bottom right, 
			//process vertices
			int[] textureOffsets = {0, 0, 0, 1, 1, 1, 1, 0};
			
			for (int j = 0; j < 4; j++) {
				vertexCoords[i + j] = (i * tileWidth + (j % 2) * tileWidth);//  x coord
				vertexCoords[i + j + 1] = ((j%2) * tileHeight); //y coord
				vertexCoords[i + j + 2] = (0.0f); //z coord	
				textureCoords[i + j] = ((float) (col + textureOffsets[j * 2]) / (float) cols); // texX
				textureCoords[i + j + 1] = ((float) (row + textureOffsets[j * 2 + 1]) / (float) rows); //texY
				indices[i + j] = (i * 4 + j ); // 4 vertices per quad plus current vertex
			}
		
			//add remaining index data
			indices[i + 4] = (i * 4 );
			indices[i + 5] = (i* 4 + 2);
			
		}
		return GUITexturePrimitive.generateModel(fileName, vertexCoords, textureCoords, indices);
		
	}
	
	
	
	
}
