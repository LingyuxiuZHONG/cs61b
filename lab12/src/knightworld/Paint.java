package knightworld;

import tileengine.TETile;
import tileengine.Tileset;

public class Paint implements SlideWindow{
    int holeSize;
    TETile[][] tiles;
    int width;
    int height;
    int rewidth;
    int reheight;


    Paint(int width, int height, int holeSize){
        this.holeSize = holeSize;
        this.width = width;
        this.height = height;
        rewidth = width / holeSize + 1;
        reheight = height / holeSize + 1;
        tiles = new TETile[width][height];
    }

    @Override
    public void work() {
        int index = 0;
        int confine = reheight * rewidth;
        while(index < confine){
            painting(index);
            index++;
        }
    }

    @Override
    public void painting(int index) {
        int[] xAy = pointer(index);
        if(judgeUsage(index) == 1){
            for(int x = xAy[0];x < (xAy[0] + holeSize < width ? xAy[0] + holeSize : width);x++){
                for(int y = xAy[1];y < (xAy[1] + holeSize < height ? xAy[1] + holeSize : height);y++){
                    tiles[x][y] = Tileset.LOCKED_DOOR;
                }
            }

        }else{
            for(int x = xAy[0];x < (xAy[0] + holeSize < width ? xAy[0] + holeSize : width);x++){
                for(int y = xAy[1];y < (xAy[1] + holeSize < height ? xAy[1] + holeSize : height);y++){
                    tiles[x][y] = Tileset.NOTHING;
                }
            }
        }
    }


    @Override
    public int judgeUsage(int index) {
        if(index % 5 == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public int[] pointer(int index) {
        int[] pointer = new int[2];
        pointer[0] = holeSize * (index % rewidth);
        pointer[1] = holeSize * (index / rewidth);

        return pointer;
    }


}
