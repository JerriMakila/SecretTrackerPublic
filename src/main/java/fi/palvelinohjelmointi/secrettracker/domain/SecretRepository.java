package fi.palvelinohjelmointi.secrettracker.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SecretRepository extends CrudRepository<Secret, Long> {
	List<Secret> findByToolId(Tool toolId);
}
