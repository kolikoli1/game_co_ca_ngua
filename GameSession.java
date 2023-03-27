package HorseSea;
import javax.swing.JOptionPane;

public class GameSession extends Game {
	private final int ONE_BONUS = -1;
	private final int NO_BONUS = 0;

	private int turn, turnBonus = 0;
	private GameMap map;
	private boolean endGameFlag;
	private GameGraphic graphic;
	private Die die;

	GameSession() {
		setTurn();
		map = new GameMap();
		die = new Die();
		graphic = new GameGraphic();
		graphic.drawMap(map);
		graphic.drawControl(die);

		endGameFlag = false;
	}

	public void setTurn() {
		while (true) {
			String strTurn = JOptionPane.showInputDialog(null, "Nháº­p player Ä‘i trÆ°á»›c (1/ 2/ 3/ 4): ", JOptionPane.INFORMATION_MESSAGE);

			if (strTurn.matches("[1234]")) {
				turn = Integer.parseInt(strTurn);
				break;
			} else {
				showError("Báº¡n nháº­p sai player. Xin má»�i nháº­p láº¡i.");
			}
		}
	}

	public void playGame() {
		while (!endGameFlag) {
			int color = turn;
			turnBonus = NO_BONUS;
			graphic.drawTurnLabel(color);

			try {
				diePhaseSema.acquire();
			} catch (InterruptedException exc) {
				System.out.println(exc);
			}
			horsePhaseFlag = true;
			int steps = die.getSteps();
			if(steps == 6){
				turnBonus = ONE_BONUS;
				graphic.drawXuatQuanButton(map, color);
			}

			map.addPlayerListener(color, steps);
			try {
				horsePhaseSema.acquire();
			} catch (InterruptedException exc) {
				System.out.println(exc);
			}
			
			map.removePlayerListener(color);
			graphic.removeXuatQuanButton();
			horsePhaseFlag = false;
			graphic.drawMap(map);
			
			diePhaseFlag = true;
			turn = (turn + turnBonus) % map.getNumberPlayer() + 1;
			
			if(map.isWin()){
				endGameFlag = true;
			}
		}
	}

	public static void main(String args[]) {
		GameSession session = new GameSession();
		session.playGame();
	}
}