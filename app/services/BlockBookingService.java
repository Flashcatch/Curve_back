package services;

import domain.Appointment;
import domain.BlockBooking;
import domain.Week;
import dto.BlockBookingDTO;
import org.mybatis.guice.transactional.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.AppointmentRepository;
import repository.BlockBookingRepository;
import repository.PatientRepository;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockBookingService {
    private static final Logger LOG = LoggerFactory.getLogger(BlockBookingService.class);

    private final BlockBookingRepository blockBookingRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Inject
    public BlockBookingService(BlockBookingRepository blockBookingRepository,
                               PatientRepository patientRepository,
                               AppointmentRepository appointmentRepository) {
        this.blockBookingRepository = blockBookingRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<BlockBookingDTO> getBlockBookings() {
        return blockBookingRepository.getBlockBookings().stream().map(BlockBookingDTO::new).collect(Collectors.toList());
    }

    public BlockBookingDTO createBlockBooking(BlockBookingDTO dto) {
        final BlockBooking data = BlockBooking.instanceOf(dto);

        List<Integer> result = new ArrayList<>();
        if (data.getRecurType() != null) {
            result = Stream.of(data.getRecurType().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }

        List<Appointment> appointmentList = new ArrayList<>();
        LocalDateTime date;

        BlockBooking createdBlockBooking = blockBookingRepository.createBlockBooking(data);

       /* String recurType = data.getRecurType()
                .stream()
                .map(dayNum -> dayNum.getId()
                        .toString())
                .collect(Collectors.joining(","));*/

       // blockBookingRepository.setRecurType(createdBlockBooking.getId(), recurType);

        for (int i = 0; i <= ChronoUnit.DAYS.between(data.getStartTime(), data.getFinishTime()); i++) {
            date = data.getStartTime().plusDays(i);
            Integer dayOfWeek = date.getDayOfWeek().getValue();
            if (result.contains(dayOfWeek) && date.isAfter(data.getStartTime()) && date.isBefore(data.getFinishTime())) {
                appointmentList.add(new Appointment(null, 1, date, data.getDuration(), null, createdBlockBooking));
                if (!appointmentRepository.isTimeAvailable(date, data.getDuration())) {
                    Boolean deleted = blockBookingRepository.deleteBlockBooking(createdBlockBooking.getId());
                    throw new RuntimeException("Time is not available");
                }
            }
        }

        if (appointmentList.size() != 0) {
            appointmentList.forEach(a -> {
                final Appointment createdAppointment = appointmentRepository.createAppointment(a);
            });
        }

        return new BlockBookingDTO(createdBlockBooking);
    }

    public Optional<BlockBookingDTO> findById(Long id) {

        List<Week> weekDays = new ArrayList<>();

        return blockBookingRepository.getBlockBookingById(id).map(BlockBookingDTO::new /*{
            String recurType = blockBookingRepository.getRecurType(data.getId());
            if (recurType != null) {
                List<Integer> result = new ArrayList<>();
                result = Stream.of(recurType.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());


                result.forEach(day -> weekDays.add(day, null));
                data.setRecurType(weekDays);
            }
            return new BlockBookingDTO(data);
        }*/);

    }

    public Optional<BlockBookingDTO> updateBlockBookingById(BlockBookingDTO dto) {
        final BlockBooking blockBooking = BlockBooking.instanceOf(dto);

        return blockBookingRepository.updateBlockBooking(blockBooking).map(BlockBookingDTO::new);
    }

    @Transactional
    public Boolean deleteBlockBooking(Long id) {

        Boolean delete = appointmentRepository.deleteAppointmentsByBookingId(id);
        return blockBookingRepository.deleteBlockBooking(id);
    }
}
