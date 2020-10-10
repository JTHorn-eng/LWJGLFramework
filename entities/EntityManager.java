package entities;

import java.util.HashMap;

import org.joml.Vector3f;

import app.ModelType;
import app.Primitives;

public class EntityManager {

	private static HashMap<String, Model> models = new HashMap<>();
	private static HashMap<String, Camera> cameras = new HashMap<>();
	private static String currentCamera = "default";
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

		model.rotX(rotation.x);
		model.rotY(rotation.y);
		model.rotZ(rotation.z);

		model.x(position.x);
		model.y(position.y);
		model.z(position.z);

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
