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

    public char glyph() {
        return glyph;
    }

    public Color color() {
        return color;
    }

    void setCreatureAi(CreatureAi ai) {
        this.ai = ai;
    }

    Creature(World world, char glyph, Color color) {
        this.world = world;
        this.color = color;
        this.glyph = glyph;
    }

    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    public void moveBy(int mx, int my) {
        Creature other = world.creature(x + mx, y + my);

        if (other == null) {
            ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
        } else {
            attack(other);
        }
    }

    private void attack(Creature other) {
        world.remove(other);
    }

    public void update() {
        ai.onUpdate();
    }

    public boolean canEnter(int x, int y) {
        return world.isEmpty(x, y) && world.isWithinWorld(x, y);
    }
}
