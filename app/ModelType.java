package app;

public enum ModelType {

	SQUARE( new float[] {-0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f},
		    new int[] { 0, 1, 3, 3, 1, 2 },
	 	    new float[] { 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f },
	 	    true
	),
	
	CUSTOM (null,
			null,
			null,
			true
	);
	
	private final float[] vertexData;
	private final int[] indexData;
	private final float[] textureData;
	private final boolean renderMode;
	
	private ModelType(float[] vertices, int[] indices, float[] textureVertices, boolean renderMode) {
		vertexData = vertices;
		indexData = indices;
		textureData = textureVertices;
		this.renderMode = renderMode;	
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

}
