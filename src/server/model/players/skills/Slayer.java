package server.model.players.skills;

import server.model.players.Client;
import server.Config;
import server.Server;
import server.util.Misc;

/**
 * Slayer.java
 * 
 * @author Sanity
 * 
 **/

public class Slayer {

	private Client c;

	public Slayer(Client c) {
		this.c = c;
	}

	public int[] lowTasks = {76,112,116,181};
	public int[] lowReqs = {1,1,1};
	public int[] medTasks = {82,83};
	public int[] medReqs = {40,50};
	public int[] highTasks = { 1590 };
	public int[] highReqs = { 70 };
	
	public void easyTask(){
		giveTask(1);
	}
	
	public void medTask(){
		giveTask(2);
	}
	
	public void hardTask(){
		giveTask(3);
	}

	public void giveTask2() {
		for (int j = 0; j < lowTasks.length; j++) {
			if (lowTasks[j] == c.slayerTask) {
				c.sendMessage("You already have an easy task... to kill "
						+ c.taskAmount + " "
						+ Server.npcHandler.getNpcListName(c.slayerTask) + ".");
				return;
			}
		}
		giveTask(1);
	}

	public void giveTask(int taskLevel) {
		int given = 0;
		int random = 1;
		if (taskLevel == 1) {
			random = (int) (Math.random() * (lowTasks.length - 1));
			given = lowTasks[random];
		} else if (taskLevel == 2) {
			random = (int) (Math.random() * (medTasks.length - 1));
			given = medTasks[random];
		} else if (taskLevel == 3) {
			random = (int) (Math.random() * (highTasks.length - 1));
			given = highTasks[random];
		}
		if (!canDoTask(taskLevel, random)) {
			giveTask(taskLevel);
			return;
		}
		c.slayerTask = given;
		c.taskAmount = Misc.random(10) + 15;
		c.getDH().sendDialogues(10, 1599);
	}
	
	public void slayloc(int slaynpc){
		slaynpc = c.slayerTask;
		switch (slaynpc) {
			case 76:
				c.getPA().sendStatement("@dre@Zombies:@bla@ Located west of Canifis ");
				break;
			case 112:
				c.getPA().sendStatement("@dre@Moss Giants:@bla@ Located in the forest of lletya.");
				break;
			case 116:
				c.getPA().sendStatement("@dre@Cyclops:@bla@ Located southwest from Duradel.");
				break;
			case 181:
				c.getPA().sendStatement("@dre@Chaos Druids: @bla@Located in the Sophanem Dungeon.");
				break;
			case 82:
				c.getPA().sendStatement("@dre@Lesser Demons: @bla@Located in the Sophanem Dungeon.");
				break;
			case 83:
				c.getPA().sendStatement("@dre@Greater Demons:@bla@ Located in the Sophanem Dungeon.");
				break;
			case 1590:
				c.getPA().sendStatement("@dre@Bronze Dragons:@bla@ Located in the Sophanem Dungeon.");
				break;
		}
	}
	
	public void slaylvl (int slayxp) {
		slayxp = c.slayerTask;
		switch (slayxp) {
			//simple task npcs
		case 76:
			c.getPA().addSkillXP(8 * Config.SLAYER_EXPERIENCE, c.playerSlayer);
			c.slayPoints += 1 + Misc.random(2);
			break;
		case 112:
			c.getPA().addSkillXP(10 * Config.SLAYER_EXPERIENCE, c.playerSlayer);
			c.slayPoints += 2 + Misc.random(2);
			break;
		case 116:
			c.getPA().addSkillXP(10 * Config.SLAYER_EXPERIENCE, c.playerSlayer);
			c.slayPoints += 2 + Misc.random(2);
			break;
		case 181:
			c.getPA().addSkillXP(8 * Config.SLAYER_EXPERIENCE, c.playerSlayer);
			c.slayPoints += 1 + Misc.random(2);
			break;
			//difficult task npcs
		case 82:
			c.getPA().addSkillXP(20 * Config.SLAYER_EXPERIENCE, c.playerSlayer);
			c.slayPoints += 2 + Misc.random(15);
			break;
		case 83:
			c.getPA().addSkillXP(25 * Config.SLAYER_EXPERIENCE, c.playerSlayer);
			c.slayPoints += 3 + Misc.random(20);
			break;
			//hard task npcs
		case 1590:
			c.getPA().addSkillXP(25 * Config.SLAYER_EXPERIENCE, c.playerSlayer);
			c.slayPoints += 3 + Misc.random(20);
			break;
		}
	}

	public boolean canDoTask(int taskLevel, int random) {
		if (taskLevel == 1) {
			return c.playerLevel[c.playerSlayer] >= lowReqs[random];
		} else if (taskLevel == 2) {
			return c.playerLevel[c.playerSlayer] >= medReqs[random];
		} else if (taskLevel == 3) {
			return c.playerLevel[c.playerSlayer] >= highReqs[random];
		}
		return false;
	}
}