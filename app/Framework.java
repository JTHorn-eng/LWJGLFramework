package app;

import org.joml.Vector3f;
import org.lwjgl.Version;

import entities.EntityManager;
import entities.LightManager;
import entities.Model;

/*
 * Default values
 * Rendering mode false - 2D
 * Viewport width = 1280
 * Viewport height = 720
 * Window title = "Application"
 */
public abstract class Framework extends Rendering {
	
	private static GameLoop gameLoop = GameLoop.init();
	protected static final boolean RENDER_2D = false;
	protected static final boolean RENDER_3D = true;
	
	public void init() {

		System.out.println("Init framework");
		System.out.println("Version: " + Version.getVersion());
		FrameworkProperties fp = FrameworkProperties.getProperties();
		Window.createWindow();

		// load models and associated shader programs
		// load shaders first !
		ShaderProgram sp = new ShaderProgram();
		try {
			sp.createProgram("default", "shaders/Test_Vertex_Shader.txt", "shaders/Test_Fragment_Shader.txt");
			sp.createProgram("lines", "shaders/Line_Vertex_Shader.txt", "shaders/Line_Fragment_Shader.txt");

		} catch (Exception e) {
			e.printStackTrace();
		}
		addCamera("default");
		Controller controller = new Controller();
		
		
		
	}
	
	public static void addLight(String name) {
		LightManager.addLight(name);
	}
	
	public static void setController(String entityName) {
		Controller.controller(entityName);
	}

	public static void addModel(String modelName, ModelType type, String textureName) {
		EntityManager.addModel(modelName, type, textureName);
	}

	public static void addModel(String modelName, ModelType type, String textureName, Vector3f position,
			Vector3f rotation, float scale) {
		EntityManager.addModel(modelName, type, textureName, position, rotation, scale);

	}
	public static void addLineSeg(String name, Vector3f start, Vector3f end, float thickness) {
		EntityManager.addLine(name, start, end, thickness);
	}

	public static void addCamera(String name) {
		EntityManager.addCamera(name);
	}
	
	public static Model selectModel(String modelName) {
		return EntityManager.getModel(modelName);
	}
	
	//start updating and rendering, call this after everything !!!
	public static void loop() {
		gameLoop.loop();
	}
	
	

}
