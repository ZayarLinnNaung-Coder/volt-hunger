package thesis.zayarlinnhtet.volt_hunger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thesis.zayarlinnhtet.volt_hunger.domain.KDTree;
import thesis.zayarlinnhtet.volt_hunger.domain.Point;
import thesis.zayarlinnhtet.volt_hunger.entity.Location;
import thesis.zayarlinnhtet.volt_hunger.repo.LocationRepo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/locations")
@CrossOrigin
public class LocationController {

  private final LocationRepo locationRepo;

    public LocationController(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    @GetMapping("/reload")
    List<Location> fetchLocation() {
      List<Location> location = locationRepo.findAll();

      KDTree instance = KDTree.getInstance();
      instance.clear();

      location.forEach(loc -> instance.insert(new Point(loc.getLat(), loc.getLng())));

      return location;
    }

    @GetMapping
    List<Location> fetchAllLocations() {
      return locationRepo.findAllByOrderByNameAsc();
    }

    @GetMapping("/nearest")
    Point getNearestPoint(@RequestParam double lat, @RequestParam double lng) {
      return KDTree.getInstance().findNearestNeighbor(lat, lng);
    }

    @GetMapping("/within-distance")
    List<Point> fetchPointsWithinDistance(@RequestParam double lat, @RequestParam double lng, @RequestParam double distance) {
        List<Point> pointsWithinDistance = KDTree.getInstance().findPointsWithinDistance(lat, lng, distance * 1000);
        Point nearestPoint = KDTree.getInstance().findNearestNeighbor(lat, lng);
        List<Point> result = pointsWithinDistance.stream().filter(p -> p.getLatitude() != nearestPoint.getLatitude() && p.getLongitude() != nearestPoint.getLongitude()).collect(Collectors.toList());
        return result;
    }

    @PostMapping
    ResponseEntity<Location> saveLocation(@RequestBody Location request) {
      Location location = locationRepo.save(request);
      KDTree.getInstance().insert(new Point(location.getLat(), location.getLng()));
      return ResponseEntity.ok(location);
    }

}
