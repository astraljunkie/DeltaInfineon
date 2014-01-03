/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deltainfineon;

import java.awt.Dimension;

/**
 *
 * @author astraljunkie
 */
class ConfigManager
{
    public int screenWidth = 240;
    public int screenHeight = 320;
    
    public int sleepTime = 80;
    
    public ConfigManager() {
        
    }
    
    public Dimension getGameViewDimensions() {
        return new Dimension(screenWidth, screenHeight);
    }
}
