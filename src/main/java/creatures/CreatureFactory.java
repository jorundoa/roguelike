package creatures;

import asciiPanel.AsciiPanel;
import creatures.CreatureAis.FungusAi;
import creatures.CreatureAis.PlayerAi;
import world.World;

public class CreatureFactory {
    private World world;


    public CreatureFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer() {
        Creature player = new Creature(world, '@', AsciiPanel.brightRed, 100, 20, 5);
        world.addAtEmptyLocation(player);
        new PlayerAi(player);
        return player;
    }

    public Creature newFungus() {
        Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0);
        world.addAtEmptyLocation(fungus);
        new FungusAi(fungus, this);
        return fungus;
    }
}
