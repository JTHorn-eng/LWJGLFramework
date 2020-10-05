package app;


//init framework
//define custom shaders
//add models 
//add rendering effects
//render them
//once finished close the framework

public class App extends Framework{

	FrameworkProperties fp = FrameworkProperties.genProperties();
	
	public App() {
		init();
		Framework.addModel("test", "stall" , "test");
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


