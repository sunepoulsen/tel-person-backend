package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persons;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.PaginationResult;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError;
import dk.sunepoulsen.tech.enterprise.labs.core.service.domain.logic.LogicException;
import dk.sunepoulsen.tech.enterprise.labs.person.rs.client.model.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Persons")
@RestController
@RequestMapping("/persons")
public class PersonController {

    private PersonLogic personLogic;

    public PersonController(PersonLogic personLogic) {
        this.personLogic = personLogic;
    }

    @ApiOperation(value = "Create a new person", response = Person.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
        @ApiResponse(response = ServiceError.class, code = 400, message = "The person body is not valid"),
        @ApiResponse(response = ServiceError.class, code = 404, message = "The person links to an unknown person")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Person createPerson(@RequestBody Person person) {
        try {
            return personLogic.createPerson(person);
        }
        catch (LogicException ex) {
            throw ex.mapApiException();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public PaginationResult<Person> getPersons(Pageable pageable) {
        try {
            return personLogic.findPersons(pageable);
        }
        catch (LogicException ex) {
            throw ex.mapApiException();
        }
    }

    @ApiOperation(value = "Returns a person by its id", response = Person.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
        @ApiResponse(response = ServiceError.class, code = 400, message = "The person id is not an id"),
        @ApiResponse(response = ServiceError.class, code = 404, message = "No person exists with the id")
    })
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    public Person getPerson(@PathVariable("id") Long id) {
        try {
            return personLogic.getPerson(id);
        }
        catch (LogicException ex) {
            throw ex.mapApiException();
        }
    }

}
