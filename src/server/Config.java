package server;


public class Config {

	public static final boolean ALLOWPINS = true;	// are you allowing pins
	public static final boolean SERVER_DEBUG = false;//needs to be false for Real world to work
	public static final int[] DESTROYABLES 	= 	{10548, 10551, 18349, 18351, 18353, 18355, 18357, 18359}; // Destroyable
	public static String SERVER_NAME = "Pre-SSL";
	public static final String WELCOME_MESSAGE = "Welcome to "+SERVER_NAME;
	public static final String FORUMS = "http://www.skyscapelive.net/forum.php";
	public static boolean LOCK_EXPERIENCE = false;
	public static boolean MINI_GAMES = true;
	public static String LOGOUT_MESSAGE = "Click here to logout!";
	public static String DEATH_MESSAGE = "Oh dear you are dead!";
	public static boolean DOUBLE_EXP = true;
	public static int MAX_NPCS = Server.npcHandler.maxNPCs;

	public static final int CLIENT_VERSION = 1;

	public static int MESSAGE_DELAY = 6000;
	public static final int ITEM_LIMIT = 16000; // item id limit, different clients have more items like silab which goes past 15000
	public static final int MAXITEM_AMOUNT = Integer.MAX_VALUE;
	public static final int BANK_SIZE = 352;
	public static final int MAX_PLAYERS = 1024;

	public static int REGION_SIZE = 0;
	public static int REGION_AMOUNT = 70;
	public static final boolean ADMIN_CAN_PVP = false; // can admins be attacked or attack other players?
	public static int REGION_DECREASE = 6;
	public static int REGION_NORMALREGION = 32;
	public static final int CONNECTION_DELAY = 100; // how long one ip can keep connecting
	public static final int IPS_ALLOWED = 1; // how many ips are allowed

	public static final boolean WORLD_LIST_FIX = false; // change to true if you want to stop that world--8 thing, but it can cause the screen to freeze on silabsoft client

	public static final int[] ITEM_SELLABLE 		=	{12745,15004,3842,3844,3840,8844,8845,8846,8847,8848,
		8849,8850,10551,6570,7462,7461,7460,7459,7458,7457,7456,7455,7454,8839,8840,8842,11663,11664,11665,10499,
		9748,9754,9751,9769,9757,9760,9763,9802,9808,9784,9799,9805,9781,9796,9793,9775,9772,9778,9787,9811,9766,
		9749,9755,9752,9770,9758,9761,9764,9803,9809,9785,9800,9806,9782,9797,9794,9776,9773,9779,9788,9812,9767,
		9747,9753,9750,9768,9756,9759,9762,9801,9807,9783,9798,9804,9780,9795,9792,9774,9771,9777,9786,9810,9765,
		995,4164,10589,8928,6131,8478,6137,5554,11756,10564,10551,6133,6617,6139,5553,10047,6809,6135,6625,6141,
		5555,1846,9006,9005,6143,6619,6147,5557,10077,2902,10075,6149,6153,5556,4081,7809,7806,7807,7808}; // what items can't be sold in any store
	public static final int[] ITEM_TRADEABLE 		= 	{12745,15004,8850,10551,8839,8840,8842,11663,11664,11665,3842,3844,3840,8844,8845,8846,8847,8848,8849,8850,10551,6570,7462,7461,7460,7459,7458,7457,7456,7455,7454,8839,8840,8842,11663,11664,11665,10499,
		9748,9754,9751,9769,9757,9760,9763,9802,9808,9784,9799,9805,9781,9796,9793,9775,9772,9778,9787,9811,9766,
		9749,9755,9752,9770,9758,9761,9764,9803,9809,9785,9800,9806,9782,9797,9794,9776,9773,9779,9788,9812,9767,
		9747,9753,9750,9768,9756,9759,9762,9801,9807,9783,9798,9804,9780,9795,9792,9774,9771,9777,9786,9810,9765}; // what items can't be traded or staked
	public static final int[] UNDROPPABLE_ITEMS 	= 	{}; // what items can't be dropped

	public static final int[] FUN_WEAPONS	=	{2460,2461,2462,2463,2464,2465,2466,2467,2468,2469,2470,2471,2471,2473,2474,2475,2476,2477}; // fun weapons for dueling

	public static boolean ADMIN_CAN_TRADE = true; //can admins trade?
	public static boolean ADMIN_CAN_SELL_ITEMS = true; // can admins sell items?
	public static boolean ADMIN_DROP_ITEMS = true; // can admin drop items?

	public static final int START_LOCATION_X = 2864; // start here
	public static final int START_LOCATION_Y = 3337;
	public static final int RESPAWN_X = 2846; // when dead respawn here
	public static final int RESPAWN_Y = 3335;
	public static final int DUELING_RESPAWN_X = 3362; // when dead in duel area spawn here
	public static final int DUELING_RESPAWN_Y = 3263;
	public static final int RANDOM_DUELING_RESPAWN = 5; // random coords

	public static final int NO_TELEPORT_WILD_LEVEL = 20; // level you can't tele on and above
	public static final int SKULL_TIMER = 1200; // how long does the skull last? seconds x 2
	public static final int TELEBLOCK_DELAY = 20000; // how long does teleblock last for.
	public static final boolean SINGLE_AND_MULTI_ZONES = true; // multi and single zones?
	public static final boolean COMBAT_LEVEL_DIFFERENCE = true; // wildy levels and combat level differences matters

	public static final boolean itemRequirements = true; // attack, def, str, range or magic levels required to wield weapons or wear items?

	public static final int MELEE_EXP_RATE = 10000; // damage * exp rate
	public static final int P1MELEE_EXP_RATE = 8000; // damage * exp rate
	public static final int RANGE_EXP_RATE = 8000;
	public static final int MAGIC_EXP_RATE = 8000;
	public static final int PRAYER_EXP_RATE = 5;
	public static final double SERVER_EXP_BONUS = 1;

	public static final int INCREASE_SPECIAL_AMOUNT = 17500; // how fast your special bar refills
	public static final boolean PRAYER_POINTS_REQUIRED = true; // you need prayer points to use prayer
	public static final boolean PRAYER_LEVEL_REQUIRED = true; // need prayer level to use different prayers
	public static final boolean MAGIC_LEVEL_REQUIRED = true; // need magic level to cast spell
	public static final int GOD_SPELL_CHARGE = 300000; // how long does god spell charge last?
	public static final boolean RUNES_REQUIRED = true; // magic rune required?
	public static final boolean CORRECT_ARROWS = true; // correct arrows for bows?
	public static final boolean CRYSTAL_BOW_DEGRADES = true; // magic rune required?

	public static final int SAVE_TIMER = 120; // save every 1 minute
	public static final int NPC_RANDOM_WALK_DISTANCE = 5; // the square created , 3x3 so npc can't move out of that box when randomly walking
	public static final int NPC_FOLLOW_DISTANCE = 10; // how far can the npc follow you from it's spawn point, 													
	public static final int[] UNDEAD_NPCS = {90,91,92,93,94,103,104,73,74,75,76,77}; // undead npcs

	public static final int EDGEVILLE_X = 3087;
	public static final int EDGEVILLE_Y = 3498;
	public static final String EDGEVILLE = "";
	public static final int AL_KHARID_X = 3293;
	public static final int AL_KHARID_Y = 3174;
	public static final String AL_KHARID = "";
	public static final int KARAMJA_X = 3087;
	public static final int KARAMJA_Y = 3500;
	public static final String KARAMJA = "";
	public static final int MAGEBANK_X = 2538;
	public static final int MAGEBANK_Y = 4716;
	public static final String MAGEBANK = "";

	/**
	 * Teleport Spells
	 **/
	// modern
	public static final int VARROCK_X = 3087;
	public static final int VARROCK_Y = 3500;
	public static final String VARROCK = "";
	public static final int LUMBY_X = 3222;
	public static final int LUMBY_Y = 3218;
	public static final String LUMBY = "";
	public static final int FALADOR_X = 2964;
	public static final int FALADOR_Y = 3378;
	public static final String FALADOR = "";
	public static final int CAMELOT_X = 2757;
	public static final int CAMELOT_Y = 3477;
	public static final String CAMELOT = "";
	public static final int ARDOUGNE_X = 2662;
	public static final int ARDOUGNE_Y = 3305;
	public static final String ARDOUGNE = "";
	public static final int WATCHTOWER_X = 3087;
	public static final int WATCHTOWER_Y = 3500;
	public static final String WATCHTOWER = "";
	public static final int TROLLHEIM_X = 3243;
	public static final int TROLLHEIM_Y = 3513;
	public static final String TROLLHEIM = "";

	// ancient

	public static final int PADDEWWA_X = 3098;
	public static final int PADDEWWA_Y = 9884;

	public static final int SENNTISTEN_X = 3322;
	public static final int SENNTISTEN_Y = 3336;

	public static final int KHARYRLL_X = 3492;
	public static final int KHARYRLL_Y = 3471;

	public static final int LASSAR_X = 3006;
	public static final int LASSAR_Y = 3471;

	public static final int DAREEYAK_X = 3161;
	public static final int DAREEYAK_Y = 3671;

	public static final int CARRALLANGAR_X = 3156;
	public static final int CARRALLANGAR_Y = 3666;

	public static final int ANNAKARL_X = 3288;
	public static final int ANNAKARL_Y = 3886;

	public static final int GHORROCK_X = 2977;
	public static final int GHORROCK_Y = 3873;

	public static final int TIMEOUT = 20;
	public static final int CYCLE_TIME = 600;
	public static final int BUFFER_SIZE = 20000;

	/**
	 * Slayer Variables
	 */
	public static final int[][] SLAYER_TASKS = {{}, //low tasks
		{1}, //med tasks
		{}, //high tasks
		{}, //low reqs
		{}, //med reqs
		{}}; //high reqs

	/**
	 * Skill Experience Multipliers
	 */	
	public static final int WOODCUTTING_EXPERIENCE = 2000; //all skills but slayer were 4 slayer was 100
	public static final int MINING_EXPERIENCE = 2000;
	public static final int SMITHING_EXPERIENCE = 2000;
	public static final int FARMING_EXPERIENCE = 2000;
	public static final int FIREMAKING_EXPERIENCE = 5000;
	public static final int HERBLORE_EXPERIENCE = 2000;
	public static final int FISHING_EXPERIENCE = 2000;
	public static final int AGILITY_EXPERIENCE = 2000;
	public static final int PRAYER_EXPERIENCE = 20000;
	public static final int P1PRAYER_EXPERIENCE = 175000;
	public static final int RUNECRAFTING_EXPERIENCE = 2000;
	public static final int CRAFTING_EXPERIENCE = 2000;
	public static final int THIEVING_EXPERIENCE = 2000;
	public static final int SLAYER_EXPERIENCE = 10000;
	public static final int COOKING_EXPERIENCE = 10000;
	public static final int FLETCHING_EXPERIENCE = 2000;
}