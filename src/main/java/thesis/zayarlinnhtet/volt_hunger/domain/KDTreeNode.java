package thesis.zayarlinnhtet.volt_hunger.domain;

// Define a class to represent a KDTree node
public class KDTreeNode {
  Point point;
  KDTreeNode left;
  KDTreeNode right;

  public KDTreeNode(Point point) {
    this.point = point;
    this.left = null;
    this.right = null;
  }
}
