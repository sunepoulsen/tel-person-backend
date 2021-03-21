package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence

import dk.sunepoulsen.tech.enterprise.labs.helloworld.rs.client.model.PersonSex
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

    void "Check injection"() {
        expect:
            this.persistence
    }

    void "Create person: Success"() {
        when:
            PersonEntity entity = this.persistence.create(
                new PersonEntity(
                    firstName: 'first name',
                    surnames: 'surnames',
                    lastSurname: 'last surname',
                    sex: PersonSex.MALE,
                    birthDate: LocalDate.now()
                ))

        then:
            entity == new PersonEntity(
                id: entity.id,
                firstName: 'first name',
                surnames: 'surnames',
                lastSurname: 'last surname',
                sex: PersonSex.MALE,
                birthDate: LocalDate.now()
            )
    }

    @Unroll
    void "Create person: #_reason"() {
        when:
            this.persistence.create(_value)

        then:
            thrown(IllegalArgumentException)

        where:
            _value                    | _reason
            null                      | 'null value'
            new PersonEntity(id: 25L) | 'Id is not null'
    }
}
