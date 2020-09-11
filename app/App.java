package app;

/**
 * End-User generates models and associates shader program for model type Then
 * pre-rendering and post-rendering effects are added by implementing rendering
 * interface methods. User then calls render method in Render.
 * 
 */

public class App {

	Framework framework;

	public App() {
		framework = new Framework();
	}

	private void init() {
		framework.init();
	}

	public static void main(String[] args) {

		App app = new App();
		app.init();

	}
}
