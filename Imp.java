import javax.swing.JFrame;


public class Imp{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    public static void main(String[] args){
        final JFrame frame = new JFrame(" Pong ");
        pong game = new pong(WIDTH, HEIGHT);
        frame.add(game);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        game.startgame();
    }
    
}