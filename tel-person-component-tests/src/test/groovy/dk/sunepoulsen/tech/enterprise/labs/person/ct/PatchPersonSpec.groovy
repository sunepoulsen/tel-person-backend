package dk.sunepoulsen.tech.enterprise.labs.person.ct

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.http.HttpHelper
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.verification.HttpResponseVerificator
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError
import dk.sunepoulsen.tech.enterprise.labs.person.ct.testdata.PersonTestData
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person
import spock.lang.Specification

class PatchPersonSpec extends Specification {

    private HttpHelper httpHelper

    void setup() {
        DeploymentSpockExtension.deployment.waitForAvailable()
        DeploymentSpockExtension.clearDatabase()

        this.httpHelper = new HttpHelper(DeploymentSpockExtension.deployment)
    }

    void "Get person that exists"() {
        given: 'A person was created successfully'
            HttpResponseVerificator createPersonVerificator = httpHelper.createAndSendPostWithJson(DeploymentSpockExtension.CONTAINER_NAME, '/persons', PersonTestData.createValid())
            createPersonVerificator.responseCode(201)

            Long personId = createPersonVerificator.bodyAsJsonOfType(Person).getId()

            Person expected = PersonTestData.createValid(personId)
            expected.firstName = 'new value'

        when: 'Call GET /persons/{id}'
            HttpResponseVerificator verificator = httpHelper.createAndSendPatchWithJson(DeploymentSpockExtension.CONTAINER_NAME, "/persons/${personId}", new Person(
                firstName: 'new value'
            ))

        then: 'Response Code is 200'
            verificator.responseCode(200)

        and: 'Response body is json'
            verificator.bodyIsJson()

        and: 'Verify person body'
            verificator.bodyAsJsonOfType(Person) == expected
    }

    void "Patch person with bad id"() {
        when: 'Call PATCH /persons/bad-id'
            HttpResponseVerificator verificator = httpHelper.createAndSendPatchWithJson(DeploymentSpockExtension.CONTAINER_NAME, '/persons/bad-id', new Person(
                firstName: 'new value'
            ))

        then: 'Response Code is 400'
            verificator.responseCode(400)

        and: 'Verify that body is empty'
            verificator.noBody()
    }

    void "Get person that does not exist"() {
        when: 'Call GET /persons/17'
            HttpResponseVerificator verificator = httpHelper.createAndSendPatchWithJson(DeploymentSpockExtension.CONTAINER_NAME, '/persons/17', new Person(
                firstName: 'new value'
            ))

        then: 'Response Code is 404'
            verificator.responseCode(404)

        and: 'Response body is json'
            verificator.bodyIsJson()

        and: 'Verify person body'
            verificator.bodyAsJsonOfType(ServiceError) == new ServiceError(
                param: 'id',
                message: 'Person does not exist'
            )
    }
}
