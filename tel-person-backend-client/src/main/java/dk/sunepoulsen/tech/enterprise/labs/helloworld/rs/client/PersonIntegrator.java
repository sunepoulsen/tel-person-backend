package dk.sunepoulsen.tech.enterprise.labs.helloworld.rs.client;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.AbstractIntegrator;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.TechEnterpriseLabsClient;
import dk.sunepoulsen.tech.enterprise.labs.helloworld.rs.client.model.Person;
import io.reactivex.Single;

public class PersonIntegrator extends AbstractIntegrator {
    public PersonIntegrator(TechEnterpriseLabsClient httpClient) {
        super(httpClient);
    }

    public Single<Person> create(Person person) {
        return Single.fromFuture(httpClient.post( "/persons", person, Person.class))
                .onErrorResumeNext(this::mapClientExceptions);
    }
}
