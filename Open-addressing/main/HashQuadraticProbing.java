
public class HashQuadraticProbing<T extends Comparable<T>> extends HashOpenAdressing<T> {
    private final double a;
    private final double b;

    public HashQuadraticProbing() {
        super();
        this.a = 0.5;
        this.b = 0.5;
    }

    public HashQuadraticProbing(int size, double a, double b) {
        super(size);
        if (a == 0)
            throw new IllegalArgumentException("Constant 'a' in Hashing with Quadratic probing can't be 0!");
        this.a = a;
        if (b == 0)
            throw new IllegalArgumentException("Constant 'b' in Hashing with Quadratic probing can't be 0!");
        this.b = b;
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();
        int f = key % m;
        int hash = (int) (f + a * i + b * i * i) % m;
        return hash < 0 ? -hash : hash;
    }

}
