package org.wololo.jts2geojson;

import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateArrays;

public class GeoJsonCoordinateUtilities {

    /**
     * Rotates if necessary JTS coordinates for conversion to a GeoJson object. This is required as GeoJson expects
     * outer contours to be anti-clockwise and holes to be clockwise. JTS expects outer contours to be clockwise and
     * holes to be anti-clockwise
     * 
     * @param coordinates
     *            the polygon coordinates from a JTS object
     * @param representsHole
     *            true if the coordinates represent a hole.
     * @return polygon coordinates ready to be applied to GeoJson objects
     */
    public static Coordinate[] correctPolygonCoordinateRotation(final Coordinate[] coordinates,
            final boolean representsHole) {

        if (isAntiClockwise(coordinates)) {
            // If the coordiantes are anti-clockwise, but they should represent a hole. Reverse their direction.
            if (representsHole) {
                CoordinateArrays.reverse(coordinates);
            }
        } else {
            // If the coordiantes are clockwise, but they should not represent a hole. Reverse their direction.
            if (!representsHole) {
                CoordinateArrays.reverse(coordinates);
            }
        }

        return coordinates;
    }

    /**
     * Checks if the co-ordinates are anti-clockwise.
     * 
     * @param coordinates
     *            The co-ordinates to test.
     * @return True if it is anti-clockwise.
     */
    public static boolean isAntiClockwise(final Coordinate[] coordinates) {
        return CGAlgorithms.isCCW(coordinates);
    }
}
