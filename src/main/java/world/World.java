package world;

import creatures.Creature;
import tile.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class World {
    private int width;
    private int height;
    private int depth;

    private Tile[][][] tiles;
    private List<Creature> creatures;


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    World(Tile[][][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.depth = tiles[0][0].length;
        creatures = new ArrayList<>();
    }


    public Tile tile(int x, int y, int z) {
        if (!isWithinWorld(x, y, z))
            return Tile.BOUNDS;
        else
            return tiles[x][y][z];
    }

    public Creature creature(int x, int y, int z) {
        return creatures.stream().filter(c -> (c.x == x && c.y == y && c.z == z)).findAny().orElse(null);
    }

    public void update() {
        List<Creature> toUpdate = new ArrayList<>(creatures);
        toUpdate.forEach(Creature::update);
    }

    public List<Creature> allCreatures() {
        return creatures;
    }

    public char glyph(int x, int y, int z) {
        return tile(x, y, z).glyph();
    }

    public Color color(int x, int y, int z) {
        return tile(x, y, z).color();
    }

    public void dig(int x, int y, int z) {
        if (tile(x, y, z).isDiggable()) {
            tiles[x][y][z] = Tile.FLOOR;
        }
    }

    public void addAtEmptyLocation(Creature creature, int z) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        } while (!isEmpty(x, y, z));

        creature.x = x;
        creature.y = y;
        creature.z = z;
        creatures.add(creature);
    }

    public void remove(Creature other) {
        creatures.remove(other);
    }

    public boolean isEmpty(int x, int y, int z) {
        return tile(x, y, z).isGround() && creature(x, y, z) == null;
    }

    public boolean isWithinWorld(int x, int y, int z) {
        return x >= 0
                && x < width
                && y >= 0
                && y < height
                && z >= 0
                && z < depth;
    }
}