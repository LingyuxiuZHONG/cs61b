package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 40;
    public static final int HEIGHT = 20;

    public static final TETile[] DECORATE = {Tileset.GRASSTOP,Tileset.MUSHROOM1,Tileset.MUSHROOM2,Tileset.FLOWER1,Tileset.FLOWER2,Tileset.FLOWER3,Tileset.GRASS1};

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        ter.initialize(WIDTH,HEIGHT+3);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, HEIGHT / 4 * 3, "CS61B: THE GAME");
        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontSmall);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 4, "Quit (q)");
        StdDraw.show();
        Random rd = new Random();
        while (!StdDraw.hasNextKeyTyped()) {
            // Wait for user input
        }
        char key = StdDraw.nextKeyTyped();
        if(key == 'n' || key == 'N') {
            StdDraw.clear(Color.BLACK);
            TETile[][] world = new TETile[WIDTH][HEIGHT];
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    world[x][y] = selectElement(DECORATE,rd.nextInt(100));
                }
            }
            RandomWorld rw = new RandomWorld(WIDTH, HEIGHT, rd, world);
            rw.create();
            Role role = new Role(world,rd,WIDTH,HEIGHT);
            StdDraw.setFont(fontSmall);
            ter.renderFrame(world);
            explore(role);
        }
    }
    public static TETile selectElement(TETile[] array, int randomNumber) {
        if (randomNumber < 80) {
            return array[0];
        } else {
            Random random = new Random();
            return array[random.nextInt(array.length - 1) + 1];
        }
    }

    private void explore(Role role){
        boolean flag = true;
        while(flag){
            while(!StdDraw.hasNextKeyTyped()){
            }
            switch (StdDraw.nextKeyTyped()){
                case 'a':
                    role.walk(-1,0);
                    break;
                case 'w':
                    role.walk(0,1);
                    break;
                case 'd':
                    role.walk(1,0);
                    break;
                case 's':
                    role.walk(0,-1);
                    break;
                case ':':
                    if(StdDraw.nextKeyTyped() == 'q'){
                        System.out.println("Close!");
                        flag = false;
                    }
                    break;
            }
        }


    }


}
