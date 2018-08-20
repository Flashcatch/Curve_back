package repository;

import domain.BlockBooking;
import mappers.BlockBookingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class BlockBookingRepository {

    private static final Logger LOG = LoggerFactory.getLogger(BlockBookingRepository.class);

    private final BlockBookingMapper mapper;

    @Inject
    public BlockBookingRepository(BlockBookingMapper mapper) {
        this.mapper = mapper;
    }

    public List<BlockBooking> getBlockBookings() {
        LOG.debug(">> getBlockBookings");

        return mapper.selectAllBlockBookings();
    }

    public BlockBooking createBlockBooking(BlockBooking blockBooking) {
        LOG.debug(">> createBlockBooking");

        return insert(blockBooking);

    }

    private BlockBooking insert(BlockBooking blockBooking) {
        mapper.insertBlockBooking(blockBooking);
        return blockBooking;
    }

    public Optional<BlockBooking> getBlockBookingById(Long id){
        return Optional.ofNullable(mapper.selectBlockBookingById(id));
    }

    private Optional<BlockBooking> modify(BlockBooking blockBooking) {
        final Optional<BlockBooking> data = getBlockBookingById(blockBooking.getId());
        data.ifPresent(blockBooking1 -> blockBooking1.instanceOf(blockBooking));
        mapper.updateBlockBooking(blockBooking);
        return data;
    }

    public Optional<BlockBooking> updateBlockBooking(BlockBooking blockBooking) {
        return modify(blockBooking);
    }

    private Boolean deleteMapper(Long id) {
        LOG.debug(">> deleteMapper");
        Boolean result = false;
        final BlockBooking data = mapper.selectBlockBookingById(id);
        if (data != null) {
            mapper.deleteBlockBooking(id);
            result = true;
        }
        return result;
    }

    public Boolean deleteBlockBooking(Long id) {
        return deleteMapper(id);
    }

    public void setRecurType(Long id, String recurType) {
        mapper.setRecurType(id, recurType);
    }

    public String getRecurType(Long id) {return mapper.getRecurType(id);}

}
