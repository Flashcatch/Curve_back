package repository;

import domain.Appointment;
import mappers.AppointmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AppointmentRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentRepository.class);

    private final AppointmentMapper mapper;

    @Inject
    public AppointmentRepository(AppointmentMapper mapper) {
        this.mapper = mapper;
    }

    public List<Appointment> getAppointments(String startDate) {
        LOG.debug(">> getAppointments");

        return mapper.selectAllAppointments(startDate);
    }

    public Appointment createAppointment(Appointment appointment) {
        LOG.debug(">> createAppointment");

        return insert(appointment);

    }

    private Appointment insert(Appointment appointment) {
        mapper.insertAppointment(appointment);
        return appointment;
    }

    public Optional<Appointment> getAppointmentById(Long id){
        return Optional.ofNullable(mapper.selectAppointmentById(id));
    }

    private Optional<Appointment> modify(Appointment appointment) {
        final Optional<Appointment> data = getAppointmentById(appointment.getId());
        if (data.isPresent()) {
            data.get().instanceOf(appointment);
        }
        mapper.updateAppointment(appointment);
        return data;
    }

    public Optional<Appointment> updateAppointment(Appointment appointment) {
        return modify(appointment);
    }

    private Boolean deleteMapper(Long id) {
        LOG.debug(">> deleteMapper");
        Boolean result = false;
        final Appointment data = mapper.selectAppointmentById(id);
        if (data != null) {
            mapper.deleteAppointment(id);
            result = true;
        }
        return result;
    }

    public Boolean deleteAppointment(Long id) {
        return deleteMapper(id);
    }

    public Boolean isTimeAvailable(LocalDateTime startDate, Integer duration) {
        return mapper.selectAppointmentsByInterval(startDate, duration).equals(0);
    }

    public Boolean deleteAppointmentsByBookingId(Long id) {mapper.deleteAppointmentsByBookingId(id); return Boolean.TRUE;}

}
