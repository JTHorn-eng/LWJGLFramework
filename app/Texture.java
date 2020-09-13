package app;

public class Texture {

	private String fileName = "";

	public Texture(String name) {
		
		fileName = "images/"+ name + ".png";
	}

	public String getFilename() {
		return fileName;
	}

	public void setFilename(String name) {
		this.fileName = name;
	}

}
