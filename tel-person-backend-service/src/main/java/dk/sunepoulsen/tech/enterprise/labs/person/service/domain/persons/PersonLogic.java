package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persons;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.PaginationResult;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.transformations.PaginationTransformations;
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person;
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonPersistence;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonLogic {

    private final PersonPersistence personPersistence;
    private final PersonTransformationService personTransformationService;

    public PersonLogic(PersonPersistence personPersistence, PersonTransformationService personTransformationService) {
        this.personPersistence = personPersistence;
        this.personTransformationService = personTransformationService;
    }

    public Person createPerson(Person newPerson) {
        return personTransformationService.fromEntity(personPersistence.create(personTransformationService.toEntity(newPerson)));
    }

    public PaginationResult<Person> findPersons(Pageable pageable) {
        return PaginationTransformations.toPaginationResult(personPersistence.findAll(pageable)
                .map(personTransformationService::fromEntity)
        );
    }
}
