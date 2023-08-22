package byow.Core;

public class DisjointSet {
    private int[] rank;
    private int[] parent;
    public DisjointSet(int size){
        rank = new int[size];
        parent = new int[size];
        for(int i = 0;i < size;i++){
            rank[i] = 0;
            parent[i] = i;
        }
    }

    public int find(int x){
        if(parent[x] != x){
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public boolean union(int x,int y){
        int rootX = find(x);
        int rootY = find(y);
        if(rootX != rootY){
            if(rank[rootX] < rank[rootY]){
                parent[x] = rootY;
            }else if(rank[rootX] > rank[rootY]){
                parent[y] = rootX;
            }else{
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true;
        }
        return false;
    }
}
