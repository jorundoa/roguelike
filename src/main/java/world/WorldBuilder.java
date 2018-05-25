package world;

import tile.Tile;
import util.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static tile.Tile.*;

public class WorldBuilder {
    private int width;
    private int height;
    private int depth;
    private Tile[][][] tiles;
    private int regions[][][];
    private int nextRegion;

    public WorldBuilder(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.nextRegion = 1;
        this.tiles = new Tile[width][height][depth];
    }

    public World build() {
        return new World(tiles);
    }

    private WorldBuilder createRegions() {
        regions = new int[width][height][depth];

        for (int z = 0; z < depth; z++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (tiles[x][y][z] != WALL && regions[x][y][z] == 0) {
                        int size = fillRegion(nextRegion++, x, y, z);

                        if (size < 25) removeRegion(nextRegion - 1, z);
                    }
                }
            }
        }
        return this;
    }

    private int fillRegion(int region, int x, int y, int z) {

        System.out.println(region);
        int size = 1;
        ArrayList<Point> open = new ArrayList<>();
        open.add(new Point(x, y, z));

        regions[x][y][z] = region;

        while (!open.isEmpty()) {
            Point p = open.remove(0);
            for (Point neighbour : p.neighbours8()) {
                if (!isWithinWorld(neighbour.x, neighbour.y, neighbour.z)
                        || regions[neighbour.x][neighbour.y][neighbour.z] > 0
                        || tiles[neighbour.x][neighbour.y][neighbour.z] == WALL)
                    continue;

                size++;
                regions[neighbour.x][neighbour.y][neighbour.z] = region;
                open.add(neighbour);
            }
        }
        return size;
    }

    private boolean isWithinWorld(int x, int y, int z) {
        return x >= 0
                && x < width
                && y >= 0
                && y < height
                && z >= 0
                && z < depth;
    }

    private void removeRegion(int region, int z) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (regions[x][y][z] == region) {
                    regions[x][y][z] = 0;
                    tiles[x][y][z] = WALL;
                }
            }
        }

    }

    private WorldBuilder connectRegions() {
        for (int z = 0; z < depth - 1; z++) {
            connectRegionsDown(z);
        }
        return this;
    }

    private void connectRegionsDown(int z) {
        List<String> connected = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String region = regions[x][y][z] + "," + regions[x][y][z + 1];

                if (tiles[x][y][z] == FLOOR
                        && tiles[x][y][z + 1] == FLOOR
                        && !connected.contains(region)) {
                    connected.add(region);
                    connectRegionsDown(z, regions[x][y][z], regions[x][y][z + 1]);
                }
            }
        }
    }

    private void connectRegionsDown(int z, int r1, int r2) {
        List<Point> candidates = findRegionOverlaps(z, r1, r2);

        int stairs = 0;

        do {
            Point p = candidates.remove(0);
            tiles[p.x][p.y][z] = STAIRS_DOWN;
            tiles[p.x][p.y][z + 1] = STAIRS_UP;
            stairs++;

        } while (candidates.size() / stairs > 250);
    }

    private List<Point> findRegionOverlaps(int z, int r1, int r2) {
        ArrayList<Point> candidates = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y][z] == FLOOR
                        && tiles[x][y][z + 1] == FLOOR
                        && regions[x][y][z] == r1
                        && regions[x][y][z + 1] == r2) {
                    candidates.add(new Point(x, y, z));
                }
            }
        }
        Collections.shuffle(candidates);
        return candidates;
    }

    public WorldBuilder makeCaves() {
        return randomizeTiles()
                .smooth(8)
                .createRegions()
                .connectRegions();
    }


    private WorldBuilder randomizeTiles() {
        for (int z = 0; z < depth; z++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    tiles[x][y][z] = Math.random() < 0.5 ? Tile.FLOOR : WALL;
                }
            }
        }


        return this;
    }

    private WorldBuilder smooth(int times) {
        Tile[][] newTiles = new Tile[width][height];
        for (int z = 0; z < depth; z++) {
            for (int time = 0; time < times; time++) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        int floors = 0;
                        int rocks = 0;

                        for (int ox = -1; ox < 2; ox++) {
                            for (int oy = -1; oy < 2; oy++) {
                                if (ox + x < 0 || ox + x >= width || y + oy < 0 || y + oy >= height)
                                    continue;
                                if (tiles[x + ox][y + oy][z] == Tile.FLOOR)
                                    floors++;
                                else
                                    rocks++;
                            }
                        }
                        newTiles[x][y] = floors >= rocks ? Tile.FLOOR : WALL;
                    }
                }
                writeToFloor(newTiles, z);
            }
        }

        return this;
    }

    private void writeToFloor(Tile[][] newTiles, int z) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y][z] = newTiles[x][y];
            }
        }
    }


}
