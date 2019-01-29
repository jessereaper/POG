package pong;

import javax.swing.JFrame;

//this file is to execute the game makes window size ext.
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frm = new JFrame();
		frm.setTitle("Pong");
		Pong g = new Pong();
		frm.setContentPane(g);
		frm.setSize(1000, 700);
		frm.setResizable(false);
		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
