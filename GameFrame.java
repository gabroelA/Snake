import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.print.attribute.standard.Destination;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameFrame extends JFrame {
	private static final int SCORE = 7;

	private int delay = 130;
	private Timer timer = new Timer(delay, new Action());
	private GamePanel panel = new GamePanel();

	private int snakeSize = 1;

	private int x[] = new int[1000];
	private int y[] = new int[1000];

	private int size = 30;
	private int direction = 1;
	private int delta = size;

	private int appleX = 300;
	private int appleY = 300;

	private boolean gameOver = false;
	private boolean pause = false;

	public GameFrame() {
		setSize(906, 600);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setBackground(Color.GREEN);

		add(panel);

		setVisible(true);
	}

	public static void main(String[] args) {
		new GameFrame();
	}

	public class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 1; i < snakeSize; i++) {
				if (dest(x[0], y[0], x[i], y[i]) <= 0) {
					gameOver = true;
				}
			}
			
			
			////////////////////////////////////////
			for (int i = snakeSize; i > 0; i--) {
				x[i] = x[i - 1];
				y[i] = y[i - 1];
			}
			////////////////////////////////////////

			if (direction == 1) {
				moveRight();
			}

			if (direction == 2) {
				moveDown();
			}

			if (direction == 3) {
				moveLeft();
			}

			if (direction == 4) {
				moveUp();
			}

			if (x[0] == appleX && y[0] == appleY) {
				appleX = generateX();
				appleY = generateY();
				snakeSize++;

			}

			if (gameOver == true) {
				System.out.println(gameOver);
				timer.stop();
			}

			if (timer.isRunning()) {
				
			}

		}

		/**
		 * get distance between two points
		 */
		public int dest(int x1, int y1, int x2, int y2) {
			int x = x2 - x1;
			int y = y2 - y1;

			Math.abs(x);
			Math.abs(y);

			x *= x;
			y *= y;

			int sum = x + y;

			Math.sqrt(sum);
			return sum;
		}

	}

	@SuppressWarnings("serial")
	class GamePanel extends JPanel implements KeyListener {

		public GamePanel() {
			timer.start();
			addKeyListener(this);
			setFocusable(true);
		}

		@Override
		protected void paintComponent(Graphics g) {
			// drawing score
			String str = "" + (snakeSize - 1) * SCORE;
			g.setFont(new Font("ddd", Font.BOLD, size));
			g.drawString(str, getWidth() - size * 2, size);

			// drawing apple
			g.setColor(Color.RED);
			g.fillOval(appleX, appleY, size, size);

			drawSnake(g);
		}

		public void drawSnake(Graphics g) {
			// drawing snake
			g.setColor(Color.BLACK);

			for (int i = 0; i < snakeSize; i++) {
				g.fillRoundRect(x[i], y[i], size, size, size / 3, size / 3);
			}

		}

		@Override
		public void keyPressed(KeyEvent k) {
			if (k.getKeyCode() == KeyEvent.VK_RIGHT && direction != 3) {
				direction = 1;
			}

			if (k.getKeyCode() == KeyEvent.VK_DOWN && direction != 4) {
				direction = 2;
			}

			if (k.getKeyCode() == KeyEvent.VK_LEFT && direction != 1) {
				direction = 3;
			}

			if (k.getKeyCode() == KeyEvent.VK_UP && direction != 2) {
				direction = 4;
			}

			if(k.getKeyCode() == KeyEvent.VK_SPACE && pause == false){
				timer.stop();
				pause = true;
			}
			
			if(k.getKeyCode() ==  KeyEvent.VK_ENTER && pause == true){
				timer.start();
				pause = false;
			}

		}

		@Override
		public void keyReleased(KeyEvent k) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent k) {
			// TODO Auto-generated method stub

		}

	}

	public int generateX() {
		int x;
		do {
			x = (int) (Math.random() * getWidth() - size);

		} while (x % size != 0);
		return x;
	}

	public int generateY() {
		int y;
		do {
			y = (int) (Math.random() * getHeight() - size);

		} while (y % size != 0);
		return y;
	}

	public void moveRight() {
		if (x[0] < getWidth()) {
			x[0] += delta;
			repaint();
		}
	}

	public void moveDown() {
		if (y[0] < getHeight()) {
			y[0] += delta;
			repaint();
		}
	}

	public void moveLeft() {
		if (x[0] > 0) {
			x[0] -= delta;
			repaint();
		}
	}

	public void moveUp() {
		if (y[0] > 0) {
			y[0] -= delta;
			repaint();
		}
	}
}
