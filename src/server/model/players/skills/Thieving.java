package server.model.players.skills;

import server.Config;
import server.model.players.Client;
import server.util.Misc;

/**
 * Thieving.java
 *
 * @author Flow
 *
 **/

public class Thieving {

	private Client c;

	public Thieving(Client c) {
		this.c = c;
	}
	
	public final static int SEEDS[] = { 5291, 5291, 5291, 5291, 5291, 5292 };

	private enum NPC {
		MAN(new int[]{1}, 1, 8, 200, 1),
		ALKHARID_WARRIOR(new int[]{18}, 25, 26, 500, 1),
		GUARD(new int[]{9}, 40, 47, 1000, 2),
		ARDOUGNE_KNIGHT(new int[]{26}, 55, 85, 1500, 3),
		PALADIN(new int[]{20}, 70, 152, 2000, 4),
		HERO(new int[]{21}, 80, 273, 3000, 5);

		private int[] NPC;
		private int levelReq, XP, reward, damage;

		private NPC(int NPC[], int levelReq, int XP, int reward, int damage) {
			this.NPC = NPC;
			this.levelReq = levelReq;
			this.XP = XP;
			this.reward = reward;
			this.damage = damage;
		}

		public int[] getNPC() {
			return NPC;
		}

		public int getReq() {
			return levelReq;
		}

		public int getXP() {
			return XP;
		}

		public int getReward() {
			return reward;
		}

		public int getDamage() {
			return damage;
		}
	}
	
	public void SNPCsteal() {
		if (System.currentTimeMillis() - c.lastThieve < 2000)
			return;
		if (c.playerThieving >= 50) {
			c.startAnimation(881);
			c.getItems().addItem(SEEDS[(int) (SEEDS.length * Math.random())], 1);
			c.getPA().addSkillXP(10 * Config.THIEVING_EXPERIENCE, c.playerThieving);
		} else {
		c.sendMessage("You must be level 50 to pickpocket the farmer.");
		}
	}

	private NPC forNpc(int id) {
		for (NPC n : NPC.values()) {
			for (int npc : n.getNPC()) {
				if (npc == id) {
					return n;
				}
			}
		}
		return null;
	}

	public void stealFromNPC(int id) {
		if (System.currentTimeMillis() - c.lastThieve < 2000)
			return;
		NPC v = forNpc(id);
		if (v != null) {
			if (c.playerLevel[c.playerThieving] >= v.getReq()) {
				if (!randomFail(v.getReq())) {
					c.getItems().addItem(995, v.getReward());
					c.getPA().addSkillXP(v.getXP(), c.playerThieving);
					c.startAnimation(881);
					c.lastThieve = System.currentTimeMillis();
					c.sendMessage("You pickpocket the NPC.");
				} else {
					c.setHitDiff(v.getDamage());
					c.setHitUpdateRequired(true);
					c.playerLevel[c.playerHitpoints] -= v.getDamage();
					c.gfx100(254);
					c.lastThieve = System.currentTimeMillis() + 2000;
					c.sendMessage("You fail to pickpocket the NPC.");
				}
			} else {
				c.sendMessage("You don't have a high enough thieving level to pickpocket this NPC.");
			}
		}
	}
	
	public boolean randomFail(int levelReq) {
		return c.playerLevel[c.playerThieving] - levelReq + getEquipmentBonus() < Misc.random(levelReq);
	}

	private int getEquipmentBonus() {
		return c.playerEquipment[c.playerHands] == 10075 ? 5 : 0;
	}

	public static StallData forStall(int id) {
		for (StallData sd : StallData.values()) {
			if (sd.getObjId() == id) {
				return sd;
			}
		}
		return null;
	}

	public static int getRandom(int stall) {
		StallData sd = forStall(stall);
		return sd.getReward()[(int)(sd.getReward().length * Math.random())];
	}
	private enum StallData {
		BAKER(2561, new int[]{1897}, 16, 5),
		SILK(2560, new int[]{950}, 24, 20),
		FUR(2563, new int[]{7680}, 36, 35),
		SPICE(2564, new int[]{1613}, 81, 65),
		GEM(2562, new int[]{1623, 1621, 1619, 1617, 1631}, 160, 75);

		private int objId, xp, level;
		private int[] reward;

		private StallData(int objId, int[] id, int xp, int level) {
			this.objId = objId;
			this.reward = id;
			this.xp = xp;
			this.level = level;
		}

		public int getObjId() {
			return objId;
		}

		public int[] getReward() {
			return reward;
		}

		public int getXP() {
			return xp;
		}

		public int getLevel() {
			return level;
		}
	}

	public void stealFromStall(int stall) {
		if (System.currentTimeMillis() - c.lastThieve > 2000) {
			StallData sd = forStall(stall);
			if (sd != null) {
				if (c.playerLevel[c.playerThieving] >= sd.getLevel()) {
					c.getItems().addItem(getRandom(stall), 1);
					c.startAnimation(832);
					c.getPA().addSkillXP(sd.getXP(), c.playerThieving);
					c.lastThieve = System.currentTimeMillis();
					c.sendMessage("You steal from the stall.");
				} else {
					c.sendMessage("You need a thieving level of at least "+sd.getLevel()+" to steal from this stall.");
				}
			}
		}
	}
}