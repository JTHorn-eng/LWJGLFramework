package app;

public enum ModelType {

	SQUARE(
		  new float[] {-0.5f, 0.5f, 0f,
						0.5f, 0.5f, 0f,
					   -0.5f, -0.5f, 0f,
						
						0.5f, -0.5f, 0f,
						0.5f, 0.5f, 0f,
					   -0.5f, -0.5f, 0f
					});

	private final float[] vertexData;
	
	private ModelType(float[] vertices) {
		vertexData = vertices;
	}
	
	public float[] getVertexData() {
		return vertexData;
	}

}
