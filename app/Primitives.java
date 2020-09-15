package app;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import static org.lwjgl.opengl.GL30.*;

public class Primitives {

	private static ArrayList<Integer> vaoIDs = new ArrayList<>();
	private static ArrayList<Integer> vboIDs = new ArrayList<>();

	public static Model loadModel(ModelType type, String textureName) {

		// load VAO
		int vaoID = glGenVertexArrays();
		int textureID = 0;
		glBindVertexArray(vaoID);

		// load data into VBOs
		storeVertexDataInAttributeList(type);
		bindIndicesBuffer(type);
		
		//load texture if specified
		if (!(textureName.equals(""))) {
			try {
				textureID = loadTexture(type, textureName);
			} catch (IOException e) {
				System.err.print(e.getLocalizedMessage());
			}
		}

		// Unbind VAO after using it
		glBindVertexArray(0);
		vaoIDs.add(vaoID);
		return new Model(type, vaoID, textureID);
	}

	public static void storeVertexDataInAttributeList(ModelType type) {
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, loadVBOFloats(type.getVertexData()), GL_STATIC_DRAW);
		// store vertex data in attribute number 0
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		vboIDs.add(vboID);

	}

	public static void bindIndicesBuffer(ModelType type) {
		int vboID = glGenBuffers();
		vboIDs.add(vboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, loadIBOInts(type.getIndexData()), GL_STATIC_DRAW);
		// NEVER UNBIND THE INDEX BUFFER !!!
	}

	public static int loadTexture(ModelType type, String fileName) throws IOException {

		// load image data
		BufferedImage imageBuffer = ImageIO.read(new File("images/" + fileName + ".png"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(imageBuffer, "png", baos);
		byte[] data = baos.toByteArray();
		ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		// load texture objects and setup parameters
		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageBuffer.getWidth(), imageBuffer.getHeight(), 0, GL_RGBA,
				GL_UNSIGNED_BYTE, buffer);

		if (isPowerOf2(imageBuffer.getWidth() * imageBuffer.getHeight())) {
			glGenerateMipmap(GL_TEXTURE_2D);
		} else {
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		}

		// load texture data
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, loadVBOFloats(type.getTextureData()), GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

		return textureID;
		
	}

	private static boolean isPowerOf2(int num) {
		return ((num & (num - 1)) == 1) && (num > 1);
	}

	private static FloatBuffer loadVBOFloats(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private static IntBuffer loadIBOInts(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	// delete all VAOs and VBOs currently in memory
	public static void cleanUp() {
		for (int x : vaoIDs) {
			glDeleteVertexArrays(x);
		}
		for (int y : vboIDs) {
			glDeleteBuffers(y);
		}
	}

}
