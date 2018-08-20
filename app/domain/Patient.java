package domain;

import dto.PatientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Patient extends Entity<Patient>{

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime birthdate;

    public static Patient instanceOf (PatientDTO dto) {
        return new Patient(dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthdate());
    }

    @Override
    public Patient instanceOf(Patient patient) {
        this.setId(patient.id);
        this.setFirstName(patient.firstName);
        this.setLastName(patient.lastName);
        this.setBirthdate(patient.birthdate);
        return this;
    }
}
