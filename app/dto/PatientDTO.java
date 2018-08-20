package dto;

import domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.LocalDateTimed;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientDTO implements AbstractDTO<Patient> {

    private Long id;
    private String firstName;
    private String lastName;
    @LocalDateTimed
    private LocalDateTime birthdate;

    public PatientDTO(Patient data) {
        this.id = data.getId();
        this.firstName = data.getFirstName();
        this.lastName = data.getLastName();
        this.birthdate = data.getBirthdate();
    }

    @Override
    public Patient instanceOf() {return Patient.instanceOf(this);}
}
