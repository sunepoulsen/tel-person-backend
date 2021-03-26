package dk.sunepoulsen.tech.enterprise.labs.person.ct

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.http.HttpHelper
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.verification.HttpResponseVerificator
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError
import dk.sunepoulsen.tech.enterprise.labs.person.ct.testdata.PersonTestData
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person
import spock.lang.Specification

class GetPersonSpec extends Specification {

    private HttpHelper httpHelper

    void setup() {
        DeploymentSpockExtension.deployment.waitForAvailable()
        DeploymentSpockExtension.clearDatabase()

        this.httpHelper = new HttpHelper(DeploymentSpockExtension.deployment)
    }

    void "Get person that exists"() {
        given: 'A person was created successfully'
            HttpResponseVerificator createPersonVerificator = httpHelper.sendValidRequest(DeploymentSpockExtension.CONTAINER_NAME, HttpHelper.POST, '/persons', PersonTestData.createValid())
            createPersonVerificator.responseCode(201)

        when: 'Call GET /persons/{id}'
            Long personId = createPersonVerificator.bodyAsJsonOfType(Person).getId()
            HttpResponseVerificator verificator = httpHelper.sendValidRequest(DeploymentSpockExtension.CONTAINER_NAME, HttpHelper.GET, "/persons/${personId}")

        then: 'Response Code is 200'
            verificator.responseCode(200)

        and: 'Response body is json'
            verificator.bodyIsJson()

        and: 'Verify person body'
            verificator.bodyAsJsonOfType(Person) == PersonTestData.createValid(personId)
    }

    void "Get person with bad id"() {
        when: 'Call GET /persons/bad-id'
            HttpResponseVerificator verificator = httpHelper.sendValidRequest(DeploymentSpockExtension.CONTAINER_NAME, HttpHelper.GET, '/persons/bad-id')

        then: 'Response Code is 400'
            verificator.responseCode(400)

        and: 'Verify that body is empty'
            verificator.noBody()
    }

    void "Get person that does not exist"() {
        when: 'Call GET /persons/17'
            HttpResponseVerificator verificator = httpHelper.sendValidRequest(DeploymentSpockExtension.CONTAINER_NAME, HttpHelper.GET, '/persons/17')

        then: 'Response Code is 404'
            verificator.responseCode(404)

        and: 'Response body is json'
            verificator.bodyIsJson()

        and: 'Verify person body'
            verificator.bodyAsJsonOfType(ServiceError) == new ServiceError(
                param: 'id',
                message: 'No person with id exists'
            )
    }
}
