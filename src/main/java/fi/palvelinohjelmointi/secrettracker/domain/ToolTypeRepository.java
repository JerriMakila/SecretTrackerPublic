package fi.palvelinohjelmointi.secrettracker.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ToolTypeRepository extends CrudRepository<ToolType, Long> {
	List<ToolType> findByToolType(String toolType);
}
