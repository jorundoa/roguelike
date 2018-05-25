package screens;

import asciiPanel.AsciiPanel;
import creatures.Creature;
import creatures.CreatureFactory;
import world.World;
import world.WorldBuilder;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {

    private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;
    private List<String> messages;


    PlayScreen() {
        screenWidth = 80;
        screenHeight = 21;
        messages = new ArrayList<>();
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
        displayMessages(terminal, messages);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                return new LoseScreen();
            case KeyEvent.VK_ENTER:
                return new WinScreen();
            case KeyEvent.VK_LEFT:
                player.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                player.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
                player.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                player.moveBy(0, 1);
                break;
            case KeyEvent.VK_Y:
                player.moveBy(-1, -1);
                break;
            case KeyEvent.VK_U:
                player.moveBy(1, -1);
                break;
            case KeyEvent.VK_B:
                player.moveBy(-1, 1);
                break;
            case KeyEvent.VK_N:
                player.moveBy(1, 1);
                break;
        }

        world.update();

        return this;
    }

    private void createWorld() {
        world = new WorldBuilder(90, 31, 3)
                .makeCaves()
                .build();
    }

    private void createCreatures(CreatureFactory creatureFactory) {
        player = creatureFactory.newPlayer(messages);

        for (int i = 0; i < 8; i++) {
            creatureFactory.newFungus();
        }
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z));
            }
        }
    }

    private void displayCreatures(AsciiPanel terminal, int left, int top) {
        world.allCreatures().stream()
                .filter(c -> withinScreen(c.x, c.y, c.z))
                .forEach(c -> terminal.write(c.glyph(), c.x - left, c.y - top, c.color()));
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size() + 1;
        for (int i = 0; i < messages.size(); i++) {
            terminal.writeCenter(messages.get(0), top + i);
        }
        messages.clear();
    }

    private boolean withinScreen(int x, int y, int z) {
        return x >= getScrollX() &&
                x < getScrollX() + screenWidth &&
                y >= getScrollY() &&
                y < getScrollY() + screenHeight &&
                z == player.z;
    }

    private int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.getWidth() - screenWidth));
    }

    private int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.getHeight() - screenHeight));
    }
}