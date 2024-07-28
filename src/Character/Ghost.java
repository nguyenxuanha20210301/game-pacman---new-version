package Character;

//import java.awt.Graphics;

import java.util.Random;
import java.awt.Graphics2D;

import Game.GamePlay;

public class Ghost{
	private int x;
	private int y;
	private int dx;
	private int dy;
	private int[] xx, yy;
//	public static int N_GHOSTS;
	private int speed;
	public static int[] validSpeed = {2, 1, 1, 3};
	
	public static int[] getValidSpeed() {
		return validSpeed;
	}

	public static void setValidSpeed(int[] validSpeed) {
		Ghost.validSpeed = validSpeed;
	}

	Random random;
	
	public Ghost(int x, int y) {
		setPosition(x, y);
		speed = 0;
		random = new Random();
		xx = new int[4];
		yy = new int[4];
	}
	
//	public int getPos() {
//		int yRow = (int)(y / GamePlay.getBlockSize()) * (GamePlay.getnBlockswidth() - 1);
//		int xCol = (int)(x / GamePlay.getBlockSize());
//		if(yRow == 0) {
//			return xCol;
//		}
//		return yRow + xCol + 1;
//	}
//	
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

	public void move(Graphics2D g2d, GamePlay game, Pacman pacc) {
		 int pos;
	        int count;

	        for (int i = 0; i < game.getnGhosts(); i++) {
	            if (x % GamePlay.BLOCK_SIZE == 0 && y % GamePlay.BLOCK_SIZE == 0) {
	                pos = x / GamePlay.BLOCK_SIZE + GamePlay.N_BLOCKSWIDTH * (int) (y / GamePlay.BLOCK_SIZE);

	                count = 0;

	                if ((game.getScreenData()[pos] & 1) == 0 && dx != 1) {
	                    xx[count] = -1;
	                    yy[count] = 0;
	                    count++;
	                }

	                if ((game.getScreenData()[pos] & 2) == 0 && dy != 1) {
	                    xx[count] = 0;
	                    yy[count] = -1;
	                    count++;
	                }

	                if ((game.getScreenData()[pos] & 4) == 0 && dx != -1) {
	                    xx[count] = 1;
	                    yy[count] = 0;
	                    count++;
	                }

	                if ((game.getScreenData()[pos] & 8) == 0 && dy != -1) {
	                    xx[count] = 0;
	                    yy[count] = 1;
	                    count++;
	                }

	                if (count == 0) {

	                    if ((game.getScreenData()[pos] & 15) == 15) {
	                        dx = 0;
	                        dy = 0;
	                    } else {
	                        dx = -dx;
	                        dy = -dy;
	                    }

	                } else {

	                    count = (int) (Math.random() * count);

	                    if (count > 3) {
	                        count = 3;
	                    }

	                    dx = xx[count];
	                    dy = yy[count];
	                }

	            }

	            x = x + (dx * speed);
	            y = y + (dy * speed);
	            g2d.drawImage(GamePlay.getGhost(), x, y, game);

	            if (pacc.getX() > (x - 12) && pacc.getX() < (x + 12)
	                    && pacc.getY() > (y - 12) && pacc.getY() < (y + 12)
	                    && GamePlay.isInGame()) {

	                GamePlay.setDying(true);
	            }
	            
	        }
	}
	
	public void move2(Graphics2D g2d, GamePlay game, Pacman pacc) {

        int pos;
        int count;

        for (int i = 0; i < game.getnGhosts(); i++) {

                if (x % GamePlay.BLOCK_SIZE == 0 && y % GamePlay.BLOCK_SIZE == 0) {
                    pos = x / GamePlay.BLOCK_SIZE + GamePlay.N_BLOCKSWIDTH * (int) (y / GamePlay.BLOCK_SIZE);

                    count = 0;

                        if ((game.getScreenData()[pos] & 1) == 0 && dx != 1 && pacc.getX() < x) {
                            xx[count] = -1;
                            yy[count] = 0;
                            count++;
                        }

                        if ((game.getScreenData()[pos] & 2) == 0 && dy != 1 && pacc.getY() <y) {
                            xx[count] = 0;
                            yy[count] = -1;
                            count++;
                        }

                        if ((game.getScreenData()[pos] & 4) == 0 && dx != -1 && pacc.getX() > x) {
                            xx[count] = 1;
                            yy[count] = 0;
                            count++;
                        }

                        if ((game.getScreenData()[pos] & 8) == 0 && dy != -1 && pacc.getY() > y) {
                            xx[count] = 0;
                            yy[count] = 1;
                            count++;
                        }

                        if (count == 0) {

                            if ((game.getScreenData()[pos] & 15) == 15) {
                                dx = 0;
                                dy = 0;
                            } else {
                                dx = -dx;
                                dy = -dy;
                            }

                        } else {

                            count = (int) (Math.random() * count);

                            if (count > 3) {
                                count = 3;
                            }

                            dx = xx[count];
                            dy = yy[count];

                    }
                }
            x = x + (dx * speed);
            y = y + (dy * speed);
            g2d.drawImage(GamePlay.getGhost(), x, y, game);

            if (pacc.getX() > (x - 12) && pacc.getX() < (x + 12)
                    && pacc.getY() > (y - 12) && pacc.getY() < (y + 12)
                    && GamePlay.isInGame()) {

            	GamePlay.setDying(true);            }

        }
    }

	
//	public void drawGhost(Graphics2D g2d, GamePlay game, int x, int y) {
//    	g2d.drawImage(GamePlay.getGhost(), x, y, game);
//       }
	
//	public boolean isFacingBlock() {
//		int ch = GamePlay.getGamePlay.getgame.getScreenData()()()[this.getPos()];
//		if((dx == 0 && dy == -1 && ((ch & 2) != 0)) || (dx == 0 && dy == 1 && ((ch & 8) != 0))
//		   || (dx == 1 && dy == 0 && (ch & 4) != 0) || (dx == -1 && dy == 0 && (ch & 1) != 0)) {
//			return true;
//		}
//		return false;
//	}

//	public void paintCharacter(Graphics g, GamePlay game) {
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.drawImage(GamePlay.getGhost(), x + 1, y + 1, game);
//	}

	public int getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setSpeed(int speed) {
		// TODO Auto-generated method stub
		this.speed = speed;
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

	public int[] getXx() {
		return xx;
	}

	public void setXx(int[] xx) {
		this.xx = xx;
	}

	public int[] getYy() {
		return yy;
	}

	public void setYy(int[] yy) {
		this.yy = yy;
	}

}
