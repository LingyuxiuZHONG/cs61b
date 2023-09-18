package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class RandomWorld implements World{

    TETile[][] tiles;
    int width;
    int height;
    Random rd;
    DisjointSet disjointSet;
    Room[] rooms;

    RandomWorld(int width, int height, Random rd, TETile[][] tiles){
        this.tiles = tiles;
        this.width = width;
        this.height = height;
        this.rd = rd;
    }

    @Override
    public void create() {
        int times = rd.nextInt(1,5);
        disjointSet = new DisjointSet(times);
        rooms = new Room[times];
        for(int i = 0;i < times;i++){
            createRoom(i);
        }
        for(int i = 0;i < times-1;i++){
            if(disjointSet.union(i,i+1)){
                createHallway(i,i+1);
            }
        }
        coinPut();
    }
    private void coinPut(){
        int times = rd.nextInt(1,10);
        for(int i = 0;i < times;i++){
            int x = rd.nextInt(width);
            int y = rd.nextInt(height);
            do {
                x = rd.nextInt(width);
                y = rd.nextInt(height);
            }while (tiles[x][y] != Tileset.FLOOR);
            tiles[x][y] = Tileset.COIN;
        }

    }
    @Override
    public void createRoom(int index) {
        int x = rd.nextInt(1,width-1);
        int y = rd.nextInt(1,height-1);
        int w = rd.nextInt(1,10);
        int h = rd.nextInt(1,10);
        w = w + x < width-2 ? w : width-1-x;
        h = h + y < height-2 ? h : height-1-y;
        for(int i = x;i < x+w;i++){
            for(int j = y;j < y+h;j++){
                tiles[i][j] = Tileset.FLOOR;
            }
        }
        rooms[index] = new Room(x,y,w,h);
        wallBuildForRoom(x,y,w,h);
    }


    @Override
    public void createHallway(int r1,int r2) {
        int w = rd.nextInt(1,2);
        wallBuildForHallway(r1,r2);
    }


    @Override
    public void wallBuildForRoom(int x,int y,int w,int h) {
        for(int i = x-1;i <= x+w;i++){
            tiles[i][y-1] = tiles[i][y-1] == Tileset.FLOOR ? Tileset.FLOOR :Tileset.WALL;
            tiles[i][y+h] = tiles[i][y+h] == Tileset.FLOOR ? Tileset.FLOOR :Tileset.WALL;
        }
        for(int i = y-1;i <= y+h;i++){
            tiles[x-1][i] = tiles[x-1][i] == Tileset.FLOOR ? Tileset.FLOOR :Tileset.WALL;
            tiles[x+w][i] = tiles[x+w][i] == Tileset.FLOOR ? Tileset.FLOOR :Tileset.WALL;
        }
    }

    @Override
    public void wallBuildForHallway(int r1,int r2){
        mod(r1,r2);
    }

    /** the beginning point and end point for the hallway  */
    private int[] randomWallPoint(int r,int mod){
        int[] xAy = new int[2];
        switch(mod){
            case 0://left
                xAy[0] = rooms[r].x-1;
                xAy[1] = rd.nextInt(rooms[r].y,rooms[r].y+rooms[r].h);
                break;
            case 1://bottom
                xAy[0] = rd.nextInt(rooms[r].x,rooms[r].x+rooms[r].w);
                xAy[1] = rooms[r].y-1;
                break;
            case 2://right
                xAy[0] = rooms[r].x+rooms[r].w;
                xAy[1] = rd.nextInt(rooms[r].y,rooms[r].y+rooms[r].h);
                break;
            case 3://top
                xAy[0] = rd.nextInt(rooms[r].x,rooms[r].x+rooms[r].w);
                xAy[1] = rooms[r].y+rooms[r].h;
                break;
        }
        if(!judge(xAy)){
            xAy = randomWallPoint(r,mod);
        }
        return xAy;
    }
    /** if the beginning point or the end point is the edge point */
    private boolean judge(int[] xAy){
        if(xAy[0] == 0 || xAy[0]==width-1 || xAy[1]==0 ||xAy[1]==height-1){
            return false;
        }
        return true;
    }

    private TETile floorOrWall(int x,int y){
        return tiles[x][y] == Tileset.FLOOR ? Tileset.FLOOR : Tileset.WALL;
    }

    /** choose mod of hallway for rooms */
    private void mod(int r1,int r2){
        int[] xy1;
        int[] xy2;
        if(rooms[r1].x < rooms[r2].x){
            if(rooms[r1].y < rooms[r2].y){
                xy1 = randomWallPoint(r1,2);
                xy2 = randomWallPoint(r2,1);
                for(int i = xy1[0];i <= xy2[0];i++){
                    tiles[i][xy1[1]] = Tileset.FLOOR;
                    tiles[i][xy1[1]+1] = floorOrWall(i,xy1[1]+1);
                    tiles[i][xy1[1]-1] = floorOrWall(i,xy1[1]-1);
                }
                tiles[xy2[0]+1][xy1[1]] = Tileset.WALL;
                for(int i = xy1[1]+1;i <= xy2[1];i++){
                    tiles[xy2[0]][i] = Tileset.FLOOR;
                    tiles[xy2[0]-1][i] = floorOrWall(xy2[0]-1,i);
                    tiles[xy2[0]+1][i] = floorOrWall(xy2[0]+1,i);
                }
            }else if(rooms[r1].y > rooms[r2].y){
                xy1 = randomWallPoint(r1,2);
                xy2 = randomWallPoint(r2,3);
                for(int i = xy1[0];i <= xy2[0];i++){
                    tiles[i][xy1[1]] = Tileset.FLOOR;
                    tiles[i][xy1[1]+1] = floorOrWall(i,xy1[1]+1);
                    tiles[i][xy1[1]-1] = floorOrWall(i,xy1[1]-1);
                }
                tiles[xy2[0]+1][xy1[1]] = Tileset.WALL;
                for(int i = xy1[1]-1;i >= xy2[1];i--){
                    tiles[xy2[0]][i] = Tileset.FLOOR;
                    tiles[xy2[0]-1][i] = floorOrWall(xy2[0]-1,i);
                    tiles[xy2[0]+1][i] = floorOrWall(xy2[0]+1,i);
                }
            }else{
                xy1 = randomWallPoint(r1,2);
                xy2 = new int[]{rooms[r2].x-1, xy1[1]};
                for(int i = xy1[0];i <= xy2[0];i++){
                    tiles[i][xy1[1]] = Tileset.FLOOR;
                    tiles[i][xy1[1]+1] = floorOrWall(i,xy1[1]+1);
                    tiles[i][xy1[1]-1] = floorOrWall(i,xy1[1]-1);
                }
            }
        }else if(rooms[r1].x > rooms[r2].x){
            if(rooms[r1].y < rooms[r2].y){
                xy1 = randomWallPoint(r1,0);
                xy2 = randomWallPoint(r2,1);
                for(int i = xy1[0];i >= xy2[0];i--){
                    tiles[i][xy1[1]] = Tileset.FLOOR;
                    tiles[i][xy1[1]+1] = floorOrWall(i,xy1[1]+1);
                    tiles[i][xy1[1]-1] = floorOrWall(i,xy1[1]-1);
                }
                tiles[xy2[0]-1][xy1[1]] = Tileset.WALL;
                for(int i = xy1[1]+1;i <= xy2[1];i++){
                    tiles[xy2[0]][i] = Tileset.FLOOR;
                    tiles[xy2[0]-1][i] = floorOrWall(xy2[0]-1,i);
                    tiles[xy2[0]+1][i] = floorOrWall(xy2[0]+1,i);
                }
            }else if(rooms[r1].y > rooms[r2].y){
                xy1 = randomWallPoint(r1,0);
                xy2 = randomWallPoint(r2,3);
                for(int i = xy1[0];i >= xy2[0];i--){
                    tiles[i][xy1[1]] = Tileset.FLOOR;
                    tiles[i][xy1[1]+1] = floorOrWall(i,xy1[1]+1);
                    tiles[i][xy1[1]-1] = floorOrWall(i,xy1[1]-1);
                }
                tiles[xy2[0]-1][xy1[1]] = Tileset.WALL;
                for(int i = xy1[1]-1;i >= xy2[1];i--){
                    tiles[xy2[0]][i] = Tileset.FLOOR;
                    tiles[xy2[0]-1][i] = floorOrWall(xy2[0]-1,i);
                    tiles[xy2[0]+1][i] = floorOrWall(xy2[0]+1,i);
                }
            }else{
                xy1 = randomWallPoint(r1,0);
                xy2 = new int[]{rooms[r2].x+1,xy1[1]};
                for(int i = xy1[0];i >= xy2[0];i--){
                    tiles[i][xy1[1]] = Tileset.FLOOR;
                    tiles[i][xy1[1]+1] = floorOrWall(i,xy1[1]+1);
                    tiles[i][xy1[1]-1] = floorOrWall(i,xy1[1]-1);
                }
            }
        }else{
            if(rooms[r1].y < rooms[r2].y){
                if(rooms[r1].w <= rooms[r2].w){
                    xy1 = randomWallPoint(r1,3);
                    xy2 = new int[]{xy1[0],rooms[r2].h-1};
                }else{
                    xy2 = randomWallPoint(r1,1);
                    xy1 = new int[]{xy2[0],rooms[r1].h+1};
                }
                xy1 = randomWallPoint(r1,3);
                xy2 = randomWallPoint(r2,1);
                for(int i = xy1[1]-1;i <= xy2[1];i++){
                    tiles[xy2[0]][i] = Tileset.FLOOR;
                    tiles[xy2[0]-1][i] = floorOrWall(xy2[0]-1,i);
                    tiles[xy2[0]+1][i] = floorOrWall(xy2[0]+1,i);
                }
            }else{
                if(rooms[r1].w <= rooms[r2].w){
                    xy1 = randomWallPoint(r1,3);
                    xy2 = new int[]{xy1[0],rooms[r2].h+1};
                }else{
                    xy2 = randomWallPoint(r1,1);
                    xy1 = new int[]{xy2[0],rooms[r1].h-1};
                }
                xy1 = randomWallPoint(r1,1);
                xy2 = randomWallPoint(r2,3);
                for(int i = xy1[1]-1;i >= xy2[1];i--){
                    tiles[xy2[0]][i] = Tileset.FLOOR;
                    tiles[xy2[0]-1][i] = floorOrWall(xy2[0]-1,i);
                    tiles[xy2[0]+1][i] = floorOrWall(xy2[0]+1,i);
                }
            }
        }
    }


}
