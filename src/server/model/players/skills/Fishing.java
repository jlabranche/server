package server.model.players.skills;

import server.util.Misc;
import server.model.players.Client;
import server.event.EventManager;
import server.event.Event;
import server.Config;
import server.event.EventContainer;

	/**
	 * Class Fishing
	 * Handles Fishing
	 * @author PapaDoc
	 * START: 16:45 01/11/2010
	 * FINISH: 16:56 01/11/2010
	 */

public class Fishing {

	private Client c;

	public Fishing(Client c) {
		this.c = c;
	}

	//id,levelreq,tool,raw,xp,anim
	private final int[][] data = {
		{1, 1, 303, 317, 10, 621},	//SHRIMP
		{2, 20, 309, 335, 70, 622},	//SALMON
		{3, 40, 301, 377, 90, 619},	//LOBSTER
		{4, 62, 311, 7944, 110, 618},	//MONK FISH
		{5, 91, 311, 389, 140, 618},	//Manta
		{6, 35, 311, 359, 80, 621},	//TUNA
		{7, 76, 311, 383, 120, 618},	//SHARK
		{8, 10, 303, 321, 40, 621},	//Anchovies
		{9, 20, 303, 363, 100, 621},	//Bass
		{10, 50, 305, 371, 100, 620},	//SwordFish
		{11, 81, 311, 395, 125, 618},	//Seatutle
	};

	public void attemptdata(final Client c, int npcId) {
		if (c.getItems().freeSlots() == 0) {
			c.sendMessage("You haven't got enough inventory space to continue fishing!");
			c.getPA().sendStatement("You haven't got enough inventory space to continue fishing!");
			resetFishing(c);
			return;
		}
		for(int i = 0; i < data.length; i++) {
			if(npcId == data[i][0]) {
				if (c.playerLevel[c.playerFishing] < data[i][1]) {
					c.sendMessage("You haven't got high enough fishing level to fish here!");
					c.sendMessage("You at list need the fishing level of "+ data[i][1] +".");
					c.getPA().sendStatement("You need the fishing level of "+ data[i][1] +" to fish here.");
					return;
				}
				if (!c.getItems().playerHasItem(data[i][2])) {
					c.sendMessage("You need a "+ c.getItems().getItemName(data[i][2]) +" to fish here.");
					return;
				}

				c.fishingProp[0] = data[i][5]; //	ANIM
				c.fishingProp[1] = data[i][3]; //	FISH
				c.fishingProp[2] = data[i][4]; //	XP

				c.sendMessage("You start fishing.");
				c.startAnimation(c.fishingProp[0]);
				c.stopPlayerSkill = true;
				if(c.playerIsFishing) {
					return;
				}
				c.playerIsFishing = true;
				EventManager.getSingleton().addEvent(new Event() {
					public void execute(EventContainer p) {
						c.getItems().addItem(c.fishingProp[1], 1);
						c.getPA().addSkillXP((c.fishingProp[2] * Config.FISHING_EXPERIENCE), c.playerFishing);
						c.sendMessage("You catch a "+ c.getItems().getItemName(c.fishingProp[1]) +".");
						c.startAnimation(c.fishingProp[0]);
						if(!c.stopPlayerSkill) {
							resetFishing(c);
							p.stop();
						}
						if (c.getItems().freeSlots() == 0) {
							resetFishing(c);
							c.sendMessage("You don't have enough inventory space to continue fishing.");
							c.getPA().sendStatement("You don't have enough inventory space to continue fishing.");
							p.stop();
						}
						/*if (Misc.random(15) == 0) {
							resetFishing(c);
							p.stop();
						}*/
					}
				}, getTimer(c, npcId) + Misc.random(2000));
			}
		}
	}

	private void resetFishing(Client c) {
		c.startAnimation(65535);
		c.getPA().resetVariables();
		c.getPA().removeAllWindows();
		c.playerIsFishing = false;
		for(int i = 0; i < 2; i++) {
			c.fishingProp[i] = -1;
		}
	}

	private final int getTimer(Client c, int npcId) {
		switch (npcId) {
			case 1: return 1300;
			case 2: return 1400;
			case 3: return 2500;
			case 4: return 3500;
			case 5: return 3800;
			case 6: return 1400;
			case 7: return 3600;
			case 8: return 1400;
			case 9: return 1500;
			case 10: return 1400;
			case 11: return 3300;
			default: return -1;
		}
	}
}