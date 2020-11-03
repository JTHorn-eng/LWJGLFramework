package gui;

public class GUIModel {

	float[] vertices;
	float[] textureCoords;
	int[] indices;
	int vaoID;
	int textureID;

	GUIModel(float[] vertices, float[] textureCoords, int[] indices, int vaoID, int textureID) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.indices = indices;
		this.vaoID = vaoID;
		this.textureID = textureID;
	}

	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public void setTextureCoords(float[] textureCoords) {
		this.textureCoords = textureCoords;
	}

	public int[] getIndices() {
		return indices;
	}

	public void setIndices(int[] indices) {
		this.indices = indices;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getTextureID() {
		return textureID;
	}

}
