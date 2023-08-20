package knightworld;

import tileengine.TERenderer;
import tileengine.TETile;

/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld extends Paint{


    public KnightWorld(int width, int height, int holeSize) {
        super(width,height,holeSize);
    }

    /** Returns the tiles associated with this KnightWorld. */
    public TETile[][] getTiles() {
        return tiles;
    }


    public static void main(String[] args) {
        // Change these parameters as necessary
        int width = 50;
        int height = 30;
        int holeSize = 4;

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        knightWorld.work();
        ter.renderFrame(knightWorld.getTiles());

    }
}
