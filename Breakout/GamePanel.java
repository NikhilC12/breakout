import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements ActionListener, KeyListener 
{
    private Timer timer;
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private int score = 0;
    private int totalScore = 0;
    private int highScore = 1;  
    private boolean gameOver = false;
    private boolean inGame = false;
    private boolean inMenu = true;
    private boolean inInstructions = false;
    private boolean inShop = false;
    private int level = 1;
    private int remainingBricks;
    private Image backgroundImage;

    private String[] menuBackgrounds = 
    {
        "menu1.jpg", "menu2.jpg", "menu3.jpg"
    };

    private String[] gameBackgrounds = 
    {
        "background1.jpg", "background2.jpg", "background3.jpg", "background4.jpg", "background5.jpg",
        "background6.jpg", "background7.jpg", "background8.jpg", "background9.jpg", "background10.jpg",
        "background11.jpg", "background12.jpg", "background13.jpg", "background14.jpg", "background15.jpg",
        "background16.jpg", "background17.jpg", "background18.jpg"
    };

    public GamePanel() 
    {
        setFocusable(true);
        addKeyListener(this);
        loadRandomMenuBackground();
        initGame();
    }

    private void initGame() 
    {
        ball = new Ball(390, 300);
        paddle = new Paddle(350, 550);
        createBricks();
        timer = new Timer(16, this);
        timer.start();
    }

    private void loadRandomMenuBackground() 
    {
        Random rand = new Random();
        int index = rand.nextInt(menuBackgrounds.length);
        backgroundImage = new ImageIcon(menuBackgrounds[index]).getImage();
    }

    private void loadRandomGameBackground() 
    {
        Random rand = new Random();
        int index = rand.nextInt(gameBackgrounds.length);
        backgroundImage = new ImageIcon(gameBackgrounds[index]).getImage();
    }

    private void createBricks() 
    {
        int rows = 5, cols = 10;
        bricks = new Brick[rows * cols];
        int startX = 60, startY = 50;
        int width = 60, height = 20, gap = 5;
        remainingBricks = rows * cols;
        for (int r = 0; r < rows; r++) 
        {
            for (int c = 0; c < cols; c++) 
            {
                int x = startX + c * (width + gap);
                int y = startY + r * (height + gap);
                bricks[r * cols + c] = new Brick(x, y, width, height);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);

        if (backgroundImage != null) 
        {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else 
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        if (inMenu) 
        {
            drawMenu(g);
        } 
        	else if (inInstructions) 
        {
            drawInstructions(g);
        } 
        	else if (inShop) 
        {
            drawShop(g);
        } 
        	else 
        {
            drawGame(g);
        }
    }

    private void drawMenu(Graphics g) 
    {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Brick Breaker", 310, 210);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Press ENTER to Start", 310, 250);
        g.drawString("Press I for Instructions", 310, 290);
        g.drawString("Press S to Visit Shop", 310, 330);
    }

    private void drawInstructions(Graphics g) 
    {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 26));
        g.drawString("Instructions", 330, 100);

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("- Use LEFT and RIGHT arrow keys to move the paddle", 180, 160);
        g.drawString("- Bounce the ball to destroy all the bricks", 220, 200);
        g.drawString("- Each level gets harder and gives more points", 215, 230);
        g.drawString("- Don’t let the ball fall below the paddle", 235, 260);
        g.drawString("- Spend points at the shop", 230, 290);

        g.setFont(new Font("Arial", Font.ITALIC, 18));
        g.drawString("Press B to go back to menu", 290, 330);
    }

    private void drawShop(Graphics g) 
    {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 26));
        g.drawString("Shop", 370, 100);

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("- Shop is under construction!", 300, 200);
        g.drawString("- Future upgrades: Paddle skins, ball trails, power-ups", 200, 240);

        g.setFont(new Font("Arial", Font.ITALIC, 18));
        g.drawString("Press B to go back to menu", 290, 340);
    }

    private void drawGame(Graphics g) 
    {
        ball.draw(g);
        paddle.draw(g);
        for (Brick brick : bricks) 
        {
            if (!brick.isDestroyed()) 
            	brick.draw(g);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Total Points: " + totalScore, 10, 20);
        g.drawString("Level: " + level, 700, 20);
        g.drawString("High Score: " + highScore, 10, 40);

        if (gameOver) 
        {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over", 300, 300);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press R to Restart", 305, 330);
            g.drawString("Press H to return Home", 305, 360);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (inGame && !gameOver) 
        {
            ball.move();
            checkCollisions();
            repaint();
        }
    }

private void checkCollisions() 
{
    if (ball.getRect().intersects(paddle.getRect())) 
    {
        ball.reverseY();
    }

    if (ball.getX() < 0 || ball.getX() > getWidth() - ball.getDiameter()) 
    {
        ball.reverseX();
    }
    if (ball.getY() < 0) 
    {
        ball.reverseY();
    }

    if (ball.getY() > getHeight()) 
    {
        gameOver = true;
    }

    boolean allDestroyed = true;
    for (Brick brick : bricks) 
    {
        if (!brick.isDestroyed()) 
        {
            if (ball.getRect().intersects(brick.getRect())) 
            {
                brick.setDestroyed(true);
                ball.reverseY();
                remainingBricks--;
                score++;
                break;
            }
            allDestroyed = false;
        }
    }

    if (remainingBricks == 0) 
    {
        totalScore += level * 10;
        level++;
        if (level > highScore) 
        {
            highScore = level;
        }
        score = 0;
        remainingBricks = 0;
        ball = new Ball(390, 300);
        createBricks();
        loadRandomGameBackground();
        ball.increaseSpeed(level); 
    }
}


    @Override
    public void keyPressed(KeyEvent e) 
    {
        int code = e.getKeyCode();

        if (inMenu) 
        {
            if (code == KeyEvent.VK_ENTER) 
            {
                inMenu = false;
                inGame = true;
                loadRandomGameBackground();
                repaint();
                return;
            } 
            else if (code == KeyEvent.VK_I) 
            {
                inMenu = false;
                inInstructions = true;
                repaint(); 
                return;
            } 
            else if (code == KeyEvent.VK_S) 
            {
                inMenu = false;
                inShop = true;
                repaint(); 
                return;
            }
        } 
        else if (inInstructions || inShop) 
        {
            if (code == KeyEvent.VK_B) 
            {
                inInstructions = false;
                inShop = false;
                inMenu = true;
                loadRandomMenuBackground();
                repaint(); 
                return;
            }
        }

        if (gameOver) 
        {
            if (code == KeyEvent.VK_R) 
            {
                resetGame(); 
                repaint(); 
            }
            if (code == KeyEvent.VK_H)
            {
				inMenu = true;
				repaint();
            }
            return;
        }

        if (!inGame) return;

        if (code == KeyEvent.VK_LEFT) 
        	paddle.move(-20);
        if (code == KeyEvent.VK_RIGHT) 
        	paddle.move(20);
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    private void resetGame() 
    {
        score = 0;
        level = 1;
        gameOver = false;
        inGame = true;
        ball = new Ball(390, 300);
        paddle = new Paddle(350, 550);
        createBricks();
        loadRandomGameBackground();
    }
}

