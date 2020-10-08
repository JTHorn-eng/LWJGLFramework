package app;

import org.joml.Vector3f;
import org.lwjgl.Version;

/*
 * Default values
 * Rendering mode false - 2D
 * Viewport width = 1280
 * Viewport height = 720
 * Window title = "Application"
 */
public abstract class Framework extends Rendering {

	public void init() {

		System.out.println("Init framework");
		System.out.println("Version: " + Version.getVersion());
		FrameworkProperties fp = FrameworkProperties.genProperties();
		
		Window.createWindow();

		// load models and associated shader programs
		// load shaders first !
		ShaderProgram sp = new ShaderProgram();
		try {
			sp.createProgram("shaders/Test_Vertex_Shader.txt", "shaders/Test_Fragment_Shader.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		addCamera("default");

	}

	public static void addModel(String modelName, ModelType type, String textureName) {
		EntityManager.addModel(modelName, type, textureName);
	}

	public static void addModel(String modelName, ModelType type, String textureName, Vector3f position,
			Vector3f rotation, float scale) {
		EntityManager.addModel(modelName, type, textureName, position, rotation, scale);

	}
	public static void addCamera(String name) {
		EntityManager.addCamera(name);
	}
	
	public static Model selectModel(String modelName) {
		return EntityManager.getModel(modelName);
	}


	public static void close() {
		Primitives.cleanUp();
		Window.destroyWindow();
	}

}
