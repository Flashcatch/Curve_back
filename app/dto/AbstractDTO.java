package dto;

public interface AbstractDTO<A> {
    /**
     * @return A is an entity.
     */
    A instanceOf();
}
