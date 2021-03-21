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
        sut = new PersonLogic(personPersistence)
    }

    void "Create person: Successfully"() {
        given: 'A new person'
            Person newPerson = new Person(
                firstName: 'first name',
                surnames: 'surnames',
                lastSurname: 'last surname',
                sex: PersonSex.MALE,
                birthDate: LocalDate.now()
            )

        when:
            Person result = sut.createPerson(newPerson)

        then:
            result == new Person(
                id: 1L,
                firstName: 'first name',
                surnames: 'surnames',
                lastSurname: 'last surname',
                sex: PersonSex.MALE,
                birthDate: LocalDate.now()
            )
            1 * personPersistence.create(_) >> new PersonEntity(
                id: 1L,
                firstName: 'first name',
                surnames: 'surnames',
                lastSurname: 'last surname',
                sex: PersonSex.MALE,
                birthDate: LocalDate.now()
            )
    }
}
