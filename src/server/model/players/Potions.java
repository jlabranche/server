package server.model.players;

/**
 * @author Andrew
 * @author Galkon
 */

public class Potions {

	private Client c;
	
	public Potions(Client c) {
		this.c = c;
	}
	
	public void usePotion(int itemId, int slot) {
		if (c.duelRule[5]) {
			c.sendMessage("You may not drink potions in this duel.");
			return;
		}
		if(!c.isDead && System.currentTimeMillis() - c.foodDelay > c.potDelay) {
			c.foodDelay = System.currentTimeMillis();
			if(getName("strength", itemId))
					handlePotion(itemId, slot, c.playerStrength);
					
			if(getName("attack", itemId))
				handlePotion(itemId, slot, c.playerAttack);
					
			if(getName("defence", itemId))
				handlePotion(itemId, slot, c.playerDefence);
					
			if(getName("saradomin brew", itemId))
				saraBrew(itemId, slot);
			
			if(getName("ranging potion", itemId))
				handlePotion(itemId, slot, c.playerRanged);
			
			if(getName("restore", itemId))
				restorePotion(itemId, slot);
			
			if(getName("prayer potion", itemId))
				prayerPotion(itemId, slot);
			
			if(getName("zamorak brew", itemId))
				zamorakBrew(itemId, slot);
			
			if(getName("agility potion", itemId))
				handlePotion(itemId, slot, c.playerAgility);
			
			if(getName("fishing potion", itemId))
				handlePotion(itemId, slot, c.playerFishing);
			
			if(getName("magic potion", itemId))
				handlePotion(itemId, slot, c.playerMagic);
						
		}
	}
	
	/**
	 * Gets the replacement item simple stuff.
	 */
	
	public int replacementId(int itemId) {
		switch(itemId) {
			/**
			 * Jagex is gay and makes us do more work making four dose potions way greater than the 3 dose.  Returns 3 dose potion.
			 */
			case 2428://Attack Potion
				return 121;
			case 2430://Restore Potion
				return 127;
			case 2432://Defence Potion
				return 133;
			case 2434://Prayer Potion
				return 139;
			case 2436://Super Attack
				return 145;
			case 2438://Fishing Potion (Some use as a overload like I might.  Not sure at this time)
				return 151;
			case 2440://Super Strength
				return 157;
			case 2442://Super Defence
				return 163;
			case 2444://Ranging Potion
				return 169;
			case 2446://Antiposion
				return 175;
			case 2448://Super Antiposion
				return 181;
			case 2450://Zamorak Brew
				return 189;
			case 3032://Agility Potion
				return 3034;
		}
		/**
		 * With one dose left return a empty vail.
		 */
		if(getName("(1)", itemId))
			return 229;
		else 
			return itemId + 2;
	}
	
	/**
	 * Simple way for getting potion ids.
	 */
	
	public boolean getName(String name, int itemId) {
		if(c.getItems().getItemName(itemId).toLowerCase().contains(name))
			return true;
		else
			return false;
	}
	
	/**
	 * Bonus for the potions.
	 */
	
	public int getBonus(int itemId, int skillId) {
		if(getName("magic", itemId))
			return 5;
		if(getName("prayer", itemId))
			return 7 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[skillId]) / 4));
		if(getName("ranging", itemId))
			return 4 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[skillId]) * .10));
		if(!getName("super", itemId))
			return 3 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[skillId]) * .10));
		else
			return 5 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[skillId]) * .15));
	}
	
	
	/**
	 * Start Potion Crap
	 */
	
	public void handlePotion(int itemId, int slot, int skillId) {
		c.startAnimation(829);
		c.getItems().deleteItem(itemId, slot, 1);
		c.getItems().addItem(replacementId(itemId), 1);
		if(c.playerLevel[skillId] <= c.getPA().getLevelForXP(c.playerXP[skillId])) {
			c.playerLevel[skillId] += getBonus(itemId, skillId);
		} else if(c.playerLevel[skillId] > c.getPA().getLevelForXP(c.playerXP[skillId])) {
			c.playerLevel[skillId] = c.getPA().getLevelForXP(c.playerXP[0]) + getBonus(itemId, skillId);
		}
		c.getPA().refreshSkill(skillId);
		c.sendMessage("You drink the " + c.getItems().getItemName(itemId).toLowerCase() + ".");
	}
	
	public void prayerPotion(int itemId, int slot) {
		c.startAnimation(829);
		c.getItems().deleteItem(itemId, slot, 1);
		c.getItems().addItem(replacementId(itemId), 1);
		c.playerLevel[c.playerPrayer] += getBonus(itemId, c.playerPrayer);
		if(c.playerLevel[c.playerPrayer] > c.getLevelForXP(c.playerXP[c.playerPrayer])){
			c.playerLevel[c.playerPrayer] = c.getLevelForXP(c.playerXP[c.playerPrayer]);
		}
		c.getPA().refreshSkill(c.playerPrayer);
		c.sendMessage("You drink the " + c.getItems().getItemName(itemId).toLowerCase() + ".");
	}
	
	public void restorePotion(int itemId, int slot) {
		int[] restorePotion = { c.playerDefence, c.playerAttack, c.playerStrength, c.playerMagic, c.playerRanged };
		c.startAnimation(829);
		c.getItems().deleteItem(itemId, slot, 1);
		c.getItems().addItem(replacementId(itemId), 1);
		if(getName("restore potion", itemId)){
			for(int skills : restorePotion){
				c.playerLevel[skills] += 10 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[skills]) * .30));
				if(c.playerLevel[skills] > c.getLevelForXP(c.playerXP[skills])){
					c.playerLevel[skills] = c.getLevelForXP(c.playerXP[skills]);
				}
				c.getPA().refreshSkill(skills);
			}
		} else {
			for(int skill = 0; skill < 21; skill++){
				if(skill != c.playerHitpoints){
					c.playerLevel[skill] += 8 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[skill]) * .25));
						if(c.playerLevel[skill] > c.getLevelForXP(c.playerXP[skill])){
							c.playerLevel[skill] = c.getLevelForXP(c.playerXP[skill]);
						}
						c.getPA().refreshSkill(skill);
				}
			}
		}
		c.sendMessage("You drink the " + c.getItems().getItemName(itemId).toLowerCase() + ".");
	}
	
	public void zamorakBrew(int itemId, int slot) {
		int[] increasing = { c.playerAttack, c.playerStrength };
		int[] refreshSkills = { c.playerAttack, c.playerStrength, c.playerDefence, c.playerHitpoints, c.playerPrayer };
		c.startAnimation(829);
		c.getItems().deleteItem(itemId, slot, 1);
		c.getItems().addItem(replacementId(itemId), 1);
		c.playerLevel[c.playerAttack] += 2 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[c.playerAttack]) * .20));
		c.playerLevel[c.playerStrength] += 2 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[c.playerStrength]) * .12));
		c.playerLevel[c.playerPrayer] += (int)(Math.floor((double)c.getLevelForXP(c.playerXP[c.playerPrayer]) * .10));
		c.playerLevel[c.playerDefence] -= 2 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[c.playerDefence]) * .10));
		c.playerLevel[c.playerHitpoints] -= 20 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[c.playerHitpoints]) * .10));
		for(int skill : increasing){
			if(c.playerLevel[skill] > 2 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[skill]) * (skill == 0 ? 1.20 : 1.12))))
				c.playerLevel[skill] = 2 + (int)(Math.floor((double)c.getLevelForXP(c.playerXP[skill]) * (skill == 0 ? 1.20 : 1.12)));
		}
		if(c.playerLevel[c.playerDefence] < 0)
			c.playerLevel[c.playerDefence] = 1;
		if(c.playerLevel[c.playerHitpoints] <= 0)
			c.getPA().applyDead();
		if(c.playerLevel[c.playerPrayer] > c.getLevelForXP(c.playerXP[c.playerPrayer]))
			c.playerLevel[c.playerPrayer] = c.getLevelForXP(c.playerXP[c.playerPrayer]);
		for(int skill : refreshSkills) {
			if(skill != c.playerHitpoints){
				c.getPA().refreshSkill(skill);
			}
			if(c.playerLevel[skill] > 0){
				c.getPA().refreshSkill(skill);
			}
		}
		c.sendMessage("You drink the " + c.getItems().getItemName(itemId).toLowerCase() + ".");
	}
		
	public void saraBrew(int itemId, int slot) {
		c.startAnimation(829);
		c.getItems().deleteItem(itemId, slot, 1);
		c.getItems().addItem(replacementId(itemId), 1);
		int[] toDecrease = {0,2,4,6};
		int[] toIncrease = {1,3};
		for (int skill : toDecrease) {
			c.playerLevel[skill] -= Math.floor(c.getLevelForXP(c.playerXP[skill])  * .10);
			if (c.playerLevel[skill] < 0)
				c.playerLevel[skill] = 1;
			c.getPA().refreshSkill(skill);
		}
		for (int skill : toIncrease){
			if(skill == 3){
				c.playerLevel[skill] += Math.floor(c.getLevelForXP(c.playerXP[skill]) * .15);
				if(c.playerLevel[skill] > Math.floor(c.getLevelForXP(c.playerXP[skill]) * 1.15))
					c.playerLevel[skill] = (int) Math.floor((c.getLevelForXP(c.playerXP[skill]) * 1.15));
				c.getPA().refreshSkill(skill);
			} else {
				c.playerLevel[skill] += Math.floor(c.getLevelForXP(c.playerXP[skill]) * .25);
				if(c.playerLevel[skill] > Math.floor(c.getLevelForXP(c.playerXP[skill]) * 1.25))
					c.playerLevel[skill] = (int) (Math.floor(c.getLevelForXP(c.playerXP[skill]) * 1.25));
				c.getPA().refreshSkill(skill);
			}
		}
		c.sendMessage("You drink the " + c.getItems().getItemName(itemId).toLowerCase() + ".");
	}
}