package game;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	public final int WIDTH=600, HEIGHT=600, UNIT_SIZE=25,DELAY=75;
	public final int GAME_UNITS=WIDTH/UNIT_SIZE*HEIGHT;
	public final int x[]=new int[GAME_UNITS],y[]=new int[GAME_UNITS];
	int bodyParts=6,applesEaten,appleX,appleY;
	char direction='R';
	boolean running=false;
	Timer timer;
	Random random;

	GamePanel(){
		random=new Random();
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newApple();
		running=true;
		timer=new Timer(DELAY,this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
		
	public void draw(Graphics g) {
		
		/*
		for(int i=0;i<=WIDTH/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,HEIGHT);
		}
		for(int i=0;i<=HEIGHT/UNIT_SIZE;i++) {
			g.drawLine(0,i*UNIT_SIZE,WIDTH,i*UNIT_SIZE);
		}*/
		
		if(running) {	
		
			g.setColor(Color.red);
			g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
		
			for(int i=0;i<bodyParts;i++) {
				if(i==0) {
					g.setColor(Color.green.darker());
				}else {
					g.setColor(Color.green.darker().darker());
				}
				g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
				
				
			}
			g.setColor(Color.white);
			g.setFont(new Font("Arial",1,35));
			FontMetrics metrics= getFontMetrics(g.getFont());
			g.drawString(String.valueOf(applesEaten),(WIDTH-metrics.stringWidth(String.valueOf(applesEaten)))/2,
					g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
	}
	
	public void newApple() {
		appleX=random.nextInt((int)(WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY=random.nextInt((int)(HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move() {
		for(int i=bodyParts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0]-=UNIT_SIZE;
			if(y[0]<0) {
				y[0]=HEIGHT-UNIT_SIZE;
			}
			break;
		case 'D':
			y[0]+=UNIT_SIZE;
			if(y[0]>=HEIGHT) {
				y[0]=0;
			}
			break;
		case 'L':
			x[0]-=UNIT_SIZE;
			if(x[0]<0) {
				x[0]=WIDTH-UNIT_SIZE;
			}
			break;
		case 'R':
			x[0]+=UNIT_SIZE;
			if(x[0]>=WIDTH) {
				x[0]=0;
			}
			break;
		}
	}
	
	public void checkApple() {
		if(x[0]==appleX && y[0]==appleY) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollision() {
		for(int i=bodyParts;i>0;i--) {
			if(x[0]==x[i] && y[0]==y[i]) {
				running=false;
			}
		}
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Arial",1,75));
		FontMetrics metrics1= getFontMetrics(g.getFont());
		g.drawString("Game Over!",(WIDTH-metrics1.stringWidth("Game Over"))/2,HEIGHT/2);
		g.setFont(new Font("Arial",1,35));
		FontMetrics metrics2= getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten,(WIDTH-metrics2.stringWidth("Score: "+applesEaten))/2,
				HEIGHT/2+g.getFont().getSize()*2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!='R') {
					direction='L';
				break;
				}
			case KeyEvent.VK_A:
				if(direction!='R') {
					direction='L';
				break;
				}
			case KeyEvent.VK_RIGHT:
				if(direction!='L') {
					direction='R';
				break;
				}
			case KeyEvent.VK_D:
				if(direction!='L') {
					direction='R';
				break;
				}
			case KeyEvent.VK_UP:
				if(direction!='D') {
					direction='U';
				break;
				}	
			case KeyEvent.VK_W:
				if(direction!='D') {
					direction='U';
				break;
				}	
			case KeyEvent.VK_DOWN:
				if(direction!='U') {
					direction='D';
				break;
				}
			case KeyEvent.VK_S:
				if(direction!='U') {
					direction='D';
				break;
				}
						
			}
			
		}
	}

}
