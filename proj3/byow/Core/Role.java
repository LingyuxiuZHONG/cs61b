package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Random;

public class Role {
    int x;
    int y;
    TETile[][] world;
    int w;
    int h;
    int coin;
    public Role(TETile[][] world,Random rd,int w,int h){
        int x;
        int y;
        do{
            x = rd.nextInt(w);
            y = rd.nextInt(h);
        }while (world[x][y] != Tileset.FLOOR);
        world[x][y] = Tileset.PERSON1;
        this.x = x;
        this.y = y;
        this.world = world;
        this.w = w;
        this.h = h;
    }
    public void walk(int x,int y){
        int nX = this.x + x;
        int nY = this.y + y;
        if(inWorld(nX,nY)){
            if(world[nX][nY] == Tileset.FLOOR) {
                world[this.x][this.y] = Tileset.FLOOR;
                world[this.x][this.y].draw(this.x, this.y);
                if(x == 1){
                    world[nX][nY] = Tileset.PERSON2;
                }else{
                    world[nX][nY] = Tileset.PERSON1;
                }
                world[nX][nY].draw(nX, nY);
                this.x = nX;
                this.y = nY;
            }else if(world[nX][nY] == Tileset.COIN){
                world[this.x][this.y] = Tileset.FLOOR;
                world[this.x][this.y].draw(this.x, this.y);
                if(x == 1){
                    world[nX][nY] = Tileset.PERSON2;
                }else{
                    world[nX][nY] = Tileset.PERSON1;
                }
                world[nX][nY].draw(nX, nY);
                this.x = nX;
                this.y = nY;
                coin++;
            }
        }
        StdDraw.setPenColor(Color.black);
        StdDraw.filledRectangle(0,h+2,w,2);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.line(0,h+1,w,h+1);
        StdDraw.textLeft(0, this.h+2, "HELLO!");
        StdDraw.textRight(w,h+2,"Coins: " + coin);
        StdDraw.show();
    }

    private boolean inWorld(int x,int y){
        if(x >=0 && x < w && y >= 0 && y < h){
            return true;
        }
        return false;
    }


}
