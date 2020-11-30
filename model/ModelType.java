package model;

public enum ModelType {

	
	
	SQUARE( new float[] {-0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f},
		    new int[] { 0, 1, 3, 3, 1, 2 },
	 	    new float[] { 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f },
	 	    new float[] {1f, 0f, 0f},
	 	    true
	),
	

	
	
	CUBE ( new float[] { // front
    -1.0f, -1.0f,  1.0f,
     1.0f, -1.0f,  1.0f,
     1.0f,  1.0f,  1.0f,
    -1.0f,  1.0f,  1.0f,
    // back
    -1.0f, -1.0f, -1.0f,
     1.0f, -1.0f, -1.0f,
     1.0f,  1.0f, -1.0f,
    -1.0f,  1.0f, -1.0f},
		new int[] {// front
				0, 1, 2,
				2, 3, 0,
				// right
				1, 5, 6,
				6, 2, 1,
				// back
				7, 6, 5,
				5, 4, 7,
				// left
				4, 0, 3,
				3, 7, 4,
				// bottom
				4, 5, 1,
				1, 0, 4,
				// top
				3, 2, 6,
				6, 7, 3},
		new float[] {},
		new float[] {},
		true),

	CUSTOM (null,
			null,
			null,
			null,
			true
	);
	
	private final float[] vertexData;
	private final int[] indexData;
	private final float[] textureData;
	private final boolean renderMode;
	private final float[] normalData;
	
	private ModelType(float[] vertices, int[] indices, float[] textureVertices, float[] normalVertices, boolean renderMode) {
		vertexData = vertices;
		indexData = indices;
		textureData = textureVertices;
		this.renderMode = renderMode;	
		this.normalData = normalVertices;
	}
	public boolean getRenderMode() {
		return renderMode;
	}
	public float[] getVertexData() {
		return vertexData;
	}
	public int[] getIndexData() {
		return indexData;
	}
	public float[] getTextureData() {
		return textureData;
	}
	public float[] getNormalData() {
		return normalData;
	}

}
