package dto;

import domain.BlockBooking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.LocalDateTimed;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlockBookingDTO implements AbstractDTO<BlockBooking> {

    private Long id;
    private String description;
    @LocalDateTimed
    private LocalDateTime startTime;
    @LocalDateTimed
    private LocalDateTime finishTime;
    private Integer duration;
    private String recurType;

    public BlockBookingDTO(BlockBooking data) {
        this.id = data.getId();
        this.description = data.getDescription();
        this.startTime = data.getStartTime();
        this.finishTime = data.getFinishTime();
        this.duration = data.getDuration();
        this.recurType = data.getRecurType() ;
    }

    @Override
    public BlockBooking instanceOf() {return BlockBooking.instanceOf(this);}
}
