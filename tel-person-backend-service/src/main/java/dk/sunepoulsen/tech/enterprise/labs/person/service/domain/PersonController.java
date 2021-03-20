package dk.sunepoulsen.tech.enterprise.labs.person.service.domain;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError;
import dk.sunepoulsen.tech.enterprise.labs.helloworld.rs.client.model.Person;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Persons")
@RestController(value = "/persons")
public class PersonController {

    @ApiOperation(value = "Create a new person", response = Person.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
        @ApiResponse(response = ServiceError.class, code = 400, message = "The person body is not valid"),
        @ApiResponse(response = ServiceError.class, code = 404, message = "The person links to an unknown person")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Person createPerson(@RequestBody Person person) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
