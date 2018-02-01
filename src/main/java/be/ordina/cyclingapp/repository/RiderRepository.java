package be.ordina.cyclingapp.repository;

import be.ordina.cyclingapp.domain.Rider;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Rider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {

}
