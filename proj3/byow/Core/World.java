package byow.Core;

public interface World {
    void createRoom(int index);
    void createHallway(int room1,int room2);
    void wallbuild(int x,int y,int w,int h);
    void create();
}
