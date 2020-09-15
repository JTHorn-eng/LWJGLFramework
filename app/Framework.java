package app;

import org.lwjgl.Version;

public class Framework extends Rendering {

	public void init() {

		System.out.println("Init framework");
		System.out.println("Version: " + Version.getVersion());
		Window.init();
		Window.createWindow();
		Window.resizeWindow(1080, 720);

		// load models and associated shader programs
		// load shaders first ! 
		ShaderProgram sp = new ShaderProgram();
		try {
			sp.createProgram("shaders/Test_Vertex_Shader.txt", "shaders/Test_Fragment_Shader.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelManager.addModel(ModelType.SQUARE, "test");
		renderLoop();
		

		cleanUp();
		Window.destroyWindow();
	}
	
	private void cleanUp() {
		Primitives.cleanUp();
	}

	@Override
	public void postRendering() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preRenderingEffects() {
		// TODO Auto-generated method stub
		
	}
}
