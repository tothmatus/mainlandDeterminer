package utils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class CoordinatesParser {

    private CoordinatesParser(){}

    public static Optional<List<Point2D>> parse(String input){
        List<Point2D> point2DList = new ArrayList<>();
        String[] points = input.split(":");
        for (String point : points) {
            String[] coordinates = point.trim().split(" ");

            if (coordinates.length == 2) {
                try {
                    double x = Double.parseDouble(coordinates[0]);
                    double y = Double.parseDouble(coordinates[1]);
                    Point2D point2D = new Point2D.Double(x, y);
                    point2DList.add(point2D);
                } catch (NumberFormatException e) {
                    log.log(Level.SEVERE, "Invalid coordinate format: {0}", point);
                    return Optional.empty();
                }
            } else {
                log.log(Level.SEVERE, "Invalid point format: {0}", point);
                return Optional.empty();
            }
        }
        return Optional.of(point2DList);
    }
}
