package app;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Primitives {

	
	

	public int prepareModel(ModelType type, String texFilename) {
		return loadBufferObjects(type, texFilename);
	}

	private int loadBufferObjects(ModelType type, String texture) {
		// generate VAO for model
		int vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		ModelManager.vaoIDs.add(vaoID);

		// load vertex data into VBO, add to VAO
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		ModelManager.vboIDs.add(vboID);

		int iboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		ModelManager.iboIDs.add(iboID);
		
		//buffer vertex data
		glBufferData(GL_ARRAY_BUFFER, loadBuffer(type.getVertices()), GL_STATIC_DRAW);
		// buffer index data
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, loadBuffer(type.getIndices()), GL_STATIC_DRAW);
		//load texture data
		if (!(texture.equals(""))) {
			int tboID = glGenBuffers();
			ModelManager.tboIDs.add(tboID);
			glBufferData(GL_ARRAY_BUFFER, loadBuffer(type.getTextureVertices()), GL_STATIC_DRAW);
		}
		
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0); // define layout of VBO

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

		return vaoID;
		
	}

	//load image data into a byte buffer
	public int loadTexture(String fileName) throws IOException  {
		
		//load image data into byte buffer
		BufferedImage image = ImageIO.read(new File(fileName));
		int width, height;
		width = image.getWidth();
		height = image.getHeight();
		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		
		//store as RGBA
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		for (int y =0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = pixels[x + y * width];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) ((pixel) & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		
		buffer.flip();

		
		//load texture 
		int texture =0;
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		//setup texture parameters
		
		//check if texture is a power of 2
		if (isPow2(width * height)) {
			glGenerateMipmap(GL_TEXTURE_2D);
		} else {
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		}
		
		
		
		
		
		
		return texture;
		
		
	}
	
	private boolean isPow2(int area) {
		return ((area & (area - 1)) == 0) && (area > 1);
	}
	
	private static FloatBuffer loadBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		return buffer;

	}

	private static IntBuffer loadBuffer(int[] data) {
		IntBuffer buffer = IntBuffer.allocate(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;

	}

}
