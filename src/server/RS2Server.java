package server;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import server.core.GameEngine;
import server.core.World;
import server.core.task.impl.CleanupTask;


/**
 * 
 * @author Graham Edgecombe
 * @author Jinrake
 * Hyperion
 *
 */
public class RS2Server {

	/**
	 * Logger instance.
	 */
	private static final Logger logger = Logger.getLogger(RS2Server.class.getName());

	/**
	 * The <code>GameEngine</code> instance.
	 */
	private static final GameEngine engine = new GameEngine();

	/**
	 * Creates the server and the <code>GameEngine</code> and initializes the
	 * <code>World</code>.
	 * @throws IOException if an I/O error occurs loading the world.
	 * @throws ClassNotFoundException if a class the world loads was not found.
	 * @throws IllegalAccessException if a class loaded by the world was not accessible.
	 * @throws InstantiationException if a class loaded by the world was not created.
	 */
	public RS2Server() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		World.getWorld().init(engine);
	}

	/**
	 * Starts the <code>GameEngine</code>.
	 * @throws ExecutionException if an error occured during background loading.
	 */
	public void start() throws ExecutionException {
		if(World.getWorld().getBackgroundLoader().getPendingTaskAmount() > 0) {
			logger.info("Waiting for pending background loading tasks...");
			World.getWorld().getBackgroundLoader().waitForPendingTasks();
		}
		World.getWorld().getBackgroundLoader().shutdown();
		engine.start();	
		logger.info("Setting up login channels...");
		Server.setupLoginChannels();
		logger.info(Config.SERVER_NAME+" accpeting incoming connections...");
		World.getWorld().submit(new CleanupTask());
	}

	/**
	 * Gets the <code>GameEngine</code>.
	 * @return The game engine.
	 */
	public static GameEngine getEngine() {
		return engine;
	}
	
}
