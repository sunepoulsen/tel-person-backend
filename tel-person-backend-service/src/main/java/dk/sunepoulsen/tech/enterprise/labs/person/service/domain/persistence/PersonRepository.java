package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
}
