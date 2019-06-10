import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
class TickTack{
	public static void main(String[] args) {
		JFrame f  = new JFrame("Game");
		GamePanel game = new GamePanel();
		f.setSize(500,600);
		f.add(game);
		f.setVisible(true);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setResizable(false);

	}
}


class GamePanel extends JPanel implements ActionListener,KeyListener{
	boolean play = false;
	int score = 0;
	

	int row = 3;
	int column = 7;

	int blocks[][] = new int[row][column];
	int barXpos = 300;
	int barYpos = 550;

	int ballXpos = 200;
	int ballYpos = 300;

	int ballXdir = 3;
	int ballYdir = 5;
	int delay = 8;
	Timer timer;
	int x;
	boolean nl = false;
	int totalbricks = row * column;

	GamePanel(){
		addKeyListener(this);
		setFocusable(true);
		timer = new Timer(delay,this);
		timer.start();
		for(int i=0;i<blocks.length;i++){
			for(int j=0;j<blocks[0].length;j++){
				blocks[i][j] = 1;
			}
		}
	}
		
	public void paint(Graphics g){
		//background
		g.setColor(Color.black);
		g.fillRect(0,0,490,590);
		
		g.setColor(Color.white);
		g.drawString("Score" +" "+ Integer.toString(score),40,20);
		g.setColor(Color.yellow);
		g.fillRect(0,0,3,580);
		g.fillRect(0,0,480,3);
		g.fillRect(480,0,3,580);

		g.setColor(Color.red);
		g.fillRect(barXpos,barYpos,100,10);

		g.setColor(Color.blue);
		g.fillOval(ballXpos,ballYpos,20,20);
		
	
		for(int i=0;i<blocks.length;i++){
			for(int j=0;j<blocks[0].length;j++){
				if(blocks[i][j]>0){
					g.setColor(Color.yellow);
					g.fillRect(j*50+70,i*40+60,50,40);
					Graphics2D g2 = (Graphics2D)g;
					g2.setStroke(new BasicStroke(3.0f));
					g2.setColor(Color.black);
					g2.drawRect(j*50+70,i*40+60,50,40);
				}
			}
		}

	}

	public void actionPerformed(ActionEvent ae){
		timer.start();
		if(play){
			ballXpos = ballXpos + ballXdir;
			ballYpos = ballYpos + ballYdir;
			if(ballXpos<0){
				ballXdir = -ballXdir;
			}
			if(ballXpos>460){
				ballXdir = -ballXdir;
			}
			if(ballYpos<0){
				ballYdir = -ballYdir;
			}
		}
		if(new Rectangle(ballXpos,ballYpos,20,20).intersects(new Rectangle(barXpos,barYpos,102,10))){
			ballYdir = -ballYdir;
 		}
 		A :for(int i=0;i<blocks.length;i++){
			for(int j=0;j<blocks[0].length;j++){
				if(blocks[i][j]>0){
					int blockX  = j*50+70;
					int blockY = i*40+60;
					int blockwidth =50;
					int blockheight = 40;
					Rectangle rect = new Rectangle(blockX,blockY,blockwidth,blockheight);
					Rectangle ball = new Rectangle(ballXpos,ballYpos,20,20);
					Rectangle blockre = rect;
						if(ball.intersects(blockre)){
		 					blocks[i][j] = 0;
		 					totalbricks--;
		 					score = score + 10;

		 					if(ballXpos + 22 <= blockre.x || ballXpos + 4 >= blockre.x + blockre.width){
		 						ballXdir = -ballXdir;
		 				}else{
		 					ballYdir = -ballYdir;
		 				}
		 				break A;
	 				} 
				}
 			}

 			if(totalbricks == 0){
 				play = false;
 				nl = true;
		 					String options[] = {"Next Level","Exit"};
 							x = JOptionPane.showOptionDialog(null,"Jinklas!","Please Choose",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
 							}
 							if(x == 0){
 								nextLevel();
							}
 		
					 		if(x == 1){
					 			System.exit(0);
					 		}
		 				}

 		if(ballYpos>585){
 			play = false;
 			String options[] = {"Restart","Exit"};
 			x = JOptionPane.showOptionDialog(null,"Harlas!","Please Choose",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
 		}
 		if(x == 0){
 			restart();
		}
 		
 		if(x == 1){
 			System.exit(0);
 		}

		repaint();
	}

	public void keyPressed(KeyEvent k){
		if(k.getKeyCode() == KeyEvent.VK_RIGHT){
			moveRight();
		}
		if(k.getKeyCode() == KeyEvent.VK_LEFT){
			moveLeft();
		}
	}

	public void keyReleased(KeyEvent k){}

	public void keyTyped(KeyEvent k){}

	public void moveLeft(){
		play = true;
		barXpos -= 40;
		if(barXpos<=2){
			barXpos =2;
		}
	}

	public void moveRight(){
		play = true;
		barXpos += 40;
		if(barXpos>=380){
			barXpos = 380;
		}
	}

	public void restart(){ 
		play(row,column);	
	}

	public void nextLevel(){
		if(nl == true){
			if(row <=7){
				int nrow = row;
				nrow = nrow +1;
				play(nrow,column);
			}else{
				String options[] = {"Restart lvl 1","Exit"};
 			x = JOptionPane.showOptionDialog(null,"Undefeatable!","Please Choose",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,null);
 		}
 		nl = false;
 		if(x == 0){
 			restart();
		}
 		
 		if(x == 1){
 			System.exit(0);
 		}
	}
		
	}

	public void play(int r1,int r2){
		row = r1;
		if(!play){
		 barXpos = 300;
		 barYpos = 550;

		 ballXpos = 200;
		 ballYpos = 300;

		 ballXdir = 3;
		 ballYdir = 5;
		 score = 0;
		 totalbricks = r1 * r2;
		 blocks = new int[r1][r2];	
		for(int i=0;i<blocks.length;i++){
			for(int j=0;j<blocks[0].length;j++){
				blocks[i][j] = 1;
			}
		}
	}
	}
}