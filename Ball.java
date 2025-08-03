import java.awt.*;

public class Ball 
{
    private int x, y, diameter = 20;
    private int dx = 5, dy = -6;

    public Ball(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }

    public void move() 
    {
        x += dx;
        y += dy;
    }

    public void reverseX() 
    {
        dx = -dx;
    }

    public void reverseY() 
    {
        dy = -dy;
    }

    public void increaseSpeed(int level) 
    {
        double speed = 1 + 0.15 * level;
        dx = (int) (dx * speed);
        dy = (int) (dy * speed);
    }

    public void draw(Graphics g) 
    {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, diameter, diameter);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getDiameter() { return diameter; }

    public Rectangle getRect() 
    {
        return new Rectangle(x, y, diameter, diameter);
    }
}

