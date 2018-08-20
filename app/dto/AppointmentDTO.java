package dto;

import domain.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.LocalDateTimed;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentDTO implements AbstractDTO<Appointment> {

    private Long id;
    private Integer type;
    @LocalDateTimed
    private LocalDateTime startTime;
    private Integer duration;
    private PatientDTO patient;
    private BlockBookingDTO blockBooking;

    public AppointmentDTO(Appointment data) {
        this.id = data.getId();
        this.type = data.getType();
        this.startTime = data.getStartTime();
        this.duration = data.getDuration();
        this.patient = data.getPatient() != null ? new PatientDTO(data.getPatient()) : null;
        this.blockBooking = data.getBlockBooking() != null ? new BlockBookingDTO(data.getBlockBooking()) : null;
    }

    @Override
    public Appointment instanceOf() {return Appointment.instanceOf(this);}
}
