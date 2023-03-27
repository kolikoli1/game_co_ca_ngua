package HorseSea;

public class GameMap extends Game {
	/*
	 * Trong game, bản đồ được mô tả bằng các ô vuông và mỗi ô vuông có thể chứa một
	 * con ngựa hoặc một phần thưởng. Các ô vuông được mã hóa bằng các số như sau:
	 * 
	 * 0: không có con ngựa ở vị trí này. 1x: có một con ngựa màu xanh nằm ở vị trí
	 * này, với x là số thứ tự của con ngựa (x = [0; 3]). 2x: có một con ngựa màu đỏ
	 * nằm ở vị trí này. 3x: có một con ngựa màu vàng nằm ở vị trí này. 4x: có một
	 * con ngựa màu xanh lá cây nằm ở vị trí này. 5x: có một phần thưởng trên ô
	 * vuông này, với giá trị tương ứng là x.
	 */
	private Player player[];
	private final int map[] = new int[NUMBER_NODE];

	static final int NUMBER_NODE = 56;
	private final int NO_HORSE = 0;
	/* Vị trí đích đến của các quân cờ. */
	private final int DES[] = { -1, 55, 13, 27, 41 };
	private final int START[] = { -1, 0, 14, 28, 42 };// Can sua gia tri 48 thanh 0
	private int numberPlayer;

	GameMap() {
		numberPlayer = 4;
		player = new Player[numberPlayer + 1];

		for (int color = 1; color <= numberPlayer; color++) {
			player[color] = new Player(color);
		}
	}

	public int getNumberPlayer() {
		return numberPlayer;
	}

	public int getMap(int index) {
		return map[index];
	}

	public Player[] getPlayer() {
		return player;
	}

	public boolean xuatQuan(int color) {
		int idHorse = player[color].stable.getHorse();

		if (idHorse == Stable.NO_HORSE) {
			return false;
		}

		if (setMap(color, idHorse, START[color], 0)) {
			player[color].stable.remove(idHorse);
			player[color].addHorse(idHorse);
			return true;
		}

		return false;
	}

	public void addPlayerListener(int color, int steps) {
		player[color].addMouseListener(this, steps);
	}

	public void removePlayerListener(int color) {
		player[color].removeMouseListener();
	}

	public boolean setMap(int color, int idHorse, int start, int steps) {
		/* Quân cờ đang ở đích đến cuối cùng. */
		if (start == DES[color]) {
			if (player[color].destination.setDestination(Destination.NO_RANK, steps, player[color].horse[idHorse])) {
				player[color].horse[idHorse].toFinish();
				map[start] = NO_HORSE;
				return true;
			} else {
				return false;
			}
		}

		if (start < DES[color] && start + steps > DES[color]) {
			showError("QuĂ¢n nĂ y khĂ´ng thá»ƒ di chuyá»ƒn, quĂ¡ Ä‘Ă­ch Ä‘áº¿n.");
			return false;
		}

		/* Kiểm tra trên đường di chuyển có quân cờ nào cản không */
		for (int step = 1; step < steps; step++) {
			if (map[(start + step) % NUMBER_NODE] != NO_HORSE) {
				showError("QuĂ¢n nĂ y khĂ´ng thá»ƒ di chuyá»ƒn, cĂ³ quĂ¢n cáº£n máº·t.");
				return false;
			}
		}
		/* Tính toán vị trí đến" có thể được hiểu là việc tính toán khoảng cách, địa điểm hoặc tọa độ đến một địa điểm cụ thể. */
		int pos = (start + steps) % NUMBER_NODE;

		if (map[pos] == NO_HORSE) {
			/* Trường hợp có thể di chuyển */
			map[start] = NO_HORSE;
			map[pos] = color * 10 + idHorse;
		} else if (map[pos] / 10 == color) {
			/* Trường hợp vị trí đến là quân cùng màu */
			showError("QuĂ¢n nĂ y khĂ´ng thá»ƒ di chuyá»ƒn, Ä‘Ă­ch Ä‘áº¿n lĂ  quĂ¢n cĂ¹ng mĂ u.");
			return false;
		} else if (map[pos] / 10 < 5) {
			/* Trường hợp ăn quân*/
			int victimPlayer = map[pos] / 10;
			int victimHorse = map[pos] % 10;

			player[victimPlayer].removeHorse(victimHorse);
			player[victimPlayer].stable.add(victimHorse);

			map[start] = NO_HORSE;
			map[pos] = color * 10 + idHorse;
		}

		return true;
	}

	public boolean isWin() {
		for (int i = 1; i <= numberPlayer; i++) {
			if (player[i].isWin()) {
				showError("Player " + i + " is winner.");
				return true;
			}
		}

		return false;
	}
}