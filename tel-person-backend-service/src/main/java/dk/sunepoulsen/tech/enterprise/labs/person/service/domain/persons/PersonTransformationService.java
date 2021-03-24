package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persons;

import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person;
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonEntity;
import org.springframework.stereotype.Service;

@Service
public class PersonTransformationService {
    public PersonEntity toEntity(Person person) {
        PersonEntity result = new PersonEntity();

        result.setId(person.getId());
        result.setFirstName(person.getFirstName());
        result.setSurnames(person.getSurnames());
        result.setLastSurname(person.getLastSurname());
        result.setSex(person.getSex());
        result.setBirthDate(person.getBirthDate());

        return result;
    }

    public Person fromEntity(PersonEntity entity) {
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
