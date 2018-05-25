package creatures;

import asciiPanel.AsciiPanel;
import creatures.CreatureAis.FungusAi;
import creatures.CreatureAis.PlayerAi;
import world.World;

import java.util.List;

public class CreatureFactory {
    private World world;


    public CreatureFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer(List<String> messages) {
        Creature player = new Creature(world, '@', AsciiPanel.brightRed, 100, 20, 5);
        world.addAtEmptyLocation(player, 0);
        new PlayerAi(player, messages);
        return player;
    }

    public Creature newFungus() {
        Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0);
        world.addAtEmptyLocation(fungus, 0);
        new FungusAi(fungus, this);
        return fungus;
    }
}
