
import services.HashTable;

public abstract class HashOpenAdressing<T extends Comparable<T>> implements HashTable<T> {

    private final T nil = null;
    private int size;
    private int nElems;
    private T[] hashElems;
    private final double correctLoadFactor;
    private boolean[] deleted;

    HashOpenAdressing() {
        this(2039); // initial size as random prime number
    }

    HashOpenAdressing(int size) {
        validateHashInitSize(size);

        this.size = size;
        this.hashElems = (T[]) new Comparable[this.size];
        this.correctLoadFactor = 0.75;
        this.deleted = new boolean[size];
    }

    @Override
    public void put(T newElem) {
        validateInputElem(newElem);
        resizeIfNeeded();

        int key = newElem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        int firstDeletedId = -1;
        int firstHashId = hashId;
        boolean reachedTheEnd = false;

        while (hashElems[hashId] != nil && (!hashElems[hashId].equals(newElem) || deleted[hashId])) {
            if (deleted[hashId] && firstDeletedId == -1) {
                firstDeletedId = hashId;
            }
            i = (i + 1) % size;
            hashId = hashFunc(key, i);

            if (hashId == firstHashId) {
                if (firstDeletedId == -1) {
                    doubleResize();
                    i = 0;
                    hashId = hashFunc(key, i);
                    firstHashId = hashId;
                } else {
                    reachedTheEnd = true;
                    break;
                }
            }
        }
        if (hashElems[hashId] != nil) {
            if (firstDeletedId == -1) {
                hashElems[hashId] = newElem;
            } else {
                hashElems[firstDeletedId] = newElem;
                deleted[firstDeletedId] = false;
                if (reachedTheEnd) {
                    nElems++;
                } else {
                    deleted[hashId] = true;
                }
            }
        } else {
            if (firstDeletedId != -1) {
                hashElems[firstDeletedId] = newElem;
                deleted[firstDeletedId] = false;
            } else
                hashElems[hashId] = newElem;
            nElems++;
        }
    }

    @Override
    public T get(T elem) {
        if (elem == null)
            throw new IllegalArgumentException("Can not search for null in hash!");

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        T foundElem = hashElems[hashId];

        int firstHashId = hashId;
        boolean reachedTheEnd = false;
        while (foundElem != nil && (!foundElem.equals(elem) || deleted[hashId])) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
            foundElem = hashElems[hashId];
            if (hashId == firstHashId) {
                reachedTheEnd = true;
                break;
            }
        }

        if (foundElem != nil && !reachedTheEnd)
            return foundElem;
        else return null;
    }

    @Override
    public void delete(T elem) {
        if (elem == null)
            throw new IllegalArgumentException("Can not delete null from hash!");

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        int firstHashId = hashId;
        boolean reachedTheEnd = false;

        while (hashElems[hashId] != nil && (!hashElems[hashId].equals(elem) || deleted[hashId])) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
            if (hashId == firstHashId) {
                reachedTheEnd = true;
                break;
            }
        }

        if (hashElems[hashId] != nil && !reachedTheEnd) {
            deleted[hashId] = true;
            nElems--;
        }
    }

    private void validateHashInitSize(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Initial size of hash table cannot be lower than 1!");
        }
    }

    private void validateInputElem(T newElem) {
        if (newElem == null) {
            throw new IllegalArgumentException("Input elem cannot be null!");
        }
    }

    abstract int hashFunc(int key, int i);

    int getSize() {
        return size;
    }

    private void resizeIfNeeded() {
        double loadFactor = countLoadFactor();

        if (loadFactor >= correctLoadFactor) {
            doubleResize();
        }
    }

    private double countLoadFactor() {
        return (double) nElems / size;
    }

    private void doubleResize() {
        this.size *= 2;
        T[] newHash = (T[]) new Comparable[this.size];
        boolean[] newDeleted = new boolean[this.size];
        for (int i = 0; i < this.size / 2; i++) {
            if (this.hashElems[i] != null) {
                int key = hashElems[i].hashCode();
                int j = 0;
                int hashId = hashFunc(key, j);

                while (newHash[hashId] != nil) {
                    j = (j + 1) % size;
                    hashId = hashFunc(key, j);
                }

                newHash[hashId] = this.hashElems[i];
                if (this.deleted[i])
                    newDeleted[hashId] = true;
            }
        }
        this.hashElems = newHash;
        this.deleted = newDeleted;
    }
}
