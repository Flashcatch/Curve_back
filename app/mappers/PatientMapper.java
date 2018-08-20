package mappers;

import domain.Patient;

import java.util.List;

public interface PatientMapper {
    List<Patient> selectAllPatients();

    Patient selectPatientById(Long id);

    void insertPatient(Patient patient);

    void updatePatient(Patient patient);

    void deletePatient(Long id);
}
