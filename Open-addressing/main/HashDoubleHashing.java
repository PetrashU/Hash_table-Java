
public class HashDoubleHashing<T extends Comparable<T>> extends HashOpenAdressing<T> {
    public HashDoubleHashing() {
        super();
    }

    public HashDoubleHashing(int size) {
        super(size);
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();
        int f = key % m;
        int g;
        if (m == 3) {
            g = 1;
        } else
            g = 1 + key % (m - 3);

        int hash = (f + i * g) % m;

        return hash < 0 ? -hash : hash;
    }
}
