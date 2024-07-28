package Character;

import java.awt.Graphics2D;

//import javax.swing.Timer;

import Maze.*;
import java.util.Timer;
import java.util.TimerTask;

import Game.*;
public class Pacman{
	private int x;
	private int y;
	private int dx;
//	private int ch;
	private int dy;
	private int speed;
	private int reqDx;
	private int reqDy;
	private int lives;
	private int score;
	private int level;
	
	public Pacman() {
		this.score = 0;
		this.level = 0;
		this.speed = 4;
		this.lives = 4;
		this.dx = 0;
		this.dy = 0;
	}
	public Pacman(int x, int y) {
		this();
		this.x = x;
		this.y = y;
	}
	public void move(GamePlay game){

        int pos;
        int ch;

        if (x % GamePlay.BLOCK_SIZE == 0 && y % GamePlay.BLOCK_SIZE == 0) {
            pos = x / GamePlay.BLOCK_SIZE + GamePlay.N_BLOCKSWIDTH * (int) (y / GamePlay.BLOCK_SIZE);
            ch = game.getScreenData()[pos];

            if((ch & 64)!=0){
            	if(Maze.getPortal_pos()[0]==pos) {
            		int random = Maze.getRandom_portal().nextInt(Maze.getnPortal() - 1) + 1;
            		x = Maze.getPortal_x()[random];
            		y = Maze.getPortal_y()[random];
            	} else {
            		x = Maze.getPortal_x()[0];
            		y = Maze.getPortal_y()[0];
            	}
            }
            if((ch & 128) != 0) {
            	game.setIndexScreenData(pos, (short)(ch & 127));
            	for(int i = speed + 1; i < 13; i++) {
            		if(24 % i == 0) {
            			speed = i;
            			break;
            		}
            	}
            }
            if((ch & 256) != 0) {
            	game.setIndexScreenData(pos, (short)(ch & 255));
            	for(int i = 0; i < game.getnGhosts(); i ++) {
          		
            		int c = i;

            		game.getGhosts()[i].setSpeed(0);
            	    TimerTask timerTask = new TimerTask() {
            	            @Override
            	            public void run() {
            	            		Ghost g = game.getGhosts()[c];
       	            		
            	            		g.setSpeed(Ghost.getValidSpeed()[(int) (Math.random() * 3)]);


            	            }
            	     };
            	     long delay = 4000L;
            	     Timer timer = new Timer("Timer");
            	     timer.schedule(timerTask, delay);
            	    
            		

            	}
            	
            }
            if((ch & 32) != 0) {
            	game.setIndexScreenData(pos, (short)(ch & 31));
            	lives++;
            	if(lives > 14) {
            		lives = 14;
            	}
            }
            if ((ch & 16) != 0) {
            	game.setIndexScreenData(pos, (short)(ch & 15));
            	score++;
            }
            if (reqDx != 0 || reqDy != 0) {
                if (!((reqDx == -1 && reqDy == 0 && (ch & 1) != 0)
                        || (reqDx == 1 && reqDy == 0 && (ch & 4) != 0)
                        || (reqDx == 0 && reqDy == -1 && (ch & 2) != 0)
                        || (reqDx == 0 && reqDy == 1 && (ch & 8) != 0))) {
                    dx = reqDx;
                    dy = reqDy;
                }
            }

            // Check for standstill
            if ((dx == -1 && dy == 0 && (ch & 1) != 0)
                    || (dx == 1 && dy == 0 && (ch & 4) != 0)
                    || (dx == 0 && dy == -1 && (ch & 2) != 0)
                    || (dx == 0 && dy == 1 && (ch & 8) != 0)) {
                dx = 0;
                dy = 0;
            }
        } 
        x = x + speed * dx;
        y = y + speed * dy;
    }
	
		
//	public void move() {
//        if (x % GamePlay.GamePlay.BLOCK_SIZE == 0 && y % GamePlay.GamePlay.BLOCK_SIZE == 0) {
//            ch = GamePlay.getGamePlay.getScreenData()()[getPos()];
//            if(isFacingBlock()) {
//            	dx = 0;
//            	dy = 0;
//            } else {
//            	dx = reqDx;
//            	dy = reqDy;
//            }
//        }
//        x = x + speed * dx;
//        y = y + speed * dy;
//	}
//	public void movePac() {
//		if(isEat()) {
//			
//		}
//	}
//	public boolean isFacingBlock() {
//		ch  = GamePlay.getGamePlay.getScreenData()()[getPos()];
//		if((isUp() && (ch & 2) != 0) || (isDown() && (ch & 8) != 0)
//	 || (isRight() && (ch & 4) != 0) || (isLeft() && (ch & 1) != 0)) {
//			return true;
//		}
//		return false;
//	}
//	public boolean isEat() {
//		int ch = GamePlay.getGamePlay.getScreenData()()[this.getPos()];
//		if(ch != 0) {
//			return true;
//		}
//		return false;
//	}
//	public int getPos() {
//		int pos;
//		pos = x / GamePlay.GamePlay.BLOCK_SIZE + GamePlay.N_BLOCKSWIDTH * (int) (y / GamePlay.GamePlay.BLOCK_SIZE);
//		return pos;
//	}
//	public boolean isUp() {
//		if(reqDx == 0 && reqDy== -1) {
//			return true;
//		}
//		return false;
//	}
//	public boolean isDown() {
//		if(reqDx == 0 && reqDy == 1) {
//			return true;
//		}
//		return false;
//	}
//	public boolean isLeft() {
//		if(reqDx == -1 && reqDy == 0) {
//			return true;
//		}
//		return false;
//	}
//	public boolean isRight() {
//		if(reqDx == 1 && reqDy == 0) {
//			return true;
//		}
//		return false;
//	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
		
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		
	}

	public void paintCharacter(Graphics2D g2d, GamePlay game) {
		if(lives >= 14 && speed <= 10) {
			g2d.drawImage(GamePlay.getPac2(), x + 1, y + 1, game);
		} else if(speed > 10) {
			g2d.drawImage(GamePlay.getSonic(), x + 1, y + 1, game);
		} else {
			g2d.drawImage(GamePlay.getPacman(), x + 1, y + 1, game);
		}
	}
	
	
	public int getSpeed() {
		
		return 0;
	}
	
	public void setSpeed(int speed) {
		// TODO Auto-generated method stub
		this.speed = speed;
	}
	
	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getReqDx() {
		return reqDx;
	}
	public void setReqDx(int reqDx) {
		this.reqDx = reqDx;
	}
	public int getReqDy() {
		return reqDy;
	}
	public void setReqDy(int reqDy) {
		this.reqDy = reqDy;
	}
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
	
}
