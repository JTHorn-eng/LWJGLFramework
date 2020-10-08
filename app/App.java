package app;

import org.joml.Vector3f;

//init framework
//define custom shaders
//add models 
//add rendering effects
//render them
//once finished close the framework


//TODO
//FIX OBJ LOADER

public class App extends Framework{

	FrameworkProperties fp = FrameworkProperties.genProperties();
	
	public App() {
		init();
		fp.setRenderingMode(true);
		Framework.addModel("stall",ModelType.CUSTOM , "test");
		//Framework.addModel("st", ModelType.CUSTOM, "test");
		//ModelManager.getModels().get("stall").setTranslation(new Vector3f(0, 0, 0f));
		
		//Framework.addModel("test", ModelType.SQUARE, "test");
		ModelManager.getModels().get("stall").setTranslation(new Vector3f(0, 0, -40f));
		render();
		close();
	}
	
	public static void main(String args[]) {
		App app = new App();
	}

	@Override
	public void postRendering() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preRenderingEffects() {
		// TODO Auto-generated method stub
		
	}
}


