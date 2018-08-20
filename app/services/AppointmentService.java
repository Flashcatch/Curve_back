package services;

import com.typesafe.config.Config;
import domain.Appointment;
import dto.AppointmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.AppointmentRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentService {
    private static final Logger LOG = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;

    @Inject
    public AppointmentService(Config config, AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<AppointmentDTO> getAppointments(String startDate) {
        return appointmentRepository.getAppointments(startDate).stream().map(AppointmentDTO::new).collect(Collectors.toList());
    }

    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        Appointment data = Appointment.instanceOf(dto);

        if (!appointmentRepository.isTimeAvailable(data.getStartTime(), data.getDuration())){
            throw new RuntimeException("Time is not available");
        }

        data.setType(0);

        Appointment createdAppointment = appointmentRepository.createAppointment(data);

        return new AppointmentDTO(createdAppointment);
    }

    public Optional<AppointmentDTO> findById (Long id) {

        return appointmentRepository.getAppointmentById(id).map(AppointmentDTO::new);

    }

    public Optional<AppointmentDTO> updateAppointmentById(AppointmentDTO dto) {
        final Appointment appointment = Appointment.instanceOf(dto);

        return appointmentRepository.updateAppointment(appointment).map(AppointmentDTO::new);
    }

    public Boolean deleteAppointment(Long id) {
        return appointmentRepository.deleteAppointment(id);
    }
}
