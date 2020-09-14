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
		
		ModelManager.addModel();

		// handle model data
		System.out.println("Loading model data");
		
		
		System.out.println("Rendering");
		renderLoop(sp, false, false);
		Window.destroyWindow();

	}

	@Override
	public void postRendering() {

	}

	@Override
	public void preRenderingEffects() {

	}

}
