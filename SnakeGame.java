import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.font.FontRenderContext;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener{
    private final int width;
    private final int height;
    private final int cellSize;
    private final Random random = new Random();
    private final static int FRAME_RATE = 20;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private GamePoint food;
    private Direction direction = Direction.RIGHT;
    private Direction newDirection = Direction.RIGHT;
    private final List<GamePoint> snake = new ArrayList<>();
    public SnakeGame(int width, int height){
        super();
        this.width = width;
        this.height = height;
        this.cellSize = width/(FRAME_RATE*2);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);

    }
    public void startGame(){
        resetGameData();
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(final KeyEvent e){
                handleKeyEvent(e.getKeyCode());
            }
        });
        new Timer(1000/FRAME_RATE, this).start();
        

    }
    private void handleKeyEvent(final int KeyCode){
        if (!gameStarted){
            if (KeyCode == KeyEvent.VK_SPACE){
                gameStarted = true;
            }
        } else if(!gameOver){
            switch(KeyCode){
                case KeyEvent.VK_UP:
                    if (direction != Direction.DOWN){

                
                        newDirection = Direction.UP;
                    }   
                break;
                case KeyEvent.VK_DOWN:
                        if (direction != Direction.UP){
                            newDirection = Direction.DOWN;
                        }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT){
                        newDirection = Direction.RIGHT;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != Direction.LEFT){
                        newDirection = Direction.LEFT;
                    }
                    break;
            
            }
        }
    }
    private void resetGameData(){
        snake.clear();
        snake.add(new GamePoint(width/2, height/2));
        generateFood();
    }
    private void generateFood(){
        do {
            food = new GamePoint(random.nextInt(width/cellSize)*cellSize, random.nextInt(height/cellSize)*cellSize);
        } while (snake.contains(food));
    }


    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        if (!gameStarted){

        


            graphics.setColor(Color.WHITE);
            graphics.setFont(graphics.getFont().deriveFont(30F));
            int currentHeight = height/4;
            final var graphics2D = (Graphics2D) graphics;
            final var frc = graphics2D.getFontRenderContext();
            final String message = "Press space bar \n to begin the game.";
            for (final var line : message.split("\n")){
                final var layout = new TextLayout(line, graphics.getFont(), frc);
                final var bounds = layout.getBounds();
                final var targetWidth = (float)(width - bounds.getWidth())/2;
                layout.draw(graphics2D, targetWidth, currentHeight);
                currentHeight += graphics.getFontMetrics().getHeight();
            }
        } else{
            graphics.setColor(Color.cyan);
            graphics.fillRect(food.x, food.y, cellSize, cellSize);

            graphics.setColor(Color.GREEN);
            for (final var point: snake){
                graphics.fillRect(point.x, point.y, cellSize, cellSize);
            }
        }
    }
    private void move(){
        final GamePoint head = snake.get(0);
        final GamePoint newHead = switch(direction){
            case UP -> new GamePoint(head.x, head.y - cellSize);
            case DOWN -> new GamePoint(head.x, head.y + cellSize);
            case LEFT -> new GamePoint(head.x-cellSize, head.y);
            case RIGHT -> new GamePoint(head.x + cellSize, head.y);
        };
        snake.add(0, newHead);
        if (newHead.equals(food)){
            generateFood();
        }else if (isCollision()){
            gameOver = true;

            snake.remove(0);
        } else{
            snake.remove(snake.size()-1);
        }
        direction = newDirection;
        
    }
    private boolean isCollision(){

        final GamePoint head = snake.getFirst();
        final var invalidWidth = (head.x < 0)||(head.x>=width);
        final var invalidHeight = (head.y < 0)||(head.y >= height);
        return (invalidWidth||invalidHeight);
    }
    @Override
    public void actionPerformed(final ActionEvent e){
        if (gameStarted && !gameOver){
            move();
        }
        
        repaint();

    }
    private record GamePoint(int x, int y){

    }
    private enum Direction{
        UP, DOWN, RIGHT, LEFT
    }

}