
import services.HashTable;

public class HashListChaining<T extends Comparable<T>> implements HashTable<T> {

    private final Elem<T> nil = null;
    private final Elem<T>[] hashElems;
    private int nElem;

    private class Elem<F extends Comparable<F>> {

        private Elem<F> next;
        private F value;

        Elem(F value, Elem<F> nextElem) {
            this.value = value;
            this.next = nextElem;
        }
    }

    public HashListChaining(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Can not create Hash List with size less then 1!");
        }
        hashElems = new Elem[size];
        initializeHash();
    }

    @Override
    public void add(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Can't add Null to Hash List!");
        }

        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem<T> oldElem = hashElems[hashId];
        while (oldElem != nil && !oldElem.value.equals(value)) {
            oldElem = oldElem.next;
        }
        if (oldElem != nil) {
            oldElem.value = value;
        } else {
            hashElems[hashId] = new Elem<>(value, hashElems[hashId]);
            nElem++;
        }
    }

    @Override
    public T get(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Can't get Null from Hash List!");
        }
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem<T> elem = hashElems[hashId];

        while (elem != nil && !elem.value.equals(value)) {
            elem = elem.next;
        }

        return elem != nil ? elem.value : null;
    }

    @Override
    public void delete(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Can't delete Null from Hash List!");
        }
        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem<T> elem = hashElems[hashId];
        Elem<T> prev = null;

        while (elem != nil && !elem.value.equals(value)) {
            prev = elem;
            elem = elem.next;
        }

        if (elem != nil) {
            if (prev != null)
                prev.next = elem.next;
            else {
                hashElems[hashId] = elem.next;
            }
            nElem--;
        }

    }

    public double countLoadFactor() {
        double size = hashElems.length;
        return nElem / size;
    }

    private void initializeHash() {
        int n = hashElems.length;

        for (int i = 0; i < n; i++) {
            hashElems[i] = nil;
        }
    }

    private int countHashId(int hashCode) {
        int n = hashElems.length;
        return Math.abs(hashCode) % n;
    }

}
