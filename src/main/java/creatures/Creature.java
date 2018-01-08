package creatures;

import world.World;

import java.awt.*;

public class Creature {

    private World world;
    private Color color;
    private char glyph;

    public int x;
    public int y;

    public char glyph(){return glyph;}
    public Color color(){return color;}

    public Creature(World world, char glyph, Color color){
        this.world = world;
        this.color = color;
        this.glyph = glyph;
    }


}
