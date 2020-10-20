package app;

//https://gameprogrammingpatterns.com/game-loop.html
import static org.lwjgl.glfw.GLFW.*;

import entities.EntityManager;
import entities.Model;
import exceptions.UniformNotFoundException;

/**
 * Singleton class
 * 
 * Only used for initialisation
 *
 */
public class GameLoop {

	private static final double MS_PER_UPDATE = 1000;
	private static FrameworkProperties fp = FrameworkProperties.genProperties();
	private static GameLoop gameLoop = null;
	
	public static GameLoop init() {
		if (gameLoop == null) {
			gameLoop = new GameLoop();
		}
		return gameLoop;
	}
	
	
	
	
	/**
	 * gameloop
	 * 
	 * while running input update render
	 * 
	 * update window
	 * 
	 * 
	 * 
	 */
	private void update() {
		for (Model model : EntityManager.getModels().values()) {

			// for 2D rendering mode force all z-coords to 0
			if (!fp.getRenderingMode()) {
				System.out.println("ASD");
				model.setZ(0);
			}
			

			// load shader variables and use shader program (also binds)
			
		}

	}

	public void loop() {
		// advance time by how much time was taking processing updates
		double previous = System.currentTimeMillis();
		double lag = 0.0;
		while (!glfwWindowShouldClose(Window.getWindow())) {
			double current = System.currentTimeMillis();
			double elapsed = current - previous;
			previous = current;
			lag += elapsed; // how much real time has passed

			// update the game - catch up with how much real time has passed in fixed
			// intervals
			while (lag >= MS_PER_UPDATE) {
				// updating methods
				System.out.println(elapsed);
				update();
				lag -= MS_PER_UPDATE;
			}
			
			Rendering.render();

		}

		// close everything
		ShaderProgram.deleteShaderPrograms();
		Primitives.cleanUp();
		Rendering.close();
		Window.destroyWindow();
		System.exit(0); // ensure all threads are shutdown

	}

}
