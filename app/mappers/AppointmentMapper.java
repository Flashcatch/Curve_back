package mappers;

import domain.Appointment;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentMapper {
    List<Appointment> selectAllAppointments(@Param("startDate") String startDate);

    Appointment selectAppointmentById(Long id);

    void insertAppointment(Appointment appointment);

    void updateAppointment(Appointment appointment);

    void deleteAppointment(Long id);

    Integer selectAppointmentsByInterval (@Param("startDate")LocalDateTime startDate,
                                          @Param("duration") Integer duration);

    void deleteAppointmentsByBookingId(Long id);
}
