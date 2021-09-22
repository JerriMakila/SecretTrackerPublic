package fi.palvelinohjelmointi.secrettracker.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
	List<Location> findByLocation(String location);
}
