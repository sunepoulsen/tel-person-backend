package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persons;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.PaginationResult;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.transformations.PaginationTransformations;
import dk.sunepoulsen.tech.enterprise.labs.core.service.domain.logic.ResourceNotFoundException;
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person;
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonEntity;
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonPersistence;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Person getPerson(Long id) {
        Optional<PersonEntity> optionalEntity = personPersistence.findPerson(id);
        if( optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException("id", "No person with id exists");
        }

        return personTransformationService.fromEntity(optionalEntity.get());
    }

    public Person patchPerson(Long id, Person patchValue) {
        return personTransformationService.fromEntity(personPersistence.patchPerson(id, personTransformationService.toEntity(patchValue)));
    }
}
