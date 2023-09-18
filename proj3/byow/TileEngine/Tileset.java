package byow.TileEngine;

import org.checkerframework.checker.units.qual.C;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {

    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall","blocks/wall.png");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor","blocks/woodenfloor.png");
    public static final TETile PINELEAVES = new TETile('a',Color.white,Color.white,"pine_leaves","blocks/pine_leaves.png");
    public static final TETile GRASSTOP = new TETile('b',Color.white,Color.white,"grass_top","blocks/grass_top.png");
    public static final TETile GRASS1 = new TETile('c',Color.green,Color.white,"grass1","blocks/grass1.jpg");
    public static final TETile GRASS2 = new TETile('"', Color.green, Color.black, "grass2","blocks/oak_leaves.png");
    public static final TETile FLOWER1 = new TETile('d',Color.green,Color.GREEN,"flower1","blocks/flower1.jpg");
    public static final TETile FLOWER2 = new TETile('e',Color.green,Color.white,"flower2","blocks/flower2.jpg");
    public static final TETile FLOWER3 = new TETile('f',Color.green,Color.white,"flower3","blocks/flower3.png");
    public static final TETile MUSHROOM1 = new TETile('g', Color.white,Color.green,"mushroom1","blocks/mushroom1.png");
    public static final TETile MUSHROOM2 = new TETile('h', Color.white,Color.green,"mushroom2","blocks/mushroom2.png");
    public static final TETile COIN = new TETile('m',Color.black,Color.orange,"coin","blocks/coin.png");

    public static final TETile PERSON1 = new TETile('n',Color.black,Color.orange,"person1","blocks/person_left.png");
    public static final TETile PERSON2 = new TETile('z',Color.black,Color.orange,"person2","blocks/person_right.png");

}


