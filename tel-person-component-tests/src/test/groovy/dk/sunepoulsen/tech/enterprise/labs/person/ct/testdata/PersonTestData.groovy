package dk.sunepoulsen.tech.enterprise.labs.person.ct.testdata

import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.PersonSex

import java.time.LocalDate

class PersonTestData {
    static Person createValid(Long id = null, String firstName = 'first name') {
        new Person(
            id: id,
            firstName: firstName,
            surnames: 'surnames',
            lastSurname: 'last surname',
            sex: PersonSex.MALE,
            birthDate: LocalDate.now()
        )
    }
}
