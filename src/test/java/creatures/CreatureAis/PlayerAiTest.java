package creatures.CreatureAis;

import creatures.Creature;
import creatures.CreatureAi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static tile.Tile.FLOOR;
import static tile.Tile.WALL;

class PlayerAiTest {

    private static Creature player;
    private static CreatureAi playerAi;

    @BeforeAll
    static void setUp() {
        player = Mockito.mock(Creature.class);
        playerAi = new PlayerAi(player);

    }

    @Test
    public void onEnterMoveIfGround() {
        player.x = 1;
        player.y = 1;
        playerAi.onEnter(0, 0, FLOOR);

        assertThat(player.x, is(0));
        assertThat(player.y, is(0));
    }

    @Test
    public void onEnterDoNotMoveIfNotGround() {
        player.x = 0;
        player.y = 0;
        playerAi.onEnter(1, 1, WALL);

        assertThat(player.x, is(0));
        assertThat(player.y, is(0));
    }

    @Test
    public void onEnterDigsIfDiggable() {
        playerAi.onEnter(0, 0, WALL);

        verify(player).dig(anyInt(), anyInt());
    }

    @Test
    public void onEnterDoesNotDigIfNotDiggable() {
        playerAi.onEnter(0, 0, FLOOR);

        verify(player, never()).dig(anyInt(), anyInt());
    }


}