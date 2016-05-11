package server.world;

import java.util.ArrayList;

import server.Server;
import server.core.PlayerHandler;
import server.model.objects.Object;
import server.util.Misc;
import server.model.players.Client;
import server.model.players.Player;

/**
 * @author Sanity
 */

public class ObjectManager {

	public ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Object> toRemove = new ArrayList<Object>();

	public void process() {
		for (final Object o : objects) {
			if (o.tick > 0) {
				o.tick--;
			} else {
				updateObject(o);
				toRemove.add(o);
			}
		}
		for (final Object o : toRemove) {
			if (o.objectId == 2732) {
				for (final Player player : PlayerHandler.players) {
					if (player != null) {
						final Client c = (Client)player;
						Server.itemHandler.createGroundItem(c, 592, o.objectX, o.objectY, 1, c.playerId);
					}
				}
			}
			if (isObelisk(o.newId)) {
				final int index = getObeliskIndex(o.newId);
				if (activated[index]) {
					activated[index] = false;
					teleportObelisk(index);
				}
			}
			objects.remove(o);
		}
		toRemove.clear();
	}

	public void removeObject(int x, int y) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				c.getPA().object(-1, x, y, 0, 10);
			}
		}
	}

	public void updateObject(Object o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				c.getPA().object(o.newId, o.objectX, o.objectY, o.face, o.type);
			}
		}
	}
	
	public boolean objectExists(final int x, final int y) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y) {
				return true;
			}
		}
		return false;
	}

	public void placeObject(Object o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60)
					c.getPA().object(o.objectId, o.objectX, o.objectY, o.face,
							o.type);
			}
		}
	}

	public Object getObject(int x, int y, int height) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height)
				return o;
		}
		return null;
	}

	public void loadObjects(Client c) {
		if (c == null)
			return;
		for (Object o : objects) {
			if (loadForPlayer(o, c))
				c.getPA().object(o.objectId, o.objectX, o.objectY, o.face,
						o.type);
		}
		loadCustomSpawns(c);
		Deletewalls(c);
		if (c.distanceToPoint(2813, 3463) <= 60) {
			c.getFarming().updateHerbPatch();
		}
	}
	
	public void Deletewalls(Client c) {
	    c.getPA().checkObjectSpawn(-1, 2837, 3342, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2837, 3343, -1, 0);
	}

	public void loadCustomSpawns(Client client) {
		if (client.heightLevel == 0) {
			client.getPA().checkObjectSpawn(4483, 2860, 3334, 0, 10);
			client.getPA().checkObjectSpawn(1307, 2839, 3381, 2, 10);//Maple Tree
			client.getPA().checkObjectSpawn(1307, 2833, 3371, 2, 10);//Maple Tree
			client.getPA().checkObjectSpawn(1307, 2839, 3373, 2, 10);//Maple Tree
			client.getPA().checkObjectSpawn(1309, 2828, 3370, 2, 10);//Yew Tree
			client.getPA().checkObjectSpawn(1309, 2826, 3380, 2, 10);//Yew Tree
			client.getPA().checkObjectSpawn(13627, 3172, 3287, 2, 10);//SSL NETWORK
			//client.getPA().checkObjectSpawn(1734, 3008, 9550, 1, 10);
			removeObject(2861, 3335);
			removeObject(2862, 3335);
			removeObject(2860, 3335);
			removeObject(2859, 3335);
			removeObject(2857, 3335);
			removeObject(2862, 3338);
			removeObject(2860, 3338);
			removeObject(2859, 3338);
			
			//Tyler's Minigame:
			client.getPA().checkObjectSpawn(27254, 2387, 4960, 1, 10);//portal
			client.getPA().checkObjectSpawn(26289, 2367, 4975, 2, 10);
			
			
			//farming?
			client.getPA().checkObjectSpawn(8151, 2811, 3337, 1, 10);
			removeObject(2812, 3337);
			removeObject(2812, 3336);
			removeObject(2812, 3335);
			removeObject(2811, 3338);
			removeObject(2812, 3338);
			removeObject(2811, 3336);
			removeObject(2811, 3335);
			removeObject(2810, 3338);
			removeObject(2810, 3337);
			removeObject(2810, 3336);
			removeObject(2810, 3335);
			removeObject(2809, 3338);
			removeObject(2809, 3337);
			removeObject(2809, 3336);
			removeObject(2809, 3335);
			
			//rock crab entrance dead trees
			removeObject(2807, 3704);
			removeObject(2807, 3703);
			removeObject(2805, 3704);
			removeObject(2803, 3704);
			removeObject(2802, 3703);
			removeObject(2801, 3704);
			
			
			//House in Canfis
			removeObject(3509, 3496);
			removeObject(3510, 3496);
			removeObject(3509, 3497);
			removeObject(3508, 3498);
			removeObject(3506, 3495);
			client.getPA().checkObjectSpawn(13611, 3505, 3498, 1, 10);//FirePlace
			client.getPA().checkObjectSpawn(13292, 3504, 3495, 0, 10);//Magic Chest
			client.getPA().checkObjectSpawn(13481, 3506, 3491, 2, 10);//Crawling Hand Statue
			
			//Soph
			client.getPA().checkObjectSpawn(5947, 3310, 2775, 2, 10);//hole
			client.getPA().checkObjectSpawn(5946, 2741, 9503, 2, 10);//rope
			
			/*removeObject(2857, 3338);
			removeObject(3315, 2770);
			//removeObject(3315, 2771);*/
			//Removal of more items near bank to clean up
			removeObject(2851, 3332);
			removeObject(2854, 3339);
			//End
			//Smithin Area
			removeObject(2830, 3349);
			removeObject(2831, 3348);
			removeObject(2831, 3350);
			removeObject(2830, 3350);
			client.getPA().checkObjectSpawn(2783, 2829, 3348, 1, 10);//Anvil
			client.getPA().checkObjectSpawn(2783, 2829, 3350, 1, 10);//Anvil
			//Mining Yanille
			client.getPA().checkObjectSpawn(2105, 2630, 3150, 1, 10);//Addy Ore
			//Yanille Town
			removeObject(2593, 3091);
			client.getPA().checkObjectSpawn(4877, 2593, 3092, 1, 10);//MagicRune
			//Rellekka
			client.getPA().checkObjectSpawn(2565, 2647, 3673, 1, 10);//Silver
			removeObject(2647, 3671);
			removeObject(2856, 3334);
			//client.getPA().checkObjectSpawn(4874, 2643, 3678, 1, 10);//Crafting Stall
			//client.getPA().checkObjectSpawn(4875, 2643, 3674, 1, 10);//Food Stall			//Sop
			client.getPA().checkObjectSpawn(4483, 3315, 2760, 2, 10);//Bank Chest
			removeObject(3314, 2760);
			client.getPA().checkObjectSpawn(2644, 3316, 2797, 1, 10);//Spinning Wheel
			client.getPA().checkObjectSpawn(4878, 3315, 2784, 1, 10);//Scimitar Stall
			//Lletya
			client.getPA().checkObjectSpawn(2213, 3165, 9630, 3, 10);//Bank booth
			client.getPA().checkObjectSpawn(27255, 2355, 3167, 2, 10);//key chest
			removeObject(2837, 3343);
			removeObject(2837, 3342);
			//Home Agility Area
			removeObject(2865, 3372);//delete trees
			removeObject(2861, 3369);//delete trees
		} else {
			client.getPA().checkObjectSpawn(-1, 2911, 3614, 1, 10);
		}
	}

	public final int IN_USE_ID = 14825;

	public boolean isObelisk(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return true;
		}
		return false;
	}

	public int[] obeliskIds = { 14829, 14830, 14827, 14828, 14826, 14831 };
	public int[][] obeliskCoords = { { 3154, 3618 }, { 3225, 3665 },
			{ 3033, 3730 }, { 3104, 3792 }, { 2978, 3864 }, { 3305, 3914 } };
	public boolean[] activated = { false, false, false, false, false, false };

	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				addObject(new Object(14825, obeliskCoords[index][0],
						obeliskCoords[index][1], 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4,
						obeliskCoords[index][1], 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0],
						obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4,
						obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId, 16));
			}
		}
	}

	public int getObeliskIndex(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return j;
		}
		return -1;
	}

	public void teleportObelisk(int port) {
		int random = Misc.random(5);
		while (random == port) {
			random = Misc.random(5);
		}
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if (c.goodDistance(c.getX(), c.getY(),
						obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2,
						1)) {
					c.getPA().startTeleport2(
							obeliskCoords[random][0] + xOffset,
							obeliskCoords[random][1] + yOffset, 0);
				}
			}
		}
	}

	public boolean loadForPlayer(Object o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60
				&& c.heightLevel == o.height;
	}

	public void addObject(Object o) {
		if (getObject(o.objectX, o.objectY, o.height) == null) {
			objects.add(o);
			placeObject(o);
		}
	}

}