package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence

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
            _value                    | _throws | _reason
            null                      | IllegalArgumentException | 'null value'
            new PersonEntity(id: 25L) | ResourceViolationException | 'Id is not null'
    }
}
