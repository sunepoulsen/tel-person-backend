package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component to access the database.
 * <p>
 *     All operations operates on @Entity classes and contains
 *     the logic to ensure the integrity of the database.
 * </p>
 * <p>
 *     All interaction with the database should go thought this component.
 *     It will not be necessary to access the database thought the
 *     @Repository classes.
 * </p>
 */
@Component
public class PersonPersistence {
    private PersonRepository repository;

    @Autowired
    public PersonPersistence(PersonRepository repository ) {
        this.repository = repository;
    }

    /**
     * Creates a new person in the database.
     *
     * @param entity The entity to create in the database.
     *
     * @return The entity after it has been updated with primary key from the database.
     */
    public PersonEntity create( PersonEntity entity ) {
        if( entity == null ) {
            throw new IllegalArgumentException("Entity can not by null");
        }
        if( entity.getId() != null ) {
            throw new IllegalArgumentException( "Can not create a person with a person id" );
        }

        return repository.save( entity );
    }
}
