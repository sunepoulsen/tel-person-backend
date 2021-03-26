package dk.sunepoulsen.tech.enterprise.labs.person.ct

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.http.HttpHelper
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.verification.HttpResponseVerificator
import spock.lang.Specification

import java.net.http.HttpRequest

class SwaggerSpec extends Specification {

    private HttpHelper httpHelper

    void setup() {
        this.httpHelper = new HttpHelper(DeploymentSpockExtension.deployment)
    }

    void "GET /swagger-ui.html returns OK"() {
        given: 'Health service is available'
            DeploymentSpockExtension.deployment.waitForAvailable(DeploymentSpockExtension.CONTAINER_NAME)

        when: 'Call GET /swagger-ui.html'
            HttpResponseVerificator verificator = httpHelper.sendValidRequest(DeploymentSpockExtension.CONTAINER_NAME, HttpHelper.GET, '/swagger-ui.html')

        then: 'Response Code is 200'
            verificator.responseCode(200)

        and: 'Content Type is text/html'
            verificator.contentType('text/html')
    }

}
