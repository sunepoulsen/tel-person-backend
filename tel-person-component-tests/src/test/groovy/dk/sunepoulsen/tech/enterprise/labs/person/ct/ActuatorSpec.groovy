package dk.sunepoulsen.tech.enterprise.labs.person.ct

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.http.HttpHelper
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.verification.HttpResponseVerificator
import spock.lang.Specification

import java.net.http.HttpRequest

class ActuatorSpec extends Specification {

    void "GET /actuator/health returns OK"() {
        given: 'Health service is available'
            DeploymentSpockExtension.deployment.waitForAvailable(DeploymentSpockExtension.CONTAINER_NAME)

        when: 'Call GET /actuator/health'
            HttpHelper httpHelper = new HttpHelper(DeploymentSpockExtension.deployment)
            HttpRequest httpRequest = httpHelper.newRequestBuilder(DeploymentSpockExtension.CONTAINER_NAME, '/actuator/health')
                .GET()
                .build()

            HttpResponseVerificator verificator = httpHelper.sendRequest(httpRequest)

        then: 'Response Code is 200'
            verificator.responseCode(200)

        and: 'Content Type is application/json'
            verificator.contentType('application/vnd.spring-boot.actuator.v3+json')

        and: 'Response body is json'
            verificator.bodyIsJson()

        and: 'Verify greetings body'
            verificator.bodyAsJson() == [
                'status': 'UP'
            ]
    }

}
