package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {
    public int x;
    public int y;
    public int z;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public List<Point> neighbours8() {
        List<Point> neighbours = new ArrayList<>();

        for (int ox = -1; ox < 2; ox++) {
            for (int oy = -1; oy < 2; oy++) {
                if ((ox == 0 && oy == 0)) continue;

                neighbours.add(new Point(x + ox, y + oy, z));
            }
        }
        Collections.shuffle(neighbours);
        return neighbours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;


        return x == point.x && y == point.y && z == point.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
