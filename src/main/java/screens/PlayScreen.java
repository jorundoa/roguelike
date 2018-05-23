package screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import creatures.Creature;
import creatures.CreatureFactory;
import world.World;
import world.WorldBuilder;

public class PlayScreen implements Screen {

    private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;



    PlayScreen(){
        screenWidth = 80;
        screenHeight = 21;
        createWorld();
        CreatureFactory creatureFactory = new CreatureFactory(world);
        createCreatures(creatureFactory);
    }

    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);
        displayCreatures(terminal, left, top);

        String stats = String.format(" %3d/%3d hp", player.hp(), player.maxHp());
        terminal.write(stats, 1, 23);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_ESCAPE: return new LoseScreen();
            case KeyEvent.VK_ENTER: return new WinScreen();
            case KeyEvent.VK_LEFT: player.moveBy(-1, 0); break;
            //case KeyEvent.VK_H:
            case KeyEvent.VK_RIGHT: player.moveBy( 1, 0); break;
            //case KeyEvent.VK_L:
            case KeyEvent.VK_UP: player.moveBy( 0,-1); break;
            //case KeyEvent.VK_K:
            case KeyEvent.VK_DOWN: player.moveBy( 0, 1); break;
            //case KeyEvent.VK_J:
            case KeyEvent.VK_Y: player.moveBy(-1,-1); break;
            case KeyEvent.VK_U: player.moveBy( 1,-1); break;
            case KeyEvent.VK_B: player.moveBy(-1, 1); break;
            case KeyEvent.VK_N: player.moveBy( 1, 1); break;
        }

        world.update();

        return this;
    }

    private void createWorld(){
        world = new WorldBuilder(90, 31)
                .makeCaves()
                .build();
    }

    private void createCreatures(CreatureFactory creatureFactory) {
        player = creatureFactory.newPlayer();

        for(int i = 0; i < 8; i ++){
            creatureFactory.newFungus();
        }
    }

    private void displayTiles(AsciiPanel terminal, int left, int top){
        for(int x = 0; x < screenWidth; x++){
            for(int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx,wy), x, y, world.color(wx, wy));
            }
        }
    }

    private void displayCreatures(AsciiPanel terminal, int left, int top) {
        world.allCreatures().stream()
                .filter(c -> withinScreen(c.x, c.y))
                .forEach(c -> terminal.write(c.glyph(), c.x-left, c.y-top, c.color()));
    }

    private boolean withinScreen(int x, int y){
        return x >= getScrollX() &&
                x < getScrollX() + screenWidth &&
                y >= getScrollY() &&
                y < getScrollY() + screenHeight;
    }

    private int getScrollX(){
        return Math.max(0,Math.min(player.x - screenWidth/2, world.getWidth() - screenWidth));
    }

    private int getScrollY(){
        return Math.max(0, Math.min(player.y - screenHeight/2, world.getHeight() - screenHeight));
    }
}