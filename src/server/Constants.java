package server;

import server.util.Misc;

public class Constants 
{


	// Assign integer value for NPC IDs to keep it clean and readable for later
	// edits.
	private static final int COW = 81, GOBLIN = 101, GARG = 1610,  ROCK = 1622, AWIZARD = 2712, EWIZARD = 2711, WWIZARD = 2710, FWIZARD = 2709;
	

	// Assign integer value for Item IDs to keep it clean and readable for later
	// edits,
	private static final int 
				//-------------------------Misc-------------------------//
		BONES = 526, COINS = 995, ASH = 592, BBONES = 532, DBONES = 536, GMAUL = 4153,
		GSHIELD = 3122, KEY1 = 3451, KEY2 = 3460, KEY3 = 3469, ADFS = 1540, PUREESSN = 7936,

				//-------------------------Runes-------------------------//
		FIRERUNES = 554, WATERRUNES = 555, AIRRUNES = 556, EARTHRUNES = 557, MINDRUNES = 558, 
		BODYRUNES = 559, DEATHRUNES = 560, NATURERUNES = 561, CHAOSRUNES = 562, LAWRUNES = 563, 
		COSMICRUNES = 564, BLOODRUNES = 565, SOULRUNES = 566, 

				//-------------------------Steel Items-------------------------//
		SLEGS = 1069, SSKIRT = 1083, SPLATE = 1119, SCHAIN = 1105, SMED = 1141, SFULL = 1157, SSQ = 1177, 
		SKITE = 1193, SBOOTS = 4125, SDAG = 1207, SSPEAR = 1241, SPICK = 1269, SSWORD = 1281, SLONG = 1295, 
		S2H = 1313, SSCIM = 1327, SHAM = 1341, SHATCH = 1362, SBAXE = 1361, SMACE = 1426, SCLAW = 3117,
		SHALB = 3194, 

				//-------------------------Black Items-------------------------//
		BLKLEGS = 1077, BLKSKIRT = 1089, BLKPLATE = 1125, BLKCHAIN = 1107, BLKMED = 1151, BLKFULL = 1165, BLKSQ = 1179, 
		BLKKITE = 1195, BLKBOOTS = 4125, BLKDAG = 1217, BLKSPEAR = 4580, BLKSWORD = 1283, BLKLONG = 1297, 
		BLK2H = 1311, BLKSCIM = 1325, BLKHAM = 1339, BLKHATCH = 1353, BLKBAXE = 1365, BLKMACE = 1424, BLKCLAW = 3098,
		BLKHALB = 3196, 

				//-------------------------Mithril Items-------------------------//
		MLEGS = 1072, MSKIRT = 1085, MPLATE = 1121, MCHAIN = 1109, MMED = 1143, MFULL = 1159, MSQ = 1181, 
		MKITE = 1197, MBOOTS = 4129, MDAG = 1209, MSPEAR = 1243, MPICK = 1273, MSWORD = 1285, MLONG = 1299, 
		M2H = 1315, MSCIM = 1329, MHAM = 1343, MHATCH = 1355, MBAXE = 1369, MMACE = 1428, MCLAW = 3099,
		MHALB = 3198, 
		
				//-------------------------Adamant Items-------------------------//
		ALEGS = 1073, ASKIRT = 1091, APLATE = 1123, ACHAIN = 1113, AMED = 1145, AFULL = 1161, ASQ = 1183, 
		AKITE = 1199, ABOOTS = 4129, ADAG = 1211, ASPEAR = 1245, APICK = 1271, ASWORD = 1287, ALONG = 1301, 
		A2H = 1317, ASCIM = 1331, AHAM = 1347, AHATCH = 1357, ABAXE = 1371, AMACE = 1430, ACLAW = 3100,
		AHALB = 3200, 
		
				//-------------------------Rune Items-------------------------//
		RLEGS = 1079, RSKIRT = 1093, RPLATE = 1127, RCHAIN = 1113, RMED = 1147, RFULL = 1163, RSQ = 1185, 
		RKITE = 1201, RBOOTS = 4131, RDAG = 1213, RSPEAR = 1247, RPICK = 1275, RSWORD = 1289, RLONG = 1303, 
		R2H = 1319, RSCIM = 1333, RHAM = 1347, RHATCH = 1359, RBAXE = 1373, RMACE = 1432, RCLAW = 3101,
		RHALB = 3202,  
				
				//---------------------Dragon Items---------------------//
		DCHAIN = 3140, DLEGS = 4087, DSKIRT = 4585, DBOOTS = 2904, DWCAXE = 6739, D2H = 7158, DSCIM = 4587, DHALB = 3204,
		DDS = 5698, DDAG = 1215, DSPEAR = 1249, DLONG = 1305, DBA = 1377, DMACE = 1434, DSQ = 1187, SLH = 2366, SRH = 2368,		

				//---------------------Mystic---------------------//
		BMYSTICHAT = 4089, BMYSTICTOP = 4091, BMYSTICBOT = 4093, BMYSTICGLOV = 4095, BMYSTICBOOT = 4097, RMYSTICHAT = 4099,
		RMYSTICTOP = 4101, RMYSTICBOT = 4103, RMYSTICGLOV = 4105, RMYSTICBOOT = 4107, WMYSTICHAT = 4109, WMYSTICTOP = 4111,
		WMYSTICBOT = 4113, WMYSTICGLOV = 4115, WMYSTICBOOT = 4117,
				
				//-------------------------ORE-------------------------
		COPPER = 436, TIN = 438, SILVERO = 442, COAL = 453, IRONO = 440, GOLDO = 444, MITHO = 447, ADAMO = 449, RUNEO = 451,

				//-------------------------BARS-------------------------
		BRONZEB = 2349, IRONB = 2351, STEELB = 2353, SILVERB = 2355, GOLDB = 2357, MITHB = 2359, ADAMB = 2361, RUNEB = 2363,
		
				//------------------------HERBLORE-------------------------
		GUAM = 199, MARR = 201, TARR = 203, HARR = 205, RAN = 207, TOAD = 2998, IRIT = 209, AVAN = 211, KWUARM = 213, SNAP = 3000,
		CADAN = 215, LANT = 2483, DWARF = 217, TORST = 219, NEWT = 221, REDSPID = 223, LIMP = 225, SNAPE = 231, VIAL = 229, 
		VIALOFWATER = 227, HORN = 237, WHITEB = 239, BSCALE = 243, ZAMWINE = 245, JANG = 247,
		
				//-------------------------Range Items-------------------------
		RXBOW = 837, RBOLT = 881, RARROW = 892, RJAV = 830, RKNIFE = 868, MSHORTB = 861;

						/****-------------------------Rarity-------------------------****/
	private static final int ALWAYS = 0, EASY = 10, COMMON = 45, UNCOMMON = 105, RARE = 215, VRARE = 550, ELITE = 900;
	public static final int[][] NPC_DROPS = {
	// Men
			{1,BONES,1,ALWAYS}, {2,BONES,1,ALWAYS}, {3,BONES,1,ALWAYS},
//--------------------------------------------------------------
	//Cow
			{COW,BONES,1,ALWAYS}, {COW,1739,1,ALWAYS}, {COW,2132,1,ALWAYS},
//--------------------------------------------------------------
	//Goblin Lvl 5
			{101,BONES,1,ALWAYS}, {101,1237,1,EASY}, {101,1103,1,EASY}, {101,1277,1,COMMON}, {101,1139,1,COMMON}, {101,288,1,UNCOMMON},
//--------------------------------------------------------------
	//Goblin Lvl 13
					{102,BONES,1,ALWAYS}, {102,1239,1,EASY}, {102,1101,1,EASY}, {102,1279,1,EASY}, {102,1137,1,COMMON}, {102,1067,1,COMMON}, {102,4119,1,COMMON}, {102,288,1,UNCOMMON},
//--------------------------------------------------------------
	//Market Guard Level 20- Relleka
					{2571,BONES,1,ALWAYS}, {2571,COINS,5 + Misc.random(20),ALWAYS}, {2571,1173,1,EASY}, {2571,1067,1,COMMON}, {2571,1323,1,EASY}, {2571,1325,1,UNCOMMON},
//-------------------------------------------------------------
	//Cyclops Lvl 56
			{116,BBONES,1,ALWAYS}, {116,1195,1,COMMON}, {116,1621,1,COMMON}, {116,484,1,50}, {116,510,1,COMMON}, 
			{116,379,1,COMMON}, {116,KEY1,1,RARE},
//-------------------------------------------------------------
	//Heros
			{21,BONES,1,ALWAYS}, {21,43,1 + Misc.random(10),ALWAYS}, {21,RPLATE,1,UNCOMMON}, {21,RLEGS,1,COMMON}, {21,RFULL,1,COMMON}, {21,RKITE,1,EASY}, {21,RSWORD,1,EASY},	
//--------------------------------------------------------------
	//Wizards
		//Air
			{AWIZARD,ASH,1,ALWAYS},{AWIZARD,COINS,2 + Misc.random(34),EASY}, {AWIZARD,AIRRUNES,2 + Misc.random(30),ALWAYS}, {AWIZARD,KEY1,1,RARE}, {AWIZARD,1381,1,COMMON},
		//Earth
			{EWIZARD,ASH,1,ALWAYS},{EWIZARD,COINS,2 + Misc.random(34),EASY}, {EWIZARD,EARTHRUNES,2 + Misc.random(30),ALWAYS}, {EWIZARD,KEY1,1,RARE}, {EWIZARD,1385,1,COMMON},
		//Water
			{WWIZARD,ASH,1,ALWAYS},{WWIZARD,COINS,2 + Misc.random(34),EASY}, {WWIZARD,WATERRUNES,2 + Misc.random(30),ALWAYS}, {WWIZARD,KEY1,1,RARE}, {WWIZARD,1383,1,COMMON},
		//Fire
			{FWIZARD,ASH,1,ALWAYS},{FWIZARD,COINS,2 + Misc.random(34),EASY}, {FWIZARD,FIRERUNES,2 + Misc.random(30),ALWAYS}, {FWIZARD,KEY1,1,RARE}, {FWIZARD,1387,1,COMMON},
		//Wizard
			{13,BONES,1,ALWAYS},{13,MINDRUNES,2 + Misc.random(30),ALWAYS},
//--------------------------------------------------------------
	//Moss Giants Lvl 42
			{112,BBONES,1,ALWAYS},{112,1179,1,COMMON},{112,1141,1,COMMON},
			{112,1193,1,UNCOMMON},{112,1389,1,COMMON},{112,1243,1,COMMON},
			{112,1285,1,COMMON},{112,886,30,COMMON},{112,884,15,1},
		/****Runes****/
			{112,AIRRUNES,18,COMMON},{112,COSMICRUNES,3,UNCOMMON},
			{112,EARTHRUNES,27,COMMON},{112,NATURERUNES,6,UNCOMMON},
			{112,LAWRUNES,3 + Misc.random(3),UNCOMMON},{112,BLOODRUNES,1,RARE},
			{112,CHAOSRUNES,7,UNCOMMON},{112,DEATHRUNES,1 + Misc.random(2),RARE},
		/****Seeds****/
			{112,5291,1,COMMON},{112,5292,1,COMMON},{112,5293,1,COMMON},
			{112,5294,1,COMMON},{112,5295,1,UNCOMMON},{112,5296,1,UNCOMMON},
			{112,5297,1,UNCOMMON},{112,5298,1,RARE},{112,5299,1,RARE},
			{112,5300,1,RARE},{112,5301,1,VRARE},{112,5302,1,VRARE},
			{112,5303,1,VRARE},
		/****Other****/
			{112,COINS,2+Misc.random(498),COMMON},{112,2353,1,COMMON},
			{112,453,1,UNCOMMON},
//--------------------------------------------------------------
	//Zombies(Lvl25)
			{76,2959,1,10}, {76,BONES,1,ALWAYS}, {76,COINS,5 + Misc.random(10),COMMON}, {76,884,3 + Misc.random(18),COMMON}, 
			{76,MINDRUNES,3 + Misc.random(18),COMMON},
//--------------------------------------------------------------
	//Bandits
			{195,BONES,1,ALWAYS}, {195,1321,1,COMMON}, {195,COINS,5 + Misc.random(25),COMMON}, {195,315,1,COMMON}, 
			{195,1327,1,COMMON}, {195,1179,1,COMMON},{2616,6571,1,COMMON},
//--------------------------------------------------------------
	//Chicken
			{41,BONES,1,ALWAYS}, {41,314,1 + Misc.random(15),ALWAYS}, {41,2138,1,ALWAYS},
//--------------------------------------------------------------
	//Lesser Demons
			{82,ASH,1,ALWAYS}, {82,COINS,25 + Misc.random(175), COMMON}, {82,1147,1,UNCOMMON},
			{82,DEATHRUNES,3,RARE}, {82,CHAOSRUNES,12,COMMON}, {82,FIRERUNES,30 + Misc.random(30),COMMON},{82,KEY2,1,RARE},
		/****Rare Drop table****/
			{82,1624,1,COMMON},{82,1621,1,UNCOMMON},{82,1619,1,UNCOMMON},{82,1617,1,RARE},{82,1631,1,RARE},{82,2363,1,RARE},
			{82,1247,1,RARE},{82,1249,1,VRARE},{82, 2366,1,VRARE},
//--------------------------------------------------------------
	//Dad
			{1125,6729,1,ALWAYS}, {1125,COINS,100 + Misc.random(200), COMMON}, {1125,1331,1,UNCOMMON},
			{1125,RPLATE,1,VRARE}, {1125,1543,1,VRARE}, {1125,1093,1,VRARE},
			
//--------------------------------------------------------------
	//Mourner
			{2373,228,1,UNCOMMON}, {2373,561,50,UNCOMMON}, {2373,212,25,UNCOMMON}, {2373,208,25,UNCOMMON},
			{2373,228,200,RARE}, {2373,4675,1,RARE},
			{2373,1506,1,VRARE},
			
//--------------------------------------------------------------			
// Shadow Warrior
			{158,ASH,1,ALWAYS}, {158,204,10,UNCOMMON}, {158,1303,1,RARE},
			
//--------------------------------------------------------------
	//Animated Armors
			{4278,1075,1,ALWAYS}, {4278,1117,1,ALWAYS}, {4278,1155,1,ALWAYS},
			{4279,1067,1,ALWAYS}, {4279,1115,1,ALWAYS}, {4279,1153,1,ALWAYS},
			{4280,1069,1,ALWAYS}, {4280,1119,1,ALWAYS}, {4280,1157,1,ALWAYS},
			{4281,1077,1,ALWAYS}, {4281,1125,1,ALWAYS}, {4281,1165,1,ALWAYS},
			{4282,1071,1,ALWAYS}, {4282,1121,1,ALWAYS}, {4282,1159,1,ALWAYS},
			{4283,1091,1,ALWAYS}, {4283,1123,1,ALWAYS}, {4283,1161,1,ALWAYS},
			{4284,RLEGS,1,ALWAYS}, {4284,RPLATE,1,ALWAYS}, {4284,RFULL,1,ALWAYS},
//--------------------------------------------------------------
	//Greater Demons
			{83,ASH,1,ALWAYS},{83,1353,1,COMMON},{83,1365,1,COMMON},{83,1311,1,COMMON},{83,1197,1,UNCOMMON},{83,1074,1,UNCOMMON},
			{83,RFULL,1,RARE},{83,1201,1,RARE},
		/****Runes/Ammunition****/
			{83,FIRERUNES,37 + Misc.random(38),COMMON},{83,CHAOSRUNES,15,UNCOMMON},{83,DEATHRUNES,5,UNCOMMON},
		/****Other****/
			{83,COINS,120,COMMON},{83,2357,1,UNCOMMON},{83,1734,10,UNCOMMON},{83,361,1,UNCOMMON},{83,KEY2,1,RARE},
		/****Rare Drop table****/
			{83,1624,1,COMMON},{83,1621,1,UNCOMMON},{83,1619,1,UNCOMMON},{83,1617,1,RARE},{83,1631,1,RARE},{83,2363,1,RARE},
			{83,1247,1,RARE},{83,1249,1,VRARE},{83, 2366,1,VRARE},
//--------------------------------------------------------------
	//Bronze Dragons
			{1590,DBONES,1,ALWAYS}, {1590,2350,5,ALWAYS},{1590,1123,1,UNCOMMON},{1590,1355,1,UNCOMMON},{1590,1197,1,UNCOMMON},
			{1590,1315,1,UNCOMMON},{1590,830,5,RARE},{1590,1197,1,RARE},{1590,RLONG,1,RARE},{1590,1373,1,RARE},
			{1590,DLEGS,1,VRARE},{1590,DSKIRT,1,VRARE},{1590,KEY3,1,VRARE},
		/****Other****/
			{1590,COINS,196,COMMON},{1590,373,1,UNCOMMON},{1590,2361,1,UNCOMMON},{1590,373,2,RARE},
		/****Runes and ammunition****/
			{1590,FIRERUNES,50,COMMON},{1590,DEATHRUNES,25 + Misc.random(20),RARE},{1590,9142,4 + Misc.random(2),UNCOMMON},
			{1590,BLOODRUNES,3 + Misc.random(12),RARE},{1590,892,42,RARE},
		/****Rare Drop table****/
			{1590,1624,1,COMMON},{1590,1621,1,UNCOMMON},{1590,1619,1,UNCOMMON},{1590,1617,1,RARE},{1590,1631,1,RARE},{1590,2363,1,RARE},
			{1590,1247,1,RARE},{1590,1249,1,VRARE},{1590, 2366,1,VRARE},
//--------------------------------------------------------------
	//Chaos Druids
			{181,BONES,1,ALWAYS},{181,COINS,2 + Misc.random(34),COMMON},
			{181,9142,2 + Misc.random(10),COMMON},{181,1291,1,UNCOMMON},{181,830,5,RARE},
			{181,231,1,RARE},{181,228,5,COMMON},{181,1452,1,RARE},
		/****Herbs****/
			{181,GUAM,1,COMMON},{181,MARR,1,COMMON},
			{181,TARR,1,COMMON},{181,HARR,1,COMMON},
			{181,RAN,1,UNCOMMON}, {181,IRIT,1,UNCOMMON},
			{181,AVAN,1,UNCOMMON},{181,KWUARM,1,UNCOMMON},
			{181,CADAN,1,UNCOMMON},{181,LANT,1,UNCOMMON},
			{181,DWARF,1,UNCOMMON},{181,TORST,1,RARE},
		/****Runes and ammunition****/
			{181,AIRRUNES,9+Misc.random(27),COMMON},{181,EARTHRUNES,9,COMMON},{181,BODYRUNES,9,COMMON},
			{181,MINDRUNES,12,COMMON},{1590,892,42,RARE},{181,LAWRUNES,2,COMMON},{181,NATURERUNES,3,UNCOMMON},
//--------------------------------------------------------------
	//Gargoyles
		/****Weapons/Armour****/
		{GARG,RFULL,1,RARE},{GARG,S2H,1,UNCOMMON},{GARG,SBAXE,1,UNCOMMON},{GARG,ABOOTS,1,RARE},{GARG,GMAUL,1,RARE},
		{GARG,RMYSTICTOP,1,VRARE},
		/****Other****/
			{GARG,COINS,11 + Misc.random(449),COMMON},{GARG,MITHB,3,UNCOMMON},
			{GARG,GOLDB,1,UNCOMMON},{GARG,STEELB,3,UNCOMMON},{GARG,KEY2,1,RARE},
			{GARG,PUREESSN,35,UNCOMMON},
		/****Runes and ammunition****/
			{GARG,FIRERUNES,37 + Misc.random(38),COMMON},{GARG,CHAOSRUNES,15,RARE},{GARG,DEATHRUNES,5,COMMON},
	};
	
}