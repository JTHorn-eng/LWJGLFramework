package app;

import java.io.IOException;

public class Model {

	private Primitives mesh;
	private int vaoID;
	private int textureID;
	
	public Model(ModelType type, Texture texture) {
		mesh = new Primitives();
		try {
			textureID = mesh.loadTexture(texture.getFilename());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		vaoID = mesh.prepareModel(type, "");	
	}

	public Primitives getMesh() {
		return mesh;
	}

	public void setMesh(Primitives mesh) {
		this.mesh = mesh;
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
