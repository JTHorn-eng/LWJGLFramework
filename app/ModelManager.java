package app;

import java.util.ArrayList;



public class ModelManager {

	private static ArrayList<Model> models = new ArrayList<>();
	
	public static void addModel(ModelType type, String textureName) {
		
		//load model data into VBOs and store vaoID in a new model
		Model model = Primitives.loadModel(type, textureName);
		models.add(model);
	}

	
	public static ArrayList<Model> getModels() {
		return models;
	}
	
}
