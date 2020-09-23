package app;

public class App {

	Framework f;
	
	public App() {
		f = new Framework();
		f.init();
		//f.test();
	}
	
	public static void main(String args[]) {
		App app = new App();
	}
}


