import java.awt.*;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.util.Random;
import java.util.Timer;

public class Pong extends Canvas {
  /**
   *
   */
  private static final long serialVersionUID = -4789572533715132229L;
  Point delta;
  Ellipse2D.Double ball;
  Rectangle paddle1;
  Rectangle paddle2;
  int paddle1Score = 0;
  int paddle2Score = 0;

  public static void main(String[] args) {
    JFrame win = new JFrame("Pong");
    win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    win.getContentPane().setBackground(Color.black);
    win.setSize(1010, 735);
    win.add(new Pong());
    win.setVisible(true);
  }

  public Pong() {
    enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK);
    requestFocus();

    ball = new Ellipse2D.Double(500, 350, 20, 20);
    delta = new Point(-5, 5);
    paddle1 = new Rectangle(50, 250, 20, 200);
    paddle2 = new Rectangle(950, 250, 20, 200);

    Timer t = new Timer(true);
    t.schedule(new java.util.TimerTask() {
      public void run() {
        doStuff();
        repaint();
      }
    }, 10, 10);

  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    g2.setFont(new Font("Bold", 1, 30));
    g2.drawString("SCORE", 470, 50);
    g2.setFont(new Font("Bold", 1, 20));
    g2.drawString("Left: " + paddle1Score + " Right: " + paddle2Score, 440, 100);

    g2.setColor(Color.GREEN);

    g2.fill(ball);

    g2.setColor(Color.white);
    g2.fill(paddle1);
    g2.fill(paddle2);
  }

  public void processKeyEvent(KeyEvent e) {
    if (e.getID() == KeyEvent.KEY_PRESSED) {
      // if (e.getKeyCode() == KeyEvent.VK_W) {
      // paddle1.y -= 30;
      // }
      // if (e.getKeyCode() == KeyEvent.VK_S) {
      // paddle1.y += 30;
      // }
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        paddle2.y -= 30;
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        paddle2.y += 30;
      }
    }
    repaint();
  }

  public void doStuff() {
    ball.x += delta.x;
    ball.y += delta.y;

    // controls computer game play
    paddle1.y = (int) (ball.y + Math.random() * 75);
    // and bounce if we hit a wall
    if (ball.y < 0 || ball.y + 20 > 700)
      delta.y = -delta.y;
    if (ball.x < 0)
      delta.x = -delta.x;

    // check if the ball is hitting the paddle
    if (ball.intersects(paddle1))
      delta.x = -delta.x;

    if (ball.intersects(paddle2))
      delta.x = -delta.x;

    // check for scoring
    if (ball.x > 1000) {
      paddle1Score++;
      ball.x = 500;
      ball.y = 350;
      delta = new Point(-5, 5);
    }
    if (ball.x < 0) {
      paddle2Score++;
      ball.x = 500;
      ball.y = 350;
      delta = new Point(5, 5);
    }
    repaint();
  }

  public boolean isFocusable() {
    return true;
  }
}