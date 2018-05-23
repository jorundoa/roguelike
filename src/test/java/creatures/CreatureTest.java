package creatures;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import world.World;

import java.awt.*;

import static org.mockito.Mockito.*;

class CreatureTest {
    private static final char GLYPH = 'T';
    private static int MAX_HP = 100;
    private static final int ATTACK_VALUE = 30;
    private static final int DEFENSE_VALUE = 20;


    private static Creature creature;
    private static World world;
    private static CreatureAi ai;


    @BeforeAll
    static void setUp() {
        world = mock(World.class);
        ai = mock(CreatureAi.class);

        creature = new Creature(world, GLYPH, Color.yellow, MAX_HP, ATTACK_VALUE, DEFENSE_VALUE);
        creature.setCreatureAi(ai);
        creature.x = 0;
        creature.y = 0;
    }

    @Test
    void notifiesOtherCreature() {
        Creature other = mock(Creature.class);
        when(world.creature(0, 1)).thenReturn(other);

        creature.notifyActionToCreaturesAround("attack an enemy");

        verify(other, times(1)).notify("The 'T' attacks an enemy.");
    }

    @Test
    void notifiesSelf() {
        when(world.creature(0, 0)).thenReturn(creature);

        creature.notifyActionToCreaturesAround("attack an enemy");

        verify(ai, times(1)).onNotify("You attack an enemy.");
    }

}