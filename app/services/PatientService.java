package services;

import domain.Patient;
import dto.PatientDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.PatientRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PatientService {
    private static final Logger LOG = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    @Inject
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> getPatients() {
        return patientRepository.getPatients().stream().map(data -> new PatientDTO(data)).collect(Collectors.toList());
    }

    public PatientDTO createPatient(PatientDTO dto) {
        final Patient data = Patient.instanceOf(dto);

        Patient createdPatient = patientRepository.createPatient(data);

        return new PatientDTO(createdPatient);
    }

    public Optional<PatientDTO> findById (Long id) {

        return patientRepository.getPatientById(id).map(PatientDTO::new);

    }

    public Optional<PatientDTO> updatePatientById(PatientDTO dto) {
        final Patient patient = Patient.instanceOf(dto);

        return patientRepository.updatePatient(patient).map(PatientDTO::new);
    }

    public Boolean deletePatient(Long id) {
        return patientRepository.deletePatient(id);
    }
}
