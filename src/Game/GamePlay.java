package Game;

import javax.swing.ImageIcon;

import javax.swing.JPanel;
import java.awt.*;
import Character.Ghost;
import Character.Pacman;
//import Game.Model.TAdapter;
import Maze.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import javax.swing.Timer;


public class GamePlay extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int BLOCK_SIZE = 24;
	public static final int N_BLOCKSWIDTH = 25;
    public static final int N_BLOCKSHEIGHT = 15;
    public static final int SCREEN_SIZEWIDTH = BLOCK_SIZE * N_BLOCKSWIDTH;
    public static final int SCREEN_SIZEHEIGHT = BLOCK_SIZE * N_BLOCKSHEIGHT;
    public static final int N_BLOCKS = N_BLOCKSWIDTH * N_BLOCKSHEIGHT;
    private short[] screenData;
    private static Image ghost, heart, portal, wall, pacman, ghost1;
    private static Image kitten, speedup, freeze, diamond, heart1, crown, pac2, sonic;
    private Dimension d;
    private Pacman pac;
    private Maze maze;
    private Ghost[] ghosts;
    public static boolean inGame = false;
    private Timer timer;
    public static boolean dying = false;
    private boolean isRS = false;
    private int nGhosts;
    private int currentSpeed;
    public int getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(int currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	private short[] tempArr;
    private int currentLevel;
    private int nLevel;
    private final Font smallFont = new Font("Times New Roman", Font.BOLD, 14);
    
    
    public GamePlay() {
    	loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
    }
    
    public void initGame() {
    	nGhosts = 2;
    	isRS = false;
    	currentLevel = 1;
    	tempArr = Maze.getLevelData1();
    	nLevel = 3;
    	pac.setLives(5);
    	pac.setScore(0);
    	initLevel(tempArr);
//    	currentSpeed = 4;
    	initGhosts();
    }
    
    public void initVariables() {
    	setD(new Dimension(400, 400));
    	pac = new Pacman();
    	maze = new Maze();
    	tempArr = new short[N_BLOCKS];
        screenData = new short[N_BLOCKSWIDTH * N_BLOCKSHEIGHT];
//        ghosts = new Ghost[N_GHOSTS];
//        for(int i = 0; i < N_GHOSTS; i++) {
//        	ghosts[i] = new Ghost(12 * BLOCK_SIZE, 6 * BLOCK_SIZE);
//        }
    	
    	timer = new Timer(20, this);
        timer.start();
    }
	
    public void initGhosts() {
    	ghosts = new Ghost[nGhosts];
        for(int i = 0; i < nGhosts; i++) {
        	ghosts[i] = new Ghost(12 * BLOCK_SIZE, 6 * BLOCK_SIZE);
        	ghosts[i].setSpeed(Ghost.getValidSpeed()[(int) (Math.random() * 3)]);
        }
    }
    public void nextLevel(Graphics2D g2d) {
    	currentLevel += 1;
		pac.setLives(pac.getLives() + 1);
		nGhosts += 1;
		initGhosts();
		maze.setCurrent_count_portal(0);
    	if(currentLevel == 2) {
    		pac.setSpeed(4);
    		initGhosts();
    		maze.setCurrent_count_portal(0);
    		maze.setCount_portal(3);
    		tempArr = Maze.getLevelData2();
    		initLevel(Maze.getLevelData2());
    		paintComponent(g2d);
    	}
    	if(currentLevel == 3) {
    		pac.setSpeed(4);
    		initGhosts();
    		maze.setCurrent_count_portal(0);
    		maze.setCount_portal(4);
    		tempArr = Maze.getLevelData3();
    		initLevel(Maze.getLevelData3());
    		paintComponent(g2d);
    	}
    }
    
    public void initLevel(short[] levelData) {

        int i;
        for (i = 0; i < N_BLOCKSWIDTH * N_BLOCKSHEIGHT; i++) {
            screenData[i] = levelData[i];
        }
//        screenData = Maze.getLevelData1();
//        setScreenData(Maze.getLevelData1());
        continueLevel();
    }
    
    public void drawScore(Graphics2D g) {
        g.setFont(smallFont);
        g.setColor(new Color(255, 0, 70));
        String s = "Score: " + pac.getScore();
        g.drawString(s, SCREEN_SIZEWIDTH / 2 + 96, SCREEN_SIZEHEIGHT + 16);

        for (int i = 0; i < pac.getLives(); i++) {
            g.drawImage(heart, i * 28 + 8, SCREEN_SIZEHEIGHT + 1, this);
        }
    }
    
    public void drawLevel(Graphics2D g) {
    	g.setFont(smallFont);
        g.setColor(new Color(255, 0, 70));
        String s = "Level: " + currentLevel;
        g.drawString(s, SCREEN_SIZEWIDTH / 2 + 200, SCREEN_SIZEHEIGHT + 16);
    }
    
    public void showIntroScreen(Graphics2D g2d) {
    	 
    	String start = "Press SPACE to start";
        g2d.setColor(Color.yellow);
        g2d.drawString(start, (SCREEN_SIZEHEIGHT)/4+145, 85);
    }
    public void showMessageNextLevel(Graphics2D g2d, int numLevel) {
    	String next = "Level " + numLevel;
    	g2d.setColor(Color.yellow);
    	g2d.drawString(next, (SCREEN_SIZEHEIGHT)/4+170, 140);
    }
    private void showMessageEnd(Graphics2D g2d) {
    	String end = "You won. Press 'Enter' to restart.";
    	g2d.setColor(Color.yellow);
    	g2d.drawString(end, (SCREEN_SIZEHEIGHT)/4+130, 200);
    }
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 620, 420);
        
        maze.drawMaze(g2d, this);
        drawScore(g2d);
        drawLevel(g2d);
        
        if (inGame) {
        	if(currentLevel <= nLevel)playGame(g2d);
        } else {
            if(isRS == false){
            	showIntroScreen(g2d);
            	showMessageNextLevel(g2d, currentLevel);
            } else {
            	showMessageEnd(g2d);
            }
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }
    
    public void playGame(Graphics2D g2d) {
    	if (dying) {

            death();

        } else {

            pac.move(this);
            pac.paintCharacter(g2d, this);
            for(int i = 0; i < nGhosts; i++) {
            	if(i % 2 == 0) {
            		ghosts[i].move(g2d, this, pac);
            	} else {
            		ghosts[i].move2(g2d, this, pac);
            	}
            }
            maze.check(g2d, pac, this);
        }
    }
    
    public int getnGhosts() {
		return nGhosts;
	}

	public void setnGhosts(int nGhosts) {
		this.nGhosts = nGhosts;
	}

	public void death() {

    	pac.setLives(pac.getLives() - 1);

        if (pac.getLives() == 0) {
            inGame = false;
        }

        continueLevel();
    }
    
    public void continueLevel() {
//    	int dx = 1;
//        int random;

//        for (int i = 0; i < N_GHOSTS; i++) {
//
////            ghost_y[i] = 6 * BLOCK_SIZE; //start position
////            ghost_x[i] = 12 * BLOCK_SIZE;
////            ghost_dy[i] = 0;
////            ghost_dx[i] = dx;
//            dx = -dx;
//            random = (int) (Math.random() * (currentSpeed + 1));
//
//            if (random > currentSpeed) {
//                random = currentSpeed;
//            }
//
////            ghostSpeed[i] = validSpeeds[random];
//        }
    	initGhosts();
        //start position
        pac.setPosition(12 * BLOCK_SIZE, 12 * BLOCK_SIZE);
        
        pac.setDx(0);
        pac.setDy(0);
  
        pac.setReqDx(0);
        pac.setReqDy(0);
        pac.setSpeed(4);
        dying = false;
    }
    public void reStart() {
    	isRS = false;
    	inGame = true;
    	initGame();
    }
    class TAdapter extends KeyAdapter{
    	public void keyPressed(KeyEvent e) {
    		int key = e.getKeyCode();
    		if(inGame) {
    			if (key == KeyEvent.VK_LEFT) {
                    pac.setReqDx(-1);
                    pac.setReqDy(0);
                } else if (key == KeyEvent.VK_RIGHT) {
                	pac.setReqDx(1);
                	pac.setReqDy(0);
                } else if (key == KeyEvent.VK_UP) {
                	pac.setReqDx(0);
                    pac.setReqDy(-1);
                } else if (key == KeyEvent.VK_DOWN) {
                	pac.setReqDx(0);
                    pac.setReqDy(1);
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_H) {
                	pac.setLives(pac.getLives() + 1);
                	if(pac.getLives() > 14){
                		pac.setLives(14);
                	}
                } 
            } else {
                if (key == KeyEvent.VK_SPACE && isRS == false) {
                    inGame = true;
                    initGame();
                }
                if(key == KeyEvent.VK_ENTER && isRS == true) {
                	reStart();
                }
            }
    	}
    }
    
//    public void initLevel(int[] levelData) {
//
//        int i;
//        for (i = 0; i < N_BLOCKSWIDTH * N_BLOCKSHEIGHT; i++) {
//            screenData[i] = levelData[i];
//        }
//
//        continueLevel();
//    }
    
	public void loadImages() {
//		down = new ImageIcon(this.getClass().getResource("/images/pacdown.gif")).getImage();
//		up = new ImageIcon(this.getClass().getResource("/images/pacup.gif")).getImage();
//		left = new ImageIcon(this.getClass().getResource("/images/pacleft.gif")).getImage();
//		right = new ImageIcon(this.getClass().getResource("/images/pacright.gif")).getImage();
		setPacman(new ImageIcon(this.getClass().getResource("/images/pacman.gif")).getImage());
		ghost = new ImageIcon(this.getClass().getResource("/images/pacghost.gif")).getImage();
		heart = new ImageIcon(this.getClass().getResource("/images/heart.png")).getImage();
		wall = new ImageIcon(this.getClass().getResource("/images/wall.png")).getImage();
		portal = new ImageIcon(this.getClass().getResource("/images/portal.gif")).getImage();
		setKitten(new ImageIcon(this.getClass().getResource("/images/kitten1.gif")).getImage());
		setSpeedup(new ImageIcon(this.getClass().getResource("/images/shitup.gif")).getImage());
		setFreeze(new ImageIcon(this.getClass().getResource("/images/freeze.gif")).getImage());
		
		setHeart1(new ImageIcon(this.getClass().getResource("/images/heart1.gif")).getImage());
		setDiamond(new ImageIcon(this.getClass().getResource("/images/diamond.gif")).getImage());
		setCrown(new ImageIcon(this.getClass().getResource("/images/crown.gif")).getImage());
		setPac2(new ImageIcon(this.getClass().getResource("/images/pac3.gif")).getImage());
		setSonic(new ImageIcon(this.getClass().getResource("/images/sonic.gif")).getImage());
		setGhost1(new ImageIcon(this.getClass().getResource("/images/ghost2.gif")).getImage());
	}
	public Dimension getD() {
		return d;
	}
	public void setD(Dimension d) {
		this.d = d;
	}
	public static int getBlockSize() {
		return BLOCK_SIZE;
	}

	public static int getnBlockswidth() {
		return N_BLOCKSWIDTH;
	}

	public static int getnBlocksheight() {
		return N_BLOCKSHEIGHT;
	}

	public static int getScreenSizewidth() {
		return SCREEN_SIZEWIDTH;
	}

	public static int getScreenSizeheight() {
		return SCREEN_SIZEHEIGHT;
	}

	public short[] getScreenData() {
		return screenData;
	}

	public void setScreenData(short[] screenData) {
		this.screenData = screenData;
	}
	
	public void setIndexScreenData(int index, short newValue) {
		this.screenData[index] = newValue;
	}
	public void setIndexTempArr(int index, short newValue) {
		this.tempArr[index] = newValue;
	}
	public static int getnBlocks() {
		return N_BLOCKS;
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
	public static Image getPacman() {
		return pacman;
	}
	public static void setPacman(Image pacman) {
		GamePlay.pacman = pacman;
	}
	public static Image getGhost() {
		return ghost;
	}
	public static void setGhost(Image ghost) {
		GamePlay.ghost = ghost;
	}
	public static Image getHeart() {
		return heart;
	}
	public static void setHeart(Image heart) {
		GamePlay.heart = heart;
	}
	public static Image getPortal() {
		return portal;
	}
	public static void setPortal(Image portal) {
		GamePlay.portal = portal;
	}
	public static Image getWall() {
		return wall;
	}
	public static void setWall(Image wall) {
		GamePlay.wall = wall;
	}
	public static boolean isInGame() {
		return inGame;
	}
	public static void setInGame(boolean inGame) {
		GamePlay.inGame = inGame;
	}
	public Ghost[] getGhosts() {
		return ghosts;
	}
	public void setGhosts(Ghost[] ghosts) {
		this.ghosts = ghosts;
	}
	public static boolean isDying() {
		return dying;
	}
	public static void setDying(boolean dying) {
		GamePlay.dying = dying;
	}

	public short[] getTempArr() {
		return tempArr;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void setTempArr(short[] tempArr) {
		this.tempArr = tempArr;
	}

	public static Image getKitten() {
		return kitten;
	}

	public static void setKitten(Image kitten) {
		GamePlay.kitten = kitten;
	}

	public static Image getSpeedup() {
		return speedup;
	}

	public static void setSpeedup(Image speedup) {
		GamePlay.speedup = speedup;
	}

	public static Image getFreeze() {
		return freeze;
	}

	public static void setFreeze(Image freeze) {
		GamePlay.freeze = freeze;
	}

	public static Image getDiamond() {
		return diamond;
	}

	public static void setDiamond(Image diamond) {
		GamePlay.diamond = diamond;
	}

	public static Image getHeart1() {
		return heart1;
	}

	public static void setHeart1(Image heart1) {
		GamePlay.heart1 = heart1;
	}

	public static Image getCrown() {
		return crown;
	}

	public static void setCrown(Image crown) {
		GamePlay.crown = crown;
	}

	public static Image getPac2() {
		return pac2;
	}

	public static void setPac2(Image pac2) {
		GamePlay.pac2 = pac2;
	}

	public static Image getSonic() {
		return sonic;
	}

	public static void setSonic(Image sonic) {
		GamePlay.sonic = sonic;
	}

	public static Image getGhost1() {
		return ghost1;
	}

	public static void setGhost1(Image ghost1) {
		GamePlay.ghost1 = ghost1;
	}
}
