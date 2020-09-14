package app;

public enum ModelType {

	SQUARE(
		  new float[] {-0.5f, 0.5f, 0f,
						-0.5f, -0.5f, 0f,
					   0.5f, -0.5f, 0f,
					   0.5f, 0.5f, 0f,

					},
		new int[] {
				
				0, 1, 3, 3,2,1
				
		});
	private final float[] vertexData;
	private final int[] indexData;
	
	private ModelType(float[] vertices, int[] indices) {
		vertexData = vertices;
		indexData = indices;
	}
	
	public float[] getVertexData() {
		return vertexData;
	}
	
	public int[] getIndexData() {
		return indexData;
	}

}
