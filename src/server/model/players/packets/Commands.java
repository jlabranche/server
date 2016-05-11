package server.model.players.packets;

import server.Config;
import server.Connection;
import server.Server;
import server.core.PlayerHandler;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.Player;
import server.model.players.PlayerSave;
import server.util.Misc;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import server.world.WorldMap;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import server.event.EventManager;
import server.event.Event;
import server.event.EventContainer;
import server.model.players.CombatAssistant;

/**
 * Commands reconfigured by Jack
 */
public class Commands implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String playerCommand = c.getInStream().readString();
		Misc.println(c.playerName + " playerCommand: " + playerCommand);
		if (c.playerRights >= 1) {// 1
			moderatorCommands(c, playerCommand);
		}
		if (c.playerRights >= 2) { // 2
			adminCommands(c, playerCommand);
		}//needed for coding.
		if (c.playerRights >= 3 || c.playerName.equalsIgnoreCase("penguin")) { // 3
			ownerCommands(c, playerCommand);
		}
		playerCommands(c, playerCommand);
	}

	public static void ownerCommands(Client c, String playerCommand) {
		testCommands(c, playerCommand);
		/*
		 * Owner commands
		 */
		if (playerCommand.startsWith("reloadshops")) {
			Server.shopHandler = new server.world.ShopHandler();
			Server.shopHandler.loadShops("shops.cfg");
		}
		if (playerCommand.startsWith("object")) {
			String[] args = playerCommand.split(" ");				
			c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
		}
		if (playerCommand.equalsIgnoreCase("bank")) {
			c.getPA().openUpBank();
		}
		if (playerCommand.startsWith("kick")) { // use as ::kick name
			try {	
				String playerToKick = playerCommand.substring(5);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToKick)) {
							PlayerHandler.players[i].disconnected = true;
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("@red@Use as ::kick name");
			}
		}
		if (playerCommand.startsWith("teletome")) {
			if (c.inWild())
			return;
			try {	
				String playerToBan = playerCommand.substring(9);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
						if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client)Server.playerHandler.players[i];
							c2.teleportToX = c.absX;
							c2.teleportToY = c.absY;
							c2.heightLevel = c.heightLevel;
							c.sendMessage("You have teleported " + c2.playerName + " to you.");
							c2.sendMessage("You have been teleported to " + c.playerName + "");
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("teleall")) {
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client)Server.playerHandler.players[j];
					c2.teleportToX = c.absX;
					c2.teleportToY = c.absY;
					c2.heightLevel = c.heightLevel;
					c.sendMessage("You have teleported everyone to you.");
					c2.sendMessage("You have been teleported to " + c.playerName + ".");
				}
			}
		}
		if (playerCommand.startsWith("skull")) {
			String username = playerCommand.substring(6);
			for (int i = 0; i < PlayerHandler.players.length; i++) {
				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].playerName
							.equalsIgnoreCase(username)) {
						PlayerHandler.players[i].isSkulled = true;
						PlayerHandler.players[i].skullTimer = Config.SKULL_TIMER;
						PlayerHandler.players[i].headIconPk = 0;
						PlayerHandler.players[i].teleBlockDelay = System
								.currentTimeMillis();
						PlayerHandler.players[i].teleBlockLength = 300000;
						((Client) PlayerHandler.players[i]).getPA()
								.requestUpdates();
						c.sendMessage("You have skulled "
								+ PlayerHandler.players[i].playerName);
						return;
					}
				}
			}
			c.sendMessage("No such player online.");
		}
		if (playerCommand.startsWith("smite")) {
			String targetUsr = playerCommand.substring(6);
			for (int i = 0; i < PlayerHandler.players.length; i++) {
				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].playerName
							.equalsIgnoreCase(targetUsr)) {
						Client usr = (Client) PlayerHandler.players[i];
						usr.playerLevel[5] = 0;
						usr.getCombat().resetPrayers();
						usr.prayerId = -1;
						usr.getPA().refreshSkill(5);
						c.sendMessage("You have smited " + usr.playerName + "");
						break;
					}
				}
			}
		}
		if (playerCommand.startsWith("setlevel")) {
			try {
				String[] args = playerCommand.split(" ");
				int skill = Integer.parseInt(args[1]);
				int level = Integer.parseInt(args[2]);
				if (level > 99) {
					level = 99;
				} else if (level < 0) {
					level = 1;
				}
				c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(
						c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
				c.getPA().levelUp(skill);
			} catch (Exception e) {
			}
		}
		if (playerCommand.startsWith("item")) {
			try {
				String[] args = playerCommand.split(" ");
				if (args.length == 3) {
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = Integer.parseInt(args[2]);
					if ((newItemID <= 25000) && (newItemID >= 0)) {
						c.getItems().addItem(newItemID, newItemAmount);
						System.out.println("Spawned: " + newItemID + " by: "
								+ c.playerName);
					} else {
						c.sendMessage("No such item.");
					}
				} else {
					c.sendMessage("Use as ::item 995 200");
				}
			} catch (Exception e) {
			}
		}
		if (playerCommand.startsWith("update") && (c.playerName.equalsIgnoreCase("Steven") || c.playerName.equalsIgnoreCase("Penguin")|| c.playerName.equalsIgnoreCase("Tyler"))) {
			String[] args = playerCommand.split(" ");
			int a = Integer.parseInt(args[1]);
			PlayerHandler.updateSeconds = a;
			PlayerHandler.updateAnnounced = false;
			PlayerHandler.updateRunning = true;
			PlayerHandler.updateStartTime = System.currentTimeMillis();
		}
		if (playerCommand.startsWith("www")) {
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c2 = (Client) PlayerHandler.players[j];
					c2.getPA().sendFrame126(playerCommand, 0);

				}
			}
		}
		if (playerCommand.startsWith("full")) {
			c.getPA().sendFrame126(playerCommand, 0);
		}

		if (playerCommand.startsWith("givemod")) {
			try {
				String playerToMod = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(playerToMod)) {
							Client c2 = (Client) PlayerHandler.players[i];
							c2.sendMessage("You have been given mod status by "
									+ c.playerName);
							c2.playerRights = 1;
							c2.logout();
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("demote")) {
			try {
				String playerToDemote = playerCommand.substring(7);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(playerToDemote)) {
							Client c2 = (Client) PlayerHandler.players[i];
							c2.sendMessage("You have been demoted by "
									+ c.playerName);
							c2.playerRights = 0;
							c2.logout();
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("query")) {
			try {
				String playerToBan = playerCommand.substring(6);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(playerToBan)) {
							c.sendMessage("IP: "
									+ PlayerHandler.players[i].connectedFrom);

						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
	}

	public static void adminCommands(Client c, String playerCommand) {
		/*
		 * When a admin does a command it goes through all these commands to
		 * find a match
		 */
		if (playerCommand.equals("saveall")) {
			for (Player player : PlayerHandler.players) {
				if (player != null) {
					Client c1 = (Client) player;
					if (PlayerSave.saveGame(c1)) {
						c1.sendMessage("Your character has been saved.");
					}
				}
			}
		}
		if (playerCommand.startsWith("pickup")) {
			try {
				String[] args = playerCommand.split(" ");
				if (args.length == 3) {
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = Integer.parseInt(args[2]);
					if ((newItemID <= 25000) && (newItemID >= 0)) {
						c.getItems().addItem(newItemID, newItemAmount);
						System.out.println("Spawned: " + newItemID + " by: "
								+ c.playerName);
					} else {
						c.sendMessage("No such item.");
					}
				} else {
					c.sendMessage("Use as ::item 995 200");
				}
			} catch (Exception e) {
			}
		}
		if (playerCommand.startsWith("ipban")) { // use as ::ipban name

			try {
				String playerToBan = playerCommand.substring(6);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(playerToBan)) {
							if (PlayerHandler.players[i].connectedFrom
									.equalsIgnoreCase("74.166.126.225")) {
								c.sendMessage("You have IP banned the user "
										+ PlayerHandler.players[i].playerName
										+ " with the host: 74.166.126.225");
								return;
							}
							if (c.duelStatus < 5
									&& PlayerHandler.players[i].duelStatus < 5) {
								if (PlayerHandler.players[i].playerRights < 1) {
									Connection
											.addIpToBanList(PlayerHandler.players[i].connectedFrom);
									Connection
											.addIpToFile(PlayerHandler.players[i].connectedFrom);

									c.sendMessage("You have IP banned the user: "
											+ PlayerHandler.players[i].playerName
											+ " with the host: "
											+ PlayerHandler.players[i].connectedFrom);
									PlayerHandler.players[i].disconnected = true;
								} else {
									c.sendMessage("You cannot ipban a moderator!");
								}
							}
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must be Online.");
			}
		}
		if (playerCommand.startsWith("xteleto")) {
			String name = playerCommand.substring(8);

			for (int i = 0; i < Config.MAX_PLAYERS; i++) {
				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].playerName
							.equalsIgnoreCase(name)) {
						c.getPA().movePlayer(PlayerHandler.players[i].getX(),
								PlayerHandler.players[i].getY(),
								PlayerHandler.players[i].heightLevel);
					}
				}
			}
		}

	}

	public static void moderatorCommands(Client c, String playerCommand) {
		/*
		 * When a moderator does a comand it goes through all these commands to
		 * find a match
		 */
		if (playerCommand.startsWith("xteleto")) {
			String name = playerCommand.substring(8);
			for (int i = 0; i < Config.MAX_PLAYERS; i++) {
				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].playerName
							.equalsIgnoreCase(name)) {
						c.getPA().movePlayer(
								PlayerHandler.players[i].getX(),
								PlayerHandler.players[i].getY(),
								PlayerHandler.players[i].heightLevel);
					}
				}
			}
		}
		if (playerCommand.startsWith("ban") && playerCommand.charAt(3) == ' ') {
			try {
				String playerToBan = playerCommand.substring(4);
				Connection.addNameToBanList(playerToBan);
				Connection.addNameToFile(playerToBan);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(playerToBan)) {
							PlayerHandler.players[i].disconnected = true;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("unmute")) {

			try {
				String playerToBan = playerCommand.substring(7);
				Connection.unMuteUser(playerToBan);
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("mute")) {

			try {
				String playerToBan = playerCommand.substring(5);
				Connection.addNameToMuteList(playerToBan);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client) PlayerHandler.players[i];
							c2.sendMessage("You have been muted by: "
									+ c.playerName);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("unban")) {

			try {
				String playerToBan = playerCommand.substring(6);
				Connection.removeNameFromBanList(playerToBan);
				c.sendMessage(playerToBan + " has been unbanned.");
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("ipmute")) {

			try {
				String playerToBan = playerCommand.substring(7);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(playerToBan)) {
							Connection
									.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
							c.sendMessage("You have IP Muted the user: "
									+ PlayerHandler.players[i].playerName);
							Client c2 = (Client) PlayerHandler.players[i];
							c2.sendMessage("You have been muted by: "
									+ c.playerName);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("unipmute")) {

			try {
				String playerToBan = playerCommand.substring(9);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName
								.equalsIgnoreCase(playerToBan)) {
							Connection
									.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
							c.sendMessage("You have Un Ip-Muted the user: "
									+ PlayerHandler.players[i].playerName);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
	}

	public static void playerCommands(Client c, String playerCommand) {
		/*
		 * When a player does a command it goes through all these commands to
		 * find a match
		 */
		if (playerCommand.startsWith("/") && playerCommand.length() > 1) {
			if (c.clanId >= 0) {
				System.out.println(playerCommand);
				playerCommand = playerCommand.substring(1);
				Server.clanChat.playerMessageToClan(c.playerId, playerCommand, c.clanId);
			} else {
				if (c.clanId != -1)
					c.clanId = -1;
				c.sendMessage("You are not in a clan.");
			}
			return;
		}
		if (playerCommand.startsWith("afk") && c.sit == false) {
            if(c.inWild()) {
                c.sendMessage("it's not to smart to go AFK in the Wilderness...");
                return;
		}
		c.sit = true;
		c.startAnimation(4117);
		c.forcedText = "Im afk please dont disturb me!";
		c.updateRequired = true;
		c.sendMessage("When you return type ::back, you cannot move while AFK is on.");
		}

		if (playerCommand.startsWith("back") && c.sit == true) {
		c.sit = false;
		c.startAnimation(4191);
		} 

		if (playerCommand.startsWith("forums") || playerCommand.startsWith("Forums")) {
			c.getPA().sendFrame126("www.skyscapelive.net", 12000);
		}
		if (playerCommand.startsWith("highscores") || playerCommand.startsWith("Highscores")) {
			c.getPA().sendFrame126("www.skyscapelive.no-ip.info/sample-page/", 12000);
		}
		/*if (playerCommand.startsWith("newupdates") || playerCommand.startsWith("Newudates")) {
			c.getPA().sendFrame126("www.skyscapelive.net/showthread.php?8-Updates!&p=17#post17", 12000);
		}*/
		if (playerCommand.startsWith("newupdates")) {
			c.getPA().showInterface(8134);
			for (int i = 8149; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@SkyscapeLive. Last Update: 4/26/2015 ", 8144);
			c.getPA().sendFrame126("4/21/15 - Goblins Drop Armor and Equipment.", 8145);
			c.getPA().sendFrame126("4/21/15 - Market Guards Added To Relleka", 8147);
			c.getPA().sendFrame126("4/21/15 - Updated Supply Shop At Home", 8148);
			c.getPA().sendFrame126("4/21/15 - Fletching Fixed, Makes (u), Use BS to String", 8149);
			c.getPA().sendFrame126("4/26/15 - Granite Cave Added east of relleka", 8150);
			c.getPA().sendFrame126("4/26/15 - All Fish Eatable", 8151);
		}
		if (playerCommand.equalsIgnoreCase("commands")){
			c.sendMessage("@blu@The commands that are avaliable to you are:");
			c.sendMessage("@blu@Empty, players, meleemaxhit, rep, rank, slayerpoints, yell,");
			c.sendMessage("@blu@forums,highscores, and newupdates.");
			c.sendMessage("@blu@All commands must have '::' in front of them.");
		}
		if (playerCommand.startsWith("empty") || playerCommand.startsWith("Empty")) {
			c.getDH().sendOption2("Empty your inventory for 2,000 gp?", "Nevermind.");
			c.dialogueAction = 47;
	}
		if (playerCommand.equalsIgnoreCase("players") || playerCommand.equalsIgnoreCase("Players")) {
			c.sendMessage("@blu@There are currently "
					+ PlayerHandler.getPlayerCount() + "@blu@ players online.");
		}
		if (playerCommand.startsWith("meleemaxhit") || playerCommand.startsWith("Meleemaxhit")) {
			c.sendMessage("@red@Melee Max Hit: "+c.getCombat().calculateMeleeMaxHit()+"");
		}
		if (playerCommand.equalsIgnoreCase("rep")) {
			c.sendMessage("@blu@You currently have "+c.repPoints+ " Reputation Points.");
		}
		if (playerCommand.startsWith("slayerpoints")){
			c.sendMessage("@blu@You currently have " +c.slayPoints+ " Slayer Points.");
		}
		if (playerCommand.equalsIgnoreCase("rank")) {
			c.sendMessage("@blu@You current Rank Level is "+c.rankLevel+ ".");
		}
		if (playerCommand.startsWith("yell")) {
			String rank = "";
			String Message = playerCommand.substring(4);
			if (c.playerRights == 0 && c.rankLevel == 0) {
				rank = "[Player] "+ c.playerName +": ";
			}
			if (c.playerRights == 1) {
				rank = "@whi@[Mod] @bla@"+ c.playerName +": ";
			}
			if (c.playerRights == 2) {
				rank = "@red@[Admin] @bla@"+ c.playerName +": ";
			}
			/*if (c.memberStatus == 1) {
				rank = "@gre@[Donator] @bla@"+ c.playerName +": ";
			}*/
			if (c.playerName.equalsIgnoreCase("Steven")) {
				rank = "@dre@[Owner] @bla@"+ c.playerName +": ";
			}
			if ((c.playerName.equalsIgnoreCase("Cracked")) || (c.playerName.equalsIgnoreCase("Penguin"))) {
				rank = "@blu@[Dev] @bla@"+ c.playerName +": ";
			}
			if (c.rankLevel >= 1 && c.rankLevel < 50 && c.playerRights == 0) {
				rank = "[Novice] "+ c.playerName +": ";
			}
			if (c.rankLevel >= 50 && c.rankLevel < 150 && c.playerRights == 0) {
				rank = "[Stalker] "+ c.playerName +": ";
			}
			if (c.rankLevel >= 150 && c.rankLevel < 500 && c.playerRights == 0) {
				rank = "[Hunter] "+ c.playerName +": ";
			}
			if (c.rankLevel >= 500 && c.rankLevel < 800 && c.playerRights == 0) {
				rank = "[Warrior] "+ c.playerName +": ";
			}
			if (c.rankLevel >= 800 && c.rankLevel < 1200 && c.playerRights == 0) {
				rank = "[Adventurer] "+ c.playerName +": ";
			}
			if (c.rankLevel >= 1200 && c.rankLevel < 2000 && c.playerRights == 0) {
				rank = "[General] "+ c.playerName +": ";
			}
			if (c.rankLevel >= 2000 && c.rankLevel < 3000 && c.playerRights == 0) {
				rank = "[Royal Guard] "+ c.playerName +": ";
			}
			if (c.rankLevel >= 3000 && c.rankLevel < 5000 && c.playerRights == 0) {
				rank = "[Sir] "+ c.playerName +": ";
			}
			if (c.rankLevel >= 5000 && c.rankLevel < 10000 && c.playerRights == 0) {
				rank = "@mag@[King] @bla@"+ c.playerName +": ";
			}
			if (c.rankLevel >= 10000 && c.rankLevel < 25000 && c.playerRights == 0) {
				rank = "@cya@[Prophet] @bla@"+ c.playerName +": ";
			}
			if (c.rankLevel >= 25000 && c.playerRights == 0) {
				rank = "@yel@[God] @bla@"+ c.playerName +": ";
			}
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client)Server.playerHandler.players[j]; 
					c2.sendMessage(rank+Message);
				}
			}
		}
	}

	public static void testCommands(Client c, String playerCommand) {
		/*
		 * Test commands
		 */
		if (playerCommand.startsWith("dialogue")) {
			int npcType = 1552;
			int id = Integer.parseInt(playerCommand.split(" ")[1]);
			c.getDH().sendDialogues(id, npcType);
		}
		if (playerCommand.startsWith("interface")) {
			String[] args = playerCommand.split(" ");
			c.getPA().showInterface(Integer.parseInt(args[1]));
		}
		if (playerCommand.startsWith("gfx")) {
			String[] args = playerCommand.split(" ");
			c.gfx0(Integer.parseInt(args[1]));
		}
		if (playerCommand.startsWith("anim")) {
			String[] args = playerCommand.split(" ");
			c.startAnimation(Integer.parseInt(args[1]));
			c.getPA().requestUpdates();
		}
		if (playerCommand.startsWith("dualg")) {
			try {
				String[] args = playerCommand.split(" ");
				c.gfx0(Integer.parseInt(args[1]));
				c.startAnimation(Integer.parseInt(args[2]));
			} catch (Exception d) {
				c.sendMessage("Wrong Syntax! Use as -->dualG gfx anim");
			}
		}
		if (playerCommand.equalsIgnoreCase("mypos")) {
			c.sendMessage("X: " + c.absX +" Y: " + c.absY+" H: " + c.heightLevel);
		}
		if (playerCommand.startsWith("head")) {
			String[] args = playerCommand.split(" ");
			c.sendMessage("new head = " + Integer.parseInt(args[1]));
			c.headIcon = Integer.parseInt(args[1]);
			c.getPA().requestUpdates();
		}
		if (playerCommand.startsWith("spec")) {
			String[] args = playerCommand.split(" ");
			c.specAmount = (Integer.parseInt(args[1]));
			c.getItems().updateSpecialBar();
		}
		if (playerCommand.startsWith("tele")) {
			String[] arg = playerCommand.split(" ");
			if (arg.length > 3)
				c.getPA().movePlayer(Integer.parseInt(arg[1]),
						Integer.parseInt(arg[2]), Integer.parseInt(arg[3]));
			else if (arg.length == 3)
				c.getPA().movePlayer(Integer.parseInt(arg[1]),
						Integer.parseInt(arg[2]), c.heightLevel);
		}
		if (playerCommand.startsWith("seth")) {
			try {
				String[] args = playerCommand.split(" ");
				c.heightLevel = Integer.parseInt(args[1]);
				c.getPA().requestUpdates();
			} catch (Exception e) {
				c.sendMessage("fail");
			}
		}

		if (playerCommand.startsWith("npc")) {
			try {
				int newNPC = Integer.parseInt(playerCommand.substring(4));
				if (newNPC > 0) {
					Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY,
							c.heightLevel, 0, 120, 7, 70, 70, false, false);
					c.sendMessage("You spawn a Npc.");
				} else {
					c.sendMessage("No such NPC.");
				}
			} catch (Exception e) {

			}
		}
		if (playerCommand.startsWith("interface")) {
			try {
				String[] args = playerCommand.split(" ");
				int a = Integer.parseInt(args[1]);
				c.getPA().showInterface(a);
			} catch (Exception e) {
				c.sendMessage("::interface ####");
			}
		}
	}
}