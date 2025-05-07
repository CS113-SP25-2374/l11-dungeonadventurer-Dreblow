
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Explorer {
    char[][] map;
    LinkedList<Node> keyLocations = new LinkedList<>();
    LinkedList<LinkedList<Node>> totalPaths = new LinkedList<>();

    int[][] moves = {{-1,0}, {1,0}, {0, -1}, {0, 1}};

    public Explorer() {

    }

    public void scanWithUAV(char[][] map) {
        this.map = map;

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                char c = map[y][x];
                if (c != DungeonMap.OPEN && c != DungeonMap.WALL) {
                    // Found one item
                    Node n = new Node(x, y, c);
                    keyLocations.add(n);
                }
            }
        }

        System.out.println(keyLocations);
    }

    public void findPaths() {
        for (Node from : keyLocations) {
            for (Node to : keyLocations) {
                if (from.equals(to)) continue;
                LinkedList<Node> path = astar(from, to);
                totalPaths.add(path);
                System.out.println(path);
            }
        }
    }

    public LinkedList<Node> astar(Node start, Node end) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        LinkedList<Node> result = new LinkedList<>();
        PriorityQueue<Node> test = new PriorityQueue<>();

        test.add(start);
        start.calcH(end);
        visited[start.y][start.x] = true;

        while(!test.isEmpty()) {
            Node current = test.poll();
            if (current.equals(end)) {
                while(current != null) {
                    result.addFirst(current);
                    current = current.parent;
                }
                return result;
            }
            // keep going
            for (int i = 0; i < moves.length; i++) {
                int nextX = current.x + moves[i][0];
                int nextY = current.y + moves[i][1];
                char nextC = map[nextY][nextX];
                if (!visited[nextX][nextY] && nextC != DungeonMap.WALL) {
                    // found an empty node
                    visited[start.y][start.x] = true;
                    Node next = new Node(nextX, nextY, nextC);
                    next.g = current.g + 1;
                    next.calcH(end);
                    next.parent = current;
                    test.add(next);
                }
            }
        }

        return null;
    }

    class Edge implements Comparable<Edge> {
        LinkedList<Node> path;
        public Edge(LinkedList<Node> path) {
            this.path = path;
        }

        public String key() {
            assert path.peekFirst() != null;
            return path.peekFirst().item + "->" + path.peekLast().item;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.path.size(), o.path.size());
        }

        @Override
        public String toString() {
            return key() + "[]" + "";
        }
    }

    public void findMST() { //prims
        Set<Character> visited = new HashSet<>();
        Map<String, Edge> mst = new HashMap<>();
        
        Node current = keyLocations.peekFirst();
        visited.add(current.item);

        PriorityQueue<LinkedList<Node>> pq = new PriorityQueue<>();
        for (LinkedList<Node> edge : totalPaths) {
            if (edge.peek().equals(current)) {
                pq.add(new Edge(edge));
            }
        }

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            char item = edge.path.peekLast().item;
            if (visited.contains(item) continue;
            visited.add(item);
            mst.put(edge.key(), edge;)
            for (LinkedList<Node> nextEdges : totalPaths) {
                if (nextEdges.peekFirst().item == item) {
                    pq.add(new Edge(nextEdges));
                }
            }
        }
        System.out.println(mst.values());
    }
    
}
