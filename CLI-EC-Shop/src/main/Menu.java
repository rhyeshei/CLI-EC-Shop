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
		System.out.println("0. メインメニューに戻る");
		System.out.println("--------------------");
		System.out.print("メニュー番号を入力してください　：");
	}

	public static void showProductSelectionPrompt() {
		System.out.println();
		System.out.println("確認したい商品IDを入力してください");
		System.out.println("商品メニューに戻る場合は、0 を入力してください。");
		System.out.println("--------------------");
		System.out.print("商品ID：");
	}

	public static void showProductDetailMenu() {
		System.out.println("1. カートに追加する");
		System.out.println("0. 商品メニューに戻る");
		System.out.println("--------------------");
		System.out.print("操作番号を入力してください：");
	}

	public static void showProductSearchPrompt() {
		System.out.println();
		System.out.println("検索したい商品名を入力してください。");
		System.out.println("商品メニューに戻る場合は、0 を入力してください。");
		System.out.println("--------------------");
		System.out.print("商品名：");
	}

	public static void showCartMenu() {
		System.out.println("====================");
		System.out.println("　　　カートメニュー　　");
		System.out.println("====================");
		System.out.println();
		System.out.println("1. 数量変更");
		System.out.println("2. 商品削除");
		System.out.println("3. 注文");
		System.out.println("0. メインメニューに戻る");
		System.out.println("--------------------");
		System.out.print("操作番号を入力してください：");
	}

	public static void showOrderConfirmationMenu() {
		System.out.println("====================");
		System.out.println("　　　　注文確認　　　　");
		System.out.println("====================");
		System.out.println();
		System.out.println("注文を確定しますか？");
		System.out.println("1. 注文を確定する");
		System.out.println("0. カートメニューに戻る");
		System.out.println("--------------------");
		System.out.print("操作番号を入力してください：");
	}

}
