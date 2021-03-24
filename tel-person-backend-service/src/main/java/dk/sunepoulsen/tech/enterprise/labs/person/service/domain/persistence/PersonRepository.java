package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends PagingAndSortingRepository<PersonEntity, Long> {
}
