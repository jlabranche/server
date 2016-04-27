package server;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;
import server.util.ControlPanel;

import server.world.PlayerManager;


//added in attempt to fix, however just caused more errors - penguin
// Good luck, never used netty stuff, I'll have to do some googling on it.
/*
    import org.jboss.netty.channel.Channel;
    import org.jboss.netty.channel.ChannelFuture;
    import org.jboss.netty.channel.ChannelFutureListener;
    import org.jboss.netty.channel.ChannelHandlerContext;
    import org.jboss.netty.channel.ChannelPipeline;
    import org.jboss.netty.channel.ChannelPipelineFactory;
    import org.jboss.netty.channel.ChannelStateEvent;
    import org.jboss.netty.channel.Channels;
    import org.jboss.netty.channel.ExceptionEvent;
    import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
    import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
    import org.jboss.netty.handler.codec.string.StringEncoder;
*/
import server.core.GameEngine;
import server.core.PlayerHandler;
import server.core.World;
import server.core.task.Task;
import server.core.tick.Tick;
import server.core.tick.TickManager;
import server.event.CycleEventHandler;
import server.event.TaskScheduler;
import server.model.minigames.FightCaves;
import server.model.minigames.FightPits;
import server.model.minigames.WarriorsGuild;
//import server.model.npcs.NPCDrops;
import server.model.npcs.NPCHandler;
import server.net.PipelineFactory;
import server.util.ShutDownHook;
import server.world.ClanChatHandler;
import server.world.ItemHandler;
import server.world.ObjectHandler;
import server.world.ObjectManager;
import server.world.ShopHandler;
import server.world.WalkingCheck;
import server.world.WalkingCheck2;
import server.clip.region.ObjectDef;
import server.clip.region.Region;
import server.world.WalkingCheck;

/**
 * Server.java
 *
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30
 *
 */

public class Server {
	
	public static boolean
	UpdateServer = false,
	shutdownClientHandler;	

	private static int
	serverlistenerPort = 43594;
	
	
	public static ItemHandler itemHandler = new ItemHandler();
	public static PlayerHandler playerHandler = new PlayerHandler();
    public static NPCHandler npcHandler = new NPCHandler();
	public static ShopHandler shopHandler = new ShopHandler();
	public static ObjectHandler objectHandler = new ObjectHandler();
	public static ObjectManager objectManager = new ObjectManager();
	public static FightPits fightPits = new FightPits();
	//public static NPCDrops npcDrops = new NPCDrops();
	public static FightCaves fightCaves = new FightCaves();
	public static TickManager tickManager = new TickManager();
	public static ClanChatHandler clanChat = new ClanChatHandler();
	public static ControlPanel panel = new ControlPanel(true); // false if you want it off
	
	public static CycleEventHandler cycleeventmanager = new CycleEventHandler();
	public static CycleEventHandler getCycleEventManager() {
		return cycleeventmanager;
	}
	
	private static final TaskScheduler taskScheduler = new TaskScheduler();
	public static TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}
	
	private static WarriorsGuild warriorsGuild = new WarriorsGuild();
	public static WarriorsGuild getWarriorsGuild() {
		return warriorsGuild;
	}
	
	public static void main(String[] args) throws Exception {
		new Server().run();
		ObjectDef.loadConfig();
        Region.load();
		WalkingCheck.load();
	}

	
	public void run() {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	try {
		World.getWorld();
		ObjectDef.loadConfig();
        Region.load();
		WalkingCheck.load();
		WalkingCheck2.check();
		WalkingCheck2.check2();
		try {
			new RS2Server().start();
		} catch(Exception ex) {
			System.out.println("Error starting the server...");
			ex.printStackTrace();
			System.exit(1);
		}
		Runtime.getRuntime().addShutdownHook(new ShutDownHook());
			while(true) {
				long start = System.nanoTime() / 1000000L;
				World.getWorld().submit(new Task() {
					@Override
					public void execute(GameEngine context) {
						Iterator<Tick> tickIt$ = tickManager.getTickables().iterator();
						while(tickIt$.hasNext()) {
							Tick t = tickIt$.next();
							t.cycle();
							if(!t.isRunning()) {
								tickIt$.remove();
							}
						}
					}					
				});
				World.getWorld().submit(new Task() {
					@Override
					public void execute(GameEngine context) {
						Server.playerHandler.process();			
					}
				});
				World.getWorld().submit(new Task() {
					@Override
					public void execute(GameEngine context) {
						Server.npcHandler.process();
					}
				});
				World.getWorld().submit(new Task() {
					@Override
					public void execute(GameEngine context) {						
						Server.shopHandler.process();
					}
				});
				World.getWorld().submit(new Task() {
					@Override
					public void execute(GameEngine context) {						
						CycleEventHandler.getSingleton().process();
					}
				});
				World.getWorld().submit(new Task() {
					@Override
					public void execute(GameEngine context) {
						Server.objectManager.process();
					}
				});
				World.getWorld().submit(new Task() {
					@Override
					public void execute(GameEngine context) {
						Server.itemHandler.process();
					}
				});
				long sleepTime = 600-(System.nanoTime() / 1000000L - start);
				if (sleepTime > 0) {
					Thread.sleep(sleepTime);
				} else {
					// The server has reached maximum load, players may now lag.
					System.out.println("[WARNING]: Server load: " + (100 + (Math.abs(sleepTime) / (Config.CYCLE_TIME / 100))) + "%!");
				}
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void setupLoginChannels() {
		/**
		 * Accepting Connections
		 */
        ServerBootstrap serverBootstrap = new ServerBootstrap (new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        serverBootstrap.setPipelineFactory (new PipelineFactory(new HashedWheelTimer()));
        //serverBootstrap.bind (new InetSocketAddress(serverlistenerPort));
		try {
	        serverBootstrap.bind (new InetSocketAddress(serverlistenerPort));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
