package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import app.FrameworkProperties;
import app.Window;

public class TextGUI {

	String text = "";
	static final int FONT_COLS = 8; // columns in font file texture
	static final int FONT_ROWS = 12;
	static final String fileName = "Font";
	static final int VERTICES_PER_QUAD = 4;
	static final int CHAR_SPACE = 128;
	private static FrameworkProperties fp = FrameworkProperties.getProperties();

	GUIModel model;
	
	public void addText(String text) {
		
		//get image width and height
		// load image data
		this.text = text;
		BufferedImage imageBuffer = null;
		try {
			imageBuffer = ImageIO.read(new File("fonts/" + fileName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//generate mesh
		model = buildTextModel( imageBuffer.getWidth(), imageBuffer.getHeight());
	}
	
	public GUIModel getModel() {
		return model;
	}

	public GUIModel buildTextModel(int width, int height) {
		
		//load text data
		byte[] chars = text.getBytes(Charset.forName("UTF-8"));
		int numChars = chars.length;
		
		List<Float> vertexCoords = new ArrayList<>();
		List<Float> textureCoords = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();
		
		float tileWidth = 2f * ((float) width / (float)FONT_COLS) / fp.getWidth();
		float tileHeight = 2f * ((float) height / (float)FONT_ROWS) / fp.getHeight();
		
		//create textured quad, assigning texture coords to specific character at specific column/row
		System.out.println(tileWidth);
		
		for (int i=0; i<numChars; i++) {
			//each character needs 4 vertices, 4 texture coords and index data
			
			byte currChar = chars[i];
			//System.out.println(currChar);

			System.out.println("Char:  " + currChar);
			
			int col = (int) ((currChar - 33) % FONT_COLS);
			int row =  (int) (Math.floor((currChar -33) / (float) FONT_COLS));

			System.out.println("Col: " + (float) col );
			System.out.println("Row: " + (float) row );

			// Left Top vertex
			vertexCoords.add((float)i*tileWidth); // x
			vertexCoords.add(0.0f); //y
			vertexCoords.add(0.0f); //z
		    textureCoords.add((float) col / (float) FONT_COLS);
		    textureCoords.add((float) row / (float) FONT_ROWS);
		    indices.add(i*VERTICES_PER_QUAD);

		    // Left Bottom vertex
		    vertexCoords.add((float)i*tileWidth); // x
		    vertexCoords.add(-tileHeight); //y
		    vertexCoords.add(0.0f); //z
		    textureCoords.add((float) col / (float) FONT_COLS);
		    textureCoords.add((float)(row + 1) / (float)FONT_ROWS );
		    indices.add(i*VERTICES_PER_QUAD + 1);

		    // Right Bottom vertex
		    vertexCoords.add((float)i*tileWidth + tileWidth); // x
		    vertexCoords.add(-tileHeight); //y
			vertexCoords.add(0.0f); //z
		    textureCoords.add((float)(col + 1)/ (float)FONT_COLS );
		    textureCoords.add((float)(row + 1) / (float)FONT_ROWS );
		    indices.add(i*VERTICES_PER_QUAD + 2);

		    // Right Top vertex
		    vertexCoords.add((float)i*tileWidth + tileWidth); // x
		    vertexCoords.add(0.0f); //y
			vertexCoords.add(0.0f); //z
		    textureCoords.add((float)(col + 1)/ (float)FONT_COLS );
		    textureCoords.add((float) row / (float) FONT_ROWS);
		    indices.add(i*VERTICES_PER_QUAD + 3);

		    // Add indices por left top and bottom right vertices
		    indices.add(i*VERTICES_PER_QUAD);
		    indices.add(i*VERTICES_PER_QUAD + 2);
			
		    
		}
		float[] vertices = new float[vertexCoords.size()];
		float[] textures = new float[textureCoords.size()];
		int[] index = new int[indices.size()];
		

		for(int x = 0; x< vertexCoords.size(); x++) {
			vertices[x] = vertexCoords.get(x);
		}
		
		for(int x = 0; x< textureCoords.size(); x++) {
			if (Float.isInfinite(textureCoords.get(x))) {
				textures[x] = 0.0f;
			} else {
				textures[x] = textureCoords.get(x);
			}
		}
		
		for(int x = 0; x< indices.size(); x++) {
			index[x] = indices.get(x);
		}
		
		
		return GUITexturePrimitive.generateModel(fileName, vertices, textures, index);
	}
}
