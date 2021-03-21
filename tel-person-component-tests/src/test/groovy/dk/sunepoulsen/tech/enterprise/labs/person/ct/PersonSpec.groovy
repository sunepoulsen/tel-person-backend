package dk.sunepoulsen.tech.enterprise.labs.person.ct

import com.google.common.net.MediaType
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.http.HttpHelper
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.verification.HttpResponseVerificator
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.utils.JsonUtils
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.PersonSex
import spock.lang.Specification

import java.net.http.HttpRequest
import java.time.LocalDate

class PersonSpec extends Specification {

    void "Create new person - OK and created"() {
        given: 'Person service is available'
            DeploymentSpockExtension.deployment.waitForAvailable(DeploymentSpockExtension.CONTAINER_NAME)

        and: 'a person body'
            Person newPerson = new Person(
                    firstName: 'first name',
                    surnames: 'surnames',
                    lastSurname: 'last surname',
                    sex: PersonSex.MALE,
                    birthDate: LocalDate.now()
            )

        when: 'Call POST /persons'
            HttpHelper httpHelper = new HttpHelper(DeploymentSpockExtension.deployment)
            HttpRequest httpRequest = httpHelper.newRequestBuilder(DeploymentSpockExtension.CONTAINER_NAME, '/persons')
                .POST(HttpRequest.BodyPublishers.ofString(JsonUtils.encodeAsJson(newPerson)))
                .header('Content-Type', MediaType.JSON_UTF_8.toString())
                .header('X-Request-ID', UUID.randomUUID().toString())
                .build()

        HttpResponseVerificator verificator = httpHelper.sendRequest(httpRequest)

        then: 'Response Code is 201'
            verificator.responseCode(201)

        and: 'Response body is json'
            verificator.bodyIsJson()

        and: 'Verify greetings body'
            verificator.bodyAsJson() == [
                firstName: newPerson.firstName,
                surnames: newPerson.surnames,
                lastSurname: newPerson.lastSurname,
                sex: newPerson.sex,
                birthDate: newPerson.birthDate
            ]
    }

}
