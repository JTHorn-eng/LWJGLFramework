package app;

import java.util.ArrayList;
import app.Model;

public class ModelManager {

	private static ArrayList<Model> models = new ArrayList<>();

	public static void addModel() {
		System.out.println("Add model");
		Model model = new Model();
		model.prepareModel();
		models.add(model);

	}

	public void setModels(ArrayList<Model> models) {
		ModelManager.models = models;
	}

	public static ArrayList<Model> getModels() {
		return ModelManager.models;
	}

	public void deleteAll() {
		models.clear();
	}
}
