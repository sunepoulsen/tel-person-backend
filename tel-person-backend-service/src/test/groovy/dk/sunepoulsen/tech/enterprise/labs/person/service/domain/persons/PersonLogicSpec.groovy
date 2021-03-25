package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persons

import dk.sunepoulsen.tech.enterprise.labs.core.service.domain.logic.ResourceNotFoundException
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonPersistence
import spock.lang.Specification

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

    void "Get person: Successfully"() {
        when:
            Person result = sut.getPerson(17L)

        then:
            result == PersonTestData.createPerson(17L)
            1 * personPersistence.findPerson(17L) >> Optional.of(PersonTestData.createPersonEntity(17L))
    }

    void "Get person: Person not found"() {
        when:
            sut.getPerson(17L)

        then:
            thrown(ResourceNotFoundException)
            1 * personPersistence.findPerson(17L) >> Optional.empty()
    }
}
