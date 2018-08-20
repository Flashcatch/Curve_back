package repository;

import domain.Patient;
import mappers.PatientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class PatientRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PatientRepository.class);

    private final PatientMapper mapper;

    @Inject
    public PatientRepository(PatientMapper mapper) {
        this.mapper = mapper;
    }

    public List<Patient> getPatients() {
        LOG.debug(">> getPatients");

        return mapper.selectAllPatients();
    }

    public Patient createPatient(Patient patient) {
        LOG.debug(">> createPatient");

        return insert(patient);

    }

    private Patient insert(Patient patient) {
        mapper.insertPatient(patient);
        return patient;
    }

    public Optional<Patient> getPatientById(Long id){
        return Optional.ofNullable(mapper.selectPatientById(id));
    }

    private Optional<Patient> modify(Patient patient) {
        final Optional<Patient> data = getPatientById(patient.getId());
        if (data.isPresent()) {
            data.get().instanceOf(patient);
        }
        mapper.updatePatient(patient);
        return data;
    }

    public Optional<Patient> updatePatient(Patient patient) {
        return modify(patient);
    }

    private Boolean deleteMapper(Long id) {
        LOG.debug(">> deleteMapper");
        Boolean result = false;
        final Patient data = mapper.selectPatientById(id);
        if (data != null) {
            mapper.deletePatient(id);
            result = true;
        }
        return result;
    }

    public Boolean deletePatient(Long id) {
        return deleteMapper(id);
    }

}
