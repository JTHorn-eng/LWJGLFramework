package gui;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import app.Primitives;

public class GUITexturePrimitive {

	float width;
	float height;
	private static final int BYTES_PER_PIXEL = 4;

	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}

	public static GUIModel generateModel(String fileName, float[] vertices, float[] textureCoords, int[] indices) {

		int vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		// store vertices
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, loadVBO(vertices), GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		// store indices
		int iboID = glGenBuffers();
		Primitives.getVBOIDs().add(iboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, loadIBOInts(indices), GL_STATIC_DRAW);

		

		int textureID = processImageData(fileName, textureCoords);
		glBindVertexArray(0);
		
		Primitives.getVAOIDs().add(vaoID);
		Primitives.getVBOIDs().add(vboID);
		return new GUIModel(vertices, textureCoords, indices, vaoID, textureID);
	}

	public static int processImageData(String fileName, float[] textureCoords) {
		// load image data
		BufferedImage imageBuffer = null;
		try {
			imageBuffer = ImageIO.read(new File("fonts/" + fileName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int[] pixels = new int[imageBuffer.getWidth() * imageBuffer.getHeight()];
		imageBuffer.getRGB(0, 0, imageBuffer.getWidth(), imageBuffer.getHeight(), pixels, 0, imageBuffer.getWidth());

		ByteBuffer buffer = BufferUtils
				.createByteBuffer(imageBuffer.getWidth() * imageBuffer.getHeight() * BYTES_PER_PIXEL);

		for (int y = 0; y < imageBuffer.getHeight(); y++) {
			for (int x = 0; x < imageBuffer.getWidth(); x++) {
				int pixel = pixels[y * imageBuffer.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // RED VALUES
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // GREEN VALUES
				buffer.put((byte) (pixel & 0xFF)); // BLUE VALUES
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA VALUES

			}
		}

		buffer.flip();

		// load texture objects and setup parameters
		int textureId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureId);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageBuffer.getWidth(), imageBuffer.getHeight(), 0, GL_RGBA,
				GL_UNSIGNED_BYTE, buffer);

		glGenerateMipmap(GL_TEXTURE_2D); // generate low-res textures for textured object scaling

		// setup texture parameters, interpolate image data where pixel data
		// doesn't align with texture coordinates when scaling
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		// clamp texture to border of primitive
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

		// load texture data
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, loadVBO(textureCoords), GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return textureId;
	}

	static FloatBuffer loadVBO(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;

	}

	static IntBuffer loadIBOInts(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}
