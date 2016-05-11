package server.model.players.packets;

import server.Config;
import server.Server;
import server.core.PlayerHandler;
import server.model.players.Player;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.model.items.GameItem;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.skills.Cooking;
import server.model.players.skills.CraftingData.tanningData;
import server.model.players.skills.Fletching;
import server.model.players.skills.LeatherMaking;
import server.model.players.skills.Tanning;
import server.util.Misc;

/**
 * Clicking most buttons
 **/
public class ClickingButtons implements PacketType {

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(c.getInStream().buffer, 0,
				packetSize);
		// int actionButtonId = c.getInStream().readShort();
		if (c.isDead)
			return;
		if (c.playerName.equalsIgnoreCase("Steven"))
			c.sendMessage("Button id = " + actionButtonId + "");
		for (tanningData t : tanningData.values()) {
			if (actionButtonId == t.getButtonId(actionButtonId)) {
				Tanning.tanHide(c, actionButtonId);
			}
		}
		LeatherMaking.craftLeather(c, actionButtonId);
		switch (actionButtonId) {
		case 118098:
			c.getPA().castVeng();
			break;
		// crafting + fletching interface:
		case 150:
			if (c.autoRet == 0)
				c.autoRet = 1;
			else
				c.autoRet = 0;
			break;
		// 1st tele option
		case 9190:
			if (c.teleAction == 1) {
				// rock crabs
				c.getPA().spellTeleport(2676, 3715, 0);
			} else if (c.teleAction == 2) {
				// barrows
				c.getPA().spellTeleport(3565, 3314, 0);
			} else if (c.teleAction == 3) {
				// godwars
				c.getPA().spellTeleport(2916, 3612, 0);
			} else if (c.teleAction == 4) {
				// varrock wildy
				c.getPA().spellTeleport(3243, 3513, 0);
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(3046, 9779, 0);
			}

			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(45, 4832, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 5) {
				c.getPA().sendStatement("Slayer Info Here");
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2786, 4839, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2398, 4841, 0);
				c.dialogueAction = -1;
			}
			break;
		// mining - 3046,9779,0
		// smithing - 3079,9502,0

		// 2nd tele option
		case 9191:
			if (c.teleAction == 1) {
				// tav dungeon
				c.getPA().spellTeleport(2884, 9798, 0);
			} else if (c.teleAction == 2) {
				// pest control
				c.getPA().spellTeleport(2662, 2650, 0);
			} else if (c.teleAction == 3) {
				// kbd
				c.getPA().spellTeleport(3007, 3849, 0);
			} else if (c.teleAction == 4) {
				// graveyard
				c.getPA().spellTeleport(3164, 3685, 0);
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(3079, 9502, 0);
			}
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2796, 4818, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2527, 4833, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2464, 4834, 0);
				c.dialogueAction = -1;
			}
			break;
		// 3rd tele option

		case 9192:
			if (c.teleAction == 1) {
				// slayer tower
				c.getPA().spellTeleport(3428, 3537, 0);
			} else if (c.teleAction == 2) {
				// tzhaar
				c.getPA().spellTeleport(2444, 5170, 0);
			} else if (c.teleAction == 3) {
				// dag kings
				c.getPA().spellTeleport(2479, 10147, 0);
			} else if (c.teleAction == 4) {
				// 44 portals
				c.getPA().spellTeleport(2975, 3873, 0);
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(2813, 3436, 0);
			}
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2713, 4836, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 5) {
				if (c.playerLevel[c.playerSlayer] >= 50) {
				if (c.slayerTask <= 0) {
					c.getSlayer().medTask();	
				} else {
					c.getDH().sendDialogues(13, 1599);
				}
				} else {
					c.getPA().sendStatement("You must be at least level 50 slayer");
				}
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2162, 4833, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2207, 4836, 0);
				c.dialogueAction = -1;
			}
			break;
		// 4th tele option
		case 9193:
			if (c.teleAction == 1) {
				// brimhaven dungeon
				c.getPA().spellTeleport(2710, 9466, 0);
			} else if (c.teleAction == 2) {
				// duel arena
				c.getPA().spellTeleport(3366, 3266, 0);
			} else if (c.teleAction == 3) {
				// chaos elemental
				c.getPA().spellTeleport(3295, 3921, 0);
			} else if (c.teleAction == 4) {
				// gdz
				c.getPA().spellTeleport(3288, 3886, 0);
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(2724, 3484, 0);
				c.sendMessage("For magic logs, try north of the duel arena.");
			}
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2660, 4839, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 5) {
				if (c.playerLevel[c.playerSlayer] >= 80) {
				if (c.slayerTask <= 0) {
					c.getSlayer().hardTask();	
				} else {
					c.getDH().sendDialogues(13, 1599);
				}
				} else {
					c.getPA().sendStatement("You must be at least level 80 slayer");
				}
			} else if (c.dialogueAction == 11) {
				// c.getPA().spellTeleport(2527, 4833, 0); astrals here
				c.getRunecrafting().craftRunes(2489);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				// c.getPA().spellTeleport(2464, 4834, 0); bloods here
				c.getRunecrafting().craftRunes(2489);
				c.dialogueAction = -1;
			}
			break;
		// 5th tele option
		case 9194:
			if (c.teleAction == 1) {
				// island
				c.getPA().spellTeleport(2895, 2727, 0);
			} else if (c.teleAction == 2) {
				// last minigame spot
				c.sendMessage("Suggest something for this spot on the forums!");
				c.getPA().closeAllWindows();
			} else if (c.teleAction == 3) {
				// last monster spot
				c.sendMessage("Suggest something for this spot on the forums!");
				c.getPA().closeAllWindows();
			} else if (c.teleAction == 4) {
				// ardy lever
				c.getPA().spellTeleport(2561, 3311, 0);
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(2812, 3463, 0);
			}
			if (c.dialogueAction == 10 || c.dialogueAction == 11) {
				c.dialogueId++;
				c.getDH().sendDialogues(c.dialogueId, 0);
			} else if (c.dialogueAction == 5) {
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 12) {
				c.dialogueId = 17;
				c.getDH().sendDialogues(c.dialogueId, 0);
			}
			break;
			
		
		case 9167:
			if (c.dialogueAction == 6) {
				if (c.slayerTask > 0){
				c.getDH().sendDialogues(55,c.npcType);
				} else {
				c.getDH().sendDialogues(56,c.npcType);
				}
			}
		break;
		
		case 9168:
			if (c.dialogueAction == 6) {
				c.getSlayer().slayloc(1625);
			}
		break;
		
		case 9169:
			if (c.dialogueAction == 6) {
				c.getDH().sendDialogues(57,c.npcType);
			}
		break;
		
		case 53152: Cooking.getAmount(c, 1); break;
		case 53151: Cooking.getAmount(c, 5); break;
		case 53150: Cooking.getAmount(c, 10); break;
		case 53149: Cooking.getAmount(c, 28); break;
		
		/** Quests **/
	
		case 28164:
			c.getGSQ().showInformation();
			break;
		
						// Skill Guides! \\
		case 33208://Mining
		c.getPA().showInterface(8134);
		for (int i = 8155; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
		c.flushOutStream();
		c.getPA().sendFrame126("@dre@Mining Skill Guide. Last Update: 4/19/2015 ", 8144);
		c.getPA().sendFrame126("Main Train Zone: North of Yanille", 8145);
		c.getPA().sendFrame126("Copper, Tin: No Level Req", 8147);
		c.getPA().sendFrame126("Iron: 15", 8148);
		c.getPA().sendFrame126("Coal: 30", 8149);
		c.getPA().sendFrame126("Mith: 55", 8150);
		c.getPA().sendFrame126("Addy: 70", 8151);
		c.getPA().sendFrame126("Rune: 85 (Not Yet Mineable)", 8152);
		c.getPA().sendFrame126("", 8153);
		c.getPA().sendFrame126("Granite,Elemental,Crytal,Gem Mining Coming Soon.", 8154);
			break;
			
		case 33211://Smithing
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Smithing Skill Guide. Last Update: 4/19/2015 ", 8144);
			c.getPA().sendFrame126("1st Train Zone: Furnance at Home", 8145);
			c.getPA().sendFrame126("2nd Train Zone: Anvils By Next to Furnance", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("Use ores in furance, then use bars on anvils.", 8149);
				break;
				
		case 33214://Fishing
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Fishing Skill Guide. Last Update: 11/21/2015 ", 8144);
			c.getPA().sendFrame126("Train Zone: Starter Fish Spots Next To Spawn", 8145);
			c.getPA().sendFrame126("2nd Train Zone: Fish Spots Around Home.", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("Get More Supplies From Fisherman On Dock.", 8149);
				break;
				
		case 33217://Cooking
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Cooking Skill Guide. Last Update: 11/21/2015 ", 8144);
			c.getPA().sendFrame126("Train Zone: Light Logs To Cook On Fire", 8145);
			c.getPA().sendFrame126("2nd Train Zone: Cooking Range at Home.", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("", 8149);
				break;
				
		case 33220://Firemaking
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Firemaking Skill Guide. Last Update: 11/21/2015 ", 8144);
			c.getPA().sendFrame126("Train: Use Logs on Tinderbox to Light a Fire.", 8145);
			c.getPA().sendFrame126("", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("", 8149);
				break;
				
		case 33223://Woodcutting
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Woodcutting Skill Guide. Last Update: 11/21/2015 ", 8144);
			c.getPA().sendFrame126("Train Zone: Trees Around Home..", 8145);
			c.getPA().sendFrame126("Train Zone 2: Magic Trees Located somewhere in Lletya.", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("Equip Wc Axe or Have In Inventory and chop trees.", 8149);
				break;
				
		case 54104://Farming
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Farming Skill Guide. Last Update: 11/21/2015 ", 8144);
			c.getPA().sendFrame126("Train Zone: Patch at Home..", 8145);
			c.getPA().sendFrame126("Supplies: Trade Kaqemeex in shop next to Patch", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("Rake Patch to Clear it.", 8149);
			c.getPA().sendFrame126("Use seed dibbler and use seed on patch to plant.", 8150);
			c.getPA().sendFrame126("Use Watering Can On Patch to to Water Seed.", 8151);
			c.getPA().sendFrame126("Harvest Herbs.", 8152);
				break;
				
		case 47130: //Slayer
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Slayer Skill Guide. Last Update: 04/14/2016 ", 8144);
			c.getPA().sendFrame126("Slayer Master: Duradel..", 8145);
			c.getPA().sendFrame126("", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("You can find Duradel in Burthorope.", 8149);
			c.getPA().sendFrame126("", 8150);
			c.getPA().sendFrame126("", 8151);
			c.getPA().sendFrame126("", 8152);
			break;
			
		case 33222://Fletching
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Fletching Skill Guide. Last Update: 04/14/2016 ", 8144);
			c.getPA().sendFrame126("Train: Use knife with log to cut log.", 8145);
			c.getPA().sendFrame126("Buy a knife from the SSL Vet located at Home.", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("", 8149);
				break;
				
		case 33219://Crafting
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Crafting Skill Guide. Last Update: 04/14/2016 ", 8144);
			c.getPA().sendFrame126("Train Zone: Use Chisel on Any Gem, Anywhere.", 8145);
			c.getPA().sendFrame126("Buy a Chisel from the SSL Vet located at Home.", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("More Updates for crafting to follow.", 8149);
				break;
				
		case 33216://Thieving
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Thieving Skill Guide. Last Update: 04/14/2016 ", 8144);
			c.getPA().sendFrame126("Train Zone: Coming Soon.", 8145);
			c.getPA().sendFrame126("", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("Check back for update", 8149);
				break;
				
		case 33213://Herblore
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Herblore Skill Guide. Last Update: 04/14/2016 ", 8144);
			c.getPA().sendFrame126("Train Zone: Anywhere.", 8145);
			c.getPA().sendFrame126("", 8147);
			c.getPA().sendFrame126("Use herb with a vial of water to create unfinshed potion.", 8148);
			c.getPA().sendFrame126("Use unfinished potion with proper ingredient.", 8149);
			c.getPA().sendFrame126("", 8150);
			c.getPA().sendFrame126("Level 1: @gre@Guam & @blu@Eye of Newt @bla@> Attack Potion", 8151);
			c.getPA().sendFrame126("Level 5: @gre@Marrentill & @blu@Unicorn Horn Dust @bla@> Antipoison.", 8152);
			c.getPA().sendFrame126("Level 12: @gre@Tarromin & @blu@Limpwurt root @bla@> Strenght Potion", 8153);
			c.getPA().sendFrame126("Level 22: @gre@Harralander & @blu@Red spiders' eggs @bla@> Restore Potion.", 8154);
			c.getPA().sendFrame126("Level 26: @gre@Harralander & @blu@Chocolate Dust @bla@> Energy Potion", 8155);
			c.getPA().sendFrame126("Level 30: @gre@Ranarr & @blu@White berries @bla@> Defense Potion", 8156);
			c.getPA().sendFrame126("Level 34: @gre@Toadflax & @blu@Toad's legs @bla@> Agility Potion", 8157);
			c.getPA().sendFrame126("Level 36: @gre@Harralander & @blu@Goat horn dust @bla@> Combat Potion.", 8158);
			c.getPA().sendFrame126("Level 38: @gre@Ranarr & @blu@Snape grass @bla@> Prayer Potion.", 8159);
			c.getPA().sendFrame126("Level 45: @gre@Irit & @blu@Eye of newt @bla@> Super Attack Potion", 8160);
			c.getPA().sendFrame126("Level 48: @gre@Irit & @blu@Unicorn horn dust @bla@> Superantipoison.", 8161);
			c.getPA().sendFrame126("Level 50: @gre@Avantoe & @blu@Snape grass @bla@> Fishing Potion.", 8162);
			c.getPA().sendFrame126("Level 52: @gre@Avantoe & @blu@Mort myre fungi @bla@> Super engery potion.", 8163);
			c.getPA().sendFrame126("Level 55: @gre@Kwuarm & @blu@Limpwurt root @bla@> Super Strenght Potion.", 8164);
			c.getPA().sendFrame126("Level 60: @gre@Kwuarm & @blu@BLue dragon scale @bla@> Weapon Poison", 8165);
			c.getPA().sendFrame126("Level 63: @gre@Snapdragon & @blu@Red spiders' eggs @bla@> Super Restore ", 8166);
			c.getPA().sendFrame126("Level 66: @gre@Cadantine & @blu@White berries @bla@> Super Defence", 8167);
			c.getPA().sendFrame126("Level 69: @gre@Lantadyme & @blu@Blue dragon scale @bla@> Anti Fire Potion.", 8168);
			c.getPA().sendFrame126("Level 72: @gre@Dwarf Weed & @blu@Wine of Zamorak @bla@> Ranging Potion.", 8169);
			c.getPA().sendFrame126("Level 76: @gre@Lantadyme & @blu@Potato cactus @bla@> Magic Potion", 8170);
			c.getPA().sendFrame126("Level 78: @gre@Torstol & @blu@Jangerberries @bla@> Zamorak Brew", 8171);
			c.getPA().sendFrame126("Level 81: @gre@Toadflax & @blu@Crushed bird's nest @bla@> Saradomin Brew", 8172);
			c.getPA().sendFrame126("", 8173);
			c.getPA().sendFrame126("", 8174);
				break;
				
		case 33210://Agility
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Agility Skill Guide. Last Update: 04/15/2016 ", 8144);
			c.getPA().sendFrame126("Train Zone: Coming Soon.", 8145);
			c.getPA().sendFrame126("", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("Check back for update", 8149);
				break;
				
		case 33224://RuneCrafting
			c.getPA().showInterface(8134);
			for (int i = 8150; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.flushOutStream();
			c.getPA().sendFrame126("@dre@Runecrafting Skill Guide. Last Update: 04/15/2016 ", 8144);
			c.getPA().sendFrame126("Train Zone: The Abyss.", 8145);
			c.getPA().sendFrame126("Teleport: The Mysterious Ruins south of home Bank", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("Use any talisman on The Ruins to teleport to abyss.", 8149);
			c.getPA().sendFrame126("Then click the correct rift in order to craft the proper rune.", 8150);
				break;
				
				// END OF SKILL GUIDES \\

		case 71074:
			if (c.clanId >= 0) {
				if (Server.clanChat.clans[c.clanId].owner
						.equalsIgnoreCase(c.playerName)) {
					Server.clanChat
							.sendLootShareMessage(
									c.clanId,
									"Lootshare has been toggled to "
											+ (!Server.clanChat.clans[c.clanId].lootshare ? "on"
													: "off")
											+ " by the clan leader.");
					Server.clanChat.clans[c.clanId].lootshare = !Server.clanChat.clans[c.clanId].lootshare;
				} else
					c.sendMessage("Only the owner of the clan has the power to do that.");
			}
			break;
		/*(case 34185:
			if(c.playerFletch) {
				Fletching.attemptData(c, 1, 0);
			} else {

			}
			break;
		case 34184:
			if(c.playerFletch) {
				Fletching.attemptData(c, 5, 0);
			} else {

			}
			break;
		case 34183:
			if(c.playerFletch) {
				Fletching.attemptData(c, 10, 0);
			} else {

			}
			break;
		case 34182:
			if(c.playerFletch) {
				Fletching.attemptData(c, 28, 0);
			} else {

			}
			break;
		case 34189: 
			if(c.playerFletch) {
				Fletching.attemptData(c, 1, 1);
			} else {

			}
			break;
		case 34188: 
			if(c.playerFletch) {
				Fletching.attemptData(c, 5, 1);
			} else {

			}
			break;
		case 34187: 
			if(c.playerFletch) {
				Fletching.attemptData(c, 10, 1);
			} else {

			}
			break;
		case 34186: 
			if(c.playerFletch) {
				Fletching.attemptData(c, 28, 1);
			} else {

			}
			break;
		case 34193:
			if(c.playerFletch) {
				Fletching.attemptData(c, 1, 2);
			} else {

			}
			break;
		case 34192:
			if(c.playerFletch) {
				Fletching.attemptData(c, 5, 2);
			} else {

			}
			break;
		case 34191:
			if(c.playerFletch) {
				Fletching.attemptData(c, 10, 2);
			} else {

			}
			break;
		case 34190:
			if(c.playerFletch) {
				Fletching.attemptData(c, 28, 2);
			} else {

			} OLD ASS FLETCH
			break;*/
			
		case 34185:
		case 34184:
		case 34183:
		case 34182:
		case 34189:
		case 34188:
		case 34187:
		case 34186:
		case 34193:
		case 34192:
		case 34191:
		case 34190:
			// if (c.craftingLeather)
			// c.getCrafting().handleCraftingClick(actionButtonId);
			if (c.getFletching().fletching)
				c.getFletching().handleFletchingClick(actionButtonId);
			break;

		case 15147:
			if (c.smeltInterface) {
				c.smeltType = 2349;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;

		case 15151:
			if (c.smeltInterface) {
				c.smeltType = 2351;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;

		case 15159:
			if (c.smeltInterface) {
				c.smeltType = 2353;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;

		case 29017:
			if (c.smeltInterface) {
				c.smeltType = 2359;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;

		case 29022:
			if (c.smeltInterface) {
				c.smeltType = 2361;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;

		case 29026:
			if (c.smeltInterface) {
				c.smeltType = 2363;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 58253:
			// c.getPA().showInterface(15106);
			c.getItems().writeBonus();
			break;

		case 59004:
			c.getPA().removeAllWindows();
			break;

		case 70212:
			if (c.clanId > -1)
				Server.clanChat.leaveClan(c.playerId, c.clanId);
			else
				c.sendMessage("You are not in a clan.");
			break;
		case 62137:
			if (c.clanId >= 0) {
				c.sendMessage("You are already in a clan.");
				break;
			}
			if (c.getOutStream() != null) {
				c.getOutStream().createFrame(187);
				c.flushOutStream();
			}
			break;

		case 9178:
			if (c.usingGlory)
				c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y,
						0, "modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(3428, 3538, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y,
						0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(3565, 3314, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 4, "modern");
				c.killCount = 0;
			}

			break;

		case 9179:
			if (c.usingGlory)
				c.getPA().startTeleport(Config.AL_KHARID_X, Config.AL_KHARID_Y,
						0, "modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2884, 3395, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(3243, 3513, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2444, 5170, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 12, "modern");
				c.killCount = 0;
			}
			break;

		case 9180:
			if (c.usingGlory)
				c.getPA().startTeleport(Config.KARAMJA_X, Config.KARAMJA_Y, 0,
						"modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2471, 10137, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(3363, 3676, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2659, 2676, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 8, "modern");
				c.killCount = 0;
			}
			break;

		case 9181:
			if (c.usingGlory)
				c.getPA().startTeleport(Config.MAGEBANK_X, Config.MAGEBANK_Y,
						0, "modern");
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2669, 3714, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(2540, 4716, 0, "modern");
			if (c.dialogueAction == 4) {
				c.getPA().startTeleport(3366, 3266, 0, "modern");
				c.sendMessage("Dueling is at your own risk. Refunds will not be given for items lost due to glitches.");
			}
			if (c.dialogueAction == 20) {
				// c.getPA().startTeleport(3366, 3266, 0, "modern");
				// c.killCount = 0;
				c.sendMessage("This will be added shortly");
			}
			break;

		case 1093:
		case 1094:
		case 1097:
			if (c.autocastId > 0) {
				c.getPA().resetAutocast();
			} else {
				if (c.playerMagicBook == 1) {
					if (c.playerEquipment[c.playerWeapon] == 4675)
						c.setSidebarInterface(0, 1689);
					else
						c.sendMessage("You can't autocast ancients without an ancient staff.");
				} else if (c.playerMagicBook == 0) {
					if (c.playerEquipment[c.playerWeapon] == 4170) {
						c.setSidebarInterface(0, 12050);
					} else {
						c.setSidebarInterface(0, 1829);
					}
				}

			}
			break;

		case 9157:// barrows tele to tunnels
			if (c.dialogueAction == 1) {
				int r = 4;
				// int r = Misc.random(3);
				switch (r) {
				case 0:
					c.getPA().movePlayer(3534, 9677, 0);
					break;

				case 1:
					c.getPA().movePlayer(3534, 9712, 0);
					break;

				case 2:
					c.getPA().movePlayer(3568, 9712, 0);
					break;

				case 3:
					c.getPA().movePlayer(3568, 9677, 0);
					break;
				case 4:
					c.getPA().movePlayer(3551, 9694, 0);
					break;
				}
			}
			if (c.dialogueAction == 2) {
				c.getPA().movePlayer(2507, 4717, 0);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 7) {
				c.getPA().startTeleport(3088, 3933, 0, "modern");
				c.sendMessage("NOTE: You are now in the wilderness...");
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 8) {
				c.getPA().resetBarrows();
				c.sendMessage("Your barrows have been reset.");
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 50 && c.getItems().playerHasItem(7143, 1)) {
				c.getItems().deleteItem(7143,c.getItems().getItemSlot(7143), 1);
				c.getItems().addItem(995, 100000000);
				c.sendMessage("You have obtained 100,000,000 coins!");
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 50 && !c.getItems().playerHasItem(7143, 1)){
				c.sendMessage("You do not have any plunder.");
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 51 && c.getItems().playerHasItem(995, 100000000)) {
				c.getItems().deleteItem(995,c.getItems().getItemSlot(995), 100000000);
				c.getItems().addItem(7143, 1);
				c.sendMessage("You have obtained plunder!");
				c.getPA().removeAllWindows();
			} else if  (c.dialogueAction == 51 && !c.getItems().playerHasItem(995, 100000000)){
				c.sendMessage("You do not have enough gold.");
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 53) {
				c.getShops().openShop(1);
				break;
			}
			if (c.dialogueAction == 28) {
				c.getPA().movePlayer(2386, 4960, 0);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 29) {
				if (c.addStarter = false) {
					c.getPA().removeAllWindows();
					c.getPA().addStarter();
				} else {
					c.getPA().removeAllWindows();
					c.sendMessage("You already have a starter.");
					break;
				}
			} 
			
			if (c.dialogueAction == 40) {
				c.repPoints += 4000;
				c.getPA().startTeleport(Config.START_LOCATION_X, Config.START_LOCATION_Y, 0, "modern");
				c.sendMessage("@blu@You gain 4,000 reputation points for leaving through the home portal.");
			}
			if (c.dialogueAction == 41) {
				if (c.rankLevel >= 5000 && c.repPoints >= 2500) {
					c.repPoints -= 2500;
					c.getPA().startTeleport(2386, 4960, 20, "modern");
					c.sendMessage("@blu@You pay 2,500 reputation points to enter.");
					c.sendMessage("@dre@You have reached the final level.");
				} else {
					c.sendMessage("@blu@You must have at least 2,500 reputation points,");
					c.sendMessage("@blu@and a rank level of at least 5000 to enter this portal.");
					break;
				}
			}
			if (c.dialogueAction == 42) {
				if (c.rankLevel >= 2000 && c.repPoints >= 1000) {
					c.repPoints -= 1000;
					c.getPA().startTeleport(2386, 4960, 16, "modern");
					c.sendMessage("@blu@You pay 1,000 reputation points to enter.");
				} else {
					c.sendMessage("@blu@You must have at least 1,000 reputation points,");
					c.sendMessage("@blu@and a rank level of at least 2,000 to enter this portal.");
					break;
				}
			} 
			if (c.dialogueAction == 43) {
				if (c.rankLevel >= 800 && c.repPoints >= 400) {
					c.repPoints -= 400;
					c.getPA().startTeleport(2386, 4960, 12, "modern");
					c.sendMessage("@blu@You pay 400 reputation points to enter.");
				} else {
					c.sendMessage("@blu@You must have at least 400 reputation points,");
					c.sendMessage("@blu@and a rank level of at least 800 to enter this portal.");
				}
			}
			if (c.dialogueAction == 44) {
				if (c.rankLevel >= 150 && c.repPoints >= 75) {
					c.repPoints -= 75;
					c.getPA().startTeleport(2386, 4960, 8, "modern");
					c.sendMessage("@blu@You pay 75 reputation points to enter.");
				} else {
					c.sendMessage("@blu@You must have at least 75 reputation points,");
					c.sendMessage("@blu@and a rank level of at least 150 to enter this portal.");
				}
			}
			if (c.dialogueAction == 45) {
				if (c.rankLevel >= 50 && c.repPoints >= 25) {
					c.repPoints -= 25;
					c.getPA().startTeleport(2386, 4960, 4, "modern");
					c.sendMessage("@blu@You pay 25 reputation points to enter.");
				} else {
					c.sendMessage("@blu@You must have at least 25 reputation points,");
					c.sendMessage("@blu@and a rank level of at least 50 to enter this portal.");
				}
			}
			if (c.dialogueAction == 47 && c.getItems().playerHasItem(995, 2000)) {
				c.getItems().removeAllItems();
				c.getItems().deleteItem(995,c.getItems().getItemSlot(995), 2000);
				c.sendMessage("@red@You empty your inventory.");
			} else if (c.dialogueAction == 47 && !c.getItems().playerHasItem(995, 2000)) {
				c.sendMessage("You do not have enough GP to do empty your inventory.");
				c.getPA().removeAllWindows();
			}
			
			c.dialogueAction = 0;
			//c.getPA().removeAllWindows();
			break;

		case 9158:
			if (c.dialogueAction == 8) {
				c.getPA().fixAllBarrows();
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 953) {
				c.getPA().openUpBank();
			} else if (c.dialogueAction == 53) {
				c.getDH().sendDialogues(38,	551);
			} else if (c.dialogueAction == 5) {
				if (c.playerSlayer >= 0 || c.playerSlayer < 15) {
					if (c.slayerTask <= 0 ) {
						c.getSlayer().Lvl1Task();	
					} else {
						c.getDH().sendDialogues(13, 1599);
					}
				} else {
					c.getPA().sendStatement("Your slayer level isn't high enough.");
			}
			} else if (c.dialogueAction == 29) {
				c.isMaxed();
				if(c.maxed) {
					if(c.presLevel < 10){
					c.getPA().resetLevels();
					c.getPA().removeAllWindows();
					c.sendMessage("Prestiege");
					//c.presLevel += 1;
					} else {
						c.getPA().removeAllWindows();
						c.sendMessage("Your Max Prestiege");
					}
				} else {
					c.getPA().removeAllWindows();
					c.sendMessage("not high enough combat.");
					break;
				}
			}
			c.dialogueAction = 0;
			//c.getPA().removeAllWindows();
			break;

		/** Specials **/
		case 29188:
			c.specBarId = 7636; // the special attack text - sendframe126(S P E
								// C I A L A T T A C K, c.specBarId);
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29163:
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 33033:
			c.specBarId = 8505;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29038:
			c.specBarId = 7486;
			/*
			 * if (c.specAmount >= 5) { c.attackTimer = 0;
			 * c.getCombat().attackPlayer(c.playerIndex); c.usingSpecial = true;
			 * c.specAmount -= 5; }
			 */
			c.getCombat().handleGmaulPlayer();
			c.getItems().updateSpecialBar();
			break;

		case 29063:
			if (c.getCombat()
					.checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
				c.gfx0(246);
				c.forcedChat("Raarrrrrgggggghhhhhhh!");
				c.startAnimation(1056);
				c.playerLevel[2] = c.getLevelForXP(c.playerXP[2])
						+ (c.getLevelForXP(c.playerXP[2]) * 15 / 100);
				c.getPA().refreshSkill(2);
				c.getItems().updateSpecialBar();
			} else {
				c.sendMessage("You don't have the required special energy to use this attack.");
			}
			break;

		case 48023:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29138:
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29113:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29238:
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		/** Dueling **/
		case 26065: // no forfeit
		case 26040:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(0);
			break;

		case 26066: // no movement
		case 26048:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(1);
			break;

		case 26069: // no range
		case 26042:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(2);
			break;

		case 26070: // no melee
		case 26043:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(3);
			break;

		case 26071: // no mage
		case 26041:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(4);
			break;

		case 26072: // no drinks
		case 26045:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(5);
			break;

		case 26073: // no food
		case 26046:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(6);
			break;

		case 26074: // no prayer
		case 26047:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(7);
			break;

		case 26076: // obsticals
		case 26075:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(8);
			break;

		case 2158: // fun weapons
		case 2157:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(9);
			break;

		case 30136: // sp attack
		case 30137:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(10);
			break;

		case 53245: // no helm
			c.duelSlot = 0;
			c.getTradeAndDuel().selectRule(11);
			break;

		case 53246: // no cape
			c.duelSlot = 1;
			c.getTradeAndDuel().selectRule(12);
			break;

		case 53247: // no ammy
			c.duelSlot = 2;
			c.getTradeAndDuel().selectRule(13);
			break;

		case 53249: // no weapon.
			c.duelSlot = 3;
			c.getTradeAndDuel().selectRule(14);
			break;

		case 53250: // no body
			c.duelSlot = 4;
			c.getTradeAndDuel().selectRule(15);
			break;

		case 53251: // no shield
			c.duelSlot = 5;
			c.getTradeAndDuel().selectRule(16);
			break;

		case 53252: // no legs
			c.duelSlot = 7;
			c.getTradeAndDuel().selectRule(17);
			break;

		case 53255: // no gloves
			c.duelSlot = 9;
			c.getTradeAndDuel().selectRule(18);
			break;

		case 53254: // no boots
			c.duelSlot = 10;
			c.getTradeAndDuel().selectRule(19);
			break;

		case 53253: // no rings
			c.duelSlot = 12;
			c.getTradeAndDuel().selectRule(20);
			break;

		case 53248: // no arrows
			c.duelSlot = 13;
			c.getTradeAndDuel().selectRule(21);
			break;

		case 26018:
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			if (o == null) {
				c.getTradeAndDuel().declineDuel();
				return;
			}

			if (c.duelRule[2] && c.duelRule[3] && c.duelRule[4]) {
				c.sendMessage("You won't be able to attack the player with the rules you have set.");
				break;
			}
			c.duelStatus = 2;
			if (c.duelStatus == 2) {
				c.getPA().sendFrame126("Waiting for other player...", 6684);
				o.getPA().sendFrame126("Other player has accepted.", 6684);
			}
			if (o.duelStatus == 2) {
				o.getPA().sendFrame126("Waiting for other player...", 6684);
				c.getPA().sendFrame126("Other player has accepted.", 6684);
			}

			if (c.duelStatus == 2 && o.duelStatus == 2) {
				c.canOffer = false;
				o.canOffer = false;
				c.duelStatus = 3;
				o.duelStatus = 3;
				c.getTradeAndDuel().confirmDuel();
				o.getTradeAndDuel().confirmDuel();
			}
			break;

		case 25120:
			if (c.duelStatus == 5) {
				break;
			}
			Client o1 = (Client) PlayerHandler.players[c.duelingWith];
			if (o1 == null) {
				c.getTradeAndDuel().declineDuel();
				return;
			}

			c.duelStatus = 4;
			if (o1.duelStatus == 4 && c.duelStatus == 4) {
				c.getTradeAndDuel().startDuel();
				o1.getTradeAndDuel().startDuel();
				o1.duelCount = 4;
				c.duelCount = 4;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if(System.currentTimeMillis() - c.duelDelay > 800 && c.duelCount > 0) {
							if(c.duelCount != 1) {
								c.forcedChat(""+(--c.duelCount));
								c.duelDelay = System.currentTimeMillis();
							} else {
								c.damageTaken = new int[Config.MAX_PLAYERS];
								c.forcedChat("FIGHT!");
								c.duelCount = 0;
							}
						}
						if (c.duelCount == 0) {
							container.stop();
						}
					}
					@Override
					public void stop() {
					}
				}, 1);
				c.duelDelay = System.currentTimeMillis();
				o1.duelDelay = System.currentTimeMillis();
			} else {
				c.getPA().sendFrame126("Waiting for other player...", 6571);
				o1.getPA().sendFrame126("Other player has accepted", 6571);
			}
			break;

		case 4169: // god spell charge
			c.usingMagic = true;
			if (!c.getCombat().checkMagicReqs(48)) {
				break;
			}

			if (System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
				c.sendMessage("You still feel the charge in your body!");
				break;
			}
			c.godSpellDelay = System.currentTimeMillis();
			c.sendMessage("You feel charged with a magical power!");
			c.gfx100(c.MAGIC_SPELLS[48][3]);
			c.startAnimation(c.MAGIC_SPELLS[48][2]);
			c.usingMagic = false;
			break;

		case 152:
			c.isRunning2 = !c.isRunning2;
			int frame = c.isRunning2 == true ? 1 : 0;
			c.getPA().sendFrame36(173, frame);
			break;

		case 9154:
			c.logout();
			break;

		case 21010:
			c.takeAsNote = true;
			break;

		case 21011:
			c.takeAsNote = false;
			break;
			
			/**Beginning Of Teleport Sytem Code **/

		case 108005: //Equipment Interface
			c.getPA().showInterface(15106);
			//c.getPA().sendStatement("Currently Being Worked On.");
			break;
			
		case 108006: //items kept on death?
			if(!c.isSkulled) {	
				c.getItems().resetKeepItems();
				c.getItems().keepItem(0, false);
				c.getItems().keepItem(1, false);	
				c.getItems().keepItem(2, false);
				c.getItems().keepItem(3, false);
				c.sendMessage("You can keep three items and a fourth if you use the protect item prayer.");
			} else {
				c.getItems().resetKeepItems();
				c.getItems().keepItem(0, false);
				c.sendMessage("You are skulled and will only keep one item if you use the protect item prayer.");
			}
			c.getItems().sendItemsKept();
			c.getPA().showInterface(6960); 
			c.getItems().resetKeepItems();
			break;
		
			//home teleports
			case 4171:
			case 75010:
			case 50056:
			case 117048:
			String type = c.playerMagicBook == 0 ? "modern" : "ancient";
			c.getPA().startTeleport(Config.START_LOCATION_X, Config.START_LOCATION_Y, 0, type);	
			break;

			case 4140: //Varrock Teleport--Yanille
			case 50235: // Al Karid
			case 117112:
			c.getPA().startTeleport(2606, 3102, 0, "modern");
			break;

			case 117123:
			case 4143:
			case 50245:
			c.getPA().startTeleport(3506, 3480, 0, "modern");
			break;
			
			case 117131:
			case 50253:
			case 4146:
			c.getPA().startTeleport(3305, 2790, 0, "modern");
			break;
			
			case 117154:
			case 51005:
			case 4150:
			c.getPA().startTeleport(2659, 3661, 0, "modern");
			break;		
			
			case 117162:
			case 51013:
			case 6004:
			c.getPA().startTeleport(2616, 3337, 0, "modern");
			break; 
			
			case 51023: //Elf City
			case 6005:
			c.getPA().startTeleport(2320, 3172, 0, "modern");
			break;
			
			
			case 51031://Burthrope
			case 29031:
			c.getPA().startTeleport(2918, 3546, 0, "modern");
			break; 	
			
			/**End Of Teleport Sytem Code **/

			case 9125: // Accurate
			case 6221: // range accurate
			case 48010: // flick (whip)
			case 21200: // spike (pickaxe)
			case 1080: // bash (staff)
			case 6168: // chop (axe)
			case 6236: // accurate (long bow)
			case 17102: // accurate (darts)
			case 8234: // stab (dagger)
			case 22230: // unarmed
				c.fightMode = 0;
				if (c.autocasting)
					c.getPA().resetAutocast();
				break;

			case 9126: // Defensive
			case 48008: // deflect (whip)
			case 21201: // block (pickaxe)
			case 1078: // focus - block (staff)
			case 6169: // block (axe)
			case 33019: // fend (hally)
			case 18078: // block (spear)
			case 8235: // block (dagger)
						// case 22231: //unarmed
			case 22228: // unarmed
				c.fightMode = 1;
				if (c.autocasting)
					c.getPA().resetAutocast();
				break;

			case 9127: // Controlled
			case 48009: // lash (whip)
			case 33018: // jab (hally)
			case 6234: // longrange (long bow)
			case 6219: // longrange
			case 18077: // lunge (spear)
			case 18080: // swipe (spear)
			case 18079: // pound (spear)
			case 17100: // longrange (darts)
				c.fightMode = 3;
				if (c.autocasting)
					c.getPA().resetAutocast();
				break;

			case 9128: // Aggressive
			case 6220: // range rapid
			case 21203: // impale (pickaxe)
			case 21202: // smash (pickaxe)
			case 1079: // pound (staff)
			case 6171: // hack (axe)
			case 6170: // smash (axe)
			case 33020: // swipe (hally)
			case 6235: // rapid (long bow)
			case 17101: // repid (darts)
			case 8237: // lunge (dagger)
			case 8236: // slash (dagger)
			case 22229: // unarmed
				c.fightMode = 2;
				if (c.autocasting)
					c.getPA().resetAutocast();
				break;
				
		/** Prayers **/
		case 21233: // thick skin
			c.getCombat().activatePrayer(0);
			break;
		case 21234: // burst of str
			c.getCombat().activatePrayer(1);
			break;
		case 21235: // charity of thought
			c.getCombat().activatePrayer(2);
			break;
		case 70080: // range
			c.getCombat().activatePrayer(3);
			break;
		case 70082: // mage
			c.getCombat().activatePrayer(4);
			break;
		case 21236: // rockskin
			c.getCombat().activatePrayer(5);
			break;
		case 21237: // super human
			c.getCombat().activatePrayer(6);
			break;
		case 21238: // improved reflexes
			c.getCombat().activatePrayer(7);
			break;
		case 21239: // hawk eye
			c.getCombat().activatePrayer(8);
			break;
		case 21240:
			c.getCombat().activatePrayer(9);
			break;
		case 21241: // protect Item
			c.getCombat().activatePrayer(10);
			break;
		case 70084: // 26 range
			c.getCombat().activatePrayer(11);
			break;
		case 70086: // 27 mage
			c.getCombat().activatePrayer(12);
			break;
		case 21242: // steel skin
			c.getCombat().activatePrayer(13);
			break;
		case 21243: // ultimate str
			c.getCombat().activatePrayer(14);
			break;
		case 21244: // incredible reflex
			c.getCombat().activatePrayer(15);
			break;
		case 21245: // protect from magic
			c.getCombat().activatePrayer(16);
			break;
		case 21246: // protect from range
			c.getCombat().activatePrayer(17);
			break;
		case 21247: // protect from melee
			c.getCombat().activatePrayer(18);
			break;
		case 70088: // 44 range
			c.getCombat().activatePrayer(19);
			break;
		case 70090: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;
		case 2171: // retrui
			c.getCombat().activatePrayer(21);
			break;
		case 2172: // redem
			c.getCombat().activatePrayer(22);
			break;
		case 2173: // smite
			c.getCombat().activatePrayer(23);
			break;
		case 70092: // chiv
			c.getCombat().activatePrayer(24);
			break;
		case 70094: // piety
			c.getCombat().activatePrayer(25);
			break;

		case 13092:
			if (System.currentTimeMillis() - c.lastButton < 400) {

				c.lastButton = System.currentTimeMillis();

				break;

			} else {

				c.lastButton = System.currentTimeMillis();

			}
			Client ot = (Client) PlayerHandler.players[c.tradeWith];
			if (ot == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			c.getPA().sendFrame126("Waiting for other player...", 3431);
			ot.getPA().sendFrame126("Other player has accepted", 3431);
			c.goodTrade = true;
			ot.goodTrade = true;

			for (GameItem item : c.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					if (ot.getItems().freeSlots() < c.getTradeAndDuel().offeredItems
							.size()) {
						c.sendMessage(ot.playerName
								+ " only has "
								+ ot.getItems().freeSlots()
								+ " free slots, please remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots()) + " items.");
						ot.sendMessage(c.playerName
								+ " has to remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots())
								+ " items or you could offer them "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots()) + " items.");
						c.goodTrade = false;
						ot.goodTrade = false;
						c.getPA().sendFrame126("Not enough inventory space...",
								3431);
						ot.getPA().sendFrame126(
								"Not enough inventory space...", 3431);
						break;
					} else {
						c.getPA().sendFrame126("Waiting for other player...",
								3431);
						ot.getPA().sendFrame126("Other player has accepted",
								3431);
						c.goodTrade = true;
						ot.goodTrade = true;
					}
				}
			}
			if (c.inTrade && !c.tradeConfirmed && ot.goodTrade && c.goodTrade) {
				c.tradeConfirmed = true;
				if (ot.tradeConfirmed) {
					c.getTradeAndDuel().confirmScreen();
					ot.getTradeAndDuel().confirmScreen();
					break;
				}

			}

			break;

		case 13218:
			if (System.currentTimeMillis() - c.lastButton < 400) {

				c.lastButton = System.currentTimeMillis();

				break;

			} else {

				c.lastButton = System.currentTimeMillis();

			}
			c.tradeAccepted = true;
			Client ot1 = (Client) PlayerHandler.players[c.tradeWith];
			if (ot1 == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}

			if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed
					&& !c.tradeConfirmed2) {
				c.tradeConfirmed2 = true;
				if (ot1.tradeConfirmed2) {
					c.acceptedTrade = true;
					ot1.acceptedTrade = true;
					c.getTradeAndDuel().giveItems();
					ot1.getTradeAndDuel().giveItems();
					break;
				}
				ot1.getPA().sendFrame126("Other player has accepted.", 3535);
				c.getPA().sendFrame126("Waiting for other player...", 3535);
			}

			break;
		/* Rules Interface Buttons */
		case 125011: // Click agree
			if (!c.ruleAgreeButton) {
				c.ruleAgreeButton = true;
				c.getPA().sendFrame36(701, 1);
			} else {
				c.ruleAgreeButton = false;
				c.getPA().sendFrame36(701, 0);
			}
			break;
		case 125003:// Accept
			if (c.ruleAgreeButton) {
				c.getPA().showInterface(3559);
				c.newPlayer = false;
			} else if (!c.ruleAgreeButton) {
				c.sendMessage("You need to click on you agree before you can continue on.");
			}
			break;
		case 125006:// Decline
			c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
			break;
		/* End Rules Interface Buttons */
		/* Player Options */
		case 74176:
			if (!c.mouseButton) {
				c.mouseButton = true;
				c.getPA().sendFrame36(500, 1);
				c.getPA().sendFrame36(170, 1);
			} else if (c.mouseButton) {
				c.mouseButton = false;
				c.getPA().sendFrame36(500, 0);
				c.getPA().sendFrame36(170, 0);
			}
			break;
		case 74184:
			if (!c.splitChat) {
				c.splitChat = true;
				c.getPA().sendFrame36(502, 1);
				c.getPA().sendFrame36(287, 1);
			} else {
				c.splitChat = false;
				c.getPA().sendFrame36(502, 0);
				c.getPA().sendFrame36(287, 0);
			}
			break;
		case 74180:
			if (!c.chatEffects) {
				c.chatEffects = true;
				c.getPA().sendFrame36(501, 1);
				c.getPA().sendFrame36(171, 0);
			} else {
				c.chatEffects = false;
				c.getPA().sendFrame36(501, 0);
				c.getPA().sendFrame36(171, 1);
			}
			break;
		case 74188:
			if (!c.acceptAid) {
				c.acceptAid = true;
				c.getPA().sendFrame36(503, 1);
				c.getPA().sendFrame36(427, 1);
			} else {
				c.acceptAid = false;
				c.getPA().sendFrame36(503, 0);
				c.getPA().sendFrame36(427, 0);
			}
			break;
		case 74192:
			if (!c.isRunning2) {
				c.isRunning2 = true;
				c.getPA().sendFrame36(504, 1);
				c.getPA().sendFrame36(173, 1);
			} else {
				c.isRunning2 = false;
				c.getPA().sendFrame36(504, 0);
				c.getPA().sendFrame36(173, 0);
			}
			break;
		case 74201:// brightness1
			c.getPA().sendFrame36(505, 1);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 1);
			break;
		case 74203:// brightness2
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 1);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 2);
			break;

		case 74204:// brightness3
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 1);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 3);
			break;

		case 74205:// brightness4
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 1);
			c.getPA().sendFrame36(166, 4);
			break;
		case 74206:// area1
			c.getPA().sendFrame36(509, 1);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74207:// area2
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 1);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74208:// area3
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 1);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74209:// area4
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 1);
			break;
		case 168:
			c.startAnimation(855);
			break;
		case 169:
			c.startAnimation(856);
			break;
		case 162:
			c.startAnimation(857);
			break;
		case 164:
			c.startAnimation(858);
			break;
		case 165:
			c.startAnimation(859);
			break;
		case 161:
			c.startAnimation(860);
			break;
		case 170:
			c.startAnimation(861);
			break;
		case 171:
			c.startAnimation(862);
			break;
		case 163:
			c.startAnimation(863);
			break;
		case 167:
			c.startAnimation(864);
			break;
		case 172:
			c.startAnimation(865);
			break;
		case 166:
			c.startAnimation(866);
			break;
		case 52050:
			c.startAnimation(2105);
			break;
		case 52051:
			c.startAnimation(2106);
			break;
		case 52052:
			c.startAnimation(2107);
			break;
		case 52053:
			c.startAnimation(2108);
			break;
		case 52054:
			c.startAnimation(2109);
			break;
		case 52055:
			c.startAnimation(2110);
			break;
		case 52056:
			c.startAnimation(2111);
			break;
		case 52057:
			c.startAnimation(2112);
			break;
		case 52058:
			c.startAnimation(2113);
			break;
		case 43092:
			c.startAnimation(0x558);
			break;
		case 2155:
			c.startAnimation(0x46B);
			break;
		case 25103:
			c.startAnimation(0x46A);
			break;
		case 25106:
			c.startAnimation(0x469);
			break;
		case 2154:
			c.startAnimation(0x468);
			break;
		case 52071:
			c.startAnimation(0x84F);
			break;
		case 52072:
			c.startAnimation(0x850);
			break;
		case 59062:
			c.startAnimation(2836);
			break;
		case 72032:
			c.startAnimation(3544);
			break;
		case 72033:
			c.startAnimation(3543);
			break;
		case 72254:
			c.startAnimation(3866);
			break;
		/* END OF EMOTES */
		case 28166:

			break;
		case 33206:
			// c.getPA().vengMe();
			// SkillMenu.openInterface(c, -1)
			// SkillMenu.openInterface(c,0);
			break;
		case 33212:
			// SkillMenu.openInterface(c, 1);
			break;
		case 33209:
			// SkillMenu.openInterface(c,2);
			break;
		case 33215:
			// SkillMenu.openInterface(c, 4);
			break;

		case 24017:
			c.getPA().resetAutocast();
			// c.sendFrame246(329, 200, c.playerEquipment[c.playerWeapon]);
			c.getItems()
					.sendWeapon(
							c.playerEquipment[c.playerWeapon],
							c.getItems().getItemName(
									c.playerEquipment[c.playerWeapon]));
			// c.setSidebarInterface(0, 328);
			// c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151 :
			// c.playerMagicBook == 1 ? 12855 : 1151);
			break;
		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
	}

}
