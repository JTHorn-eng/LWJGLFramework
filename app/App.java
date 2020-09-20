package app;

public class App {

	Framework f;
	
	public App() {
		f = new Framework();
		f.init();

	}
	
	public static void main(String args[]) {
		App app = new App();
	}
}


//THE PROBLEM LIES WITH LOADING VARIABLES
//WITH OPENGL PROPERLY
//REWRITE THE SHADER PROGRAM !!!