package server.model.players;

import server.Server;
//import server.model.players.skills.Slayer;

public class DialogueHandler {

	private Client c;

	public DialogueHandler(Client client) {
		this.c = client;
	}

	/**
	 * Handles all talking
	 * 
	 * @param dialogue
	 *            The dialogue you want to use
	 * @param npcId
	 *            The npc id that the chat will focus on during the chat
	 */
	public void sendDialogues(int dialogue, int npcId) {
		c.talkingNpc = npcId;
		switch (dialogue) {
		case 0:
			c.talkingNpc = -1;
			c.getPA().removeAllWindows();
			c.nextChat = 0;
			break;
		case 1:
			sendStatement("You found a hidden tunnel! Do you want to enter it?");
			c.dialogueAction = 1;
			c.nextChat = 2;
			break;
		case 2:
			sendOption2("Yea! I'm fearless!", "No way! That looks scary!");
			c.dialogueAction = 1;
			c.nextChat = 0;
			break;
		case 3:
			sendNpcChat4(
					"Hello!",
					"My name is Duradel and I am a master of the slayer skill.",
					"I can assign you a slayer task suitable to your combat level.",
					"Would you like a slayer task?", c.talkingNpc, "Duradel");
			c.nextChat = 4;
			break;
		case 5:
			sendNpcChat4("Hello adventurer...",
					"My name is Kolodion, the master of this mage bank.",
					"Would you like to play a minigame in order ",
					"to earn points towards recieving magic related prizes?",
					c.talkingNpc, "Kolodion");
			c.nextChat = 6;
			break;
		case 6:
			sendNpcChat4("The way the game works is as follows...",
					"You will be teleported to the wilderness,",
					"You must kill mages to recieve points,",
					"redeem points with the chamber guardian.", c.talkingNpc,
					"Kolodion");
			c.nextChat = 15;
			break;
		/*case 8:
			sendOption2("Max Stats", "Wipe Inventory", "");
			c.dialogueAction = 51;
			break;*/
		case 10:
			sendNpcChat3("Certainly, Go slay:", "" + c.taskAmount + " " + Server.npcHandler.getNpcListName(c.slayerTask) +".", "Come back for rewards afterwards!", c.talkingNpc, "Duradel" );
			c.nextChat = 0;
			break;
		case 11:
			sendNpcChat2("Greetings "+c.playerName+".", "What can I help you with today?.", c.talkingNpc, "Duradel");
			c.nextChat = 12;
			break;
		case 12:
			sendOption5("I would like some information on how to train slayer.", "I would like an Simple Task.", "I would like a Difficult Task", "I would like an Challanging task.", "Nothing at the moment");
			c.dialogueAction = 5;
		break;
		case 13:
			sendNpcChat1("You must finsh the task at hand.", c.talkingNpc, "Duradel");
			c.nextChat = 12;
		break;
		case 14:
			sendOption2("Yes I would like an easier task.",
					"No I would like to keep my task.");
			c.dialogueAction = 6;
			break;
		case 15:
			sendOption2("Yes I would like to play",
					"No, sounds too dangerous for me.");
			c.dialogueAction = 7;
			break;
		case 16:
			sendOption2("I would like to reset my barrows brothers.",
					"I would like to fix all my barrows");
			c.dialogueAction = 8;
			break;
		case 17:
			sendOption5("Air", "Mind", "Water", "Earth", "More");
			c.dialogueAction = 10;
			c.dialogueId = 17;
			c.teleAction = -1;
			break;
		case 18:
			sendOption5("Fire", "Body", "Cosmic", "Astral", "More");
			c.dialogueAction = 11;
			c.dialogueId = 18;
			c.teleAction = -1;
			break;
		case 19:
			sendOption5("Nature", "Law", "Death", "Blood", "More");
			c.dialogueAction = 12;
			c.dialogueId = 19;
			c.teleAction = -1;
			break;
		case 27:
			sendNpcChat1("Yarr! Ye should try using a bag to store ye plunder mate!", 5444, "Cap'n Hand");
			c.nextChat = 28;
		break;
		case 28:
			sendNpcChat1("Does ye have 100M gold coins?", 5444, "Cap'n Hand");
			c.nextChat = 29;
		break;
		case 29:
			sendOption2("Yarr!",  "Nope..");
			c.dialogueAction = 51;
		break;
		case 30:
			sendNpcChat1("Hello adventurer, would you happen to have any plunder?", c.talkingNpc, "King's Messanger");
			c.nextChat = 31;
		break;
		case 31:
			sendNpcChat2("King Percival has sent me to destroy all plunder.", "You shall be rewarded greatly for it's retrieval...", c.talkingNpc, "King's Messanger");
			c.nextChat = 32;
		break;
		case 32:
			sendNpcChat2("Please deposit any money you currently have, in the bank.", "You might have too much on you!", c.talkingNpc, "King's Messanger");
			c.nextChat = 33;
		break;
		case 33:
			sendOption2("Why, I happen to have some!",  "I spit on the crown!");
			c.dialogueAction = 50;
		break;
		case 34:
			sendNpcChat1("Please leave me alone, I'm admiring the sea right now.", 3393, "Former Guide");
			c.nextChat = 35;
		break;
		case 35:
			sendPlayerChat2("Wow he seems upset","Maybe it has something to do with the champion?");
			c.nextChat = 0;
		break;
		case 36:
			sendNpcChat1("Hello Adventurer, How may I help you?", c.talkingNpc, "SSL Vet");
			c.nextChat = 37;
		break;
		case 37:
			sendOption2("I would like to view your suppiles shop", "What Happaned With Your Arm?");
			c.dialogueAction = 53;
		break;
		case 38:
			sendNpcChat3("I Have Played Every SkyScapeLive Server since 2006,", "And over the years i have skilled so much", "It fell off, So steven gave me this job", 537, "SSL Vet");
			c.nextChat = 39;
			break;
		case 39:
			sendPlayerChat2("I'm Sorry To Hear You Been Reduced to This,","Hopefully this is the final ssl?");
			c.nextChat = 40;
			break;
		case 40:
			sendNpcChat1("That's All Up To Steven ;)", 537, "SSL Vet");
			c.nextChat = 0;
			break;
		case 45:
			sendNpcChat2("Well, since you haven't shown me a defender to", "prove your prowess as a warrior.", 4289, "Kamfreena");
			c.nextChat = 46;
			break;
		case 46:
			sendNpcChat3("I'll release some Cyclopes which might drop bronze", "defenders for you to start off with, unless you show me", "another. Have fun in there.", 4289, "Kamfreena");
			break;
		case 47:
			sendNpcChat2("You have a defender, so I'll release some cyclopes", "which may drop " + Server.getWarriorsGuild().getCyclopsDrop126(c) + " defenders.", 4289, "Kamfreena");
			break;
		case 53:
			sendNpcChat2("Greetings @dre@"+c.playerName+"@bla@.", "What do you need?.", 1599, "Duradel");
			c.nextChat = 54;
		break;
		case 54:
			sendOption3("What's my current slayer task", "What's the location of my slayer task.", "How many slayer points do I have?.");
			c.dialogueAction = 6;
		break;
		case 55:
			sendNpcChat1("You still have @dre@" + c.taskAmount + " " + Server.npcHandler.getNpcListName(c.slayerTask) +"s@bla@ to slay.", 1599, "Duradel");
			c.nextChat = 0;
		break;
		case 56:
			sendNpcChat2("You currently do not have an Slayer task.", "Speak To Me For One.", 1599, "Duradel");
			c.nextChat = 0;
		break;
		case 57:
			sendNpcChat1("You currently have @dre@"+c.slayPoints+" @bla@slayer points.", 1599, "Duradel");
			c.nextChat = 54;
		break;
		case 58:
			sendNpcChat2("Hello "+c.playerName+".", "Do you need some hides tanned today?", c.talkingNpc, "Tanner");
			c.nextChat = 59;
		break;
		case 59:
			sendOption2("Yes Please", "Not right now");
			c.dialogueAction = 20;
		break;
		case 106:
			sendNpcChat3("@blu@Welcome to Skyscapelive!@bla@", "I'm the Champion of Skyscape.", "How may I help you?", 4929, "Champion of Skyscape");
			c.nextChat = 107;
		break;
		case 107:
			sendOption2("Can I have some extra equipment please?", "I would like to prestige please.");
			c.dialogueAction = 29;
		break;
		/*case 108:
			sendOption2("Yes please!", "No, thank you, I can make it on my own.");
			c.dialogueAction = 29;
		break;*/
		case 109:
			sendNpcChat2("Eeehhhh...", "Do what I couldn't! Kill them all...", c.talkingNpc, "Sir Elion");
			c.nextChat = 110;
		break;
		case 110:
			sendOption2("I shall fight for your honor.",  "And end up like you? No..");
			c.dialogueAction = 28;
		break;
		case 111:
			sendNpcChat3("Greetings "+c.playerName+",", "Your current rank level is "+c.rankLevel+"", "and you have " +c.repPoints+ " reputation points.", c.talkingNpc, "Sir Percival");
			c.nextChat = 0;
		break;
		case 112:
			sendNpcChat2("Congratulations "+c.playerName+"!", "You have made it to the last area.", c.talkingNpc, "Old Man");
			c.nextChat = 113;
		break;
		case 113:
			sendNpcChat1("You may stay to raise your rank or gain more points.", c.talkingNpc, "Old Man");
			c.nextChat = 114;
		break;
		case 114:
			sendNpcChat2("Your current rank level is "+c.rankLevel+"", "and you have " +c.repPoints+ " reputation points.", c.talkingNpc, "Old Man");
			c.nextChat = 0;
		break;
		case 115:
			sendNpcChat1("Your current rank level is " +c.rankLevel+ ".", c.talkingNpc, "Martin Thwait");
			c.nextChat = 116;
		break;
		case 116:
			sendNpcChat1("and you have a total of "+c.repPoints+ " reputation points to spend.", c.talkingNpc, "Martin Thwait");
			c.nextChat = 0;
		break;
		/*case 120:
			sendNpcChat1("Hello "+c.repPoints+ " reputation points to spend.", c.talkingNpc, "Martin Thwait");
			c.nextChat = 121;
		break;*/
		
		}
	}

	/*
	 * Information Box
	 */

	public void sendStartInfo(String text, String text1, String text2,
			String text3, String title) {
		c.getPA().sendFrame126(title, 6180);
		c.getPA().sendFrame126(text, 6181);
		c.getPA().sendFrame126(text1, 6182);
		c.getPA().sendFrame126(text2, 6183);
		c.getPA().sendFrame126(text3, 6184);
		c.getPA().sendFrame164(6179);
	}

	/*
	 * Options
	 */

	public void sendOption2(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame164(2459);
	}
	
	public void sendOption3(String s, String s1, String s2) {
		c.getPA().sendFrame126("Select an Option", 2470);
		c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126(s2, 2473);
		c.getPA().sendFrame164(2469);
	}

	public void sendOption4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126("Select an Option", 2481);
		c.getPA().sendFrame126(s, 2482);
		c.getPA().sendFrame126(s1, 2483);
		c.getPA().sendFrame126(s2, 2484);
		c.getPA().sendFrame126(s3, 2485);
		c.getPA().sendFrame164(2480);
	}

	public void sendOption5(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126("Select an Option", 2493);
		c.getPA().sendFrame126(s, 2494);
		c.getPA().sendFrame126(s1, 2495);
		c.getPA().sendFrame126(s2, 2496);
		c.getPA().sendFrame126(s3, 2497);
		c.getPA().sendFrame126(s4, 2498);
		c.getPA().sendFrame164(2492);
	}

	/*
	 * Statements
	 */

	private void sendStatement(String s) { // 1 line click here to continue chat
											// box interface
		c.getPA().sendFrame126(s, 357);
		c.getPA().sendFrame126("Click here to continue", 358);
		c.getPA().sendFrame164(356);
	}

	/*
	 * Npc Chatting
	 */
	
	private void sendNpcChat1(String s, int ChatNpc, String name) {
		c.getPA().sendFrame200(4883, 591);
		c.getPA().sendFrame126(name, 4884);
		c.getPA().sendFrame126(s, 4885);
		c.getPA().sendFrame75(ChatNpc, 4883);
		c.getPA().sendFrame164(4882);
	}
	
	private void sendNpcChat2(String s, String s1, int ChatNpc, String name) {
		c.getPA().sendFrame200(4888, 591);
		c.getPA().sendFrame126(name, 4889);
		c.getPA().sendFrame126(s, 4890);
		c.getPA().sendFrame126(s1, 4891);
		c.getPA().sendFrame75(ChatNpc, 4888);
		c.getPA().sendFrame164(4887);
	}
	
	private void sendNpcChat3(String s, String s1, String s2, int ChatNpc, String name) {
		c.getPA().sendFrame200(4894, 591);
		c.getPA().sendFrame126(name, 4895);
		c.getPA().sendFrame126(s, 4896);
		c.getPA().sendFrame126(s1, 4897);
		c.getPA().sendFrame126(s2, 4898);
		c.getPA().sendFrame75(ChatNpc, 4894);
		c.getPA().sendFrame164(4893);
	}
	
	private void sendNpcChat4(String s, String s1, String s2, String s3, int ChatNpc, String name) {
		c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame126(s1, 4904);
		c.getPA().sendFrame126(s2, 4905);
		c.getPA().sendFrame126(s3, 4906);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
	}

	/*
	 * Player Chating Back
	 */
	
	@SuppressWarnings("unused")
	private void sendPlayerChat1(String s) {
		c.getPA().sendFrame200(969, 591);
		c.getPA().sendFrame126(c.playerName, 970);
		c.getPA().sendFrame126(s, 971);
		c.getPA().sendFrame185(969);
		c.getPA().sendFrame164(968);
	}

private void sendPlayerChat2(String s, String s1) {
	c.getPA().sendFrame200(974, 591);
	c.getPA().sendFrame126(c.playerName, 975);
	c.getPA().sendFrame126(s, 976);
	c.getPA().sendFrame126(s1, 977);
	c.getPA().sendFrame185(974);
	c.getPA().sendFrame164(973);
	}
}