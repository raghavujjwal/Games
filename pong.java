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
    private Ship red_ship;
    private Ship green_ship;
    private int redShipVelocity = 2;
    private Timer Timer;

    public pong(int width, int height){
        super();
        this.width = width;
        this.height = height;
        this.cellSize = width/(FRAME_RATE*2);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        red_ship = new Ship(700, 300, 10, 20, Color.RED);
        green_ship = new Ship(100, 300, 10, 20, Color.GREEN);
        
    }
    public void startgame(){
        resetGameData();
        setFocusable(true);
        requestFocusInWindow();
        timer = new Timer(1000/FRAME_RATE, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (gameStarted){
                    moveRedShip();
                    repaint();
                }
            }
        });
        addKeyListener(new KeyAdapter(){
            @Override
            public void KeyPressed(final KeyEvent e){
                handleKeyEvent(e.getKeyCode());
            }
        });
        timer.start();
    }

    private move redShip(){
        int topBoundary = 0;
        int bottomBoundary = height - red_ship.height;

        if (redShipMoveingDown){
            red_ship.setY(red_ship.getY() + redShipVelocity);
            if (red_ship.getY() >= bottomBoundary){
                redShipMovingDown = false;
            }
        } else{
            red_ship.setY(red_ship.getY() - redShipVelocity);
            if (red_ship.getY() <= topBoundary){
                redShipMovingDown = true;
            }
        }
    }
    private void resetGameData(){
        

    }

    private void handleKeyEvent(int KeyCode){
        int moveAmount = 10;

        switch (keyCode){
            case KeyEvent.VK_LEFT:
                green_ship.move(-moveAmount);
                break;
            case KeyEvent.VK_RIGHT:
                green_ship.move(moveAmount);
                break;
            case KeyEvent.VK_UP:
                green_ship.moveup();
                break;
            case KeyEvent.VK_DOWN:
                green_ship.movedown();
                break;
        }
    }
    public class Ship{
        private int x, y;
        private int width, height;
        private Color color;

        public ship(int x, int y, int width, int height, Color, color){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
        }

        public void draw(Graphics g){
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }

        public void moveup(){
            y -= 10;

        }
        public void movedown(){
            y += 10;
        }
        public int getY(){
            return y;
        }
        public void setY(int y){
            this.y = y;
        }


        public void move(int dx){
            int newX = x + dx;
            if(color== Color.RED && newX + this.width > width/2){
                return;
            }
            if(color == Color.GREEN && newX < width/2){
                return;
            }
            x = newX;
        }
    }
       
    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        int midX = width/2;
        g.setColor(Color.WHITE);
        g.DrawLine(midX, 0, midX, height);

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
            red_ship.draw(g);
            green_ship.draw(g);
        }
    }

}
