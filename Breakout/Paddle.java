import java.awt.*;

public class Paddle 
{
    private int x, y, width = 100, height = 10;
    private Color color;

    public Paddle(int x, int y) 
    {
        this.x = x;
        this.y = y;
        this.color = new Color((int)(Math.random() * 0x1000000)); 
    }

    public void move(int dx) 
    {
        x += 1.2*dx;
        if (x < 0) x = 0;
        if (x > 800 - width) x = 800 - width;
    }
    

    public void draw(Graphics g) 
    {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getRect() 
    {
        return new Rectangle(x, y, width, height);
    }
}


