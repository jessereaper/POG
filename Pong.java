package pong;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Pong extends JPanel implements KeyListener, ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int height, width;
	private Timer t = new Timer(5, this);
	private boolean first;
	
	private HashSet<String> keys = new HashSet<String>();
	
	// lol as you can imagine this is for the score
	private int scoreTop, scoreBottom;

	// ball configurations
	//vel is for the speed of the ball its x speed and its y speed
	private double ballX, ballY, velX = 2, velY = 2, ballSize = 10;
	
	// paddle configurations 
	private final int SPEED = 2;
	private int padH = 10, padW = 70;
	private int inset = 10;
	private int bottomPadX, topPadX;

	// this is for two things first for teh app to listen for keys aka be looking for a key to be used
	//second to start the app with the delay of 2 second so the player can get ready
	public Pong() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		first = true;
		t.setInitialDelay(2000);
		t.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		height = getHeight();
		width = getWidth();

		// this is to place the starting positions of the ball an3d paddles 
		if (first) {
			bottomPadX = width / 2 - padW / 2;
			topPadX = bottomPadX;
			ballX = width / 2 - ballSize / 2;
			ballY = height / 2 - ballSize / 2;
			first = false;
		}
		
		// bottom pad
		Rectangle2D bottomPad = new Rectangle(bottomPadX, height - padH - inset, padW, padH);
		g2d.fill(bottomPad);
		
		// top pad
		Rectangle2D topPad = new Rectangle(topPadX, inset, padW, padH);
		g2d.fill(topPad);
		
		// ball
		Ellipse2D ball = new Ellipse2D.Double(ballX, ballY, ballSize, ballSize);
		g2d.fill(ball);
		
		// scores
		@SuppressWarnings("deprecation")
		String scoreB = "Bottom: " + new Integer(scoreBottom).toString();
		@SuppressWarnings("deprecation")
		String scoreT = "Top: " + new Integer(scoreTop).toString();
		g2d.drawString(scoreB, 10, height / 2);
		g2d.drawString(scoreT, width - 50, height / 2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// just stating the walls on the left and right
		if (ballX < 0 || ballX > width - ballSize) {
			velX = -velX;
		}
		// making it so that if a ball touches the bottom or top walls it adds a score
		if (ballY < 0) {
			velY = -velY;
			++ scoreBottom;
		}
		
		if (ballY + ballSize > height) {
			velY = -velY;
			++ scoreTop;
		}
		// paddle b
		if (ballY + ballSize >= height - padH - inset && velY > 0)
			if (ballX + ballSize >= bottomPadX && ballX <= bottomPadX + padW)
				velY = -velY;

		// paddle t
		if (ballY <= padH + inset && velY < 0)
			if (ballX + ballSize >= topPadX && ballX <= topPadX + padW)
				velY = -velY;

		ballX += velX;
		ballY += velY;
		
		// this will allow your keys presses to actually control the bottom paddle
		if (keys.size() == 1) {
			if (keys.contains("LEFT")) {
				bottomPadX -= (bottomPadX > 0) ? SPEED : 0;
			}
			else if (keys.contains("RIGHT")) {
				bottomPadX += (bottomPadX < width - padW) ? SPEED : 0;
			}
		}
		
		// this is what is going to command how the top paddle moves
		//its going to follow the balls x movement around exmp if ball is going right
		//and the paddle hit the wall it will stay on that wall till ball moves left
		double path = ballX - topPadX;
		if (path > 0) {
			topPadX += (topPadX < width - padW) ? SPEED : 0;
		}
		else if (path < 0) {
			topPadX -= (topPadX > 0) ? SPEED : 0;
		}
		
		repaint();
	}
//key events 
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_LEFT:
			keys.add("LEFT");
			break;
		case KeyEvent.VK_RIGHT:
			keys.add("RIGHT");
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
  		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_LEFT:
			keys.remove("LEFT");
			break;
		case KeyEvent.VK_RIGHT:
			keys.remove("RIGHT");
			break;
		}
	}
}
