import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int N;
    int opened;
    boolean[][] grid;
    WeightedQuickUnionUF uf;


    public Percolation(int N) {
        if(N <= 0){
            throw new IllegalArgumentException();
        }
        this.N = N;
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
    }

    public void open(int row, int col) {
        if(row < 0 || row > N - 1 || col < 0 || col > N - 1){
            throw new IndexOutOfBoundsException();
        }
        if(row == 0){
            uf.union(xyTo1D(row,col),N*N);
        }else if(row == N - 1){
            uf.union(xyTo1D(row,col),N*N+1);
        }
        grid[row][col] = true;
        opened++;
        connect(row,col);
    }

    private void connect(int row,int col){
        if(row - 1 >= 0 && grid[row-1][col]){
            uf.union(xyTo1D(row,col),xyTo1D(row-1,col));
        }
        if(col - 1 >= 0 && grid[row][col-1]){
            uf.union(xyTo1D(row,col),xyTo1D(row,col-1));
        }
        if(row + 1 < N && grid[row+1][col]){
            uf.union(xyTo1D(row,col),xyTo1D(row+1,col));
        }
        if(col + 1 < N && grid[row][col+1]){
            uf.union(xyTo1D(row,col),xyTo1D(row,col+1));
        }
    }

    public boolean isOpen(int row, int col) {
        if(row < 0 || row > N - 1 || col < 0 || col > N - 1){
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        if(row < 0 || row > N - 1 || col < 0 || col > N - 1){
            throw new IndexOutOfBoundsException();
        }
        return uf.connected(xyTo1D(row,col),N*N) || (uf.connected(xyTo1D(row,col),N*N+1) && percolates());
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        return uf.connected(N*N,N*N+1);
    }

    private int xyTo1D(int x, int y){
        return x * N + y;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1,3);
        p.open(0,3);
        p.open(1,2);
        p.open(2,2);
        p.open(3,2);

        System.out.println(p.percolates());

    }
}
