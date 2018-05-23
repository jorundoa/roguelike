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

    private int hp;
    private int maxHp;
    private int attackValue;
    private int defenseValue;

    public char glyph() {
        return glyph;
    }

    public Color color() {
        return color;
    }

    void setCreatureAi(CreatureAi ai) {
        this.ai = ai;
    }

    Creature(World world, char glyph, Color color, int maxHp, int attackValue, int defenseValue) {
        this.world = world;
        this.color = color;
        this.glyph = glyph;

        this.hp = maxHp;
        this.maxHp = maxHp;
        this.attackValue = attackValue;
        this.defenseValue = defenseValue;
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
        int damage = Math.max(0, attackValue() - other.defenseValue());

        damage = (int) (Math.random() * damage + 1);

        other.modifyHp(-damage);
    }

    private void modifyHp(int amount) {
        hp += amount;

        if (hp <= 0) {
            world.remove(this);
        }
    }

    public void update() {
        ai.onUpdate();
    }

    public boolean canEnter(int x, int y) {
        return world.isEmpty(x, y) && world.isWithinWorld(x, y);
    }

    public int maxHp() {
        return maxHp;
    }

    public int hp() {
        return hp;
    }

    private int defenseValue() {
        return defenseValue;
    }

    private int attackValue() {
        return attackValue;
    }
}
