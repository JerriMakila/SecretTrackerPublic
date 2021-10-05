package fi.palvelinohjelmointi.secrettracker.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ToolRepository extends CrudRepository<Tool, Long> {
	List<Tool> findByTool(String tool);
	List<Tool> findByTooltypeId(ToolType tooltypeId);
}
