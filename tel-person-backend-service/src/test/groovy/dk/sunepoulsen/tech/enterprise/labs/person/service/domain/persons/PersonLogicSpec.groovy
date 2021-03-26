package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persons

import dk.sunepoulsen.tech.enterprise.labs.core.service.domain.logic.ResourceNotFoundException
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonEntity
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonPersistence
import spock.lang.Specification

class PersonLogicSpec extends Specification {

    private PersonPersistence personPersistence
    private PersonTransformationService transformationService
    private PersonLogic sut

    void setup() {
        this.personPersistence = Mock(PersonPersistence)
        this.transformationService = Mock(PersonTransformationService)
        sut = new PersonLogic(personPersistence, transformationService)
    }

    void "Create person: Successfully"() {
        given: 'A new person'
            Person newPerson = PersonTestData.createPerson()

        when:
            Person result = sut.createPerson(newPerson)

        then:
            result == PersonTestData.createPerson(1L)
            1 * transformationService.toEntity(newPerson) >> PersonTestData.createPersonEntity()
            1 * personPersistence.create(PersonTestData.createPersonEntity()) >> PersonTestData.createPersonEntity(1L)
            1 * transformationService.fromEntity(PersonTestData.createPersonEntity(1L)) >> PersonTestData.createPerson(1L)
    }

    void "Get person: Successfully"() {
        when:
            Person result = sut.getPerson(17L)

        then:
            result == PersonTestData.createPerson(17L)
            1 * personPersistence.findPerson(17L) >> Optional.of(PersonTestData.createPersonEntity(17L))
            1 * transformationService.fromEntity(PersonTestData.createPersonEntity(17L)) >> PersonTestData.createPerson(17L)
    }

    void "Get person: Person not found"() {
        when:
            sut.getPerson(17L)

        then:
            thrown(ResourceNotFoundException)
            1 * personPersistence.findPerson(17L) >> Optional.empty()
            0 * transformationService._
    }

    void "Patch person successfully"() {
        given:
            Person patchValue = new Person(firstName: 'new value')
            PersonEntity patchEntityValue = new PersonEntity(firstName: 'new value')

            PersonEntity patchedEntity = PersonTestData.createPersonEntity(17L)
            patchedEntity.firstName = patchValue.firstName

            Person patchedPerson = PersonTestData.createPerson(17L)
            patchedPerson.firstName = patchValue.firstName

        when:
            Person result = sut.patchPerson(17L, new Person(firstName: 'new value'))

        then:
            result == patchedPerson
            1 * transformationService.toEntity(patchValue) >> patchEntityValue
            1 * personPersistence.patchPerson(17L, patchEntityValue) >> patchedEntity
            1 * transformationService.fromEntity(patchedEntity) >> patchedPerson
    }
}
