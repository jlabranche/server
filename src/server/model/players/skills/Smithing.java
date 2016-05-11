package server.model.players.skills;

import server.Config;
import server.model.players.Client;

/**
 * Smithing.java
 * 
 * @author Sanity
 * 
 **/

public class Smithing {

	private Client c;
	private final int[] SMELT_BARS = { 2349, 2351, 2355, 2353, 2357, 2359,
			2361, 2363 };
	private final int[] SMELT_FRAME = { 2405, 2406, 2407, 2409, 2410, 2411,
			2412, 2413 };
	private final int[] BAR_REQS = { 1, 15, 20, 30, 40, 50, 70, 85 };
	private final int[] ORE_1 = { 438, 440, -1, 440, 444, 447, 449, 451 };
	private final int[] ORE_2 = { 436, -1, -1, -1, -1, -1, -1, -1 };
	private final int[] SMELT_EXP = { 6, 13, -1, 18, 23, 30, 38, 50 };
	public int item2;
	public int item3;
	public int Mold;
	public int level;
	public int item;
	public int xp;
	public int remove;
	public int removeamount;
	public int maketimes;
	private int exp;
	private int oreId;
	private int oreId2;
	private int barId;

	public Smithing(Client c) {
		this.c = c;
	}

	public void sendSmelting() {
		for (int j = 0; j < SMELT_FRAME.length; j++) {
			c.getPA().sendFrame246(SMELT_FRAME[j], 150, SMELT_BARS[j]);
		}
		c.getPA().sendFrame164(2400);
		c.smeltInterface = true;
	}

	public void startSmelting(int barType) {
		if (canSmelt(barType)) {
			// c.sendMessage("We canSmelt");
			if (hasOres(barType)) {
				// c.sendMessage("We have ores");
				this.exp = getExp(barType);
				this.oreId = getOre(barType);
				this.oreId2 = getOre2(barType);
				this.barId = barType;
				c.smeltAmount = c.getItems().getItemAmount(getOre(barType));
				smelt(barType);
			} else {
				c.sendMessage("You do not have the required ores to smelt this.");
				c.getPA().resetVariables();
			}
		} else {
			c.sendMessage("You must have a higher smithing level to smith this.");
			c.getPA().resetVariables();
		}
	}

	public void smelt(int barType) {
		if (c.smeltAmount > 0) {
			c.getPA().closeAllWindows();
			if (hasOres(barType)) {
				c.getItems().deleteItem(oreId, c.getItems().getItemSlot(oreId),
						1);
				if (oreId2 > 0)
					c.getItems().deleteItem(oreId2,
							c.getItems().getItemSlot(oreId2), 1);
				c.getItems().addItem(barId, 1);
				c.getPA().addSkillXP(exp * Config.SMITHING_EXPERIENCE,
						c.playerSmithing);
				c.getPA().refreshSkill(c.playerSmithing);
				c.smeltAmount--;
				c.smeltTimer = 1;
			} else {
				c.sendMessage("You do not have the required ores to smelt this.");
				c.getPA().removeAllWindows();
			}
		} else {
			c.getPA().resetVariables();
		}
	}

	public int getExp(int barType) {
		for (int j = 0; j < SMELT_BARS.length; j++) {
			if (barType == SMELT_BARS[j]) {
				return SMELT_EXP[j];
			}
		}
		return 0;
	}

	public int getOre(int barType) {
		for (int j = 0; j < SMELT_BARS.length; j++) {
			if (barType == SMELT_BARS[j]) {
				// c.sendMessage("" + ORE_1[j]);
				return ORE_1[j];
			}
		}
		return 0;
	}

	public int getOre2(int barType) {
		for (int j = 0; j < SMELT_BARS.length; j++) {
			if (barType == SMELT_BARS[j]) {
				// c.sendMessage("" + ORE_2[j]);
				return ORE_2[j];
			}
		}
		return 0;
	}

	public boolean canSmelt(int barType) {
		for (int j = 0; j < SMELT_BARS.length; j++) {
			if (barType == SMELT_BARS[j]) {
				// c.sendMessage("" + c.playerLevel + " bar: " + BAR_REQS[j]);
				return c.playerLevel[c.playerSmithing] >= BAR_REQS[j];
			}
		}
		return false;
	}

	public boolean hasOres(int barType) {
		if (getOre2(barType) > 0)
			return c.getItems().playerHasItem(getOre(barType))
					&& c.getItems().playerHasItem(getOre2(barType));
		else
			return c.getItems().playerHasItem(getOre(barType));
	}

	public void readInput(int level, String type, Client c, int amounttomake) {

		if (c.getItems().getItemName(Integer.parseInt(type)).contains("Bronze")) {
			CheckBronze(c, level, amounttomake, type);
		} else if (c.getItems().getItemName(Integer.parseInt(type))
				.contains("Iron")) {
			CheckIron(c, level, amounttomake, type);
		} else if (c.getItems().getItemName(Integer.parseInt(type))
				.contains("Steel")) {
			CheckSteel(c, level, amounttomake, type);
		} else if (c.getItems().getItemName(Integer.parseInt(type))
				.contains("Mith")) {
			CheckMith(c, level, amounttomake, type);
		} else if (c.getItems().getItemName(Integer.parseInt(type))
				.contains("Adam")
				|| c.getItems().getItemName(Integer.parseInt(type))
						.contains("Addy")) {
			CheckAddy(c, level, amounttomake, type);
		} else if (c.getItems().getItemName(Integer.parseInt(type))
				.contains("Rune")
				|| c.getItems().getItemName(Integer.parseInt(type))
						.contains("Runite")) {
			CheckRune(c, level, amounttomake, type);
		} else if (c.getItems().getItemName(Integer.parseInt(type)).contains("ring") || c.getItems().getItemName(Integer.parseInt(type)).contains("ammy") || c.getItems().getItemName(Integer.parseInt(type)).contains("Ring") || c.getItems().getItemName(Integer.parseInt(type)).contains("amulet") || c.getItems().getItemName(Integer.parseInt(type)).contains("Amulet") || c.getItems().getItemName(Integer.parseInt(type)).contains("necklace") || c.getItems().getItemName(Integer.parseInt(type)).contains("Necklace") ){
			checkCrafting(c, level, amounttomake, type);
		}
		c.sendMessage("Item: " + type);
	}
	
private void checkCrafting(Client c, int level, int amounttomake, String type) {
		
		if (type.equalsIgnoreCase("1637")) {
				xp = 40;
				item = 1637;
				item2 = 1607;
				item3 = 2357;
				Mold = 1592;
				level = 20;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1639")) {
				xp = 55;
				item = 1639;
				item2 = 1605;
				item3 = 2357;
				Mold = 1592;
				level = 27;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1641")) {
				xp = 70;
				item = 1641;
				item2 = 1603;
				item3 = 2357;
				Mold = 1592;
				level = 34;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1643")) {
				xp = 85;
				item = 1643;
				item2 = 1601;
				item3 = 2357;
				Mold = 1592;
				level = 43;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1645")) {
				xp = 100;
				item = 1645;
				item2 = 1615;
				item3 = 2357;
				Mold = 1592;
				level = 55;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("6575")) {
				xp = 115;
				item = 6575;
				item2 = 6573;
				item3 = 2357;
				Mold = 1592;
				level = 67;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1675") ) {
				xp = 65;
				item = 1675;
				item2 = 1607;
				item3 = 2357;
				Mold = 1595;
				level = 24;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1677")) {
				xp = 70;
				item = 1677;
				item2 = 1605;
				item3 = 2357;
				Mold = 1595;
				level = 31;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1679")) {
				xp = 85;
				item = 1679;
				item2 = 1603;
				item3 = 2357;
				Mold = 1595;
				level = 50;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1681")) {
				xp = 100;
				item = 1681;
				item2 = 1601;
				item3 = 2357;
				Mold = 1595;
				level = 70;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1683")) {
				xp = 150;
				item = 1683;
				item2 = 1615;
				item3 = 2357;
				Mold = 1595;
				level = 80;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("6579")) {
				xp = 165 ;
				item = 6579;
				item2 = 6573;
				item3 = 2357;
				Mold = 1595;
				level = 90;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1656")) {
				xp = 55;
				item = 1656;
				item2 = 1607;
				item3 = 2357;
				Mold = 1597;
				level = 22;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1658")) {
				xp = 60;
				item = 1658;
				item2 = 1605;
				item3 = 2357;
				Mold = 1597;
				level = 29;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1660")) {
				xp = 75;
				item = 1660;
				item2 = 1603;
				item3 = 2357;
				Mold = 1597;
				level = 40;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1662")) {
				xp = 90;
				item = 1662;
				item2 = 1601;
				item3 = 2357;
				Mold = 1597;
				level = 56;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("1664")) {
				xp = 105;
				item = 1664;
				item2 = 1615;
				item3 = 2357;
				Mold = 1597;
				level = 72;
				maketimes = amounttomake;
		}
		if (type.equalsIgnoreCase("6577")) {
				xp = 120;
				item = 6577;
				item2 = 1615;
				item3 = 2357;
				Mold = 1597;
				level = 82;
				maketimes = amounttomake;
		}
		doactionC(c, item, item2, item3, maketimes, Mold , level, xp);
	}

	private void CheckIron(Client c, int level, int amounttomake, String type) {
		remove = 2351;
		maketimes = amounttomake;
		int ReqLevel = 0;
		int iType = Integer.valueOf(type);
		item = iType;
		removeamount = 1; // Unless stated otherwise why would we have this?
		switch(iType){
		case 1349:
			xp = 25;
			ReqLevel = 16;
		break;
		case 1203:
			xp = 25;
			ReqLevel = 15;
		break;
		case 1420:
			xp = 25;
			ReqLevel = 17;
		break;
		case 1137:
			xp = 25;
			ReqLevel = 18;
		break;
		case 9140:
			xp = 25;
			ReqLevel = 19;
		break;
		case 1279:
		    xp = 25;
		    ReqLevel = 19;
		break;
		case 4820:
			xp = 25;
			ReqLevel = 19;
		break;
		case 40:
			xp = 25;
			ReqLevel = 19;
		break;
		case 1323:
			xp = 50;
			ReqLevel = 20;
			removeamount = 2;
		break;
		case 1293:
			xp = 50;
			ReqLevel = 21;
			removeamount = 2;
		break;
		case 863:
			xp = 25;
			ReqLevel = 22;
		break;
		case 1153:
			xp = 25;
			ReqLevel = 22;
		break;
		case 1175:
			xp = 50;
			removeamount = 2;
			ReqLevel = 23;
		break;
		case 1335:
			ReqLevel = 24;
			xp = 38;
			removeamount = 3;
		break;
		case 1363:
			ReqLevel = 25;
			xp = 75;
			removeamount = 3;
		break;
		case 1101:
			xp = 75;
			removeamount = 3;
			ReqLevel = 26;
		break;
		case 1191:
			ReqLevel = 27;
			xp = 75;
			removeamount = 3;
		break;
		case 1309:
			xp = 75;
			removeamount = 3;
			ReqLevel = 29;
		break;
		case 1081:
		case 1067:
			xp = 75;
			removeamount = 3;
			ReqLevel = 31;
		break;
		case 1115:
			xp = 100;
			removeamount = 5;
			ReqLevel = 33;
		break;
		
		default:
			xp = 0;
			item = -1;
			removeamount = 0;
			ReqLevel = 0;
		break;
		
		}
		
		if (level < ReqLevel) {
			c.sendMessage("You need a smithing level of "+ReqLevel+" to make this item!");
			return;
		}

		doaction(c, item, remove, removeamount, maketimes, -1, -1, xp);

	}

	private void CheckSteel(Client c, int level, int amounttomake, String type) {
		remove = 2353;
		maketimes = amounttomake;
		int ReqLevel = 0;
		int iType = Integer.valueOf(type);
		item = iType;
		removeamount = 1; // Unless stated otherwise why would we have this?
		switch(iType){
		case 1353:
			xp = 38;
			ReqLevel = 31;
		break;
		case 1207:
			xp = 50;
			ReqLevel = 30;
			break;
		case 1424:
			xp = 50;
			ReqLevel = 32;
			break;
		case 1141:
			xp = 50;
			ReqLevel = 33;
			break;
		case 9141:
			xp = 50;
			ReqLevel = 34;
			break;
		case 1281:
			xp = 50;
			ReqLevel = 34;
			break;
		case 1539:
			xp = 50;
			ReqLevel = 34;
			break;
		case 41:
			xp = 50;
			ReqLevel = 35;
			break;
		case 1325:
			xp = 75;
			removeamount = 2;
			ReqLevel = 35;
			break;
		case 1295:
			xp = 75;
			removeamount = 2;
			ReqLevel = 36;
			break;
		case 865:
			xp = 50;
			ReqLevel = 37;
			break;
		case 1157:
			xp = 75;
			removeamount = 2;
			ReqLevel = 37;
			break;
		case 1177:
			xp = 75;
			removeamount = 2;
			ReqLevel = 38;
			break;
		case 1339:
			xp = 113;
			removeamount = 3;
			ReqLevel = 39;
			break;
		case 1365:
			xp = 113;
			removeamount = 3;
			ReqLevel = 40;
			break;
		case 1105:
			xp = 113;
			removeamount = 3;
			ReqLevel = 41;
			break;
		case 1193:
			xp = 113;
			removeamount = 3;
			ReqLevel = 42;
			break;
		case 1311:
			xp = 113;
			removeamount = 3;
			ReqLevel = 44;
			break;
		case 1083:
		case 1069:
			xp = 113;
			removeamount = 3;
			ReqLevel = 46;
			break;
		case 1119:
			xp = 188;
			removeamount = 5;
			ReqLevel = 48;
			break;
			
		default:
			xp = 0;
			ReqLevel = 0;
			break;
		}
		if (level < ReqLevel) {
			c.sendMessage("You need a smithing level of "+ReqLevel+" to make this item.");
			return;
		}

		doaction(c, item, remove, removeamount, maketimes, -1, -1, xp);

	}
	
	public boolean doactionC(Client c, int toadd, int toremove, int toremove2, int timestomake, int Mold, int level, int xp) {
		int maketimes = timestomake;
		c.getPA().closeAllWindows();
		if (c.playerLevel[c.playerCrafting] >= level){
		if (c.getItems().playerHasItem(toremove, 1) && c.getItems().playerHasItem(toremove2, 1) && c.getItems().playerHasItem(Mold,1))
		
		{
			c.startAnimation(899);
		if (maketimes > 1 && c.getItems().playerHasItem(toremove, toremove2 * 2))
		{
		c.sendMessage("You make some " + c.getItems().getItemName(toadd) +"s");
		}
		else
		{
			c.sendMessage("You make a " + c.getItems().getItemName(toadd));
		}
		while (maketimes > 0)
		{
			if (c.getItems().playerHasItem(toremove, 1) && c.getItems().playerHasItem(toremove2, 1)){
				c.getItems().deleteItem2(toremove, 1);
				c.getItems().deleteItem2(toremove2, 1);

		
		c.getItems().addItem(toadd, 1);
		c.getPA().addSkillXP(xp * Config.CRAFTING_EXPERIENCE, c.playerCrafting);
		c.getPA().refreshSkill(c.playerCrafting);
		maketimes--;
			}
			else
			{
				break;
			}
		}
		}
		else
		{
			c.getPA().sendStatement("You need the gem and mold to make this item.");
			return false;
		}
		//return true;
	}else {
	c.getPA().sendStatement("You need a crafting level of at least "+level+" to make this");
	return false;
	}
	return true;
	}

	private void CheckMith(Client c, int level, int amounttomake, String type) {
		remove = 2359;

		if (type.equalsIgnoreCase("1355") && level >= 51) // Axe
		{
			xp = 50;
			item = 1355;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equalsIgnoreCase("1209") && level >= 50) // Dagger
		{
			xp = 50;
			item = 1209;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1428") && level >= 52) // Mace
		{
			xp = 50;
			item = 1428;
			removeamount = 1;
			maketimes = amounttomake;
		} else if (type.equals("1143") && level >= 53) // Med helm
		{
			xp = 50;
			item = 1143;
			removeamount = 1;
			maketimes = amounttomake;
		} else if (type.equals("9142") && level >= 54) // Dart tips
		{
			xp = 50;
			item = 9142;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1285") && level >= 54) // Sword (s)
		{
			xp = 50;
			item = 1285;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("4822") && level >= 54) // Nails
		{
			xp = 50;
			item = 4822;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("42") && level >= 55) // Arrow tips
		{
			xp = 50;
			item = 42;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1329") && level >= 55)// Scim
		{
			xp = 100;
			item = 1329;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1299") && level >= 56) // Longsword
		{
			xp = 100;
			item = 1299;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("866") && level >= 57) // Knives
		{
			xp = 50;
			item = 866;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1159") && level >= 57) // Full Helm
		{
			xp = 100;
			item = 1159;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1181") && level >= 58) // Square shield
		{
			xp = 100;
			item = 1181;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1343") && level >= 59) // Warhammer
		{
			xp = 150;
			item = 1343;
			removeamount = 3;
			maketimes = amounttomake;
		}

		else if (type.equals("1369") && level >= 60) // Battle axe
		{
			xp = 150;
			item = 1369;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1109") && level >= 61) // Chain
		{
			xp = 150;
			item = 1109;
			removeamount = 3;
			maketimes = amounttomake;
		}

		else if (type.equals("1197") && level >= 62) // Kite
		{
			xp = 150;
			item = 1197;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1315") && level >= 64) // 2h Sword
		{
			xp = 150;
			item = 1315;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1071") && level >= 66) // Platelegs
		{
			xp = 150;
			item = 1071;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1085") && level >= 66) // PlateSkirt
		{
			xp = 150;
			item = 1085;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1121") && level >= 68) // Platebody
		{
			xp = 250;
			item = 1121;
			removeamount = 5;
			maketimes = amounttomake;
		} else {
			c.sendMessage("You don't have a high enough level to make this Item!");
			return;
		}

		doaction(c, item, remove, removeamount, maketimes, -1, -1, xp);

	}

	private void CheckRune(Client c, int level, int amounttomake, String type) {
		remove = 2363;

		if (type.equalsIgnoreCase("1359") && level >= 86) // Axe
		{
			xp = 75;
			item = 1359;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equalsIgnoreCase("1213") && level >= 85) // Dagger
		{
			xp = 75;
			item = 1213;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1432") && level >= 87) // Mace
		{
			xp = 75;
			item = 1432;
			removeamount = 1;
			maketimes = amounttomake;
		} else if (type.equals("1147") && level >= 88) // Med helm
		{
			xp = 75;
			item = 1147;
			removeamount = 1;
			maketimes = amounttomake;
		} else if (type.equals("9144") && level >= 89) // Dart tips
		{

			xp = 75;
			item = 9144;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1289") && level >= 89) // Sword (s)
		{
			xp = 75;
			item = 1289;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("4824") && level >= 89) // Nails
		{
			xp = 75;
			item = 4824;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("44") && level >= 90) // Arrow tips
		{
			xp = 75;
			item = 44;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1333") && level >= 90)// Scim
		{
			xp = 150;
			item = 1333;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1303") && level >= 91) // Longsword
		{
			xp = 150;
			item = 1303;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("868") && level >= 92) // Knives
		{
			xp = 75;
			item = 868;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1163") && level >= 92) // Full Helm
		{
			xp = 150;
			item = 1163;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1185") && level >= 93) // Square shield
		{
			xp = 150;
			item = 1185;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1347") && level >= 94) // Warhammer
		{
			xp = 225;
			item = 1347;
			removeamount = 3;
			maketimes = amounttomake;
		}

		else if (type.equals("1373") && level >= 95) // Battle axe
		{
			xp = 225;
			item = 1373;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1113") && level >= 96) // Chain
		{
			xp = 225;
			item = 1113;
			removeamount = 3;
			maketimes = amounttomake;
		}

		else if (type.equals("1201") && level >= 97) // Kite
		{
			xp = 225;
			item = 1201;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1319") && level >= 99) // 2h Sword
		{
			xp = 225;
			item = 1319;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1079") && level >= 99) // Platelegs
		{
			xp = 225;
			item = 1079;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1093") && level >= 99) // PlateSkirt
		{
			xp = 225;
			item = 1093;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1127") && level >= 99) // Platebody
		{
			xp = 313;
			item = 1127;
			removeamount = 5;
			maketimes = amounttomake;
		} else {
			c.sendMessage("You don't have a high enough level to make this Item!");
			return;
		}

		doaction(c, item, remove, removeamount, maketimes, -1, -1, xp);

	}

	private void CheckAddy(Client c, int level, int amounttomake, String type) {
		remove = 2361;

		if (type.equalsIgnoreCase("1357") && level >= 71) // Axe
		{
			xp = 63;
			item = 1357;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equalsIgnoreCase("1211") && level >= 70) // Dagger
		{
			xp = 63;
			item = 1211;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1430") && level >= 72) // Mace
		{
			xp = 63;
			item = 1430;
			removeamount = 1;
			maketimes = amounttomake;
		} else if (type.equals("1145") && level >= 73) // Med helm
		{
			xp = 63;
			item = 1145;
			removeamount = 1;
			maketimes = amounttomake;
		} else if (type.equals("9143") && level >= 74) // Dart tips
		{
			xp = 63;
			item = 9143;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1287") && level >= 74) // Sword (s)
		{
			xp = 63;
			item = 1287;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("4823") && level >= 74) // Nails
		{
			xp = 63;
			item = 4823;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("43") && level >= 75) // Arrow tips
		{
			xp = 63;
			item = 43;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1331") && level >= 75)// Scim
		{
			xp = 125;
			item = 1331;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1301") && level >= 76) // Longsword
		{
			xp = 125;
			item = 1301;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("867") && level >= 77) // Knives
		{
			xp = 63;
			item = 867;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1161") && level >= 77) // Full Helm
		{
			xp = 125;
			item = 1161;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1183") && level >= 78) // Square shield
		{
			xp = 125;
			item = 1183;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1345") && level >= 79) // Warhammer
		{
			xp = 188;
			item = 1345;
			removeamount = 3;
			maketimes = amounttomake;
		}

		else if (type.equals("1371") && level >= 80) // Battle axe
		{
			xp = 188;
			item = 1371;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1111") && level >= 81) // Chain
		{
			xp = 188;
			item = 1111;
			removeamount = 3;
			maketimes = amounttomake;
		}

		else if (type.equals("1199") && level >= 82) // Kite
		{
			xp = 188;
			item = 1199;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1317") && level >= 84) // 2h Sword
		{
			xp = 188;
			item = 1317;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1073") && level >= 86) // Platelegs
		{
			xp = 188;
			item = 1073;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1091") && level >= 86) // PlateSkirt
		{
			xp = 188;
			item = 1091;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1123") && level >= 88) // Platebody
		{
			xp = 313;
			item = 1123;
			removeamount = 5;
			maketimes = amounttomake;
		} else {
			c.sendMessage("You don't have a high enough level to make this Item!");
			return;
		}

		doaction(c, item, remove, removeamount, maketimes, -1, -1, xp);

	}

	private void CheckBronze(Client c, int level, int amounttomake, String type) {
		if (type.equalsIgnoreCase("1351") && level >= 1) {
			xp = 13;
			item = 1351;
			remove = 2349;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equalsIgnoreCase("1205") && level >= 1) {
			xp = 13;
			item = 1205;
			remove = 2349;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1422") && level >= 2) {
			xp = 13;
			item = 1422;
			remove = 2349;
			removeamount = 1;
			maketimes = amounttomake;
		} else if (type.equals("1139") && level >= 3) {
			xp = 13;
			item = 1139;
			remove = 2349;
			removeamount = 1;
			maketimes = amounttomake;
		} else if (type.equals("819") && level >= 4) {
			xp = 13;
			item = 819;
			remove = 2349;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1277") && level >= 4) {
			xp = 13;
			item = 1277;
			remove = 2349;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("4819") && level >= 4) {
			xp = 13;
			item = 4819;
			remove = 2349;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("39") && level >= 5) {
			xp = 13;
			item = 39;
			remove = 2349;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1321") && level >= 5) {
			xp = 25;
			item = 1321;
			remove = 2349;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1291") && level >= 6) {
			xp = 25;
			item = 1291;
			remove = 2349;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("864") && level >= 7) {
			xp = 25;
			item = 864;
			remove = 2349;
			removeamount = 1;
			maketimes = amounttomake;
		}

		else if (type.equals("1155") && level >= 7) {
			xp = 25;
			item = 1155;
			remove = 2349;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1173") && level >= 8) {
			xp = 25;
			item = 1173;
			remove = 2349;
			removeamount = 2;
			maketimes = amounttomake;
		}

		else if (type.equals("1337") && level >= 9) {
			xp = 38;
			item = 1337;
			remove = 2349;
			removeamount = 3;
			maketimes = amounttomake;
		}

		else if (type.equals("1375") && level >= 10) {
			xp = 38;
			item = 1375;
			remove = 2349;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1103") && level >= 11) {
			xp = 38;
			item = 1103;
			remove = 2349;
			removeamount = 3;
			maketimes = amounttomake;
		}

		else if (type.equals("1189") && level >= 12) {
			xp = 38;
			item = 1189;
			remove = 2349;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1307") && level >= 14) {
			xp = 38;
			item = 1307;
			remove = 2349;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1075") && level >= 16) {
			xp = 38;
			item = 1075;
			remove = 2349;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1087") && level >= 16) {
			xp = 38;
			item = 1087;
			remove = 2349;
			removeamount = 3;
			maketimes = amounttomake;
		} else if (type.equals("1117") && level >= 18) {
			xp = 63;
			item = 1117;
			remove = 2349;
			removeamount = 5;
			maketimes = amounttomake;
		} else {
			c.sendMessage("You don't have a high enough level to make this Item!");
			return;
		}

		doaction(c, item, remove, removeamount, maketimes, -1, -1, xp);

	}

	public boolean doaction(Client c, int toadd, int toremove, int toremove2,
			int timestomake, int NOTUSED, int NOTUSED2, int xp) {
		int maketimes = timestomake;
		c.getPA().closeAllWindows();
		if (c.getItems().playerHasItem(toremove, toremove2)) {
			c.startAnimation(898);
			if (maketimes > 1
					&& c.getItems().playerHasItem(toremove, toremove2 * 2)) {
				c.sendMessage("You make some "
						+ c.getItems().getItemName(toadd) + "s");
			} else {
				c.sendMessage("You make a " + c.getItems().getItemName(toadd));
			}
			while (maketimes > 0) {
				if (c.getItems().playerHasItem(toremove, toremove2)) {
					c.getItems().deleteItem2(toremove, toremove2);
					if (c.getItems().getItemName(toadd).contains("bolt")) {
						c.getItems().addItem(toadd, 10);
					} else if (c.getItems().getItemName(toadd).contains("nail")) {
						c.getItems().addItem(toadd, 15);
					} else if (c.getItems().getItemName(toadd)
							.contains("arrow")) {
						c.getItems().addItem(toadd, 15);
					} else if (c.getItems().getItemName(toadd)
							.contains("knife")) {
						c.getItems().addItem(toadd, 5);
					} else if (c.getItems().getItemName(toadd)
							.contains("cannon")) {
						c.getItems().addItem(toadd, 4);
					} else {
						c.getItems().addItem(toadd, 1);
					}
					c.getPA().addSkillXP(xp * Config.SMITHING_EXPERIENCE, 13);
					c.getPA().refreshSkill(13);
					maketimes--;
				} else {
					break;
				}
			}
		} else {
			c.sendMessage("You don't have enough bars to make this item!");
			return false;
		}
		return true;
	}
}