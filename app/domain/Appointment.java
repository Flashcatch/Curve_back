package domain;

import dto.AppointmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Appointment extends Entity<Appointment>{

    private Long id;
    private Integer type;
    private LocalDateTime startTime;
    private Integer duration;
    private Patient patient;
    private BlockBooking blockBooking;

    public static Appointment instanceOf (AppointmentDTO dto) {
        return new Appointment(dto.getId(),
                dto.getType(),
                dto.getStartTime(),
                dto.getDuration(),
                dto.getPatient() != null ? Patient.instanceOf(dto.getPatient()) : null,
                dto.getBlockBooking() != null ? BlockBooking.instanceOf(dto.getBlockBooking()) : null);
    }

    @Override
    public Appointment instanceOf(Appointment appointment) {
        this.setId(appointment.id);
        this.setType(appointment.type);
        this.setStartTime(appointment.startTime);
        this.setDuration(appointment.duration);
        this.setPatient(appointment.patient);
        this.setBlockBooking(appointment.blockBooking);
        return this;
    }
}
