package app;

public enum ModelType {

	RIGHT_TRIANGLE(new float[] 
			{
			 -0.5f, 0.0f, 0.0f,
			 0.5f, 0.0f, 0.0f,
			 0.5f, 0.5f, 0.0f,
					},
			new int[] {
					
			},
			
			new float[] {
				0.0f, 0.0f,
				1.0f, 0.0f,
				1.0f, 1.0f
					
			}
	
			
			
			),
	SQUARE(new float[] {}, new int[] {},  new float[] {});
	
	private final float[] VERTICES;
	private final float[] TEXTURE_COORDS;
	private final int[] INDICES;
	
	private ModelType(float[] vertexData, int[] indexData,  float[] textureData) {
		this.VERTICES = vertexData;
		this.INDICES = indexData;
		this.TEXTURE_COORDS = textureData;
	}
	
	public float[] getVertices() {
		return VERTICES;
	}
	public float[] getTextureVertices() {
		return TEXTURE_COORDS;
	}
	public int[] getIndices() {
		return INDICES;
	}
	
}
