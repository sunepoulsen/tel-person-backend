package dk.sunepoulsen.tech.enterprise.labs.person.ct

import com.google.common.net.MediaType
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.http.HttpHelper
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.verification.HttpResponseVerificator
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError
import dk.sunepoulsen.tech.enterprise.labs.person.ct.testdata.PersonTestData
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person
import spock.lang.Specification
import spock.lang.Unroll

import java.net.http.HttpRequest

/**
 * Component test of <code>POST /persons</code>
 */
class CreatePersonSpec extends Specification {

    private HttpHelper httpHelper

    void setup() {
        DeploymentSpockExtension.deployment.waitForAvailable()
        DeploymentSpockExtension.clearDatabase()

        this.httpHelper = new HttpHelper(DeploymentSpockExtension.deployment)
    }

    void "Create new person - 200"() {
        given: 'a person body'
            Person newPerson = PersonTestData.createValid()

        when: 'Call POST /persons'
            HttpResponseVerificator verificator = httpHelper.createAndSendPostWithJson(DeploymentSpockExtension.CONTAINER_NAME, '/persons', newPerson)

        then: 'Response Code is 201'
            verificator.responseCode(201)

        and: 'Response body is json'
            verificator.bodyIsJson()

        and: 'Verify person body'
            with(verificator.bodyAsJsonOfType(Person)) {
                id > 0
                firstName == newPerson.firstName
                surnames == newPerson.surnames
                lastSurname == newPerson.lastSurname
                sex == newPerson.sex
                birthDate == newPerson.birthDate
            }
    }

    void "Create new person - 400 - Person with id"() {
        given: 'a person body'
            Person newPerson = PersonTestData.createValid(37L)

        when: 'Call POST /persons'
            HttpResponseVerificator verificator = httpHelper.createAndSendPostWithJson(DeploymentSpockExtension.CONTAINER_NAME, '/persons', newPerson)

        then: 'Response Code is 400'
            verificator.responseCode(400)

        and: 'Response body is json'
            verificator.bodyIsJson()

        and: 'Verify person body'
            verificator.bodyAsJsonOfType(ServiceError) == new ServiceError(
                param: 'id',
                message: 'id must be null'
            )
    }

    @Unroll
    void "Create new person - #_responseCode - #_reason"() {
        when: 'Call POST /persons'
            HttpResponseVerificator verificator = httpHelper.createAndSendPostWithBody(DeploymentSpockExtension.CONTAINER_NAME,
                '/persons',
                _contentType,
                HttpRequest.BodyPublishers.ofString(_body)
            )

        then: 'Response Code is #_responseCode'
            verificator.responseCode(_responseCode)

        and: 'Response has no body'
            verificator.noBody()

        where:
            _contentType                    | _body               | _responseCode | _reason
            MediaType.JSON_UTF_8.toString() | '{"property":"27,}' | 400           | 'bad json'
            MediaType.JSON_UTF_8.toString() | '{"sex":"BAD_SEX"}' | 400           | 'valid json with bad enum'
            MediaType.HTML_UTF_8.toString() | '<html>'            | 415           | 'non json'
    }

}
