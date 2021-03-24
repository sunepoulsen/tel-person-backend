package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persons

import dk.sunepoulsen.tech.enterprise.labs.core.service.domain.logic.ResourceViolationException
import dk.sunepoulsen.tech.enterprise.labs.core.service.domain.requests.ApiBadRequestException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class PersonControllerSpec extends Specification {
    private PersonLogic personLogic
    private PersonController sut

    void setup() {
        this.personLogic = Mock(PersonLogic)
        this.sut = new PersonController(personLogic)
    }

    void "POST /persons throws Api exception in case of a logic exception"() {
        when: 'Call POST /persons'
            sut.createPerson(PersonTestData.createPerson())

        then: 'Logic layer throws logic exception'
            thrown(ApiBadRequestException)
            1 * personLogic.createPerson(PersonTestData.createPerson()) >> {
                throw new ResourceViolationException("message")
            }
    }

    void "GET /persons throws Api exception in case of a logic exception"() {
        given: 'A pageable request'
            Pageable pageable = PageRequest.of(1, 20)

        when: 'Call GET /persons'
            sut.getPersons(pageable)

        then: 'Logic layer throws logic exception'
            thrown(ApiBadRequestException)
            1 * personLogic.findPersons(pageable) >> {
                throw new ResourceViolationException("message")
            }
    }
}
