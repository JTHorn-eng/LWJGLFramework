package app;

import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

import java.util.ArrayList;
import app.Model;

public class ModelManager {

	public static ArrayList<Integer> vaoIDs = new ArrayList<>();
	public static ArrayList<Integer> vboIDs = new ArrayList<>();
	public static ArrayList<Integer> iboIDs = new ArrayList<>();
	public static ArrayList<Integer> tboIDs = new ArrayList<>();

	public static ArrayList<Model> models = new ArrayList<>();

	public static void addModel(ModelType type, Texture texture) {
		System.out.println("Added model");
		Model model = new Model(type, texture);
		models.add(model);

	}
	
	public static void clean() {
		for (int x : ModelManager.vaoIDs) {
			glDeleteVertexArrays(x);
		}
		
		for (int x : ModelManager.vboIDs) {
			glDeleteVertexArrays(x);
		}
		deleteAll();
	}

	public static void deleteAll() {
		models.clear();
	}
}
