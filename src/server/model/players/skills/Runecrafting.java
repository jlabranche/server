package server.model.players.skills;

import server.Config;
import server.model.players.Client;
/**
 * RuneCrafting.java
 *
 * @author Sanity
 *
 **/
 
public class Runecrafting {
	
	private Client c;
		
	public Runecrafting(Client c) {
		this.c = c;
	}
	
    /**
     * Rune essence ID constant.
     */
    private static final int RUNE_ESS = 1436;

    /**
     * Pure essence ID constant.
     */
    private static final int PURE_ESS = 7936;	
	
    /**
     * An array containing the rune item numbers.
     */
    public int[] runes = {
        556, 558, 555, 557, 554, 559, 564, 562, 561, 563, 560, 565, 566
    };

    /**
     * An array containing the object IDs of the runecrafting altars.
     */
    public int[] altarID = {
        7139, 7140, 7137, 7430, 7129, 7131, 7132, 7134, 7133, 7135, 7136, 7141, 7138
    };
	
	
	public int[] tallyID = {
        1438, 1448, 1444, 1440, 1442, 1446, 1454, 1452, 1462, 1458, 1456, 1450, 1460
    };
	
    /**
     * 2D Array containing the levels required to craft the specific rune.
     */
    public int[][] craftLevelReq = {
        {556, 1, 1438}, {558, 2, 1448}, {555, 5, 1444}, {557, 9, 1440}, {554, 14, 1442}, {559, 20, 1446},
        {564, 27, 1454}, {562, 35, 1452}, {561, 44, 1462}, {563, 54, 1458}, {560, 65, 1456}, {565, 77, 1450}, {566, 90, 1460}
    };

    /**
     * 2D Array containing the levels that you can craft multiple runes.
     */
    public int[][] multipleRunes = {
        {11, 22, 33, 44, 55, 66, 77, 88, 99},
        {14, 28, 42, 56, 70, 84, 98},
        {19, 38, 57, 76, 95},
        {26, 52, 78},
        {35, 70},
        {46, 92},
        {59},
        {74},
        {91},
		{100},
		{100},
		{100}
    };
	
    public int[] runecraftExp = {
        5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18
    };
	/**
     * Checks through all 28 item inventory slots for the specified item.
     */
    private boolean itemInInv(int itemID, int slot, boolean checkWholeInv) {
		if (checkWholeInv) {
			for (int i = 0; i < 28; i++) {
				if (c.playerItems[i] == itemID + 1) {
					return true;
                }
			}
		} else {
			if (c.playerItems[slot] == itemID + 1) {
				return true;
			}
		}
        return false;
    }

    /**
     * Replaces essence in the inventory with the specified rune.
     */
    private void replaceEssence(int essType, int runeID, int index) {
		int exp = 0;
        for (int i = 0; i < 28; i++) {
			if (itemInInv(essType, i, false)) {
                c.getItems().deleteItem(essType, i, 1);
                c.getItems().addItem(runeID, 1);
                exp += runecraftExp[index];                
            }
        }
        c.getPA().addSkillXP(exp * Config.RUNECRAFTING_EXPERIENCE, c.playerRunecrafting);
    }
	
	 private void replaceEssence2(int essType, int runeID, int index) {
		int exp = 0;
        for (int i = 0; i < 28; i++) {
			if (itemInInv(essType, i, false)) {
                c.getItems().deleteItem(essType, i, 1);
                c.getItems().addItem(runeID, 1);
                exp += runecraftExp[index];                
            }
        }
        c.getPA().addSkillXP(exp * Config.RUNECRAFTING_EXPERIENCE * 2, c.playerRunecrafting);
    }

    /**
     * Crafts the specific rune.
     */
    public void craftRunes(int altarID) {
        int runeID = 0;

        for (int i = 0; i < this.altarID.length; i++) {
            if (altarID == this.altarID[i]) {
                runeID = runes[i];
            }
        }
        for (int i = 0; i < craftLevelReq.length; i++) {
        if (runeID == runes[i]) {
			if (c.getItems().playerHasItem(craftLevelReq[i][2])) {
				if (c.playerLevel[20] >= craftLevelReq[i][1]) {
                    if (c.getItems().playerHasItem(RUNE_ESS)) {
                        replaceEssence(RUNE_ESS, runeID, i);
                        c.startAnimation(791);
                        //c.frame174(481, 0, 0); for sound
                        c.gfx100(186);
                        return;
                    }
					 if (c.getItems().playerHasItem(PURE_ESS)) {
                        replaceEssence2(PURE_ESS, runeID, i);
                        c.startAnimation(791);
                        //c.frame174(481, 0, 0); for sound
                        c.gfx100(186);
                        return;
                    }
                    c.sendMessage("You need to have essence to craft runes!");
                    return;
                }
				c.sendMessage("You need a Runecrafting level of "+ craftLevelReq[i][1] +" to craft "+ c.getItems().getItemName(craftLevelReq[i][0]) +"s.");
				}
                c.sendMessage("You need a "+ c.getItems().getItemName(craftLevelReq[i][2]) +" to craft "+ c.getItems().getItemName(craftLevelReq[i][0]) +"s.");
            }
        }
    }
}