/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deltainfineon;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author astraljunkie
 */
public class DeltaInfineon
{
    private ConfigManager config;
    private ResourceManager resources;
    
    private JFrame gameWindow;
    private GamePanel gameView;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DeltaInfineon();
            }
        });
    }
    
    public DeltaInfineon() {
        config = new ConfigManager(); // <-- refactor into singleton
        resources = ResourceManager.getInstance();
        
        gameWindow = new JFrame("Game Window");
        gameView = new GamePanel(this);
        
        gameWindow.add(gameView);
        
        gameWindow.pack();
        gameWindow.setResizable(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setVisible(true);
    }
    
    public ConfigManager getConfigManager()     { return config; }
    public ResourceManager getResourceManager() { return resources; }
}
