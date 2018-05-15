package screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import world.World;
import world.WorldBuilder;

public class PlayScreen implements Screen {

    public World world;
    private int centerX;
    private int centerY;
    private int screenWidth;
    private int screenHeight;



    public PlayScreen(){
        screenWidth = 80;
        screenHeight = 21;
        createWorld();
    }

    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);

        terminal.write('x', centerX - left, centerY - top);
        System.out.format("%d %d %n", centerX, centerY);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_ESCAPE: return new LoseScreen();
            case KeyEvent.VK_ENTER: return new WinScreen();
            case KeyEvent.VK_LEFT: scrollBy(-1, 0); break;
            //case KeyEvent.VK_H:
            case KeyEvent.VK_RIGHT: scrollBy( 1, 0); break;
            //case KeyEvent.VK_L:
            case KeyEvent.VK_UP: scrollBy( 0,-1); break;
            //case KeyEvent.VK_K:
            case KeyEvent.VK_DOWN: scrollBy( 0, 1); break;
            //case KeyEvent.VK_J:
            case KeyEvent.VK_Y: scrollBy(-1,-1); break;
            case KeyEvent.VK_U: scrollBy( 1,-1); break;
            case KeyEvent.VK_B: scrollBy(-1, 1); break;
            case KeyEvent.VK_N: scrollBy( 1, 1); break;
        }

        return this;
    }

    private void createWorld(){
        world = new WorldBuilder(90, 31)
                .makeCaves()
                .build();
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

    private void scrollBy(int mx, int my){
        centerX = Math.max(0, Math.min(centerX + mx, world.getWidth() - 1));
        centerY = Math.max(0, Math.min(centerY + my, world.getHeight() - 1));
    }

    public int getScrollX(){
        return Math.max(0,Math.min(centerX - screenWidth/2, world.getWidth() - screenWidth));
    }

    public int getScrollY(){
        return Math.max(0, Math.min(centerY - screenHeight/2, world.getHeight() - screenHeight));
    }
}