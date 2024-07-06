package thesis.zayarlinnhtet.volt_hunger.domain;

import java.util.ArrayList;
import java.util.List;

// Implement the KDTree with methods for nearest neighbor search and range search
public class KDTree {

    private static KDTree instance;
    private KDTreeNode root;

    private KDTree() {
        this.root = null;
    }

    public static KDTree getInstance() {
        if (instance == null) {
            instance = new KDTree();
        }
        return instance;
    }

    public void clear() {
        this.root = null;
    }

    // Method to insert a point into the KDTree
    public void insert(Point point) {
        this.root = insertRec(this.root, point, 0);
    }

    // Recursive method to insert a point into the KDTree
    private KDTreeNode insertRec(KDTreeNode node, Point point, int depth) {
        // If the tree is empty, create a new node
        if (node == null) {
            return new KDTreeNode(point);
        }

        // Calculate current dimension (latitude or longitude based on depth)
        int currentDimension = depth % 2; // Assuming 2D KDTree (latitude, longitude)

        // Compare the new point with the current node and decide the left or right subtree
        if (currentDimension == 0) { // Compare latitude
            if (point.latitude < node.point.latitude) {
                node.left = insertRec(node.left, point, depth + 1);
            } else {
                node.right = insertRec(node.right, point, depth + 1);
            }
        } else { // Compare longitude
            if (point.longitude < node.point.longitude) {
                node.left = insertRec(node.left, point, depth + 1);
            } else {
                node.right = insertRec(node.right, point, depth + 1);
            }
        }

        return node;
    }

    // Method to find all points within a given distance from a target point
    public List<Point> findPointsWithinDistance(double targetLatitude, double targetLongitude, double distanceMeters) {
        List<Point> pointsWithinDistance = new ArrayList<>();
        findPointsWithinDistanceRec(this.root, targetLatitude, targetLongitude, distanceMeters, pointsWithinDistance, 0);
        return pointsWithinDistance;
    }

    // Recursive method to find points within a given distance from a target point
    private void findPointsWithinDistanceRec(KDTreeNode node, double targetLatitude, double targetLongitude,
                                            double distanceMeters, List<Point> pointsWithinDistance, int depth) {
        if (node == null) {
            return;
        }

        // Calculate Haversine distance between target and current node
        double currentDistance = haversineDistance(targetLatitude, targetLongitude,
                node.point.latitude, node.point.longitude);

        // If within the distance, add to results
        if (currentDistance <= distanceMeters) {
            pointsWithinDistance.add(node.point);
        }

        // Determine which subtree to search next based on current dimension
        int currentDimension = depth % 2; // Assuming 2D KDTree (latitude, longitude)
        if (currentDimension == 0) { // Compare latitude
            if (targetLatitude - distanceMeters <= node.point.latitude) {
                findPointsWithinDistanceRec(node.left, targetLatitude, targetLongitude, distanceMeters, pointsWithinDistance, depth + 1);
            }
            if (targetLatitude + distanceMeters >= node.point.latitude) {
                findPointsWithinDistanceRec(node.right, targetLatitude, targetLongitude, distanceMeters, pointsWithinDistance, depth + 1);
            }
        } else { // Compare longitude
            if (targetLongitude - distanceMeters <= node.point.longitude) {
                findPointsWithinDistanceRec(node.left, targetLatitude, targetLongitude, distanceMeters, pointsWithinDistance, depth + 1);
            }
            if (targetLongitude + distanceMeters >= node.point.longitude) {
                findPointsWithinDistanceRec(node.right, targetLatitude, targetLongitude, distanceMeters, pointsWithinDistance, depth + 1);
            }
        }
    }

  // Method to find the nearest neighbor to a given target point
  public Point findNearestNeighbor(double targetLatitude, double targetLongitude) {
    return findNearestNeighborRec(this.root, targetLatitude, targetLongitude, 0, null);
  }

  // Recursive method to find the nearest neighbor
  private Point findNearestNeighborRec(KDTreeNode node, double targetLatitude, double targetLongitude, int depth, Point bestPoint) {
    if (node == null) {
      return bestPoint;
    }

    double currentDistance = haversineDistance(targetLatitude, targetLongitude,
      node.point.latitude, node.point.longitude);

    if (bestPoint == null || currentDistance < haversineDistance(targetLatitude, targetLongitude,
      bestPoint.latitude, bestPoint.longitude)) {
      bestPoint = node.point;
    }

    int currentDimension = depth % 2;
    KDTreeNode nextNode = null;
    KDTreeNode otherNode = null;

    if (currentDimension == 0) {
      if (targetLatitude < node.point.latitude) {
        nextNode = node.left;
        otherNode = node.right;
      } else {
        nextNode = node.right;
        otherNode = node.left;
      }
    } else {
      if (targetLongitude < node.point.longitude) {
        nextNode = node.left;
        otherNode = node.right;
      } else {
        nextNode = node.right;
        otherNode = node.left;
      }
    }

    bestPoint = findNearestNeighborRec(nextNode, targetLatitude, targetLongitude, depth + 1, bestPoint);

    if (otherNode != null) {
      if (currentDimension == 0) {
        if (Math.abs(targetLatitude - node.point.latitude) < haversineDistance(targetLatitude, targetLongitude,
          bestPoint.latitude, bestPoint.longitude)) {
          bestPoint = findNearestNeighborRec(otherNode, targetLatitude, targetLongitude, depth + 1, bestPoint);
        }
      } else {
        if (Math.abs(targetLongitude - node.point.longitude) < haversineDistance(targetLatitude, targetLongitude,
          bestPoint.latitude, bestPoint.longitude)) {
          bestPoint = findNearestNeighborRec(otherNode, targetLatitude, targetLongitude, depth + 1, bestPoint);
        }
      }
    }

    return bestPoint;
  }

    // Method to calculate Haversine distance between two points given their coordinates
    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Radius of the Earth in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c * 1000; // Convert to meters
    }

    // Example usage
//    public static void main(String[] args) {
//        KDTree kdTree = new KDTree();
//
//        // Insert points into the KDTree
//        kdTree.insert(new Point(40.7128, -74.0060)); // New York City
//        kdTree.insert(new Point(34.0522, -118.2437)); // Los Angeles
//        kdTree.insert(new Point(51.5074, -0.1278)); // London
//        kdTree.insert(new Point(48.8566, 2.3522)); // Paris
//
//        // Find all points within 5000 meters (5 kilometers) from a target point
//        List<Point> pointsWithinDistance = kdTree.findPointsWithinDistance(37.7749, -122.4194, 5000000);
//        for (Point point : pointsWithinDistance) {
//            System.out.println("Point within distance: " + point.latitude + ", " + point.longitude);
//        }
//
//        Point nearestNeighbor = kdTree.findNearestNeighbor(37.7749, -122.4194);
//        System.out.println("Point within distance: " + nearestNeighbor.latitude + ", " + nearestNeighbor.longitude);
//    }
}
