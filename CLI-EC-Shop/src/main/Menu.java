package main;

public class Menu {
	public static void showMainMenu() {
		System.out.println("====================");
		System.out.println("　　CLI　EC　SHOP　　 ");
		System.out.println("====================");
		System.out.println();
		System.out.println("1. 商品を探す");
		System.out.println("2. カートを見る");
		System.out.println("0. 終了する");
		System.out.println("--------------------");
		System.out.print("メニュー番号を入力してください　：");
	}

	public static void showProductMenu() {
		System.out.println("====================");
		System.out.println("　　　商品メニュー　　　");
		System.out.println("====================");
		System.out.println();
		System.out.println("1. 商品一覧表示");
		System.out.println("2. 商品検索");
		System.out.println("0. 戻る");
		System.out.println("--------------------");
		System.out.print("メニュー番号を入力してください　：");
	}

}
