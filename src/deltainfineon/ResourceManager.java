/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deltainfineon;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author astraljunkie
 */
public class ResourceManager {
    private GraphicsEnvironment gfxEnv;
    private GraphicsConfiguration gfxConf;
    
    private ArrayList<BufferedImage> mapTiles;
    
    private ResourceManager() {
        gfxEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gfxConf = gfxEnv.getDefaultScreenDevice().getDefaultConfiguration();
        
        initImages();
    }
    
    private void initImages() {
        mapTiles = new ArrayList<BufferedImage>();
        mapTiles = loadImageStrip("graphics/tiles.png");
    }
    
    public BufferedImage getMapTileImage(int id) {
        if (id >= 0 && id < mapTiles.size()) {
            return mapTiles.get(id);
        } else {
            System.out.println("Error: map tile image");
            return null;
        }
    }
    
    public static ResourceManager getInstance() {
        return ResourceManagerHolder.INSTANCE;
    }
    
    private static class ResourceManagerHolder {

        private static final ResourceManager INSTANCE = new ResourceManager();
    }
    
    public BufferedImage loadImageFromResource(String fn) {
        try {
            BufferedImage tempImage = ImageIO.read(
                    getClass().getResource(fn));
            if (tempImage == null) throw new IOException();
            
            int tp = tempImage.getColorModel().getTransparency();
            BufferedImage copy = gfxConf.createCompatibleImage(
                    tempImage.getWidth(), tempImage.getHeight(), tp);
            Graphics2D g2d = copy.createGraphics();
            g2d.drawImage(tempImage, 0, 0, null);
            g2d.dispose();
            
            return copy;
        } catch (IOException e) {
            System.out.println("Error: couldn't load image" + e);
            return null;
        }
    }
    
    public ArrayList<BufferedImage> loadImageStrip(String fn) {
        BufferedImage imgTileStrip;
        ArrayList<BufferedImage> array = new ArrayList<BufferedImage>();
        
        if ((imgTileStrip = loadImageFromResource(fn)) == null) {
            System.out.println("Error: could not load image strip.");
        }
        
        /* TODO :: Add error checking here to ensure that the each tile
		   		   conforms to the specified width given as an argument,
		   		   and possibly crop/adjust incorrect dimensions. */
        int tileWidth = 16;
        int tileHeight = (int)imgTileStrip.getHeight();
        int numTilesInStrip = (int)imgTileStrip.getWidth() / tileWidth;
        int transparency = imgTileStrip.getColorModel().getTransparency();
        
        Graphics2D stripGC;
        
        for (int i = 0; i < numTilesInStrip; i++) {
            BufferedImage nextTile;
            
            nextTile = gfxConf.createCompatibleImage(tileWidth, tileHeight);
            
            stripGC = nextTile.createGraphics();
            stripGC.drawImage(imgTileStrip, 0, 0, tileWidth, tileHeight,
                i*tileWidth, 0, (i*tileWidth)+tileWidth, tileHeight, null);
            stripGC.dispose();
            
            array.add(nextTile);
        }
        
        return array;
    }
}
