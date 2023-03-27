package HorseSea;
import javax.swing.JOptionPane;
import java.util.concurrent.Semaphore;

public class Game {

	static final int BLUE = 1;
	static final int RED = 4;
	static final int YELLOW = 2;
	static final int GREEN = 3;
	static final int DISTANCE = 45;// khoảng cách 2 cá ngựa

	
	static boolean diePhaseFlag = true; // giai đoạn tung xúc xắc
	static boolean horsePhaseFlag = false; // Giai đoạn ăn quân
	static Semaphore diePhaseSema = new Semaphore(0);
	static Semaphore horsePhaseSema = new Semaphore(0);

	public void showError(String error) {
		JOptionPane.showMessageDialog(null, error);
	}

	public void sleep(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}