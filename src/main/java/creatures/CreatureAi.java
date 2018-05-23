package creatures;

import tile.Tile;

public class CreatureAi {
    protected Creature creature;

    public CreatureAi(Creature creature){
        this.creature = creature;
        creature.setCreatureAi(this);
    }

    public void onEnter(int x, int y, Tile tile){

    }

    public void onUpdate() {
    }
}
