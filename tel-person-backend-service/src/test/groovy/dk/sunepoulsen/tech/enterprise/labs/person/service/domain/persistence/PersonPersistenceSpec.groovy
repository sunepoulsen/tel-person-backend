package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence

import dk.sunepoulsen.tech.enterprise.labs.core.service.domain.logic.ResourceNotFoundException
import dk.sunepoulsen.tech.enterprise.labs.core.service.domain.logic.ResourceViolationException
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.PersonSex
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persons.PersonTestData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("ut")
class PersonPersistenceSpec extends Specification {

    @Autowired
    PersonPersistence persistence

    @Autowired
    PersonRepository repository

    void setup() {
        repository.deleteAll()
    }

    void "Create person: Success"() {
        when:
            PersonEntity entity = this.persistence.create(PersonTestData.createPersonEntity())

        then:
            entity == PersonTestData.createPersonEntity(entity.id)
    }

    @Unroll
    void "Create person: #_reason"() {
        when:
            this.persistence.create(_value)

        then:
            thrown(_throws)

        where:
            _value                                 | _throws                    | _reason
            null                                   | IllegalArgumentException   | 'null value'
            PersonTestData.createPersonEntity(25L) | ResourceViolationException | 'Id is not null'
    }

    @Unroll
    void "Patch person: #_propName"() {
        given: 'Person in the database'
            PersonEntity newPerson = PersonTestData.createPersonEntity()
            newPerson.sex = PersonSex.MALE

            PersonEntity createdPerson = this.persistence.create(newPerson)

        when: 'Patch first name'
            PersonEntity patchEntity = new PersonEntity()
            patchEntity[_propName] = _patchValue

            this.persistence.patchPerson(createdPerson.getId(), patchEntity)

            Optional<PersonEntity> foundEntity = this.persistence.findPerson(createdPerson.getId())
            PersonEntity expected = PersonTestData.createPersonEntity(createdPerson.getId())
            expected[_propName] = _patchValue

        then:
            !foundEntity.empty
            foundEntity.get() == expected

        where:
            _propName     | _patchValue
            'firstName'   | 'new text value'
            'surnames'    | 'new text value'
            'lastSurname' | 'new text value'
            'sex'         | PersonSex.FEMALE
            'birthDate'   | LocalDate.now().plusDays(7L)
    }

    @Unroll
    void "Patch person: #_reason"() {
        when:
            this.persistence.patchPerson(_id, _patch)

        then:
            thrown(_throws)

        where:
            _id  | _patch             | _throws                   | _reason
            null | new PersonEntity() | IllegalArgumentException  | 'id is null'
            1L   | null               | IllegalArgumentException  | 'person is null'
            1L   | new PersonEntity() | ResourceNotFoundException | 'id does not exist'
    }
}
