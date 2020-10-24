package entities;

import java.util.HashMap;

import org.joml.Vector3f;

import app.ModelType;
import app.Primitives;

public class EntityManager {
	
	private static HashMap<String, Line> lines = new HashMap<>();
	private static HashMap<String, Model> models = new HashMap<>();
	private static HashMap<String, Camera> cameras = new HashMap<>();
	private static String currentCamera = "default";
	private static int lineID = 0;
	/**
	 * 
	 * @param modelName   - name of the model, use OBJ filename is using custom
	 *                    model
	 * @param type        - use a predefined shape or custom model ModelType.CUSTOM
	 * @param textureName
	 */
	
	public static Camera getCurrentCamera() {
		return cameras.get(currentCamera);		
	}
	
	public static void setCurrentCamera(String name) {
		EntityManager.currentCamera = name;
	}
	
	public static Camera getCamera(String name) {
		return cameras.get(name);
	}

	public static void addCamera(String name) {
		cameras.put(name, new Camera());
	}
	public static HashMap<String, Line> getLines() {
		return EntityManager.lines;
	}
	
	public static void addLine(String name, Vector3f start, Vector3f end, float thickness) {
		lines.put(name, new Line(start, end, thickness, lineID));
		Primitives.loadLines();
		lineID++;
	}
	
	//no texture
	public static void addModel(String modelName, ModelType type) {
		if (!type.equals(ModelType.CUSTOM)) {
			Model model = Primitives.loadModel(type, "");
			models.put(modelName, model);
		} else {
			Model model = Primitives.loadOBJModel(modelName, "");
			models.put(modelName, model);
		}
	}

	public static void addModel(String modelName, ModelType type, String textureName) {

		if (!type.equals(ModelType.CUSTOM)) {
			// load model data into VBOs and store vaoID in a new model
			Model model = Primitives.loadModel(type, textureName);
			models.put(modelName, model);
		} else {
			Model model = Primitives.loadOBJModel(modelName, textureName);
			models.put(modelName, model);
		}
	}

	public static void addModel(String modelName, ModelType type, String textureName, Vector3f position,
			Vector3f rotation, float scale) {

		// load model data into VBOs and store vaoID in a new model
		Model model;

		if (!type.equals(ModelType.CUSTOM)) {
			// load model data into VBOs and store vaoID in a new model
			model = Primitives.loadModel(type, textureName);
		} else {
			model = Primitives.loadOBJModel(modelName, textureName);
		}

		model.setRotX(rotation.x);
		model.setRotY(rotation.y);
		model.setRotZ(rotation.z);

		model.setX(position.x);
		model.setY(position.y);
		model.setZ(position.z);

		model.setScale(scale);
		models.put(modelName, model);
	}

	public static HashMap<String, Model> getModels() {
		return models;
	}

	public static Model getModel(String name) {
		return models.get(name);
	}

}
