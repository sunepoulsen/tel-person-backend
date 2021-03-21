package dk.sunepoulsen.tech.enterprise.labs.person.service.domain.persistence;

import dk.sunepoulsen.tech.enterprise.labs.helloworld.rs.client.model.PersonSex;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table( name = "persons" )
@Data
public class PersonEntity {
    /**
     * Primary key.
     */
    @Id
    @SequenceGenerator( name = "persons_id_seq", sequenceName = "persons_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "persons_id_seq" )
    @Column( name = "person_id" )
    private Long id;

    @Column( name = "first_name" )
    private String firstName;

    @Column( name = "surnames" )
    private String surnames;

    @Column( name = "last_surname" )
    private String lastSurname;

    @Enumerated(EnumType.STRING)
    @Column( name = "sex" )
    private PersonSex sex;

    @Column( name = "birth_date" )
    private LocalDate birthDate;

}
