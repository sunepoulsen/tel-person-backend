package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persons

import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.PersonSex
import dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence.PersonEntity

import java.time.LocalDate

class PersonTestData {
    static PersonEntity createPersonEntity(Long id = null) {
        return new PersonEntity(
            id: id,
            firstName: 'first name',
            surnames: 'surnames',
            lastSurname: 'last surname',
            sex: PersonSex.MALE,
            birthDate: LocalDate.now()
        )
    }

    static Person createPerson(Long id = null) {
        return new Person(
            id: id,
            firstName: 'first name',
            surnames: 'surnames',
            lastSurname: 'last surname',
            sex: PersonSex.MALE,
            birthDate: LocalDate.now()
        )
    }
}
