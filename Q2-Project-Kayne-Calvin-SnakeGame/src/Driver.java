import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class Driver extends JPanel implements ActionListener{
	//for commit and push
	static final int SCREEN_WIDTH = 600; 
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 50; //grid size
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 200; //movement speed
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int body = 3; //initial snake size
	int foodsEaten;
	int foodX;
	int foodY;
	char direction = 'D'; //initial direction from top left corner
	boolean running = false;
	Timer timer;
	Random random;
	
	Driver(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	
	public void startGame() {
		newFood();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
			
		for(int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
			//draws grid
			g.setColor(Color.blue);
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);				
			
			//draws food
			g.setColor(Color.blue);
			g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);			
			
			//draws snake
			for(int i1 = 0; i1<body; i1++) {
				if(i1 == 0) {
					g.setColor(Color.blue);
					g.fillOval(x[i1], y[i1], UNIT_SIZE, UNIT_SIZE);
				}else {
					g.fillOval(x[i1], y[i1], UNIT_SIZE, UNIT_SIZE);
				}
			}
			//scoring
			g.setColor(Color.red);
			g.setFont(new Font("Serif Plain", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("SCORE: " + foodsEaten, (SCREEN_WIDTH - metrics.stringWidth("SCORE: " + foodsEaten))/2, g.getFont().getSize());
		}
			
		
			
	
		
	}
	public void newFood() {
		//food spawning and randomization of its positions
		foodX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		foodY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void move() {
		
		for(int i = body; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U': //up
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D': //down
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L': //left
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R': //right
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkFood() {
		if((x[0] == foodX) && (y[0] == foodY)) {
			body++;
			foodsEaten++;
			newFood();
		}
	}
	public void checkCollisions() {
		//checks if head of snake collides with body
		for(int i = body; i>0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		//checks if snake touches left border
		if(x[0] < 0) {
			running = false;
		}
		//check if snake touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//check if snake touches top border
		if(y[0] < 0) {
			running = false;
		}
		//check if snake touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	//public void gameOver(Graphics g) {
		//game over text
		//g.setColor(Color.white);
		//g.setFont(new Font("Ink Free", Font.BOLD, 40));
		//FontMetrics metrics = getFontMetrics(g.getFont());
		//g.drawString("Score: " + foodsEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + foodsEaten))/2, g.getFont().getSize());
	//}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running = true) {
			move();
			checkFood();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
				
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
	

}
