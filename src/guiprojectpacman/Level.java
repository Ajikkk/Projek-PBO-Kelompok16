package guiprojectpacman;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private int width;
    private int height;
    public Tile[][] tiles;
    public List<Apple> apples;
    public List<Enemy> enemies;
    public Level(String path){
        apples = new ArrayList<Apple>();
        enemies = new ArrayList<Enemy>();
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            this.width = map.getWidth();
            this.height = map.getHeight();
            int[] pixels = new int[width * height];
            tiles = new Tile[width][height];
            map.getRGB(0, 0, width, height, pixels, 0, width);

            for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    int val = pixels[x+(y*width)];
                    if(val == 0xff000000){
//                        Tile
                         tiles[x][y] = new Tile(x*32, y*32);
                    }else if(val == 0xff0000ff){
//                        Player
                        Game.player.x = x*32;
                        Game.player.y = y*32;
                    }else if(val == 0xffff0000){
//                        Enemy
                        enemies.add(new Enemy(x*32, y*32));
                    }else{
                        apples.add(new Apple(x*32, y*32));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tick(){
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).tick();
        }
    }

    public void render(Graphics g){
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(tiles[x][y] != null)
                     tiles[x][y].render(g);
            }
        }

        for(int i = 0; i < apples.size(); i++){
            apples.get(i).render(g);
        }

        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).render(g);
        }
    }
}
