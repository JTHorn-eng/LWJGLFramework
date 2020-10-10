package entities;

public class ModelData {
	
	private float[] vertexData;
	private int[] indexData;
	private float[] textureData;
	private float[] normalData;
	private boolean renderMode;
	
	public float[] getVertexData() {
		return vertexData;
	}
	public void setVertexData(float[] vertexData) {
		this.vertexData = vertexData;
	}
	public int[] getIndexData() {
		return indexData;
	}
	public void setIndexData(int[] indexData) {
		this.indexData = indexData;
	}
	public float[] getTextureData() {
		return textureData;
	}
	public void setTextureData(float[] textureData) {
		this.textureData = textureData;
	}
	public void setNormalData(float[] normalData) {
		this.normalData = normalData;
	}
	public float[] getNormalData() {
		return normalData;
	}
	public boolean isRenderMode() {
		return renderMode;
	}
	public void setRenderMode(boolean renderMode) {
		this.renderMode = renderMode;
	}
	
	
	
	
}
