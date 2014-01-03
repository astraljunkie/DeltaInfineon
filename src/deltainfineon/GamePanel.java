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
    
    private Thread animThread;
    
    private boolean kdUp, kdDown, kdLeft, kdRight, kdFire; // keyDown states
    
    private int curGameState;
    
    // Constructors and Initializers
    public GamePanel(DeltaInfineon parent) {
        app = parent;
        
        // This should be a reference, not a copy!
        viewSize = app.config.getGameViewDimensions();
        
        this.setFocusable(true);
        
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        
        kdUp = kdDown = kdLeft = kdRight = kdFire = false;
        
        curGameState = 0;
        
        animThread = new Thread(this);
    }
    
    public void init() {
        this.addKeyListener(this);
        animThread.start();
    }
    
    // Overridden methods
    @Override
    public Dimension getPreferredSize() {
        return viewSize;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        
        g2d.setPaint(Color.BLACK);
        g2d.fill(new Rectangle(0, 0, viewSize.width, viewSize.height));
    }
    
    // Interface methods
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
        }
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            
            try {
                // TODO :: Find a better, more accurate method
                Thread.sleep(app.config.sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    // Other methods
}
