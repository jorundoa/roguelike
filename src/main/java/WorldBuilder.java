public class WorldBuilder {
    private int width;
    private int height;
    private Tile[][] tiles;

    public WorldBuilder(Tile[][] tiles){
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.tiles = tiles;
    }

    public World build(){
        return new World(tiles);
    }

    public WorldBuilder makeCaves(){
        return randomizeTiles().smooth(8);
    }


    private WorldBuilder randomizeTiles(){
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                tiles[x][y] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
            }
        }
        return this;
    }

    private WorldBuilder smooth(int times){
        Tile[][] newTiles = new Tile[width][height];
        for (int time = 0; time < times; time++){
            for (int x = 0; x < width; x++){
                for (int y = 0; y < height; y++){
                    int floors = 0;
                    int rocks = 0;

                    for (int ox = -1; ox < 2; ox++){
                        for (int oy = -1; oy < 2; oy++){
                            if (ox + x < 0 || ox + x >= width || y + oy < 0 || y + oy >= height)
                                continue;
                            if (tiles[x+ox][y+oy] == Tile.FLOOR)
                                floors++;
                            else
                                rocks++;
                        }
                    }
                    newTiles[x][y] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
                }
            }
            tiles = newTiles;
        }
        return this;
    }
}
