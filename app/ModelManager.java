package app;

import java.util.HashMap;

import org.joml.Vector3f;



public class ModelManager {

	private static HashMap<String, Model> models = new HashMap<>();

	/**
	 * 
	 * @param modelName - name of the model, use OBJ filename is using custom model
	 * @param type      - use a predefined shape or custom model ModelType.CUSTOM
	 * @param textureName
	 */
	public static void addModel(String modelName, ModelType type, String textureName) {
		
		if (!type.equals(ModelType.CUSTOM)) {
			//load model data into VBOs and store vaoID in a new model
			Model model = Primitives.loadModel(type, textureName);
			
			models.put(modelName, model);
		} else {
			Model model = Primitives.loadOBJModel(modelName, textureName);
			models.put(modelName, model);
		}
	}
	
	public static void addModel(String modelName, ModelType type, String textureName, Vector3f position, Vector3f rotation, float scale) {
		
		//load model data into VBOs and store vaoID in a new model
		Model model;
		
		if (!type.equals(ModelType.CUSTOM)) {
			//load model data into VBOs and store vaoID in a new model
			model = Primitives.loadModel(type, textureName);
		} else {
			 model = Primitives.loadOBJModel(modelName, textureName);
		}
		
		model.setRotX(rotation.x);
		model.setRotY(rotation.y);
		model.setRotZ(rotation.z);
		
		model.setTranslation(position);
		model.setScale(scale);
		models.put(modelName, model);
	}
	
	public static void addModel(String modelName, String objFile,  String textureName) {
		
	}
	
	public static HashMap<String, Model> getModels() {
		return models;
	}
	
}
