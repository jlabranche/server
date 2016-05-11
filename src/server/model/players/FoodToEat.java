package server.model.players;

import java.util.HashMap;

public enum FoodToEat {
    LOBSTER(379, 12),
    MANTA(391, 22),
    MONKFISH(7946, 16),
    SALMON(329, 9),
    SEA_TURTLE(397, 22),
    SHARK(385, 20),
    SWORDFISH(373, 14),
    TROUT(333, 7), 
    TUNA(361, 10),
    TUNA_POTATO(7060, 22);

    public static HashMap<Integer, FoodToEat> food = new HashMap<Integer, FoodToEat>();
    
    static {
	for (final FoodToEat f : FoodToEat.values()) {
	    FoodToEat.food.put(f.getId(), f);
	}
    }

    public static FoodToEat forId(int id) {
	return FoodToEat.food.get(id);
    }

    private int heal;

    private int id;

    private FoodToEat(int id, int heal) {
	this.id = id;
	this.heal = heal;
    }

    public int getHeal() {
	return heal;
    }

    public int getId() {
	return id;
    }

}