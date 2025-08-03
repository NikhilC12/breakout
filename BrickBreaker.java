import javax.swing.*;

public class BrickBreaker 
{
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Brick Breaker");
            GamePanel panel = new GamePanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setResizable(false);
            frame.add(panel);
            frame.setVisible(true);
            panel.requestFocusInWindow();
        });
    }
}



