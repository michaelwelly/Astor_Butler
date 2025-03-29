package museon_online.astor_butler.table;

import museon_online.astor_butler.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, UUID> {
    List<RestaurantTable> findByLocation(Location location);

}