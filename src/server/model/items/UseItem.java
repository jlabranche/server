package server.model.items;

import server.model.players.Client;
import server.model.players.skills.BowStringing;
import server.model.players.skills.Firemaking;
import server.model.players.skills.Fletching;
import server.model.players.skills.GemCutting;
import server.model.players.skills.Crafting;
import server.model.players.skills.Grinding;
import server.model.players.skills.LeatherMaking;
import server.model.players.skills.PotionMaking;
import server.util.Misc;
import server.Config;
import server.Server;

/**
 * 
 * @author Ryan / Lmctruck30
 * 
 */

public class UseItem {

	public static void ItemonObject(Client c, int objectID, int objectX,
			int objectY, int itemId) {
		if (!c.getItems().playerHasItem(itemId, 1))
			return;
		switch (objectID) {
		case 2781:
		case 2783:
		case 11666:
			c.getSmithingInt().showSmithInterface(itemId);
			break;
		case 8151:
		case 8389:
		case 8132:
		case 8174:
		case 7848: ///flower patch catherby
			c.getFarming().checkItemOnObject(itemId);
		break;
		case 15621:
			Server.getWarriorsGuild().handleArmor(c, itemId, objectX, objectY);
		break;
		case 409:
		case 10638:
			if (c.getPrayer().isBone(itemId) && c.usingAltar==false) {
				c.usingAltar=true;
				c.altarItemId=itemId;
				c.getOutStream().createFrame(27);
				}
			break;
		case 2459:
			if (c.getItems().playerHasItem(1438)) {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1438))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1440))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1442))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1444))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1446))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1448))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1450))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1452))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1454))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1456))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1458))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1460))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else if (c.getItems().playerHasItem(1462))  {
				c.sendMessage("The ruins suck you into the abyss");
				c.getPA().movePlayer(3041, 4833, c.heightLevel);
			} else {
				c.getPA().sendStatement("You need a talisman to use these rocks");
			}
			break;
		default:
			if (c.playerRights == 3)
				Misc.println("Player At Object id: " + objectID
						+ " with Item id: " + itemId);
			break;
		}

	}

	public static void ItemonItem(final Client c, final int itemUsed, final int useWith, final int itemUsedSlot, final int usedWithSlot) {
		if (itemUsed == 227 || useWith == 227) {
			PotionMaking.createUnfinishedPotion(c, itemUsed, useWith);
		}
			PotionMaking.createPotion(c, itemUsed, useWith);
		if (itemUsed == 233 || useWith == 233) {
			Grinding.grindItem(c, itemUsed, useWith, (itemUsed == 233 ? usedWithSlot : itemUsedSlot));
		}
		if (c.getItems().getItemName(itemUsed).contains("(")
				&& c.getItems().getItemName(useWith).contains("("))
			c.getPotMixing().mixPotion2(itemUsed, useWith);
		/*if (itemUsed == 1733 || useWith == 1733)
			c.getCrafting().handleLeather(itemUsed, useWith);*/
		if (itemUsed == 1733 || useWith == 1733) {
			LeatherMaking.craftLeatherDialogue(c, itemUsed, useWith);
		}
		if (itemUsed == 1755 || useWith == 1755)
			GemCutting.cutGem(c, itemUsed, useWith);
		if (itemUsed == 4 || useWith == 1057)//test
			c.getPA().sendFrame164(4429);
			c.getPA().sendFrame246(13716, 190, 1057);
			c.getPA().sendFrame126("lol1234567890rofl123", 13717);
		if (itemUsed == 1759 && useWith == 1675 || itemUsed == 1675 && useWith == 1759)
			c.getCrafting().MakeAmulet(1675);
		if (itemUsed == 1759 && useWith == 1677 || itemUsed == 1677 && useWith == 1759)
			c.getCrafting().MakeAmulet(1677);
		if (itemUsed == 1759 && useWith == 1679 || itemUsed == 1679 && useWith == 1759)
			c.getCrafting().MakeAmulet(1679);
		if (itemUsed == 1759 && useWith == 1681 || itemUsed == 1681 && useWith == 1759)
			c.getCrafting().MakeAmulet(1681);
		if (itemUsed == 1759 && useWith == 1683 || itemUsed == 1683 && useWith == 1759)
			c.getCrafting().MakeAmulet(1683);
		if (itemUsed == 1759 && useWith == 6579 || itemUsed == 6579 && useWith == 1759)
			c.getCrafting().MakeAmulet(6579);
		//GemCutting.cutGem(c, itemUsed, useWith);
		if (itemUsed == 1777 || useWith == 1777) {
			BowStringing.stringBow(c, itemUsed, useWith);
		}
		if (itemUsed == 946 || useWith == 946)
			c.getFletching().handleLog(itemUsed,useWith);
		if (itemUsed == 53 || useWith == 53 || itemUsed == 52 || useWith == 52)
			c.getFletching().makeArrows(itemUsed, useWith);
		if ((itemUsed == 1540 && useWith == 11286)
				|| (itemUsed == 11286 && useWith == 1540)) {
			if (c.playerLevel[c.playerSmithing] >= 95) {
				c.getItems()
						.deleteItem(1540, c.getItems().getItemSlot(1540), 1);
				c.getItems().deleteItem(11286, c.getItems().getItemSlot(11286),
						1);
				c.getItems().addItem(11284, 1);
				c.sendMessage("You combine the two materials to create a dragonfire shield.");
				c.getPA().addSkillXP(500 * Config.SMITHING_EXPERIENCE,
						c.playerSmithing);
			} else {
				c.sendMessage("You need a smithing level of 95 to create a dragonfire shield.");
			}
		}
		if (itemUsed == 9142 && useWith == 9190 || itemUsed == 9190
				&& useWith == 9142) {
			if (c.playerLevel[c.playerFletching] >= 58) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9241, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 6 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 58 to fletch this item.");
			}
		}
		if (itemUsed == 9143 && useWith == 9191 || itemUsed == 9191
				&& useWith == 9143) {
			if (c.playerLevel[c.playerFletching] >= 63) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9242, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 7 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 63 to fletch this item.");
			}
		}
		if (itemUsed == 9143 && useWith == 9192 || itemUsed == 9192
				&& useWith == 9143) {
			if (c.playerLevel[c.playerFletching] >= 65) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9243, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 7 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 65 to fletch this item.");
			}
		}
		if (itemUsed == 9144 && useWith == 9193 || itemUsed == 9193
				&& useWith == 9144) {
			if (c.playerLevel[c.playerFletching] >= 71) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9244, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 10 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 71 to fletch this item.");
			}
		}
		if (itemUsed == 9144 && useWith == 9194 || itemUsed == 9194
				&& useWith == 9144) {
			if (c.playerLevel[c.playerFletching] >= 58) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9245, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 13 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 58 to fletch this item.");
			}
		}
		/*if (itemUsed == 1601 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1601) {
			if (c.playerLevel[c.playerFletching] >= 63) {
				c.getItems()
						.deleteItem(1601, c.getItems().getItemSlot(1601), 1);
				c.getItems().addItem(9192, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 63 to fletch this item.");
			}
		}
		if (itemUsed == 1607 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1607) {
			if (c.playerLevel[c.playerFletching] >= 65) {
				c.getItems()
						.deleteItem(1607, c.getItems().getItemSlot(1607), 1);
				c.getItems().addItem(9189, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 65 to fletch this item.");
			}
		}
		if (itemUsed == 1605 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1605) {
			if (c.playerLevel[c.playerFletching] >= 71) {
				c.getItems()
						.deleteItem(1605, c.getItems().getItemSlot(1605), 1);
				c.getItems().addItem(9190, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 71 to fletch this item.");
			}
		}
		if (itemUsed == 1603 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1603) {
			if (c.playerLevel[c.playerFletching] >= 73) {
				c.getItems()
						.deleteItem(1603, c.getItems().getItemSlot(1603), 1);
				c.getItems().addItem(9191, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}
		
		if (itemUsed == 1615 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1615) {
			if (c.playerLevel[c.playerFletching] >= 73) {
				c.getItems()
						.deleteItem(1615, c.getItems().getItemSlot(1615), 1);
				c.getItems().addItem(9193, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}*/
		if (itemUsed == 590 || useWith == 590) {
			Firemaking.attemptFire(c, itemUsed, useWith, c.absX, c.absY, false);
		}
		if (itemUsed >= 11710 && itemUsed <= 11714 && useWith >= 11710
				&& useWith <= 11714) {
			if (c.getItems().hasAllShards()) {
				c.getItems().makeBlade();
			}
		}
		if (itemUsed == 2368 && useWith == 2366 || itemUsed == 2366
				&& useWith == 2368) {
			c.getItems().deleteItem(2368, c.getItems().getItemSlot(2368), 1);
			c.getItems().deleteItem(2366, c.getItems().getItemSlot(2366), 1);
			c.getItems().addItem(1187, 1);
		}

		if (c.getItems().isHilt(itemUsed) || c.getItems().isHilt(useWith)) {
			int hilt = c.getItems().isHilt(itemUsed) ? itemUsed : useWith;
			int blade = c.getItems().isHilt(itemUsed) ? useWith : itemUsed;
			if (blade == 11690) {
				c.getItems().makeGodsword(hilt);
			}
		
		}
	}

	public static void ItemonNpc(Client c, int itemId, int npcId, int slot) {
		switch (itemId) {

		default:
			if (c.playerRights == 3)
				Misc.println("Player used Item id: " + itemId
						+ " with Npc id: " + npcId + " With Slot : " + slot);
			break;
		}

	}

}
