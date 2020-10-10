package app;

import org.joml.Vector3f;

//init framework
//define custom shaders
//add models 
//add rendering effects
//render them
//once finished close the framework


//TODO


//Basic lighting
//Create float buffer method
//Controllers for models + cameras
//Using custom shaders
//GUIs - Menus, clickables, not sure what else !!!
//Audio engine
//sort out timings of threads




public class App extends Framework{

	FrameworkProperties fp = FrameworkProperties.genProperties();
	
	public App() {
		init();
		fp.setRenderingMode(true);
		addModel("stall",ModelType.CUSTOM , "test");
		selectModel("stall").z(-30f);

		render();
		close();
	}
	
	public static void main(String args[]) {
		App app = new App();
	}
	
	@Override
	public void preRendering() {
		
	}
	
	@Override
	public void postRendering() {
		
	}

	
}


