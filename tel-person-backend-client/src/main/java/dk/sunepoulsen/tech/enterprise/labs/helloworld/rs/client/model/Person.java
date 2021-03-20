package dk.sunepoulsen.tech.enterprise.labs.helloworld.rs.client.model;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@ApiModel(value = "Model of a person", description = "The model is used to create, read and update persons")
public class Person implements BaseModel {
    @ApiModelProperty(value = "Person id")
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "The persons first name")
    private String firstName;

    @ApiModelProperty(value = "The persons surnames except the last surname")
    private String surnames;

    @NotBlank
    @ApiModelProperty(value = "The last surname of the person")
    private String lastSurname;

    @ApiModelProperty(value = "The persons sex")
    private PersonSex sex;

    @Valid
    @ApiModelProperty(value = "Birthday of the person.")
    private LocalDate birthDate;
}
