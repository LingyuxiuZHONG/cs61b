package byow.Core;

public interface World {
    void create();
    void createRoom(int index);
    void createHallway(int r1,int r2);
    void wallBuildForRoom(int x,int y,int w,int h);
    void wallBuildForHallway(int r1,int r2);
}
