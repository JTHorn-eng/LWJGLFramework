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

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import entities.EntityManager;
import entities.Line;
import entities.Model;
import entities.ModelData;

import static org.lwjgl.opengl.GL30.*;

public class Primitives {

	private static ArrayList<Integer> vaoIDs = new ArrayList<>();
	private static ArrayList<Integer> vboIDs = new ArrayList<>();
	private static int lineVAOID, lineVBOID;

	private static final int BYTES_PER_PIXEL = 4;

	public static int getLineVAOID() {
		return lineVAOID;
	}

	public static void loadLines() {
		lineVAOID = glGenVertexArrays();
		lineVBOID = glGenBuffers();
		glBindVertexArray(lineVAOID);

		glBindBuffer(GL_ARRAY_BUFFER, lineVBOID);
		int size = EntityManager.getLines().values().size();

		float[] fVertices = new float[size * 6];
		ArrayList<Float> vertices = new ArrayList<>();
		for (Line line : EntityManager.getLines().values()) {
			vertices.add(line.getStart().x);
			vertices.add(line.getStart().y);
			vertices.add(line.getStart().z);
			vertices.add(line.getEnd().x);
			vertices.add(line.getEnd().y);
			vertices.add(line.getEnd().z);

		}
		for (int x = 0; x < size * 6; x++) {
			fVertices[x] = vertices.get(x);
		}
		glBufferData(GL_ARRAY_BUFFER, loadVBOFloats(fVertices), GL_STATIC_DRAW);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glBindVertexArray(0);
		vboIDs.add(lineVBOID);
		vaoIDs.add(lineVAOID);

	}

	public static Model loadModel(ModelType type, String textureName) {
		ModelData data = new ModelData();

		// load VAO
		int vaoID = glGenVertexArrays();
		int textureID = 0;
		int textureMode = 0;
		glBindVertexArray(vaoID);

		// load data into VBOs
		storeVertexDataInAttributeList(type, data);

		bindIndicesBuffer(type, data);
		
		// load texture if specified
		if ((textureName.equals("")))  textureMode = 1;
		
		textureID = loadTexture(type, textureName, textureMode, data);
		// Unbind VAO after using it
		glBindVertexArray(0);
		vaoIDs.add(vaoID);
		return new Model(type, vaoID, textureID, textureMode, data);
	}

	public static void storeVertexDataInAttributeList(ModelType type, ModelData data) {
		int vboID = glGenBuffers();
		int nboID = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		if (type.equals(ModelType.CUSTOM)) {
			data.setVertexData(OBJLoader.getfVertices());
			glBufferData(GL_ARRAY_BUFFER, loadVBOFloats(OBJLoader.getfVertices()), GL_STATIC_DRAW);
		} else {
			data.setVertexData(type.getVertexData());
			glBufferData(GL_ARRAY_BUFFER, loadVBOFloats(type.getVertexData()), GL_STATIC_DRAW);
		}
		// store vertex data in attribute number 0
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glBindBuffer(GL_ARRAY_BUFFER, nboID);
		if (type.equals(ModelType.CUSTOM)) {
			data.setNormalData(OBJLoader.getfNormals());
			glBufferData(GL_ARRAY_BUFFER, loadVBOFloats(OBJLoader.getfNormals()), GL_STATIC_DRAW);
		} else {
			data.setVertexData(type.getNormalData());
			glBufferData(GL_ARRAY_BUFFER, loadVBOFloats(type.getNormalData()), GL_STATIC_DRAW);
		}
		// store vertex data in attribute number 2
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		vboIDs.add(vboID);
		vboIDs.add(nboID);
	}

	public static void bindIndicesBuffer(ModelType type, ModelData data) {
		int vboID = glGenBuffers();
		vboIDs.add(vboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		if (type.equals(ModelType.CUSTOM)) {
			data.setIndexData(OBJLoader.getfIndices());

			glBufferData(GL_ELEMENT_ARRAY_BUFFER, loadIBOInts(OBJLoader.getfIndices()), GL_STATIC_DRAW);
		} else {
			data.setIndexData(type.getIndexData());
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, loadIBOInts(type.getIndexData()), GL_STATIC_DRAW);

		}
		// NEVER UNBIND THE INDEX BUFFER !!!
	}

	public static int loadTexture(ModelType type, String fileName, int mode, ModelData data) {
		// load image data
		BufferedImage imageBuffer = null;
		try {
			imageBuffer = ImageIO.read(new File("images/" + fileName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(imageBuffer);
		int[] pixels = new int[imageBuffer.getWidth() * imageBuffer.getHeight()];
		imageBuffer.getRGB(0, 0, imageBuffer.getWidth(), imageBuffer.getHeight(), pixels, 0,
				imageBuffer.getWidth());

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
		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageBuffer.getWidth(), imageBuffer.getHeight(), 0, GL_RGBA,
				GL_UNSIGNED_BYTE, buffer);
		
		// glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
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
		if (type.equals(ModelType.CUSTOM)) {
			data.setTextureData(OBJLoader.getfTextureCoords());
			glBufferData(GL_ARRAY_BUFFER, loadVBOFloats(OBJLoader.getfTextureCoords()), GL_STATIC_DRAW);

		} else {
			data.setTextureData(type.getTextureData());

			glBufferData(GL_ARRAY_BUFFER, loadVBOFloats(type.getTextureData()), GL_STATIC_DRAW);

		}
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		return textureID;

	}

	public static Model loadOBJModel(String objFilename, String textureFilename) {
		OBJLoader.loadObjModel(objFilename);
		return loadModel(ModelType.CUSTOM, textureFilename);

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
