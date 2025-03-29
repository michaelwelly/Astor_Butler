package museon_online.astor_butler.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

    Location findByName(String name);

}
