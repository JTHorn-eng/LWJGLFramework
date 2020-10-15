package app;

import org.joml.Vector3f;



//init framework
//define custom shaders
//add models 
//add rendering effects
//render them
//once finished close the framework


//TODO

//Add more primitives - lines, shapes, 3d shapes
//Create float buffer method
//Using custom shaders
//GUIs - Menus, clickables, not sure what else !!!
//Audio engine
//sort out timings of threads
//animations
//particle effects


public class App extends Framework{

	FrameworkProperties fp = FrameworkProperties.genProperties();
	
	
	
	
	public App() {
		init();
		fp.setRenderingMode(true);
		addModel("stall",ModelType.CUSTOM , "test");
		addLine("line1", new Vector3f(0, 0, 0), new Vector3f(1,1,-1f), 1.0f);
		selectModel("stall").setZ(-30f);
		setController("stall");
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


