package app;

public class Model {

	private int vaoID;
	private ModelType type;
	private int textureID;

	public Model(ModelType t, int vaoID, int textureID) {
		type = t;
		this.vaoID = vaoID;
		this.textureID = textureID;
	}
	
	public ModelType getType() {
		return type;
	}
	
	public void setVAOID(int id) {
		vaoID = id;
	}
	
	public int getVAOID() {
		return vaoID;
	}
	
	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}
	
	
	
	
}
