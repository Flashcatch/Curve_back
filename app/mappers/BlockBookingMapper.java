package mappers;

import domain.BlockBooking;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlockBookingMapper {
    List<BlockBooking> selectAllBlockBookings();

    BlockBooking selectBlockBookingById(Long id);

    void insertBlockBooking(BlockBooking blockBooking);

    void updateBlockBooking(BlockBooking blockBooking);

    void deleteBlockBooking(Long id);

    void setRecurType(@Param("id") Long id,
                      @Param("recurType") String recurType);

    String getRecurType (Long id);
}
