package com.duoqio.mahjong.mahjong.org.yungege.mj;

import java.util.*;

public class Program {
	public static void print_cards(int[] cards) {
		for (int i = 0; i < 9; ++i) {
			System.out.print(cards[i]);
			System.out.print(",");
		}
		System.out.println("");

		for (int i = 9; i < 18; ++i) {
			System.out.print(cards[i]);
			System.out.print(",");
		}
		System.out.println("");
		for (int i = 18; i < 27; ++i) {
			System.out.print(cards[i]);
			System.out.print(",");
		}
		System.out.println("");
		for (int i = 27; i < 34; ++i) {
			System.out.print(cards[i]);
			System.out.print(",");
		}
		System.out.println("\n=========================================");
	}

	static HashSet<Integer> tested = new HashSet<Integer>();
	static void check_hu(int[] cards, int max_gui_index) {

		for (int i = 0; i < max_gui_index; ++i) {
			if (cards[i] > 4)
				return;
		}

		int num = 0;
		for (int i = 0; i < 9; ++i) {
			num = num * 10 + cards[i];
		}

		if (tested.contains(num)) {
			return;
		}

		tested.add(num);

		for (int i = 0; i < max_gui_index; ++i) {
			if (!Hulib.getInstance().getHu(cards, 0, 0)) {
				System.out.print("测试失败 i=%d\n" + i);
				print_cards(cards);
			}
		}
	}

	static void gen_auto_table_sub(int[] cards, int level) {
		for (int i = 0; i < 32; ++i) {
			int index = -1;
			if (i <= 17) {
				cards[i] += 3;
			} else if (i <= 24) {
				index = i - 18;
			} else {
				index = i - 16;
			}

			if (index >= 0) {
				cards[index] += 1;
				cards[index + 1] += 1;
				cards[index + 2] += 1;
			}

			if (level == 4) {
				check_hu(cards, 18);
			} else {
				gen_auto_table_sub(cards, level + 1);
			}

			if (i <= 17) {
				cards[i] -= 3;
			} else {
				cards[index] -= 1;
				cards[index + 1] -= 1;
				cards[index + 2] -= 1;
			}
		}
	}

	public static void test1() {
		System.out.println("测试两种花色\n");
		int[] cards = { 0, 0, 0, 1, 1, 1, 0, 2, 0, /* 桶 */ 0, 0, 0, 0, 0, 0, 0, 0, 0,
				/* 条 */ 0, 0, 0, 0, 0, 0, 0,0,0,/* 字 */ 0, 0, 0, 0, 0, 0, 0 };

		gen_auto_table_sub(cards, 1);
		// for (int i = 0; i < 18; ++i) {
		// cards[i] = 2;
		// System.out.print("将 ");
		// System.out.println(i);
		// gen_auto_table_sub(cards, 1);
		// cards[i] = 0;
		// }
	}

	static void test_one() {
		TableMgr.getInstance().load();
		int guiIndex = 1;

		// 1筒，2筒，3筒，4筒，4筒，5筒，5筒，6筒，7筒，7筒，8筒，8筒，9筒，9筒
		// int[] cards = { 1, 0, 0, 0, 0, 0, 0, 0, 0,/*桶*/ 1, 1, 1, 2, 1, 1, 2,
		// 2, 2,
		// /*条*/ 0, 0, 0, 0, 0, 0, 0, 0, 0,/*字*/ 0, 0, 0, 0, 0, 0, 0 };

		int[] cards = {
			2, 1, 0, 0, 0, 0, 0, 0, 0, /* 桶 */
			0, 0, 1, 2, 3, 2, 0, 0, 0, /* 条 */
			0, 0, 0, 3, 0, 0, 0, 0, 0, /* 字 */
			0, 0, 0, 0, 0, 0, 0 };

// check
		System.out.println("癞子下标:" + guiIndex);
		print_cards(cards);
		long start = System.currentTimeMillis();

		if (!Hulib.getInstance().getHu(cards, 34, guiIndex)) {
			System.out.print("false\n");
		} else {
			System.out.print("true\n");
		}


		System.out.println("use:" + (System.currentTimeMillis() - start));
	}
	//听牌测试
	static void testTing() {
		HashMap<Integer, List<Integer>> tingMap = new HashMap<>();
		ArrayList<Integer> list = new ArrayList<>();
		TableMgr.getInstance().load();
		int guiIndex = 1;

		int[] cards = {
			0, 2, 0, 1, 1, 0, 0, 0, 0, /* 桶 */
			0, 1, 0, 1, 1, 0, 0, 0, 0, /* 条 */
			0, 0, 0, 1, 1, 0, 0, 0, 0, /* 字 */
			0, 3, 0, 0, 2, 0, 0 };

		print_cards(cards);
		getFan(cards,cards[guiIndex]);
		System.out.println("癞子下标:" + guiIndex);
		if (Hulib.getInstance().getHu(cards, 34, guiIndex)) {
			System.out.println("胡了");
		}else {
			System.out.println("听牌测试");
			for (int i = 0; i < cards.length; i++) {
				if (cards[i] > 0 && i != guiIndex) {
					cards[i] -= 1;//模拟打出手牌中的一张
					if (Tinglib.getInstance().get_hu_info(cards, 34, guiIndex)) {
						list.add(i);//能胡则代表可以听牌
					}
					cards[i] += 1;//还原手牌
				}
			}
			maybe(list, cards ,guiIndex);
		}

	}
	//打出哪一张可以听牌，分别胡哪些牌
	public static void maybe(List<Integer> list, int[] cards, int guiNum){
		HashMap<Integer, List<Integer>> tingMap = new HashMap<>();
		ArrayList<Integer> list1 = new ArrayList<>();

		list.forEach( i -> {
			cards[i] -= 1;//模拟打出该牌，找出可以自摸牌的列表
			for (int j = 0; j < 34; j++) {//将所有牌循环加入手牌,然后测试能否胡牌
				if (j != guiNum) {
					cards[j] += 1;
					if (Hulib.getInstance().getHu(cards, 34, guiNum)) {
						list1.add(j);
					}
					cards[j] -= 1;
				}
			}
			tingMap.put(i, list1);
		});

		System.out.println(tingMap.toString());
	}

	public static void getFan(int[] cards, int guiNum){
		int fan = 0;

		ArrayList<Integer> list = new ArrayList<>();
		//循环手牌数组
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] > 0) {
				for (int j = 0; j < cards[i]; j++) {
					list.add(i);
				}
			}
		}

		if (list.isEmpty()) {
			return;
		}
		if (list.get(0) > 27) {
			System.out.println("真风: 40台");
		}
		if (guiNum > 6){
			System.out.println("七百搭: 40台");
		}
		//玩家杠出8朵花
		if (list.stream().filter(e -> e > 33).count() == 8) {
			System.out.println("花胡: 40台");
		}



	}
	public static void main(String[] args) {
//		ak_test();
//		test_one();
//		isTing();
		testTing();
	}
	
	private static Random random = new Random();
	private static void ak_test()
	{
		System.out.print("test hulib begin...\n");

		TableMgr.getInstance().load();
		
		int lose = 0;
		int win = 0;
		long start = System.currentTimeMillis();
		for (int i = 0 ; i < 10000000 ; i++)
		{
			int[] cards = new int[34];
			int cardNum = 0;
			while (cardNum < 14)
			{
				if(cards[random.nextInt(cards.length)] < 4)
				{
					cards[random.nextInt(cards.length)]++;
					cardNum++;
				}
			}
			int guiIndex =  random.nextInt(34);
// 			System.out.println("测试1种,癞子:" + guiIndex);
 			
// 			print_cards(cards);
			if (!Hulib.getInstance().getHu(cards, 34, guiIndex)) {
// 				System.out.print("测试失败\n");
				lose++;
			} else {
// 				System.out.print("测试成功\n");
//				System.out.println("测试1种,癞子:" + guiIndex);
//				print_cards(cards);
				win++;
			}
		}

		System.out.println("1000000次,use:" + (System.currentTimeMillis() - start)+"ms,成功:"+win+"次，失败:"+lose+"次.");
		// System.Console.ReadKey();
	}
}
