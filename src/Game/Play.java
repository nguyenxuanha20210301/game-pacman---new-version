package Game;

import javax.swing.JFrame;

public class Play extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Play() {
		add(new GamePlay()); 
	}
	public static void main (String[] args) {
		Play p = new Play();
		p.setVisible(true);
		p.setTitle("Pacman");
		p.setSize(24 * 25 + 15, 24 * 15 + 64); 
		p.setLocationRelativeTo(null);
		p.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
