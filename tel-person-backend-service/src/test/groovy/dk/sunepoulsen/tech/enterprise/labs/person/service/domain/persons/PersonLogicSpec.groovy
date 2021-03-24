package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persons

import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.PersonSex
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonEntity
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonPersistence
import spock.lang.Specification

import java.time.LocalDate

class PersonLogicSpec extends Specification {

    private PersonPersistence personPersistence
    private PersonLogic sut

    void setup() {
        this.personPersistence = Mock(PersonPersistence)
        sut = new PersonLogic(personPersistence, new PersonTransformationService())
    }

    void "Create person: Successfully"() {
        given: 'A new person'
            Person newPerson = PersonTestData.createPerson()

        when:
            Person result = sut.createPerson(newPerson)

        then:
            result == PersonTestData.createPerson(1L)
            1 * personPersistence.create(PersonTestData.createPersonEntity()) >> PersonTestData.createPersonEntity(1L)
    }
}
