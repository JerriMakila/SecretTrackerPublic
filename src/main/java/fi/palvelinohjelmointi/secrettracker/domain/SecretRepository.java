package fi.palvelinohjelmointi.secrettracker.domain;

import org.springframework.data.repository.CrudRepository;

public interface SecretRepository extends CrudRepository<Secret, Long> {

}
