import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font.TextLayout;
import java.awt.Font.FontRenderContext;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class pong extends JPanel{
    private final int width;
    private final int height;
    private final static int FRAME_RATE = 20;
    private final int cellSize;
    private boolean gameStarted = false;


    public pong(int width, int height){
        super();
        this.width = width;
        this.height = height;
        this.cellSize = width/(FRAME_RATE*2);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        
    }
    public void startgame(){
        resetGameData();
        setFocusable(true);
        requestFocusInWindow();
        add KeyListener(new KeyAdapter(){
            @Override
            public void KeyPressed(final KeyEvent e){
                handleKeyEvent(e.getKeyCode());
            }
        });
    }
    private void resetGameData(){
        pong.clear();

    }
    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        if (!gameStarted){
            graphics.setColor(Color.WHITE);
            graphics.setFont(graphics.getFont().deriveFont(30F));
            int currentHeight = height/4;
            final var Graphics2D = (Graphics2D) graphics;
            final var src = Graphics2D.getFontRenderContext();
            final String message  = "Press Space Bar\n to start.";
            for(final var line: message.split("\n")){
                final var layout = new TextLayout(line, graphics.Font(), frc);
                final var bounds = layout.getBounds();
                final var targetWidth = (float)(width - bounds.getWidth())/2;
                layout.draw(graphics2D, targetWidth, currentHeight);
                currentHeight += graphics.getFontMetrics().getHeight();
            }
        } else {
            graphics.setColor(Color.cyan)
        }
    }

}
