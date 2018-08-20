package domain;

import dto.BlockBookingDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlockBooking extends Entity<BlockBooking>{

    private Long id;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private Integer duration;
    private String recurType;

    public static BlockBooking instanceOf (BlockBookingDTO dto) {
        return new BlockBooking(dto.getId(),
                dto.getDescription(),
                dto.getStartTime(),
                dto.getFinishTime(),
                dto.getDuration(),
                dto.getRecurType());
    }

    @Override
    public BlockBooking instanceOf(BlockBooking blockBooking) {
        this.setId(blockBooking.id);
        this.setDescription(blockBooking.description);
        this.setStartTime(blockBooking.startTime);
        this.setFinishTime(blockBooking.finishTime);
        this.setDuration(blockBooking.duration);
        this.setRecurType(blockBooking.recurType);
        return this;
    }
}
