package app;

import org.lwjgl.Version;

public class App implements Runnable {

	private Rendering rendering;

	public App() {
		rendering = new Rendering();
	}

	private void init() {

		System.out.println("Version: " + Version.getVersion());
		Window.init();
		Window.createWindow();
		rendering.renderLoop(false);
		Window.destroyWindow();

	}

	public static void main(String[] args) {

		App app = new App();
		Thread applicationThread = new Thread(app);
		applicationThread.start();

	}

	@Override
	public void run() {
		init();
	}

}
