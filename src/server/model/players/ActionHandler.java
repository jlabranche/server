package server.model.players;

import server.Config;
import server.Server;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.model.objects.Object;
import server.model.players.skills.Mining;
import server.model.players.skills.Tanning;
import server.model.players.skills.Thieving;
import server.model.players.skills.Woodcutting;
import server.util.Misc;
import server.util.ScriptManager;
import server.model.players.skills.Thieving;;

public class ActionHandler {

	public Client c;

	public ActionHandler(Client Client) {
		this.c = Client;
	}

	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		if (c.playerName.equalsIgnoreCase("penguin")) {
			c.sendMessage("Object1 type: " + objectType);
		}
		// c.sendMessage("Object type: " + objectType);
		if (Woodcutting.playerTrees(c, objectType)) {
			Woodcutting.attemptData(c, objectType, obX, obY);
			return;
		}	

		switch (objectType) {
		//Cave Agility Area
		case 5847:
			if (c.objectX == 2760 && c.objectY == 3658) {
				c.startAnimation(839);
				c.getPA().movePlayer(2762, 3660, 0);
			}
			break;
		case 10076:
				c.sendMessage("You Do Some Push-Ups and Gain Some Strenght Exp");
				break;
		case 5857:
				c.getPA().movePlayer(3577, 9927, 0);
				c.sendMessage("You enter the cave.");
				break;
		/*case 5170:
			if (c.objectX == 3510 && c.objectY == 9967) {
				c.getPA().movePlayer(3511, 9967, 0);
			}
			break;*/
		case 2492:
			if (c.killCount >= 20) {
				c.getDH().sendOption4("Armadyl", "Bandos", "Saradomin",
						"Zamorak");
				c.dialogueAction = 20;
			} else {
				c.sendMessage("You need 20 kill count before teleporting to a boss chamber.");
			}
			break;
			
		case 1738:
		case 15638:
			if(c.heightLevel == 0) {
				if (c.objectX == 2839 && c.objectY == 3537) {
					c.getPA().movePlayer(2840, 3539, 2);
				}
			}
			if(c.heightLevel == 2) {
				if (c.objectX == 2840 && c.objectY == 3538) {
					c.getPA().movePlayer(2839, 3539, 0);
				}
			}
		break;
			
		case 15644:
		case 15641:
			if(c.heightLevel == 0) {
				if (c.absX == 2855 && c.absY == 3545 || c.absX == 2854 && c.absY == 3545) {
					c.getPA().movePlayer(2855, 3546, 0);
				}
				if (c.absX == 2855 && c.absY == 3546 || c.absX == 2854 && c.absY == 3546) {
					c.getPA().movePlayer(2855, 3545, 0);					
				}
			}
			if(c.heightLevel == 2) {
				Server.getWarriorsGuild().handleKamfreena(c, true);
			}
			break;
			
		case 15653:
			if (c.absX == 2876 && c.absY == 3546) {
				c.getPA().movePlayer(2877, 3546, 0);
			}
			if (c.absX == 2877 && c.absY == 3546) {
				c.getPA().movePlayer(2876, 3546, 0);
				c.sendMessage("Welcome to the Warriors Guild.");
			}
			break;
			
	//New Sky Scape Stuff
		/*case 1733: //Yanille Dung in
			if (c.objectX == 2603 && c.objectY == 3078) {
				c.getPA().movePlayer(3007, 9550, 0);
			}
			break;
		case 1734: //Yanille Dung out
			if (c.objectX == 3008 && c.objectY == 9550) {
				c.getPA().movePlayer(2606, 3079, 0);
			}
			break;*/
	//Dodian Tribute Red Key
		case 1733: //Yanille Dung in
			if (c.objectX == 2603 && c.objectY == 3078) {
				if (c.getItems().playerHasItem(1543, 1)) {
				c.getPA().movePlayer(2602, 9478, 0);
				} else {
				c.getPA().sendStatement("You need a red key to go down there.");
				}
			}
			break;
		case 1734: //Yanille Dung out
			if (c.objectX == 2603 && c.objectY == 9478) {
				c.getPA().movePlayer(2606, 3079, 0);
			}
			if (c.objectX == 2724 && c.objectY == 9774) {
				c.getPA().movePlayer(2647, 3658, 0);
			}
			break;
			//Dodian Tribute Red Key Boss
				case 1728: //Yanille Dung in
					if (c.objectX == 2620 && c.objectY == 9497) {
						if (c.getItems().playerHasItem(1543, 1)) {
						c.getPA().movePlayer(2615, 9506, 0);
						} else {
						c.getPA().sendStatement("You need a red key to go down there.");
						}
						}
						break;
				case 2318: //Yanille Dung out
					if (c.objectX == 2615 && c.objectY == 9504) {
						c.getPA().movePlayer(2620, 9496, 0);
					}
					break;
		case 1746:
			if (c.objectX == 2715 && c.objectY == 3470) {
				c.getPA().movePlayer(c.absX, c.absY, 0);
				c.sendMessage("Ladder not coded in");
			}
			break;
		case 8742:
			if (c.absX == 2306 && c.absY == 3195 || c.absX == 2306 && c.absY == 3195) {
				c.startAnimation(844);
			while (c.absX != 2304 && c.absY != 3195) {
				c.getPA().walkTo(2306 - c.absX, 3195 - c.absY);
			}
			c.agilityWalk(c, 844, -2, 0);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (c.absX == 2304 && c.absY == 3195) {
						container.stop();
					}
				}
				@Override
				public void stop() {		
					c.sendMessage("You crawl under the tree's.");
					c.resetAgilityWalk(c);
				}
			}, 1);
			} else {
				c.getPA().sendStatement("You are to far away.");
			}
			break;
			
		//Rank Minigame
	
			
		//Monkey Bars Course
		case 2320:
			if (c.objectX == 2864 && c.objectY == 3373) {
				if (c.absX == 2865 && c.absY == 3374) {
			c.startAnimation(742);
			while (c.absX != 2865 && c.absY != 3373) {
				c.getPA().walkTo(2865 - c.absX, 3373 - c.absY);
			}
			c.agilityWalk(c, 744, 0, -6);
			//c.getPA().movePlayer(c.absX, 3373, 0);
			//c.getPA().addSkillXP((int) 7.5 * Config.AGILITY_EXPERIENCE, c.playerAgility);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (c.absX == 2865 && c.absY == 3368) {
						container.stop();
					}
				}
				@Override
				public void stop() {
					//c.getPA().addSkillXP((int) 7.5 * Config.AGILITY_EXPERIENCE, c.playerAgility);
					c.startAnimation(745);		
					c.sendMessage("Exp Here");
					c.resetAgilityWalk(c);
				}
			}, 1);
			} else {
				c.getPA().sendStatement("You are to far away.");
			}
			} else {
				c.getPA().sendStatement("Use the other side of the bars.");
			}
			break;
		
	//End Of SSL

		case 1765:
			c.getPA().movePlayer(3067, 10256, 0);
			break;
		case 2882:
		case 2883:
			if (c.objectX == 3268) {
				if (c.absX < c.objectX) {
					c.getPA().walkTo(1, 0);
				} else {
					c.getPA().walkTo(-1, 0);
				}
			}
			break;
		case 272:
			c.getPA().movePlayer(c.absX, c.absY, 1);
			break;

		case 273:
			c.getPA().movePlayer(c.absX, c.absY, 0);
			break;
		case 245:
			c.getPA().movePlayer(c.absX, c.absY + 2, 2);
			break;
		case 246:
			c.getPA().movePlayer(c.absX, c.absY - 2, 1);
			break;
		case 1766:
			c.getPA().movePlayer(3016, 3849, 0);
			break;
		case 6552:
			if (c.playerMagicBook == 0) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.sendMessage("An ancient wisdomin fills your mind.");
				c.getPA().resetAutocast();
			} else {
				c.setSidebarInterface(6, 1151); // modern
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			break;

		case 410:
			if (c.playerMagicBook == 0) {
				c.playerMagicBook = 2;
				c.setSidebarInterface(6, 29999);
				c.sendMessage("Lunar wisdom fills your mind.");
				c.getPA().resetAutocast();
			} else {
				c.setSidebarInterface(6, 1151); // modern
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			break;

		case 1816:
			c.getPA().startTeleport2(2271, 4680, 0);
			break;
		case 1817:
			c.getPA().startTeleport(3067, 10253, 0, "modern");
			break;
		case 1814:
			// ardy lever
			c.getPA().startTeleport(3153, 3923, 0, "modern");
			break;

		case 9356:
			// c.getPA().enterCaves();
			c.sendMessage("Temporarily removed due to bugs.");
			break;

		case 9357:
			c.getPA().resetTzhaar();
			break;

		case 8959:
			if (c.getX() == 2490 && (c.getY() == 10146 || c.getY() == 10148)) {
				if (c.getPA().checkForPlayer(2490,
						c.getY() == 10146 ? 10148 : 10146)) {
					new Object(6951, c.objectX, c.objectY, c.heightLevel, 1,
							10, 8959, 15);
				}
			}
			break;
			
		case 2213:
		case 14367:
		case 11758:
		case 4483:
		case 3193:
			c.getPA().openUpBank();
			break;

		case 10177:
			c.getPA().movePlayer(1890, 4407, 0);
			break;
		case 10230:
			c.getPA().movePlayer(2900, 4449, 0);
			break;
		case 10229:
			c.getPA().movePlayer(1912, 4367, 0);
			break;
		case 2623:
			if (c.absX >= c.objectX)
				c.getPA().walkTo(-1, 0);
			else
				c.getPA().walkTo(1, 0);
			break;
		// pc boat
		case 14315:
			c.getPA().movePlayer(2661, 2639, 0);
			break;
		case 14314:
			c.getPA().movePlayer(2657, 2639, 0);
			break;

		case 1596:
		case 1597:
			if (c.getY() >= c.objectY)
				c.getPA().walkTo(0, -1);
			else
				c.getPA().walkTo(0, 1);
			break;

		case 14235:
		case 14233:
			if (c.objectX == 2670)
				if (c.absX <= 2670)
					c.absX = 2671;
				else
					c.absX = 2670;
			if (c.objectX == 2643)
				if (c.absX >= 2643)
					c.absX = 2642;
				else
					c.absX = 2643;
			if (c.absX <= 2585)
				c.absY += 1;
			else
				c.absY -= 1;
			c.getPA().movePlayer(c.absX, c.absY, 0);
			break;

		case 14829:
		case 14830:
		case 14827:
		case 14828:
		case 14826:
		case 14831:
			// Server.objectHandler.startObelisk(objectType);
			Server.objectManager.startObelisk(objectType);
			break;
		case 4387:
			// Server.castleWars.joinWait(c,1);
			break;

		case 4388:
			// Server.castleWars.joinWait(c,2);
			break;

		case 4408:
			// Server.castleWars.joinWait(c,3);
			break;

		case 9369:
			if (c.getY() > 5175)
				c.getPA().movePlayer(2399, 5175, 0);
			else
				c.getPA().movePlayer(2399, 5177, 0);
			break;

		case 9368:
			if (c.getY() < 5169) {
				Server.fightPits.removePlayerFromPits(c.playerId);
				c.getPA().movePlayer(2399, 5169, 0);
			}
			break;
		case 4411:
		case 4415:
		case 4417:
		case 4418:
		case 4419:
		case 4420:
		case 4469:
		case 4470:
		case 4911:
		case 4912:
		case 1747:
			if (c.objectX == 2715 && c.objectY == 3470) {
				c.getPA().movePlayer(c.absX, c.absY, +1);
			} else {
				c.sendMessage("Ladder not coded in");
			}
		case 1757:
			if (c.objectX == 2892 && c.objectY == 9907) {
				c.getPA().movePlayer(2649, 3661, 0);
			}
			// Server.castleWars.handleObjects(c, objectType, obX, obY);
			break;

		case 2286:
		case 154:
		case 4058:
		case 2295:
		case 2285:
		case 2313:
		case 2312:
		case 2314:
			c.getAgility().handleGnomeCourse(objectType, obX, obY);
			break;

			/*
			 * Barrows Chest
			 */
		case 10284:
			if (c.barrowsKillCount < 5) {
				c.sendMessage("You haven't killed all the brothers");
			}
			if (c.barrowsKillCount == 5
					&& c.barrowsNpcs[c.randomCoffin][1] == 1) {
				c.sendMessage("I have already summoned this npc.");
			}
			if (c.barrowsNpcs[c.randomCoffin][1] == 0
					&& c.barrowsKillCount >= 5) {
				Server.npcHandler.spawnNpc(c, c.barrowsNpcs[c.randomCoffin][0],
						3551, 9694 - 1, 0, 0, 120, 30, 200, 200, true, true);
				c.barrowsNpcs[c.randomCoffin][1] = 1;
			}
			if ((c.barrowsKillCount > 5 || c.barrowsNpcs[c.randomCoffin][1] == 2)
					&& c.getItems().freeSlots() >= 2) {
				c.getPA().resetBarrows();
				c.getItems().addItem(c.getPA().randomRunes(),
						Misc.random(150) + 100);
				if (Misc.random(2) == 1)
					c.getItems().addItem(c.getPA().randomBarrows(), 1);
				c.getPA().startTeleport(3564, 3288, 0, "modern");
			} else if (c.barrowsKillCount > 5 && c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need at least 2 inventory slot opened.");
			}
			break;
		/*
		 * Doors
		 */
		case 6749:
			if (obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6730:
			if (obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6727:
			if (obX == 3551 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
		case 6746:
			if (obX == 3552 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
		case 6748:
			if (obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6729:
			if (obX == 3545 && obY == 9677) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6726:
			if (obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6745:
			if (obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6743:
			if (obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
		case 6724:
			if (obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
		/*
		 * Cofins
		 */
		case 6707: // verac
			c.getPA().movePlayer(3556, 3298, 0);
			break;

		case 6823:
			if (server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[0][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2030, c.getX(), c.getY() - 1, -1,
						0, 120, 25, 200, 200, true, true);
				c.barrowsNpcs[0][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6706: // torag
			c.getPA().movePlayer(3553, 3283, 0);
			break;

		case 6772:
			if (server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[1][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2029, c.getX() + 1, c.getY(), -1,
						0, 120, 20, 200, 200, true, true);
				c.barrowsNpcs[1][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6705: // karil stairs
			c.getPA().movePlayer(3565, 3276, 0);
			break;
		case 6822:
			if (server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[2][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2028, c.getX(), c.getY() - 1, -1,
						0, 90, 17, 200, 200, true, true);
				c.barrowsNpcs[2][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6704: // guthan stairs
			c.getPA().movePlayer(3578, 3284, 0);
			break;
		case 6773:
			if (server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[3][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2027, c.getX(), c.getY() - 1, -1,
						0, 120, 23, 200, 200, true, true);
				c.barrowsNpcs[3][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6703: // dharok stairs
			c.getPA().movePlayer(3574, 3298, 0);
			break;
		case 6771:
			if (server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[4][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2026, c.getX(), c.getY() - 1, -1,
						0, 120, 45, 250, 250, true, true);
				c.barrowsNpcs[4][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6702: // ahrim stairs
			c.getPA().movePlayer(3565, 3290, 0);
			break;
		case 6821:
			if (server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[5][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2025, c.getX(), c.getY() - 1, -1,
						0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[5][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 2090:// copper
		case 2091:
			c.mining[0] = 436;
			c.mining[1] = 1;
			c.mining[2] = 18;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
			break;
			
		case 2491://rune ess
			c.mining[0] = 1436;
			c.mining[1] = 25;
			c.mining[2] = 20;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
			break;

		case 2094:// tin
			c.mining[0] = 438;
			c.mining[1] = 1;
			c.mining[2] = 18;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
			break;

		case 145856:
		case 2092:
		case 2093: // iron
			c.mining[0] = 440;
			c.mining[1] = 15;
			c.mining[2] = 35;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
			break;

		case 14850:
		case 14851:
		case 14852:
		case 2096:
		case 2097: // coal
			c.mining[0] = 453;
			c.mining[1] = 30;
			c.mining[2] = 50;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
			break;

		case 2098:
		case 2099:
			c.mining[0] = 444;
			c.mining[1] = 40;
			c.mining[2] = 65;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
			break;

		case 2102:
		case 2103:
		case 14853:
		case 14854:
		case 14855: // mith ore
			c.mining[0] = 447;
			c.mining[1] = 55;
			c.mining[2] = 80;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
			break;

		case 2105:
		case 14862: // addy ore
			c.mining[0] = 449;
			c.mining[1] = 70;
			c.mining[2] = 95;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
			break;

		case 14859:
		case 14860: // rune ore
			c.mining[0] = 451;
			c.mining[1] = 85;
			c.mining[2] = 125;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
			break;

		case 8143:
			if (c.farm[0] > 0 && c.farm[1] > 0) {
				c.getFarming().pickHerb();
			}
			break;

		// DOORS
		case 1516:
		case 1519:
			if (c.objectY == 9698) {
				if (c.absY >= c.objectY)
					c.getPA().walkTo(0, -1);
				else
					c.getPA().walkTo(0, 1);
				break;
			}
		case 1530:
		case 1531:
		case 1533:
		case 1600:
		case 1601:
		case 1551:
		case 1553:
		case 1534:
		case 11712:
		case 11711:
		case 11707:
		case 11708:
		case 6725:
		case 3198:
		case 3197:
		case 2559:
		case 4247:
			Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY,
					0);
			break;

		case 9319:
			if (c.heightLevel == 0)
				c.getPA().movePlayer(c.absX, c.absY, 1);
			else if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 2);
			break;

		case 9320:
			if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 0);
			else if (c.heightLevel == 2)
				c.getPA().movePlayer(c.absX, c.absY, 1);
			break;

		case 4496:
		case 4494:
			if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 0);
			}
			break;

		case 4493:
			if (c.heightLevel == 0) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
			break;

		case 4495:
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
			break;

		case 5126:
			if (c.absY == 3554)
				c.getPA().walkTo(0, 1);
			else
				c.getPA().walkTo(0, -1);
			break;

		case 1755:
			if (c.objectX == 2884 && c.objectY == 9797)
				c.getPA().movePlayer(c.absX, c.absY - 6400, 0);
			break;
		case 1759:
			if (c.objectX == 2884 && c.objectY == 3397)
				c.getPA().movePlayer(c.absX, c.absY + 6400, 0);
			break;
		case 409:
		case 2640:
		case 4859:
		case 8749:
		case 10638:
			if (c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;
		case 26289://minigame altar
			if (c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5]) && c.getItems().playerHasItem(995, 7000)) {
				c.getItems().deleteItem(995,c.getItems().getItemSlot(995), 7000);
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else if (!c.getItems().playerHasItem(995, 7000)){
				c.sendMessage("You must tribute 7,000gp to use this altar");
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;
		case 2873:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Saradomin blesses you with a cape.");
				c.getItems().addItem(2412, 1);
			}
			break;
		case 2875:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Guthix blesses you with a cape.");
				c.getItems().addItem(2413, 1);
			}
			break;
		case 2874:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Zamorak blesses you with a cape.");
				c.getItems().addItem(2414, 1);
			}
			break;
		case 2879:
			c.getPA().movePlayer(2538, 4716, 0);
			break;
		case 2878:
			c.getPA().movePlayer(2509, 4689, 0);
			break;
		case 5960:
			c.getPA().startTeleport2(3090, 3956, 0);
			break;

		case 1815:
			c.getPA().startTeleport2(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0);
			break;

		case 9706:
			c.getPA().startTeleport2(3105, 3951, 0);
			break;
		case 9707:
			c.getPA().startTeleport2(3105, 3956, 0);
			break;

		case 5959:
			c.getPA().startTeleport2(2539, 4712, 0);
			break;

		case 2558:
			c.sendMessage("This door is locked.");
			break;

		case 9294:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(c.objectX + 1, c.absY, 0);
			} else if (c.absX > c.objectX) {
				c.getPA().movePlayer(c.objectX - 1, c.absY, 0);
			}
			break;

		case 9293:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(2892, 9799, 0);
			} else {
				c.getPA().movePlayer(2886, 9799, 0);
			}
			break;
		case 10529:
		case 10527:
			if (c.absY <= c.objectY)
				c.getPA().walkTo(0, 1);
			else
				c.getPA().walkTo(0, -1);
			break;
		case 20365:
		case 2644:
			c.playerIsSpinning = true;
			c.getCrafting().flaxToBowString();
			break;
		case 2781:
			//c.sendMessage("TEST");
		case 3044:
			c.getSmithing().sendSmelting();
			break;
		case 733:
			c.startAnimation(451);
			if (c.objectX == 3158 && c.objectY == 3951) {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 1, 10,
						733, 50);
			} else {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 0, 10,
						733, 50);
			}
			break;
			
			case 27255:	//KeyChest
				if(c.getItems().playerHasItem(3451, 1) && c.getItems().freeSlots() >= 1) {
					c.getItems().deleteItem(3451,1);
					c.getItems().addItem(c.getPA().randomClueLvl1(), 1);
					c.getItems().addItem(c.getPA().randomClueLvl1(), 1);
				} else if(c.getItems().playerHasItem(3460, 1) && c.getItems().freeSlots() >= 1) {
					c.getItems().deleteItem(3460,1);
					c.getItems().addItem(c.getPA().randomClueLvl1(), 1);
					c.getItems().addItem(c.getPA().randomClueLvl2(), 1);
				} else if(c.getItems().playerHasItem(3469, 1) && c.getItems().freeSlots() >= 1) {
					c.getItems().deleteItem(3469,1);
					c.getItems().addItem(c.getPA().randomClueLvl2(), 1);
					c.getItems().addItem(c.getPA().randomClueLvl3(), 1);
				} else  if(c.getItems().playerHasItem(3451, 1) || c.getItems().playerHasItem(3460, 1) || c.getItems().playerHasItem(3469, 1) && c.getItems().freeSlots() <= 2) {
					c.sendMessage("You need at least 1 free slot.");
				} else if(!c.getItems().playerHasItem(3451, 1) || !c.getItems().playerHasItem(3460, 1) || !c.getItems().playerHasItem(3469, 1)){
					c.sendMessage("It seems to be locked, maybe there's a key somewhere.");
				}
				break;
				
			case 100://Rellekka Dung Trapdoors
				if (c.objectX == 2647 && c.objectY == 3657) { //legends
					c.getPA().movePlayer(2728, 9774, 0);
				}
				if (c.objectX == 2650 && c.objectY == 3661) { //heros
					c.getPA().movePlayer(2893, 9907, 0);
				}
				break;
			case 5947://hole to brimhaven dung
				if (c.objectX == 3310&& c.objectY == 2775) {
						c.getPA().movePlayer(2741, 9502, 0);
				}
				break;
			case 5946://rope to sophanem
				if (c.objectX == 2741 && c.objectY == 9503){
					c.getPA().movePlayer(3310, 2774, 0);
				}
				break;
			case 5099:
				if (c.objectX == 2698 && c.objectY == 9493){
					c.getPA().movePlayer(2698, 9500, 0);
				} else if (c.objectX == 2698 && c.objectY == 9498){
					c.getPA().movePlayer(2698, 9492, 0);
				}
				break;
			case 27254: //new mingame portals
				if (c.heightLevel == 0){
					c.getDH().sendOption2("Step into the portal for 25 rep points", "Continue fighting");
					c.dialogueAction = 45;
				} else if (c.heightLevel == 4){
					c.getDH().sendOption2("Step into the portal for 75 rep points", "Continue fighting");
					c.dialogueAction = 44;
				} else if (c.heightLevel == 8){
					c.getDH().sendOption2("Step into the portal for 400 rep points", "Continue fighting");
					c.dialogueAction = 43;				
				} else if (c.heightLevel == 12){
					c.getDH().sendOption2("Step into the portal for 1,000 rep points", "Continue fighting");
					c.dialogueAction = 42;
				} else if (c.heightLevel == 16){
					c.getDH().sendOption2("Step into the portal for 2,500 rep points", "Continue fighting");
					c.dialogueAction = 41;
				} else if (c.heightLevel == 20){
					c.getDH().sendOption2("Step into the portal and go home", "Stay and fight");
					c.dialogueAction = 40;				
				} 
				break;
		default:
			ScriptManager.callFunc("objectClick1_" + objectType, c, objectType,
					obX, obY);
			break;

		}
	}

	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		if (c.playerName.equalsIgnoreCase("penguin")) {
			c.sendMessage("Object2 type: " + objectType);
		}
		switch (objectType) {
		case 2646:
			if (c.getItems().playerHasItem(-1)) {
				c.startAnimation(2292);
				c.getItems().addItem(1779, 1);
				c.sendMessage("You pick a flax.");
			} else {
				c.getPA().sendStatement("You have no room in your inventory.");
			}
		break;
		case 20365:
		case 2644:
			c.playerIsSpinning = true;
			c.getCrafting().flaxToBowString();
			break;
		case 11666:
		case 3044:
		case 2781:
			c.getSmithing().sendSmelting();
			break;
		case 2213:
		case 14367:
		case 11758:
			c.getPA().openUpBank();
			break;
		//case 6162:
			//c.getThieving().stealFromStall(1613, 170, 90);
			//break;
		case 2558:
			if (System.currentTimeMillis() - c.lastLockPick < 3000
					|| c.freezeTimer > 0)
				break;
			if (c.getItems().playerHasItem(1523, 1)) {
				c.lastLockPick = System.currentTimeMillis();
				if (Misc.random(10) <= 3) {
					c.sendMessage("You fail to pick the lock.");
					break;
				}
				if (c.objectX == 3044 && c.objectY == 3956) {
					if (c.absX == 3045) {
						c.getPA().walkTo2(-1, 0);
					} else if (c.absX == 3044) {
						c.getPA().walkTo2(1, 0);
					}

				} else if (c.objectX == 3038 && c.objectY == 3956) {
					if (c.absX == 3037) {
						c.getPA().walkTo2(1, 0);
					} else if (c.absX == 3038) {
						c.getPA().walkTo2(-1, 0);
					}
				} else if (c.objectX == 3041 && c.objectY == 3959) {
					if (c.absY == 3960) {
						c.getPA().walkTo2(0, -1);
					} else if (c.absY == 3959) {
						c.getPA().walkTo2(0, 1);
					}
				}
			} else {
				c.sendMessage("I need a lockpick to pick this lock.");
			}
			break;
		default:
			ScriptManager.callFunc("objectClick2_" + objectType, c, objectType,
					obX, obY);
			break;
		}
	}

	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.sendMessage("Object type: " + objectType);
		switch (objectType) {
		default:
			ScriptManager.callFunc("objectClick3_" + objectType, c, objectType,
					obX, obY);
			break;
		}
	}

	public void firstClickNpc(int i) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch (i) {
		case 706:
			c.getDH().sendDialogues(9, i);
			break;
		case 436:
			c.getDH().sendDialogues(109, i);
		break;
		case 211:
			c.getDH().sendDialogues(111, i);
		break;
		case 1696:
			c.getDH().sendDialogues(112, i);
		break;
		case 2270:
			c.getDH().sendDialogues(115, i);
		break;
		
		case 2258:
			c.getDH().sendDialogues(17, i);
			break;
			
		case 4289:
			Server.getWarriorsGuild().handleKamfreena(c, false);
			break;

		case 1304:
			c.getDH().sendOption5("Home", "Edgeville", "Island",
					"Dagannoth Kings", "Next Page");
			c.teleAction = 1;
			break;
//* Servers Entire New Stuff, Old Stuff Pending Deletion to save space *//
		//Fishing//
		/*case 324: //lobs
			c.getFishing().attemptdata(c, 3);
			break;
		case 1496: //shrimp
			c.getFishing().attemptdata(c, 1);
			break;
		case 334: //mantas
			c.getFishing().attemptdata(c, 5);
			break;
		case 314: //salmon
			c.getFishing().attemptdata(c, 2);
			break;
		case 326: //monks
			c.getFishing().attemptdata(c, 4);
			break;
		case 318: //sharks
			c.getFishing().attemptdata(c, 7);
			break;
		case 1506: //tuna
			c.getFishing().attemptdata(c, 6);
			break;
		case 1497: //Anchovies
			c.getFishing().attemptdata(c, 8);
			break;
		case 321: //Swordfish
			c.getFishing().attemptdata(c, 10);
			break;
		case 322: //Sea Turtle
			c.getFishing().attemptdata(c, 11);
			break;*/
		//End Fishing//
		//Player DH Shop
		case 4929:
			c.getDH().sendDialogues(106, i);
		break;
		case 537:
			c.getDH().sendDialogues(36, i);
		break;
		case 455://Herblore
			c.getShops().openShop(5);
		break;
		case 535:
			c.getShops().openShop(2);
		break;
		case 4065:
			c.getShops().openShop(9);
		break;
		case 1210:
			c.getDH().sendDialogues(30, i);
		break;
		case 5444:
			c.getDH().sendDialogues(27, i);
		break;
		case 3393:
			c.getDH().sendDialogues(34, i);
			break;
		case 1599:
			//if (c.slayerTask <= 0) {
			c.getDH().sendDialogues(11,i);
			//} else {
			//	c.getDH().sendDialogues(13,i);
			//}
			break;
			
		case 3257: //Hand Coded Master Farmer
			if (System.currentTimeMillis() - c.lastThieve < 2000)
				return;
			if (c.playerThieving >= 50) {
				c.startAnimation(881);
				c.getItems().addItem(Thieving.SEEDS[(int) (Thieving.SEEDS.length * Math.random())], 1);
				c.getPA().addSkillXP(10 * Config.THIEVING_EXPERIENCE, c.playerThieving);
			} else {
			c.sendMessage("You must be level 50 to pickpocket the farmer.");
			}
		break;
			
		case 804:
			Tanning.sendTanningInterface(c);
			break;
		//End Player Shops
		
//End NEW Stuff
		case 1152:
			c.getDH().sendDialogues(16, i);
			break;

		case 494:
			c.getPA().openUpBank();
			break;

		case 2566:
			c.getShops().openSkillCape();
			break;

		case 3789:
			c.sendMessage((new StringBuilder()).append("You currently have ")
					.append(c.pcPoints).append(" pest control points.")
					.toString());
			break;

		case 3788:
			c.getShops().openVoid();
			break;

		case 905:
			c.getDH().sendDialogues(5, i);
			break;

		case 460:
			c.getDH().sendDialogues(3, i);
			break;

		case 462:
			c.getDH().sendDialogues(7, i);
			break;

		case 522:
		case 523:
			c.getShops().openShop(1);
			break;

		case 599:
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;
			break;

		case 904:
			c.sendMessage((new StringBuilder()).append("You have ")
					.append(c.magePoints).append(" points.").toString());
			break;
		}
	}

	public void secondClickNpc(int i) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch (i) {
		//New Stuff
		case 4929:
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;
		break;
		case 804:
			c.getShops().openShop(4);
		break;
		case 537:
			c.getShops().openShop(1);
		break;
		case 1599:
			c.getShops().openShop(3);
		break;
		case 3257: //Hand Coded Master Farmer
			if (System.currentTimeMillis() - c.lastThieve < 2000)
				return;
			if (c.playerLevel[c.playerThieving] >= 50) {
				c.startAnimation(881);
				c.getItems().addItem(Thieving.SEEDS[(int) (Thieving.SEEDS.length * Math.random())], 1);
				c.getPA().addSkillXP(10 * Config.THIEVING_EXPERIENCE, c.playerThieving);
			} else {
			c.sendMessage("You must be level 50 to pickpocket the farmer.");
			}
		break;
		case 2270:
			if (c.rankLevel >= 0 && c.rankLevel < 150) {		//Slave shop
				c.getShops().openShop(70);
				c.sendMessage("You currently have: "+c.repPoints+" reputation points.");
				c.sendMessage("Your rank level must be 150 to use the Stalker shop");
			} else if (c.rankLevel >= 150 && c.rankLevel < 800) {		//Stalker shop
				c.getShops().openShop(71);
				c.sendMessage("You currently have: "+c.repPoints+" reputation points.");
				c.sendMessage("Your rank level must be 800 to use the Adventurer shop");
			} else if (c.rankLevel >= 800 && c.rankLevel < 2000) {		//Adventurer shop
				c.getShops().openShop(72);
				c.sendMessage("You currently have: "+c.repPoints+" reputation points.");
				c.sendMessage("Your rank level must be 2,000 to use the  Royal Guard shop");
			} else if (c.rankLevel >= 2000 && c.rankLevel < 3000) {		//Royal Guard shop
				c.getShops().openShop(73);
				c.sendMessage("You currently have: "+c.repPoints+" reputation points.");
				c.sendMessage("Your rank level must be 3,000 to use the General shop");
			} else if (c.rankLevel >= 3000 && c.rankLevel < 5000) {		//General shop
				c.getShops().openShop(74);
				c.sendMessage("You currently have: "+c.repPoints+" reputation points.");
				c.sendMessage("Your rank level must be 5,000 to use the King shop");
			} else if (c.rankLevel >= 5000 && c.rankLevel < 10000) {		//King shop
				c.getShops().openShop(75);
				c.sendMessage("You currently have: "+c.repPoints+" reputation points.");
				c.sendMessage("Your rank level must be 10,000 to use the Prophet shop");
			} else if (c.rankLevel >= 10000 && c.rankLevel < 25000) {		//Prophet shop
				c.getShops().openShop(76);
				c.sendMessage("You currently have: "+c.repPoints+" reputation points.");
				c.sendMessage("Your rank level must be 25,000 to use the God shop");
			} else if (c.rankLevel >= 25000) {		//God shop
				c.getShops().openShop(77);
				c.sendMessage("You currently have: "+c.repPoints+" reputation points.");
			}
			break;
			
		//End New
		case 1282:
			c.getShops().openShop(7);
			break;

		case 3788:
			c.getShops().openVoid();
			break;

		case 494:
			c.getPA().openUpBank();
			break;

		case 904:
			c.getShops().openShop(17);
			break;

		case 522:
		case 523:
			c.getShops().openShop(1);
			break;

		case 541:
			c.getShops().openShop(5);
			break;

		case 461:
			c.getShops().openShop(2);
			break;

		case 683:
			c.getShops().openShop(3);
			break;

		case 535:
			c.getShops().openShop(4);
			break;

		case 2538:
			c.getShops().openShop(6);
			break;

		case 519:
			c.getShops().openShop(8);
			break;

		case 3789:
			c.getShops().openShop(18);
			break;
		}
	}

	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch (npcType) {
		
		case 553:
			c.getPA().startTeleport(2898, 4818, 0, "modern");
		break;
		
		case 1599:
			c.getShops().openShop(80);
		break;
		
		
		default:
			ScriptManager.callFunc("npcClick3_" + npcType, c, npcType);
			if (c.playerRights == 3)
				Misc.println("Third Click NPC : " + npcType);
			break;

		}
	}

}