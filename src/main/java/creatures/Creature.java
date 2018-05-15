package creatures;

import world.World;

import java.awt.*;

public class Creature {

    private World world;
    private Color color;
    private char glyph;

    private CreatureAi ai;

    public int x;
    public int y;

    public char glyph(){return glyph;}
    public Color color(){return color;}
    public void setCreatureAi(CreatureAi ai){
        this.ai = ai;
    }

    public Creature(World world, char glyph, Color color){
        this.world = world;
        this.color = color;
        this.glyph = glyph;
    }

    public void dig(int wx, int wy){world.dig(wx, wy);}

    public void moveBy(int mx, int my){
        ai.onEnter(x + mx, y + my, world.tile(x+mx, y+my));
    }
}
