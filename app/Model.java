package app;

public class Model {

	private int vaoID;
	private ModelType type;
	
	public Model(ModelType t) {
		type = t;
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
	
	
	
	
	
}
