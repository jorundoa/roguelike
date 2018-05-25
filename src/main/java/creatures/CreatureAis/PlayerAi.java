package creatures.CreatureAis;

import creatures.Creature;
import creatures.CreatureAi;
import tile.Tile;

import java.util.List;

public class PlayerAi extends CreatureAi {

    private List<String> messages;

    public PlayerAi(Creature creature, List<String> messages) {
        super(creature);
        this.messages = messages;
    }

    @Override
    public void onEnter(int x, int y, int z, Tile tile) {
        if (tile.isGround()) {
            creature.x = x;
            creature.y = y;
            creature.z = z;
        } else if (tile.isDiggable()) {
            creature.dig(x, y);
        }

        //Add door and item interaction here?
    }

    @Override
    public void onNotify(String message) {
        messages.add(message);
    }

}
