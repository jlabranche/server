package server.model.players;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Future;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import server.Config;
import server.Server;
import server.model.items.ItemAssistant;
import server.model.npcs.NPC;
import server.model.shops.ShopAssistant;
import server.net.HostList;
import server.net.Packet;
import server.net.Packet.Type;
import server.util.Misc;
import server.util.Stream;
import server.model.players.skills.*;
import server.model.players.skills.Cooking;
import server.core.PlayerHandler;
import server.event.CycleEventHandler;
import server.event.EventManager;
import server.event.Event;
import server.event.EventContainer;
import server.util.SQL;
import server.model.players.quests.*;

public class Client extends Player {

	private Channel session;
	public Channel getSession() {
		return session;
	}

	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private Potions potions = new Potions(this);
	private TanHide tan = new TanHide(this);
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	public int doAmount;
	public boolean playerIsCooking;
	// instance of Cooking so we can access fields in the class
	public Cooking cooking1 = new Cooking();
	 
	// flag that determines if we are cooking or not
	public boolean isCooking;
	 
	// flag that determines if we are using a stove or not
	public boolean usingStove;
	 
	/**
	 * Skill instances
	 */
	private Slayer slayer = new Slayer(this);
	private Runecrafting runecrafting = new Runecrafting(this);
	//private Woodcutting woodcutting = new Woodcutting(this);
	private Mining mine = new Mining(this);
	private Agility agility = new Agility(this);
	public Cooking cooking = new Cooking();
	private Fishing fish = new Fishing(this);
	private Crafting crafting = new Crafting(this);
	private Smithing smith = new Smithing(this);
	private Prayer prayer = new Prayer(this);
	private Fletching fletching = new Fletching(this);
	private SmithingInterface smithInt = new SmithingInterface(this);
	private Farming farming = new Farming(this);
	private Thieving thieving = new Thieving(this);
	private Firemaking firemaking = new Firemaking();
	private Herblore herblore = new Herblore(this);
	
	//quests
	private GettingStart GettingStart = new GettingStart(this);
	
	
	public GettingStart getGSQ() {
		return GettingStart;
	}
	
	
	public boolean kamfreenaDone;
	public boolean inCyclops;
	public boolean spawned;

	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;
	public int returnCode = 2;
	private Future<?> currentTask;

	public Client(Channel s, int _playerId) {
		super(_playerId);
		this.session = s;
		outStream = new Stream(new byte[Config.BUFFER_SIZE]);
		outStream.currentOffset = 0;
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
	}

	public void flushOutStream() {
		if (!session.isConnected() || disconnected || outStream.currentOffset == 0)
			return;

		byte[] temp = new byte[outStream.currentOffset];
		System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
		Packet packet = new Packet(-1, Type.FIXED, ChannelBuffers.wrappedBuffer(temp));
		session.write(packet);
		outStream.currentOffset = 0;           
	}

	public void sendClan(String name, String message, String clan, int rights) {
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}

	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
		0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
		0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
		2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
		0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50
		8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
		6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
		0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
		0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
		0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
		0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
		1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
		0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
		0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
		0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
		0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
		0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
		0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
		2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
		4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
		0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
		1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
		0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
		0, 0, 6, 6, 0, 0, 0 // 250
	};
	public static boolean sit = false;

	public void destruct() {
		if(session == null) 
			return;
		Server.panel.removeEntity(playerName);
		CycleEventHandler.getSingleton().stopEvents(this);
		PlayerSave.saveGame(this);//dat is voor normale logout ja,maar voor unexpected logout meotn we bij destruct zijn denk ik
		if (clanId >= 0)
			Server.clanChat.leaveClan(playerId, clanId);
		Misc.println("[DEREGISTERED]: "+playerName+"");
		HostList.getHostList().remove(session);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
	}

	public void sendMessage(String s) {
			if (getOutStream() != null) {
				outStream.createFrameVarSize(253);
				outStream.writeString(s);
				outStream.endFrameVarSize();
		}
	}

	public void setSidebarInterface(int menuId, int form) {
			if (getOutStream() != null) {
				outStream.createFrame(71);
				outStream.writeWord(form);
				outStream.writeByteA(menuId);
		}
	}
	
	public void agilityWalk(final Client c, final int walkAnimation, final int x, final int y) {
		c.isRunning2 = false;
		c.getPA().sendFrame36(173, 0);
		c.playerWalkIndex = walkAnimation;
		c.getPA().requestUpdates();
		c.getPA().walkTo(x ,y);
	}
	
	public void resetAgilityWalk(final Client c) {
		c.isRunning2 = true;
		c.getPA().sendFrame36(173, 1);
		c.playerWalkIndex = 0x333;
		c.getPA().requestUpdates();
	}

	public void initialize() {
			outStream.createFrame(249);
			outStream.writeByteA(1); // 1 for members, zero for free
			outStream.writeWordBigEndianA(playerId);
			Server.panel.addEntity(playerName);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (j == playerId)
					continue;
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].playerName
							.equalsIgnoreCase(playerName))
						disconnected = true;
				}
			}
			for (int i = 0; i < 25; i++) {
				getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
				getPA().refreshSkill(i);
			}
			for (int p = 0; p < PRAYER.length; p++) { // reset prayer glows
				prayerActive[p] = false;
				getPA().sendFrame36(PRAYER_GLOW[p], 0);
			}
			getPA().handleWeaponStyle();
			getPA().handleLoginText();
			getPA().loadQuests();
			accountFlagged = getPA().checkForFlags();
			// getPA().sendFrame36(43, fightMode-1);
			getPA().sendFrame36(108, 0);// resets autocast button
			getPA().sendFrame36(172, 1);
			getPA().sendFrame107(); // reset screen
			getPA().setChatOptions(0, 0, 0); // reset private messaging options
			setSidebarInterface(1, 3917);
			setSidebarInterface(2, 638);
			setSidebarInterface(3, 3213);
			setSidebarInterface(4, 1644);
			setSidebarInterface(5, 5608);
			if (playerMagicBook == 0) {
				setSidebarInterface(6, 1151); // modern
			} else {
				if (playerMagicBook == 2) {
					setSidebarInterface(6, 29999); // lunar
				} else {
					setSidebarInterface(6, 12855); // ancient
				}
			}
			correctCoordinates();
			setSidebarInterface(7, 18128);
			setSidebarInterface(8, 5065);
			setSidebarInterface(9, 5715);
			setSidebarInterface(10, 2449);
			//setSidebarInterface(11, 4445); // wrench tab
			setSidebarInterface(11, 904); // wrench tab
			setSidebarInterface(12, 147); // run tab
			setSidebarInterface(13, -1);
			setSidebarInterface(0, 2423);
			sendMessage("Welcome to " + Config.SERVER_NAME);
			//sendMessage("@blu@Click a skill tab to learn more information on how to train it.");
			//sendMessage("Items you collect during Pre-SSL, will not roll over into SSL,");
			//sendMessage("But will give you tookens for a Re-Release shop with certain 'rare' items");
			//sendMessage("More information will follow at Skyscapelive.com");
			//sendMessage("@blu@::newupdates to see list of current updates!");
			getPA().showOption(4, 0, "Trade With", 3);
			//getPA().showOption(5, 0, "Follow", 4); //Follow Temp Disabled.
			getItems().resetItems(3214);
			getItems().sendWeapon(playerEquipment[playerWeapon],
					getItems().getItemName(playerEquipment[playerWeapon]));
			getItems().resetBonus();
			getItems().getBonus();
			getItems().writeBonus();
			getItems().setEquipment(playerEquipment[playerHat], 1, playerHat);
			getItems().setEquipment(playerEquipment[playerCape], 1, playerCape);
			getItems().setEquipment(playerEquipment[playerAmulet], 1,
					playerAmulet);
			getItems().setEquipment(playerEquipment[playerArrows],
					playerEquipmentN[playerArrows], playerArrows);
			getItems().setEquipment(playerEquipment[playerChest], 1,
					playerChest);
			getItems().setEquipment(playerEquipment[playerShield], 1,
					playerShield);
			getItems().setEquipment(playerEquipment[playerLegs], 1, playerLegs);
			getItems().setEquipment(playerEquipment[playerHands], 1,
					playerHands);
			getItems().setEquipment(playerEquipment[playerFeet], 1, playerFeet);
			getItems().setEquipment(playerEquipment[playerRing], 1, playerRing);
			getItems().setEquipment(playerEquipment[playerWeapon],
					playerEquipmentN[playerWeapon], playerWeapon);
			getCombat().getPlayerAnimIndex(
					getItems().getItemName(playerEquipment[playerWeapon])
					.toLowerCase());
			getPA().logIntoPM();
			getItems().addSpecialBar(playerEquipment[playerWeapon]);
			saveTimer = Config.SAVE_TIMER;
			saveCharacter = true;
			Misc.println("[REGISTERED]: " + playerName + "");
			getPA().sendFrame126("Bank of " +playerName+ ".", 5383);//Bank
			/*Logout*/
			getPA().sendFrame126("         Thank You For Playing", 2450);
			getPA().sendFrame126("We hope to see you again soon", 2451);
			getPA().sendFrame126("         Skyscapelive.com", 2452);
			/*End Logout*/
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
			getPA().clearClanChat();
			getPA().resetFollow();
			if (addStarter)
				getPA().addStarter();
			if (autoRet == 1)
				getPA().sendFrame36(172, 1);
			else
				getPA().sendFrame36(172, 0);
	}
	
	public void yell(String msg) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
			Client c2 = (Client)Server.playerHandler.players[j];
			c2.sendMessage(msg);
			}
		}	
	}

	public void update() {
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
	}

	public void logout() {
		/*if (!playerName.equalsIgnoreCase("Steven") && (!playerName.equalsIgnoreCase("Tyler"))){
			SQL.saveHighScore(this);
		}*/
		if (System.currentTimeMillis() - logoutDelay > 10000) {
			outStream.createFrame(109);
			properLogout = true;
		} else {
			sendMessage("You must wait a few seconds from being out of combat to logout.");
		}
	}

	public int packetSize = 0, packetType = -1;
	public int donatorPoints = 0;
	public boolean usingClaws;
	public int previousDamage;
	public boolean oldSpec;
	public boolean canWalk = true;
	public int currentRegion = 0;

	public boolean canWalk() {
		return canWalk;
	}

	public void setCanWalk(boolean canWalk) {
		this.canWalk = canWalk;
	}
	
	public boolean isBanking;

	public void process() {
		if(followId > 0) {
			getPA().followPlayer();
		} else if (followId2 > 0) {
			getPA().followNpc();
		}

		if (System.currentTimeMillis() - lastPoison > 20000 && poisonDamage > 0) {
			int damage = poisonDamage/2;
			if (damage > 0) {
				sendMessage("The poison damages you");
				if (!getHitUpdateRequired()) {
					setHitUpdateRequired(true);
					setHitDiff(damage);
					updateRequired = true;
					poisonMask = 1;
				} else if (!getHitUpdateRequired2()) {
					setHitUpdateRequired2(true);
					setHitDiff2(damage);
					updateRequired = true;
					poisonMask = 2;
				}
				lastPoison = System.currentTimeMillis();
				poisonDamage--;
				dealDamage(damage);
			} else {
				poisonDamage = -1;
				sendMessage("You are no longer poisoned.");
			}	
		}

		if(System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if(specAmount < 10) {
				specAmount += .5;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}

		getCombat().handlePrayerDrain();

		if(System.currentTimeMillis() - singleCombatDelay >  9900) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 9900) {
			underAttackBy2 = 0;
		}

		if(System.currentTimeMillis() - restoreStatsDelay >  60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++)  {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if(level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}

		if (inWild()) {
			int modY = absY > 6400 ? absY - 6400 : absY;
			wildLevel = (((modY - 3520) / 8) + 1);
			getPA().walkableInterface(197);
			if (Config.SINGLE_AND_MULTI_ZONES) {
				if (inMulti()) {
					getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
				} else {
					getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
				}
			} else {
				getPA().multiWay(-1);
				getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
			}
			getPA().showOption(3, 0, "Attack", 1);
		} else if (inDuelArena()) {
			getPA().walkableInterface(201);
			if (duelStatus == 5) {
				getPA().showOption(3, 0, "Attack", 1);
			} else {
				getPA().showOption(3, 0, "Challenge", 1);
			}
		} else if (inBarrows()) {
			getPA().sendFrame99(2);
			getPA().sendFrame126("Kill Count: " + barrowsKillCount, 4536);
			getPA().walkableInterface(4535);
		} else if (inCwGame || inPits) {
			getPA().showOption(3, 0, "Attack", 1);
		} else if (getPA().inPitsWait()) {
			getPA().showOption(3, 0, "Null", 1);
		} else if (!inCwWait) {
			getPA().sendFrame99(0);
			getPA().walkableInterface(-1);
			getPA().showOption(3, 0, "Null", 1);
		}

		if(!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}

		if(hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}

		if(skullTimer > 0) {
			skullTimer--;
			if(skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}	
		}
		
		if(hitDelay > 0) {
			hitDelay--;
		}
		
		if(hitDelay == 1) {
			if(oldNpcIndex > 0) {
				getCombat().delayedHit(this, oldNpcIndex);
			}
			if(oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(this, oldPlayerIndex);				
			}		
		}
		
		if(attackTimer > 0) {
			attackTimer--;
		}
		
		if(attackTimer == 1){
			if(npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if(playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}

		if(isDead && respawnTimer == -6) {
			getPA().applyDead();
		}

		if(respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if(respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
			poisonDamage = -1;
		}	

		if(respawnTimer > -6) {
			respawnTimer--;
		}
		if(freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (PlayerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY, PlayerHandler.players[frozenBy].absX, PlayerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}
		if(teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if(teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if(teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if(teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}

		if (wcTimer > 0 && woodcut[0] > 0) {
			wcTimer--;
		/*} else if (wcTimer == 0 && woodcut[0] > 0) {
			getWoodcutting().cutWood();*/
		} else if (miningTimer > 0 && mining[0] > 0) {
			miningTimer--;
		} else if (miningTimer == 0 && mining[0] > 0) {
			getMining().mineOre();
		} else  if (smeltTimer > 0 && smeltType > 0) {
			smeltTimer--;
		} else if (smeltTimer == 0 && smeltType > 0) {
			getSmithing().smelt(smeltType);
		} else if (spinTimer > 0) {
			spinTimer--;
		} else if (spinTimer == 0) {
			getCrafting().flaxToBowString();//starts the timer over
			getCrafting().addBowString();
		}
	}

	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}

	public synchronized Stream getInStream() {
		return inStream;
	}

	public synchronized int getPacketType() {
		return packetType;
	}

	public synchronized int getPacketSize() {
		return packetSize;
	}

	public synchronized Stream getOutStream() {
		return outStream;
	}

	public ItemAssistant getItems() {
		return itemAssistant;
	}

	public PlayerAssistant getPA() {
		return playerAssistant;
	}

	public DialogueHandler getDH() {
		return dialogueHandler;
	}

	public ShopAssistant getShops() {
		return shopAssistant;
	}

	public TradeAndDuel getTradeAndDuel() {
		return tradeAndDuel;
	}

	public CombatAssistant getCombat() {
		return combatAssistant;
	}

	public ActionHandler getActions() {
		return actionHandler;
	}

	public Potions getPotions() {
		return potions;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	public Food getFood() {
		return food;
	}
	
	public Client getClient(String name) {
		name = name.toLowerCase();
		for(int i = 0; i < Config.MAX_PLAYERS; i++) {
			if(validClient(i)) {
				Client client = getClient(i);
				if(client.playerName.toLowerCase().equalsIgnoreCase(name)) {
					return client;
				}
			}
		}
		return null;
	}
	public Client getClient(int id) {
		return (Client) Server.playerHandler.players[id];
	}
	public boolean validClient(int id) {
		if (id < 0 || id > Config.MAX_PLAYERS) {
			return false;
		}
		return validClient(getClient(id));
	}
	public boolean validClient(String name) {
		return validClient(getClient(name));
	}
	public boolean validClient(Client client) {
		return (client != null && !client.disconnected);
	}
	public boolean validNpc(int index) {
		if (index < 0 || index >= Config.MAX_NPCS) {
			return false;
		}
		NPC n = getNpc(index);
		if (n != null) {
			return true;
		}
		return false;
	}
	public NPC getNpc(int index) {
		return ((NPC) Server.npcHandler.npcs[index]);
	}

	/**
	 * Skill Constructors
	 */
	public Slayer getSlayer() {
		return slayer;
	}

	public Runecrafting getRunecrafting() {
		return runecrafting;
	}

	/*public Woodcutting getWoodcutting() {
		return woodcutting;
	}*/

	public Mining getMining() {
		return mine;
	}

	public Cooking getCooking() {
		return cooking1;
	}

	public Agility getAgility() {
		return agility;
	}

	public Fishing getFishing() {
		return fish;
	}

	public Crafting getCrafting() {
		return crafting;
	}

	public Smithing getSmithing() {
		return smith;
	}

	public Farming getFarming() {
		return farming;
	}
	
	public TanHide getTanning() {
		return tan;
	}

	public Thieving getThieving() {
		return thieving;
	}

	public Herblore getHerblore() {
		return herblore;
	}

	public Firemaking getFiremaking() {
		return firemaking;
	}

	public SmithingInterface getSmithingInt() {
		return smithInt;
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public Fletching getFletching() {
		return fletching;
	}

	/**
	 * End of Skill Constructors
	 */

	public void queueMessage(Packet arg1) {
		synchronized(queuedPackets) {
			queuedPackets.add(arg1);
		}
	}

	public boolean processQueuedPackets() {
		synchronized (queuedPackets) {
			Packet p = null;
			while ((p = queuedPackets.poll()) != null) {
				inStream.currentOffset = 0;
				packetType = p.getOpcode();
				packetSize = p.getLength();
				inStream.buffer = p.getPayload().array();
				if (packetType > 0) {
					PacketHandler.processPacket(this, packetType, packetSize);
				}
			}
			return true;
		}
	}
	
	public Map<Integer, String> getStringMap() {
		return stringMap;
	}
	
	public Map<Integer, String> stringMap = new HashMap<Integer, String>();

	public void correctCoordinates() {
		if (inPcGame()) {
			getPA().movePlayer(2657, 2639, 0);
		}
		if (inFightCaves()) {
			getPA().movePlayer(absX, absY, playerId * 4);
			sendMessage("Your wave will start in 10 seconds.");
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer c) {
					Server.fightCaves.spawnNextWave((Client) PlayerHandler.players[playerId]);
					c.stop();
				}
			}, 10000);

		}

	}

}
