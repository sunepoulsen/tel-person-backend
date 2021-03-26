package dk.sunepoulsen.tech.enterprise.labs.person.ct


import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.http.HttpHelper
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.verification.HttpResponseVerificator
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.PaginationResult
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.PaginationResultMetaData
import dk.sunepoulsen.tech.enterprise.labs.person.ct.testdata.PersonTestData
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Component test of <code>POST /persons</code>
 */
class GetPersonListSpec extends Specification {

    private HttpHelper httpHelper

    void setup() {
        DeploymentSpockExtension.deployment.waitForAvailable()
        DeploymentSpockExtension.clearDatabase()

        this.httpHelper = new HttpHelper(DeploymentSpockExtension.deployment)
    }

    void "Get empty list"() {
        when: 'Call GET /persons'
            HttpResponseVerificator verificator = httpHelper.sendValidRequest(DeploymentSpockExtension.CONTAINER_NAME, HttpHelper.GET, '/persons')

        then: 'Response Code is 200'
            verificator.responseCode(200)

        and: 'Response body is json'
            verificator.bodyIsJson()

        and: 'Verify person body'
            verificator.bodyAsJsonOfType(PaginationResult) == new PaginationResult<Person>(
                metadata: new PaginationResultMetaData(
                    page: 0,
                    totalPages: 0,
                    totalItems: 0,
                    size: 20
                ),
                results: []
            )
    }

    void "Get list of 2 persons out of 10 and verify person data"() {
        given: 'A database with 10 persons'
            createPersons(10)

        when: 'Call GET /persons'
            HttpResponseVerificator verificator = httpHelper.sendValidRequest(DeploymentSpockExtension.CONTAINER_NAME, HttpHelper.GET, '/persons?start=0&size=2')

        then: 'Response Code is 200'
            verificator.responseCode(200)

        and: 'Response body is json'
            verificator.bodyIsJson()

        and: 'Verify person body'
            PaginationResult<Person> result = verificator.bodyAsJsonOfType(PaginationResult)
            with(result) {
                metadata == new PaginationResultMetaData(
                    page: 0,
                    totalPages: 5,
                    totalItems: 10,
                    size: 2
                )
                results == [
                    PersonTestData.createValid(results[0].id, 'Name 1'),
                    PersonTestData.createValid(results[1].id, 'Name 2')
                ]
                results[0].id > 0
                results[1].id > 0
            }
    }

    @Unroll
    void "Get list and verify metadata with #_reason"() {
        given: 'A database with #_total persons'
            createPersons(_total)

        when: 'Call GET /persons'
            HttpResponseVerificator verificator = httpHelper.sendValidRequest(DeploymentSpockExtension.CONTAINER_NAME, HttpHelper.GET, "/persons?start=${_start}&size=${_size}")

        then: 'Response Code is 200'
            verificator.responseCode(200)

        and: 'Response body is json'
            verificator.bodyIsJson()

        and: 'Verify person body'
            with(verificator.bodyAsJsonOfType(PaginationResult)) {
                metadata == new PaginationResultMetaData(
                    page: _start,
                    totalPages: _totalPages,
                    totalItems: _total,
                    size: _size
                )
                results.size() == Math.min(_total, _size)
            }

        where:
            _total | _totalPages | _start | _size | _startParam | _sizeParam | _reason
            10     | 5           | 0      | 2     | '0'         | '2'        | 'valid page'
            10     | 5           | 0      | 2     | 'Q'         | '2'        | 'bad start'
            10     | 1           | 0      | 20    | '0'         | '!'        | 'bad size'
    }

    private void createPersons(int i) {
        (1..i).each {
            httpHelper.sendValidRequest(DeploymentSpockExtension.CONTAINER_NAME, HttpHelper.POST, '/persons',
                PersonTestData.createValid(null, 'Name ' + it)
            )
        }
    }

}
