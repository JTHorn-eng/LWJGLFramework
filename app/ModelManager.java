package app;

import java.util.ArrayList;



public class ModelManager {

	private static ArrayList<Model> models = new ArrayList<>();


	
	public static void addModel(ModelType type) {
		Model model = new Model(type);
		
		//load model data into VBOs and store vaoID in a new model
		int vaoID = Primitives.loadModel(type);
		model.setVAOID(vaoID); 
		models.add(model);
	}

	
	public static ArrayList<Model> getModels() {
		return models;
	}
	
}
