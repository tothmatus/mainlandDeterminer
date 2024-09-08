package com.toth.matus.utils;

import java.awt.geom.Point2D;
import java.util.List;

public class AreaCalculator {
    private AreaCalculator(){}
    public static Double calculatePolygonArea(List<Point2D> coordinates) {
        int n = coordinates.size();
        double area = 0;

        for (int i = 0; i < n - 1; i++) {
            Point2D current = coordinates.get(i);
            Point2D next = coordinates.get(i + 1);
            
            area += (current.getX() * next.getY()) - (current.getY() * next.getX());
        }
        Point2D first = coordinates.get(0);
        Point2D last = coordinates.get(n - 1);
        area += (last.getX() * first.getY()) - (last.getY() * first.getX());

        return Math.abs(area) / 2.0;
    }
}
