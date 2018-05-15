package world;

import creatures.Creature;
import tile.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class World {
    private int width;
    private int height;

    private Tile[][] tiles;
    private List<Creature> creatures;


    public int getWidth(){return width;}
    public int getHeight(){return height;}

    public World(Tile[][] tiles){
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        creatures = new ArrayList<>();
    }


    public Tile tile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[x][y];
    }

    public Creature creature(int x, int y) {
        return creatures.stream().filter(c -> (c.x == x && c.y == y)).findAny().orElse(null);
    }

    public char glyph(int x, int y){
        return tile(x,y).glyph();
    }

    public Color color(int x, int y) {
        return tile(x,y).color();
    }

    public void dig(int x, int y) {
        if(tile(x, y).isDiggable()){
            tiles[x][y] = Tile.FLOOR;
        }
    }

    public void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do{
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        } while (!tile(x, y).isGround() || creature(x, y) == null);

        creature.x = x;
        creature.y = y;
        creatures.add(creature);
    }
}