package entities;

import java.util.HashMap;


public class LightManager {

	static Light test = new Light();
	
	static HashMap<String, Light> lights = new HashMap<>();
	
	public HashMap<String, Light> getLights() {
		return lights;
	}
	
	public static void addLight(String name) {
		lights.put(name, new Light());
	}
	
	public static Light getTest() {
		return test;
	}
	
	
}
