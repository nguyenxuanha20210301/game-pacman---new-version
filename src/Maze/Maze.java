package Maze;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import Character.Pacman;
import Game.*;
public class Maze {
    private static final int DOT_1 = 266; //266
    private static final int DOT_2 = 496 - 266; //496 - 266
    private static final int DOT_3 = 737 - 496;
	public int getCount_portal() {
		return count_portal;
	}

	public void setCount_portal(int count_portal) {
		this.count_portal = count_portal;
	}

	private static int nPortal;
	private static int[] portal_x, portal_y, portal_pos;
	private static Random random_portal;
	private final int MAX_PORTAL = 10;
	private int current_count_portal;
	private int count_portal;
	
	public Maze() {
		random_portal = new Random();
		portal_x = new int[MAX_PORTAL];
		portal_y = new int[MAX_PORTAL];
		portal_pos = new int[MAX_PORTAL];
		count_portal = 2;
	}
	
	public void check(Graphics2D g2d, Pacman pac, GamePlay game) {
		if(pac.getScore() == DOT_1|| pac.getScore() == DOT_1 + DOT_2) {
    		
    		game.nextLevel(g2d);
    	}
    	if(pac.getScore() == DOT_1 + DOT_2 + DOT_3) {
    		//showMessageEnd(g2d);
    		GamePlay.setInGame(false);
    		
    	}
	}
	public static Random getRandom_portal() {
		return random_portal;
	}
	public static void setRandom_portal(Random random_portal) {
		Maze.random_portal = random_portal;
	}
	public static int getnPortal() {
		return nPortal;
	}
	public static void setnPortal(int nPortal) {
		Maze.nPortal = nPortal;
	}
	public static int[] getPortal_x() {
		return portal_x;
	}
	public static void setPortal_x(int[] portal_x) {
		Maze.portal_x = portal_x;
	}
	public static int[] getPortal_y() {
		return portal_y;
	}
	public static void setPortal_y(int[] portal_y) {
		Maze.portal_y = portal_y;
	}
	public static int[] getPortal_pos() {
		return portal_pos;
	}
	public static void setPortal_pos(int[] portal_pos) {
		Maze.portal_pos = portal_pos;
	}
	public static short[] getLevelData1() {
		return levelData1;
	}
	public static void setLevelData1(short[] levelData1) {
		Maze.levelData1 = levelData1;
	}
	public static short[] getLevelData2() {
		return levelData2;
	}
	public static void setLevelData2(short[] levelData2) {
		Maze.levelData2 = levelData2;
	}
	public static short[] getLevelData3() {
		return levelData3;
	}
	public static void setLevelData3(short[] levelData3) {
		Maze.levelData3 = levelData3;
	}
	public void drawMaze(Graphics2D g2d, GamePlay game) {
		nPortal = 0;
        int i = 0;
        int x, y;

        for (y = 0; y < GamePlay.SCREEN_SIZEHEIGHT; y += GamePlay.BLOCK_SIZE) {
            for (x = 0; x < GamePlay.SCREEN_SIZEWIDTH; x += GamePlay.BLOCK_SIZE) {

                g2d.setColor(new Color(102,51,0)); // set mau xanh (blue)
                g2d.setStroke(new BasicStroke(5));
                
                if ((game.getTempArr()[i] == 0)) {
                    g2d.drawImage(GamePlay.getWall(), x, y, null);
                	// g2d.fillRect(x, y, GamePlay.BLOCK_SIZE, GamePlay.BLOCK_SIZE); // ve hinh chu nhat do mau (blue)
                };
                if((game.getScreenData()[i] == 32)) {
                	g2d.drawImage(GamePlay.getHeart1(), x, y, null);
                }
                if((game.getScreenData()[i] == 128)) {
//                	g2d.drawImage(GamePlay.getSpeedup(), x, y, null);
                	if(i % 2 == 0) {
                		g2d.drawImage(GamePlay.getDiamond(), x, y, null);
                	} else {
                		g2d.drawImage(GamePlay.getCrown(), x, y, null);
                	}
                }
                if((game.getScreenData()[i] == 256)) {
                	g2d.drawImage(GamePlay.getFreeze(), x, y, null);
                }
                if (game.getScreenData()[i]==16) {
                    if(current_count_portal<this.count_portal) {
	                    Random rdRandom = new Random();
	                    int rd = rdRandom.nextInt(100);
	                    if(rd<30&&current_count_portal==0) {
	                    	game.setIndexTempArr(i, (short)64);
	                    	game.setIndexScreenData(i, (short) 64);
	                    	current_count_portal++;
	                    }else {
	                    	if(rd<3&&Math.abs(portal_pos[current_count_portal-1]-i)>=24) {
	                    		game.setIndexTempArr(i, (short)64);
		                    	game.setIndexScreenData(i, (short) 64);
		                    	current_count_portal++;
	                    	}
	                    }
                    }
                }
                if((game.getTempArr()[i] & 64)!=0) {
                	portal_x[nPortal] = x;
                	portal_y[nPortal] = y;
                	portal_pos[nPortal] = x / GamePlay.BLOCK_SIZE + GamePlay.N_BLOCKSWIDTH * (int) (y / GamePlay.BLOCK_SIZE);
                	nPortal++;
                	g2d.drawImage(GamePlay.getPortal(), x, y, null);
                }
                
                if ((game.getScreenData()[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + GamePlay.BLOCK_SIZE - 1);
                }
                
                if ((game.getScreenData()[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + GamePlay.BLOCK_SIZE - 1, y);
                }

                if ((game.getScreenData()[i] & 4) != 0) {
                    g2d.drawLine(x + GamePlay.BLOCK_SIZE - 1, y, x + GamePlay.BLOCK_SIZE - 1,
                            y + GamePlay.BLOCK_SIZE - 1);
                }

                if ((game.getScreenData()[i] & 8) != 0) {
                    g2d.drawLine(x, y + GamePlay.BLOCK_SIZE - 1, x + GamePlay.BLOCK_SIZE - 1,
                            y + GamePlay.BLOCK_SIZE - 1);
                }

                if ((game.getScreenData()[i] & 16) != 0) {
                    g2d.setColor(new Color(255,255,255));
                    g2d.fillOval(x + 10, y + 10, 6, 6);
               }

                i++;
            }
        }
    }
	
	public int getCurrent_count_portal() {
		return current_count_portal;
	}

	public void setCurrent_count_portal(int current_count_portal) {
		this.current_count_portal = current_count_portal;
	}

	private static short levelData1[] = {
			19, 26, 26, 26, 18, 18, 26, 26, 26, 26, 26, 18, 18, 18, 26, 26, 26, 26, 26, 18, 18, 26, 26, 26, 22,
            21,  0,  0,  0, 17, 20,  0,  0,  0,  0,  0, 17, 16, 20,  0,  0,  0,  0,  0, 17, 20,  0,  0, 0, 21,
            17, 26, 26, 26, 16, 128, 26, 18, 26, 26, 26, 24, 24, 24, 26, 26, 26, 18, 26, 32, 16, 26, 26, 26, 20,
            21,  0,  0, 0, 17, 20, 0, 21, 27, 26, 26, 18, 18, 18, 26, 26, 30, 21, 0, 17, 20, 0, 0, 0, 21,
            17, 22,  0, 19, 24, 20, 0, 25, 26, 18, 22, 17, 16, 20, 19, 18, 26, 28, 0, 17, 24, 22, 0, 19, 20,
            17, 20,  0, 21,  0, 21, 0,  0,  0, 17, 20, 25, 256, 28, 17, 20, 0, 0, 0, 21, 0, 21, 0, 17, 20,
            17, 16, 26, 28,  0, 21, 0, 19, 18, 16, 16, 18, 16, 18, 32, 16, 18, 22, 0, 21, 0, 25, 26, 16, 20,
            17, 20,  0,  0,  0, 21, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21, 0, 0, 0, 17, 20,
            25, 24, 26, 18, 18, 16, 18, 16, 16, 24, 24, 24, 24, 24, 24, 24, 16, 16, 18, 16, 18, 18, 26, 24, 28,
             0,  0,  0, 17, 16, 16, 24, 24, 20, 0, 0, 0, 0, 0, 0, 0, 17, 24, 24, 128, 16, 20, 0, 0, 0,
            27, 22, 0, 17, 24, 20, 0, 0, 17, 18, 22, 0, 0, 0, 19, 18, 20, 0, 0, 17, 24, 20, 0, 19, 30,
            0, 21, 0, 21, 0, 21, 0, 0, 17, 16, 16, 22, 0, 19, 16, 16, 20, 0, 0, 21, 0, 21, 0, 21, 0,
            19, 24, 26, 28,  0, 25, 26, 18, 32, 24, 24, 16, 18, 16, 24, 24, 16, 18, 26, 28, 0, 25, 26, 24, 22,
            21,  0,  0,  0,  0,  0,  0, 17, 20,  0,  0, 17, 16, 20,  0,  0, 17, 20, 0, 0, 0, 0, 0, 0, 21,
            25, 26, 26, 26, 26, 26, 26, 24, 24, 26, 26, 24, 24, 24, 26, 26, 24, 24, 26, 26, 26, 26, 26, 26, 28
    };
	private static short levelData2[] = {
			19,	26,	26,	26,	18,	22,	0,	0,	0,	0,	19,	18,	18,	18,	22,	0,	0,	0,	0,	19,	18,	26,	26,	26,	22,
    		21,	0,	0,	0,	17,	32,	18,	18,	18,	26,	24,	24,	24,	24,	24,	26,	18,	18,	18,	16,	20,	0,	0,	0,	21,
    		21,	0,	19,	18,	24,	24,	24,	16,	20,	0,	0,	0,	0,	0,	0,	0,	17,	256,	24,	24,	24,	18,	22,	0,	21,
    		21,	0,	17,	20,	0,	0,	0,	17,	256,	18,	22,	0,	0,	0,	19,	18,	32,	20,	0,	0,	0,	17,	20,	0,	21,
    		17,	18,	24,	24,	18,	22,	0,	17,	24,	128,	16,	22,	0,	19,	16,	16,	24,	20,	0,	19,	18,	24,	24,	18,	20,
    		25,	20,	0,	0,	25,	20,	0,	21,	0,	25,	16,	24,	26,	24,	16,	28,	0,	21,	0,	17,	28,	0,	0,	17,	28,
    		0,	21,	0,	0,	0,	17,	18,	20,	0,	0,	21,	19,	18,	22,	21,	0,	0,	17,	18,	20,	0,	0,	0,	21,	0,
    		0,	21,	0,	0,	43,	128,	16,	20,	0,	0,	21,	25,	16,	28,	21,	0,	0,	17,	16,	128,	46,	0,	0,	21,	0,
    		0,	21,	0,	0,	0,	17,	24,	20,	0,	0,	17,	18,	16,	18,	20,	0,	0,	17,	24,	20,	0,	0,	0,	21,	0,
    		19,	20,	0,	0,	19,	20,	0,	21,	0,	19,	16,	24,	24,	24,	16,	22,	0,	21,	0,	17,	22,	0,	0,	17,	22,
    		17,	24,	18,	18,	24,	28,	0,	17,	18,	16,	28,	0,	0,	0,	25,	32,	18,	20,	0,	25,	24,	18,	18,	24,	20,
    		21,	0,	17,	20,	0,	0,	0,	17,	16,	28,	0,	0,	0,	0,	0,	25,	16,	20,	0,	0,	0,	17,	20,	0,	21,
    		21,	0,	25,	24,	18,	18,	18,	128,	20,	0,	0,	0,	23,	0,	0,	0,	17,	256,	18,	18,	18,	24,	28,	0,	21,
    		21,	 0,	 0,	 0,	17,	32,	24,	24,	24,	26,	18,	18,	32,	18,	18,	26,	24,	24,	24,	128,	20,	0,	0,	0,	21,
    		25,	26,	26,	26,	24,	28,	0,	0,	0,	0,	25,	24,	24,	24,	28,	0,	0,	0,	0,	25,	24,	26,	26,	26,	28
    };
	private static short levelData3[] = {
			0,	0,	0,	19,	18,	26,	26,	26,	18,	18,	26,	26,	26,	26,	26,	18,	18,	26,	26,	26,	18,	22,	0,	0,	0,
    		19,	18,	26,	24,	20,	0,	0,	0,	17,	20,	0,	0,	0,	0,	0,	17,	20,	0,	0,	0,	17,	24,	26,	18,	22,
    		25,	20,	0,	0,	21,	0,	19,	26,	24,	256,	22,	0,	0,	0,	19,	16,	24,	26,	22,	0,	21,	0,	0,	17,	28,
    		0,	21,	0,	0,	21,	0,	21,	0,	0,	17,	24,	22,	0,	19,	24,	20,	0,	0,	21,	0,	21,	0,	0,	21,	0,
    		0,	25,	26,	18,	32,	18,	20,	0,	0,	21,	23,	17,	18,	20,	23,	21,	0,	0,	17,	18,	256,	18,	26,	28,	0,
    		0,	0,	0,	17,	128,	24,	16,	18,	26,	28,	21,	25,	16,	28,	21,	25,	26,	18,	16,	24,	16,	20,	0,	0,	0,
    		19,	26,	26,	24,	20,	0,	17,	20,	27,	26,	24,	18,	32,	18,	24,	26,	30,	17,	20,	0,	17,	24,	26,	26,	22,
    		21,	0,	0,	0,	21,	0,	17,	16,	18,	18,	22,	25,	24,	28,	19,	18,	18,	16,	20,	0,	21,	0,	0,	0,	21,
    		25,	26,	26,	18,	20,	0,	17,	16,	24,	24,	24,	26,	26,	26,	24,	24,	24,	128,	20,	0,	17,	18,	26,	26,	28,
    		0,	0,	0,	17,	20,	0,	17,	20,	0,	0,	0,	0,	0,	0,	0,	0,	0,	17,	20,	0,	17,	20,	0,	0,	0,
    		0,	19,	26,	24,	256,	18,	16,	16,	26,	18,	18,	26,	58,	26,	18,	18,	26,	16,	16,	18,	128,	24,	26,	22,	0,
    		0,	21,	0,	0,	17,	24,	16,	20,	0,	17,	20,	0,	0,	0,	17,	20,	0,	17,	16,	24,	20,	0,	0,	21,	0,
    		27,	20,	0,	0,	21,	0,	17,	20,	0,	25,	24,	18,	26,	18,	24,	28,	0,	17,	20,	0,	21,	0,	0,	17,	30,
    		0,	25,	18,	26,	28,	0,	17,	20,	0,	0,	0,	21,	0,	21,	0,	0,	0,	17,	20,	0,	25,	26,	18,	28,	0,
    		0,	0,	29,	0,	0,	0,	25,	24,	26,	26,	26,	24,	26,	24,	26,	26,	26,	24,	28,	0,	0,	0,	29,	0,	0


    };
	public static int getDots(int[] levelData) {
		int count = 0;
		for(int i = 0; i < GamePlay.getnBlocksheight() * GamePlay.getnBlockswidth(); i++) {
			if(levelData[i] != 0 && levelData[i] != 64) {
				count++;
			}
		}
		return count;
	}
}
