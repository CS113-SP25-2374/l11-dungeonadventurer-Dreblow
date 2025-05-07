
import static java.lang.Math.abs;

public class Node implements Comparable<Node>{
    int x, y, g, h;
    char item;
    boolean visited;
    Node parent;

    public Node(int x, int y, char item) {
        this.x = x;
        this.y = y;
        this.item = item;
        this.visited = false;
    }


    public int calcH(Node goal) { 
        int dx = abs(this.x - goal.x);
        int dy = abs(this.y - goal.y);
        
        return dx + dy;
    }

    public int f() { return g + h; }
    
    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.f(), o.f());
    }

    @Override   
    public String toString() {
        return "" + item + "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node other)) return false;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return x * y + h + g;
    }

}
