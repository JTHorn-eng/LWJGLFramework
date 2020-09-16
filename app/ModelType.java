package app;

public enum ModelType {

	SQUARE(
		  new float[] {-0.5f, 0.5f, 0f,
						-0.5f, -0.5f, 0f,
					   0.5f, -0.5f, 0f,
					   0.5f, 0.5f, 0f,

					},
		new int[] {
				
				0, 1, 3, 
				3, 1, 2
				
		},
	 	new float[] {
	 		0f, 0f,
	 		0f, 1f, 
	 		1f, 1f,
	 		1f, 0f
	 			
	 	});
	
	
	private final float[] vertexData;
	private final int[] indexData;
	private final float[] textureData;
	
	private ModelType(float[] vertices, int[] indices, float[] textureVertices) {
		vertexData = vertices;
		indexData = indices;
		textureData = textureVertices;
		
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
