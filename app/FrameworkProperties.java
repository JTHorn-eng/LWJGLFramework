package app;

//singleton for framework properties
public class FrameworkProperties {

	private static FrameworkProperties properties = null;
	private String windowTitle = "Application";
	private int width = 1280;
	private int height = 720;
	private boolean renderingMode = false;
	private static final String FONT = "";
	

	private FrameworkProperties() {
		
	}
	
	

	public static FrameworkProperties getProperties() {
		if (properties == null) {
			properties = new FrameworkProperties();
			
		}
		
		return properties;
	}
	
	
	public String getWindowTitle() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isRenderingMode() {
		return renderingMode;
	}

	public void setRenderingMode(boolean renderingMode) {
		this.renderingMode = renderingMode;
		Rendering.updateProjectionMatrix();
	}
	public boolean getRenderingMode() {
		return renderingMode;
	}
}
