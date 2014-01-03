/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deltainfineon;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 *
 * @author astraljunkie
 */
class GamePanel extends JPanel implements KeyListener, Runnable
{
    // Globals
    private DeltaInfineon app;
    private Dimension viewSize;
    
    private GraphicsEnvironment ge;
    private GraphicsConfiguration gc;
    
    private Graphics2D dbg;
    private Image dbImage = null;
    
    private Thread animThread;
    private volatile boolean running = false;
    private volatile boolean gameOver = false;
    private int period = 10;
    
    private int curGameState;
    
    private boolean kdUp, kdDown, kdLeft, kdRight, kdFire; // keyDown states
    
    // Constructors and Initializers
    public GamePanel(DeltaInfineon parent) {
        app = parent;
        
        // This should be a reference, not a copy!
        viewSize = app.config.getGameViewDimensions();
        
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        
        setDoubleBuffered(true);
        
        this.setFocusable(true);
        requestFocus();
        
        kdUp = kdDown = kdLeft = kdRight = kdFire = false;
        
        curGameState = 0;
    }
    
    private void startGame() {
        this.addKeyListener(this);
        
        if (animThread == null || !running) {
            animThread = new Thread(this);
            animThread.start();
        }
    }
    
    public void stopGame() {
        running = false;
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        startGame();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return viewSize;
    }
    
    @Override
    public void keyTyped(KeyEvent ke) { /* not used */ }

    @Override
    public void keyPressed(KeyEvent ke) {
        // TODO :: Allow keys to be redefined instead of being hardcoded
        int keyCode = ke.getKeyCode();
        
        switch (keyCode) {
            case KeyEvent.VK_UP:
                kdUp = true;
                break;
            case KeyEvent.VK_DOWN:
                kdDown = true;
                break;
            case KeyEvent.VK_LEFT:
                kdLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                kdRight = true;
                break;
            case KeyEvent.VK_Z:
                kdFire = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        // TODO :: Allow keys to be redefined instead of being hardcoded
        int keyCode = ke.getKeyCode();
        
        switch (keyCode) {
            case KeyEvent.VK_UP:
                kdUp = false;
                break;
            case KeyEvent.VK_DOWN:
                kdDown = false;
                break;
            case KeyEvent.VK_LEFT:
                kdLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                kdRight = false;
                break;
            case KeyEvent.VK_Z:
                kdFire = false;
                break;
            case KeyEvent.VK_ESCAPE:
                running = false;
                break;
        }
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleepTime;
        
        beforeTime = System.currentTimeMillis();
        
        running = true;
        
        while (running) {
            gameUpdate();
            gameRender();
            paintScreen();
            
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleepTime = period - timeDiff; // time left in this loop
            
            if (sleepTime <= 0) // update/render took longer than period
                sleepTime = 5; // sleep some anyhow
            
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                
            }
            
            beforeTime = System.currentTimeMillis();
        }
        
        System.exit(0);
    }
    
    private void gameUpdate() {
        if (!gameOver) {
            // update game state
        }
    }
    
    private void gameRender() {
        if (dbImage == null) {
            dbImage = createImage(viewSize.width, viewSize.height);
            if (dbImage == null) {
                System.out.println("Error: Graphics buffer is null.");
                return;
            } else {
                dbg = (Graphics2D)dbImage.getGraphics();
            }
            
            dbg.setColor(Color.BLACK);
            dbg.fill(new Rectangle(0, 0, viewSize.width, viewSize.height));
            
            // Do drawing here
            
            if (gameOver) {
                // game over message
            }
        }
    }
    
    private void paintScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            System.out.println("Graphics context error:" + e);
        }
    }
}
