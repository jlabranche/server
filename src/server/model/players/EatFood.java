package server.model.players;

public class EatFood {
    
    public static void eat(final Client c, int id, int slot) {
	final String name = c.getItems().getItemName(id);
	if (c.duelRule[6]) {
	    c.sendMessage("You may not eat in this duel.");
	    return;
	}
	if (((System.currentTimeMillis() - c.foodDelay) >= 1800) && (c.playerLevel[3] > 0)) {
	    c.getCombat().resetPlayerAttack();
	    c.attackTimer += 2;
	    c.startAnimation(829);
	    c.getItems().deleteItem(id, slot, 1);
	    final FoodToEat f = FoodToEat.food.get(id);
	    if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
		c.playerLevel[3] += f.getHeal();
		if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3])) {
		    c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
		}
	    }
	    c.foodDelay = System.currentTimeMillis();
	    c.getPA().refreshSkill(3);
	    c.sendMessage("You eat the " + name + ".");
	}
    }
}