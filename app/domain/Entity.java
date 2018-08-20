package domain;

public abstract class Entity<K> {
    /**
     * @param k
     * @return
     */
    public abstract K instanceOf(K k);
}
