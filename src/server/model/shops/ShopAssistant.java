package server.model.shops;

import server.Config;
import server.Server;
import server.core.PlayerHandler;
import server.model.items.Item;
import server.model.players.Client;
import server.world.ShopHandler;

public class ShopAssistant {

	private Client c;

	public ShopAssistant(Client client) {
		this.c = client;
	}

	public boolean shopSellsItem(int itemID) {
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if (itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Shops
	 **/

	public void openShop(int ShopID) {
		c.getItems().resetItems(3823);
		resetShop(ShopID);
		c.isShopping = true;
		c.myShopId = ShopID;
		c.getPA().sendFrame248(3824, 3822);
		c.getPA().sendFrame126(ShopHandler.ShopName[ShopID], 3901);
	}

	public void updatePlayerShop() {
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				if (PlayerHandler.players[i].isShopping == true
						&& PlayerHandler.players[i].myShopId == c.myShopId
						&& i != c.playerId) {
					PlayerHandler.players[i].updateShop = true;
				}
			}
		}
	}

	public void updateshop(int i) {
		resetShop(i);
	}

	public void resetShop(int ShopID) {
		synchronized (c) {
			int TotalItems = 0;
			for (int i = 0; i < ShopHandler.MaxShopItems; i++) {
				if (ShopHandler.ShopItems[ShopID][i] > 0) {
					TotalItems++;
				}
			}
			if (TotalItems > ShopHandler.MaxShopItems) {
				TotalItems = ShopHandler.MaxShopItems;
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(3900);
			c.getOutStream().writeWord(TotalItems);
			int TotalCount = 0;
			for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
				if (ShopHandler.ShopItems[ShopID][i] > 0
						|| i <= ShopHandler.ShopItemsStandard[ShopID]) {
					if (ShopHandler.ShopItemsN[ShopID][i] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord_v2(
								ShopHandler.ShopItemsN[ShopID][i]);
					} else {
						c.getOutStream().writeByte(
								ShopHandler.ShopItemsN[ShopID][i]);
					}
					if (ShopHandler.ShopItems[ShopID][i] > Config.ITEM_LIMIT
							|| ShopHandler.ShopItems[ShopID][i] < 0) {
						ShopHandler.ShopItems[ShopID][i] = Config.ITEM_LIMIT;
					}
					c.getOutStream().writeWordBigEndianA(
							ShopHandler.ShopItems[ShopID][i]);
					TotalCount++;
				}
				if (TotalCount > TotalItems) {
					break;
				}
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public double getItemShopValue(int ItemID, int Type, int fromSlot) {
		double ShopValue = 1;
		double TotPrice = 0;
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					ShopValue = Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}

		TotPrice = ShopValue;

		if (ShopHandler.ShopBModifier[c.myShopId] == 1) {
			TotPrice *= 1;
			TotPrice *= 1;
			if (Type == 1) {
				TotPrice *= 1;
			}
		} else if (Type == 1) {
			TotPrice *= 1;
		}
		return TotPrice;
	}

	public int getItemShopValue(int itemId) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == itemId) {
					return (int) Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}
		return 0;
	}

	/**
	 * buy item from shop (Shop Price)
	 **/

	public void buyFromShopPrice(int removeId, int removeSlot) {
		int ShopValue = (int) Math.floor(getItemShopValue(removeId, 0,
				removeSlot));
		ShopValue *= 1.15;
		String ShopAdd = "";
		if (c.myShopId >= 17) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " points.");
			return;
		}
		if (c.myShopId == 15) {
			c.sendMessage("This item current costs "
					+ c.getItems().getUntradePrice(removeId) + " coins.");
			return;
		}
		if (ShopValue >= 1000 && ShopValue < 1000000) {
			ShopAdd = " (" + (ShopValue / 1000) + "K)";
		} else if (ShopValue >= 1000000) {
			ShopAdd = " (" + (ShopValue / 1000000) + " million)";
		}
		c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs "
				+ ShopValue + " coins" + ShopAdd);
	}

	public int getSpecialItemValue(int id) {
		switch (id) {
		case 6889:
		case 6914:
			return 200;
		case 4151:
			return 20;
		case 6916:
		case 6918:
		case 6920:
		case 6922:
		case 6924:
			return 50;
		case 11663:
		case 11664:
		case 11665:
		case 8842:
			return 30;
		case 8839:
		case 8840:
			return 75;
		case 10499:
			return 20;
		case 8845:
			return 5;
		case 8846:
			return 10;
		case 8847:
			return 15;
		case 8848:
			return 20;
		case 8849:
		case 8850:
			return 25;
		case 7462:
			return 15;
		case 4714:
			return 10;
		case 11694:
			return 200;
		case 11696:
		case 11698:
		case 11700:
			return 100;
		case 10548:
			return 50;
		case 11235:
			return 40;
			//tyler's minigame
			//Rank Minigame Shop
			//Helms
			case 4164:
				return 90; //25
			case 10589:
				return 121; //50
			case 8928:
				return 175;  //75
			case 6131:
				return 230; //100
			case 8478:
				return 415; //125
			case 6137:
				return 625; //175
			case 5554:
				return 815; //200
				
			//Plate bodies
			case 11756:
				return 135; //25
			case 10564:
				return 249; //50
			case 10551:
				return 314; //75
			case 6133:
				return 392; //100
			case 6617:
				return 447; //125
			case 6139:
				return 617; //175
			case 5553:
				return 892; //200
				
			//Plate Legs
			case 10047:
				return 100; //25
			case 6809:
				return 146; //50
			case 6130:
				return 208; //75
			case 6135:
				return 307; //100
			case 6625:
				return 386; //125
			case 6141:
				return 590; //175
			case 5555:
				return 856; //200
				
			//boots
			case 1846:
				return 80; //25
			case 9006:
				return 122; //50
			case 9005:
				return 163; //75
			case 6143:
				return 251; //100
			case 6619:
				return 416; //125
			case 6147:
				return 526; //175
			case 5557:
				return 676; //200
							
			//gloves
			case 10077:
				return 74; //25
			case 2902:
				return 114; //50
			case 10075:
				return 156; //75
			case 6149:
				return 211; //100
			case 6629:
				return 391; //125
			case 6153:
				return 496; //175
			case 5556:
				return 608; //200
							
			//Final weps 
			case 4081:
				return 3000;
			case 7809:
				return 3500;
			case 7806:
				return 3750;
			case 7807:
				return 4000;
			case 7808:
				return 4500;
				
		//Slayer Rewards
			case 4675:
				return 500;
			case 1540:
				return 100;
		}
		return 0;
	}

	/**
	 * Sell item to shop (Shop Price)
	 **/
	public void sellToShopPrice(int removeId, int removeSlot) {
		for (int i : Config.ITEM_SELLABLE) {
			if (i == removeId) {
				c.sendMessage("You can't sell "
						+ c.getItems().getItemName(removeId).toLowerCase()
						+ ".");
				return;
			}
		}
		boolean IsIn = false;
		if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
			for (int j = 0; j <= ShopHandler.ShopItemsStandard[c.myShopId]; j++) {
				if (removeId == (ShopHandler.ShopItems[c.myShopId][j] - 1)) {
					IsIn = true;
					break;
				}
			}
		} else {
			IsIn = true;
		}
		if (IsIn == false) {
			c.sendMessage("You can't sell "
					+ c.getItems().getItemName(removeId).toLowerCase()
					+ " to this store.");
		} else {
			int ShopValue = (int) Math.floor(getItemShopValue(removeId, 1,
					removeSlot));
			String ShopAdd = "";
			if (ShopValue >= 1000 && ShopValue < 1000000) {
				ShopAdd = " (" + (ShopValue / 1000) + "K)";
			} else if (ShopValue >= 1000000) {
				ShopAdd = " (" + (ShopValue / 1000000) + " million)";
			}
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": shop will buy for " + ShopValue + " coins" + ShopAdd);
		}
	}

	public boolean sellItem(int itemID, int fromSlot, int amount) {
		if (c.myShopId == 14)
			return false;
		for (int i : Config.ITEM_SELLABLE) {
			if (i == itemID) {
				c.sendMessage("You can't sell "
						+ c.getItems().getItemName(itemID).toLowerCase() + ".");
				return false;
			}
		}

		if (amount > 0 && itemID == (c.playerItems[fromSlot] - 1)) {
			if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
				boolean IsIn = false;
				for (int i = 0; i <= ShopHandler.ShopItemsStandard[c.myShopId]; i++) {
					if (itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
						IsIn = true;
						break;
					}
				}
				if (IsIn == false) {
					c.sendMessage("You can't sell "
							+ c.getItems().getItemName(itemID).toLowerCase()
							+ " to this store.");
					return false;
				}
			}

			if (amount > c.playerItemsN[fromSlot]
					&& (Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == true || Item.itemStackable[(c.playerItems[fromSlot] - 1)] == true)) {
				amount = c.playerItemsN[fromSlot];
			} else if (amount > c.getItems().getItemAmount(itemID)
					&& Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == false
					&& Item.itemStackable[(c.playerItems[fromSlot] - 1)] == false) {
				amount = c.getItems().getItemAmount(itemID);
			}
			// double ShopValue;
			// double TotPrice;
			int TotPrice2 = 0;
			// int Overstock;
			for (int i = amount; i > 0; i--) {
				TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 1,
						fromSlot));
				if (c.getItems().freeSlots() > 0
						|| c.getItems().playerHasItem(995)) {
					if (Item.itemIsNote[itemID] == false) {
						c.getItems().deleteItem(itemID,
								c.getItems().getItemSlot(itemID), 1);
					} else {
						c.getItems().deleteItem(itemID, fromSlot, 1);
					}
					c.getItems().addItem(995, TotPrice2);
					addShopItem(itemID, 1);
				} else {
					c.sendMessage("You don't have enough space in your inventory.");
					break;
				}
			}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return true;
	}

	public boolean addShopItem(int itemID, int amount) {
		boolean Added = false;
		if (amount <= 0) {
			return false;
		}
		if (Item.itemIsNote[itemID] == true) {
			itemID = c.getItems().getUnnotedItem(itemID);
		}
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if ((ShopHandler.ShopItems[c.myShopId][i] - 1) == itemID) {
				ShopHandler.ShopItemsN[c.myShopId][i] += amount;
				Added = true;
			}
		}
		if (Added == false) {
			for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
				if (ShopHandler.ShopItems[c.myShopId][i] == 0) {
					ShopHandler.ShopItems[c.myShopId][i] = (itemID + 1);
					ShopHandler.ShopItemsN[c.myShopId][i] = amount;
					ShopHandler.ShopItemsDelay[c.myShopId][i] = 0;
					break;
				}
			}
		}
		return true;
	}

	public boolean buyItem(int itemID, int fromSlot, int amount) {
		if (!shopSellsItem(itemID) && c.myShopId != 50 && c.myShopId != 60)
			return false;
		if (c.myShopId == 14) {
			skillBuy(itemID);
			return false;
		} else if (c.myShopId == 15) {
			buyVoid(itemID);
			return false;
		}
		if (amount > 0) {
			if (amount > ShopHandler.ShopItemsN[c.myShopId][fromSlot]) {
				amount = ShopHandler.ShopItemsN[c.myShopId][fromSlot];
			}
			// double ShopValue;
			// double TotPrice;
			int TotPrice2 = 0;
			// int Overstock;
			int Slot = 0;
			int Slot1 = 0;// Tokkul
			if (c.myShopId >= 17 && c.myShopId <= 18 || c.myShopId == 29 || c.myShopId == 30 || c.myShopId == 70 || c.myShopId == 71 || c.myShopId == 72 || c.myShopId == 73 || c.myShopId == 74 || c.myShopId == 75 || c.myShopId == 76 || c.myShopId == 77 || c.myShopId == 80) {
				handleOtherShop(itemID);
				return false;
			}
			for (int i = amount; i > 0; i--) {
				TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 0,
						fromSlot));
				Slot = c.getItems().getItemSlot(995);
				Slot1 = c.getItems().getItemSlot(6529);
				if (Slot == -1 && c.myShopId != 29 && c.myShopId != 30
						&& c.myShopId != 31) {
					c.sendMessage("You don't have enough coins.");
					break;
				}
				if (Slot1 == -1 && c.myShopId == 29 || c.myShopId == 30
						|| c.myShopId == 31) {
					c.sendMessage("You don't have enough tokkul.");
					break;
				}
				if (TotPrice2 <= 1) {
					TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 0,
							fromSlot));
					TotPrice2 *= 1.66;
				}
				if (c.myShopId != 29 || c.myShopId != 30 || c.myShopId != 31) {
					if (c.playerItemsN[Slot] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							c.getItems().deleteItem(995,
									c.getItems().getItemSlot(995), TotPrice2);
							c.getItems().addItem(itemID, 1);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough coins.");
						break;
					}
				}
				if (c.myShopId == 29 || c.myShopId == 30 || c.myShopId == 31) {
					if (c.playerItemsN[Slot1] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							c.getItems().deleteItem(6529,
									c.getItems().getItemSlot(6529), TotPrice2);
							c.getItems().addItem(itemID, 1);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough tokkul.");
						break;
					}
				}
			}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return false;
	}

	public void handleOtherShop(int itemID) {
		if (c.myShopId == 17) {
			if (c.magePoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.magePoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough points to buy this item.");
			}
		} else if (c.myShopId == 18) {
			if (c.pcPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.pcPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough points to buy this item.");
			}
		} else if (c.myShopId == 70 || c.myShopId == 71 || c.myShopId == 72 || c.myShopId == 73 || c.myShopId == 74 || c.myShopId == 75 || c.myShopId == 76  || c.myShopId == 77) {
			if (c.repPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0){
					c.repPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID,1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough reputation points to buy this item.");			
			}

		} else if (c.myShopId == 80) {
			if (c.slayPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.slayPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID,1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You don't have enough slayer points for this item.");
			}
		}
	}

	public void openSkillCape() {
		int capes = get99Count();
		if (capes > 1)
			capes = 1;
		else
			capes = 0;
		c.myShopId = 14;
		setupSkillCapes(capes, get99Count());
	}

	/*
	 * public int[][] skillCapes =
	 * {{0,9747,4319,2679},{1,2683,4329,2685},{2,2680
	 * ,4359,2682},{3,2701,4341,2703
	 * },{4,2686,4351,2688},{5,2689,4347,2691},{6,2692,4343,2691},
	 * {7,2737,4325,2733
	 * },{8,2734,4353,2736},{9,2716,4337,2718},{10,2728,4335,2730
	 * },{11,2695,4321,2697},{12,2713,4327,2715},{13,2725,4357,2727},
	 * {14,2722,4345
	 * ,2724},{15,2707,4339,2709},{16,2704,4317,2706},{17,2710,4361,
	 * 2712},{18,2719,4355,2721},{19,2737,4331,2739},{20,2698,4333,2700}};
	 */
	public int[] skillCapes = { 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801,
			9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786,
			9810, 9765 };

	public int get99Count() {
		int count = 0;
		for (int j = 0; j < c.playerLevel.length; j++) {
			if (c.getLevelForXP(c.playerXP[j]) >= 99) {
				count++;
			}
		}
		return count;
	}

	public void setupSkillCapes(int capes, int capes2) {
		synchronized (c) {
			c.getItems().resetItems(3823);
			c.isShopping = true;
			c.myShopId = 14;
			c.getPA().sendFrame248(3824, 3822);
			c.getPA().sendFrame126("Skillcape Shop", 3901);

			int TotalItems = 0;
			TotalItems = capes2;
			if (TotalItems > ShopHandler.MaxShopItems) {
				TotalItems = ShopHandler.MaxShopItems;
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(3900);
			c.getOutStream().writeWord(TotalItems);
			for (int i = 0; i < 21; i++) {
				if (c.getLevelForXP(c.playerXP[i]) < 99)
					continue;
				c.getOutStream().writeByte(1);
				c.getOutStream().writeWordBigEndianA(skillCapes[i] + 2);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public void skillBuy(int item) {
		int nn = get99Count();
		if (nn > 1)
			nn = 1;
		else
			nn = 0;
		for (int j = 0; j < skillCapes.length; j++) {
			if (skillCapes[j] == item || skillCapes[j] + 1 == item) {
				if (c.getItems().freeSlots() > 1) {
					if (c.getItems().playerHasItem(995, 99000)) {
						if (c.getLevelForXP(c.playerXP[j]) >= 99) {
							c.getItems().deleteItem(995,
									c.getItems().getItemSlot(995), 99000);
							c.getItems().addItem(skillCapes[j] + nn, 1);
							c.getItems().addItem(skillCapes[j] + 2, 1);
						} else {
							c.sendMessage("You must have 99 in the skill of the cape you're trying to buy.");
						}
					} else {
						c.sendMessage("You need 99k to buy this item.");
					}
				} else {
					c.sendMessage("You must have at least 1 inventory spaces to buy this item.");
				}
			}
		}
		c.getItems().resetItems(3823);
	}

	public void openVoid() {
	}

	public void buyVoid(int item) {
	}

}
