package server.model.players.quests;

import server.model.players.Client;

public class GettingStart {

	Client c;
	
	public GettingStart(Client c) {
		this.c = c;
	}
	
	public int interfaceId() { 
		return 7332; 
		}

	
	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Getting Started", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.getStart == 0) {
			c.getPA().sendFrame126("Getting Started", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to the Master fisher", 8147);
			c.getPA().sendFrame126("", 8148);
			c.getPA().sendFrame126("", 8149);
			c.getPA().sendFrame126("There are no minimum requirments.", 8150);
		} else if(c.getStart == 1) {
			c.getPA().sendFrame126("Getting Started", 8144);
			c.getPA().sendFrame126("@str@I've talked to the Master fisher.", 8147);
			c.getPA().sendFrame126("He gave me a small fishing net and told me to fish from pond.", 8148);
		} else if(c.getStart == 2) {
			c.getPA().sendFrame126("Getting Started", 8144);
			c.getPA().sendFrame126("@str@I talked to the Master fisher.", 8147);
			c.getPA().sendFrame126("@str@He provided me with a small fishing net.", 8148);
			c.getPA().sendFrame126("@str@and taught me how to fish with it.", 8149);
			c.getPA().sendFrame126("He instructed me to head east to speak with woodsman tutor.", 8150);
		} else if(c.getStart == 3) {
			c.getPA().sendFrame126("Getting Started", 8144);
			c.getPA().sendFrame126("@str@I've talked to the woodsman tutor.", 8147);
			c.getPA().sendFrame126("He instructed me to grab an axe from the chicken coop.", 8148);
			c.getPA().sendFrame126("I should use it to chop an tree.", 8149);
		} else if(c.getStart == 4) {
			c.getPA().sendFrame126("Getting Started", 8144);
			c.getPA().sendFrame126("@str@I've talked to the woodsman tutor.", 8147);
			c.getPA().sendFrame126("@str@He showed me where to get an axe.", 8148);
			c.getPA().sendFrame126("@str@I chopped the tree down and got some logs.", 8149);
			c.getPA().sendFrame126("He gave me an tinderbox.", 8150);
			c.getPA().sendFrame126("I should use it with the logs to create fire!", 8151);
		} else if(c.getStart == 5) {
			c.getPA().sendFrame126("Getting Started", 8144);
			c.getPA().sendFrame126("@str@I've talked to the woodsman tutor.", 8147);
			c.getPA().sendFrame126("@str@He showed me where to get an axe.", 8148);
			c.getPA().sendFrame126("@str@I chopped the tree down and got some logs.", 8149);
			c.getPA().sendFrame126("@str@He gave me an tinderbox.", 8150);
			c.getPA().sendFrame126("@str@I should use it with the logs to create fire!", 8151);
			c.getPA().sendFrame126("He told me to head east", 8152);
			c.getPA().sendFrame126("And to speak with the combat tutors!", 8153);
			c.getPA().sendFrame126("Speak to the priest in lumbridge church after", 8154);
			c.getPA().sendFrame126("obtaining these 3 items:", 8155);
			if (!c.getItems().playerHasItem(9705, 1)) {
			c.getPA().sendFrame126("Training Bow and Arrows", 8156);
			} else  {
			c.getPA().sendFrame126("@str@Training Bow and Arrows", 8156);	
			}
			if (!c.getItems().playerHasItem(1379, 1)) {
			c.getPA().sendFrame126("Staff and Runes", 8157);
			} else {
			c.getPA().sendFrame126("@str@Staff and Runes", 8157);	
			}
			if (!c.getItems().playerHasItem(9703, 1)) {
			c.getPA().sendFrame126("Training Sword and Shield", 8158);
			} else {
			c.getPA().sendFrame126("@str@Training Sword and Shield", 8158);	
			}
		}
		c.getPA().showInterface(8134);


	}
}