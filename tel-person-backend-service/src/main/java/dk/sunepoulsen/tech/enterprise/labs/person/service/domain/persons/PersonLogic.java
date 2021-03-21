package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persons;

import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person;
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonEntity;
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonPersistence;
import org.springframework.stereotype.Service;

@Service
public class PersonLogic {

    private final PersonPersistence personPersistence;

    public PersonLogic(PersonPersistence personPersistence) {
        this.personPersistence = personPersistence;
    }

    public Person createPerson(Person newPerson) {
        return fromEntity(personPersistence.create(toEntity(newPerson)));
    }

    private PersonEntity toEntity(Person person) {
        PersonEntity result = new PersonEntity();

        result.setId(person.getId());
        result.setFirstName(person.getFirstName());
        result.setSurnames(person.getSurnames());
        result.setLastSurname(person.getLastSurname());
        result.setSex(person.getSex());
        result.setBirthDate(person.getBirthDate());

        return result;
    }

    private Person fromEntity(PersonEntity entity) {
        Person result = new Person();

        result.setId(entity.getId());
        result.setFirstName(entity.getFirstName());
        result.setSurnames(entity.getSurnames());
        result.setLastSurname(entity.getLastSurname());
        result.setSex(entity.getSex());
        result.setBirthDate(entity.getBirthDate());

        return result;
    }
}
