package creatures;

import asciiPanel.AsciiPanel;
import creatures.CreatureAis.PlayerAi;
import world.World;

public class CreatureFactory {
    private World world;


    public CreatureFactory(World world){
        this.world = world;
    }

    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightRed);
        world.addAtEmptyLocation(player);
        new PlayerAi(player);
        return player;
    }
}
