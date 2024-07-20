package thesis.zayarlinnhtet.volt_hunger.domain;

import java.util.Objects;

// Define a class to represent a point with latitude and longitude
public class Point {
  double latitude;
  double longitude;

  public Point(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point point = (Point) o;
    boolean result = Double.compare(latitude, point.latitude) == 0 && Double.compare(longitude, point.longitude) == 0;
    System.out.println(result);
    return result;
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitude, longitude);
  }

}
