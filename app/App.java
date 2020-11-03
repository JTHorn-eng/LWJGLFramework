package app;

//init framework
//define custom shaders
//add models 
//add rendering effects
//render them
//once finished close the framework

//TODO

//Create float buffer method
//Using custom shaders
//GUIs - Menus, clickables, not sure what else !!!
//Audio engine
//Sort out timings of threads
//animations
//particle effects
//Option to load models and change vertex data, index data etc... for custom models made in the framework
//scene editor ???

//Lines have to be on the same plane !!!

public class App extends Framework{

	FrameworkProperties fp = FrameworkProperties.getProperties();

	public App() {
		init();
		fp.setRenderingMode(RENDER_3D);
		addModel("stall", ModelType.CUSTOM , "test");
		addText("Hello World!");
		//addModel("test1", ModelType.SQUARE, "");
		//addLineSeg("line1", new Vector3f(-10f, -10f, -1.1f), new Vector3f(10,10,-1f), 1.0f);
		addLight("asd");
		selectModel("stall").setZ(-10f);
		//selectModel("test1").setZ(-10f);
		setController("stall");
		loop();
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


