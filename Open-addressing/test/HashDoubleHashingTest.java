
import org.junit.Test;

import services.HashTable;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HashDoubleHashingTest {
    private class Struct implements Comparable<Struct> {
        int x;

        public Struct(int x) {
            this.x = x;
        }

        @Override
        public int compareTo(Struct o) {
            return x - o.x;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Struct struct = (Struct) o;
            return x == struct.x;
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }

    @Test
    public void SizeIs3() {
        //given
        HashTable<Integer> hash = new HashDoubleHashing<>(3);

        //when
        hash.put(3);
        hash.put(2);
        hash.put(1);

        //then
        assertEquals(3, getNumOfElems(hash));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenInitialSizeIsLowerThanOne() {
        // given
        int initialSize = 0;

        // when
        HashTable<Double> unusedHash = new HashDoubleHashing<>(initialSize);

        // then
        assert false;
    }

    @Test
    public void should_CorrectlyAddNewElems_WhenNotExistInHashTable() {
        // given
        HashTable<String> emptyHash = new HashDoubleHashing<>();
        String newEleme = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(emptyHash);
        emptyHash.put(newEleme);
        int nOfElemsAfterPut = getNumOfElems(emptyHash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowExceptionWhenPuttingNull() {
        //given
        HashTable<String> hash = new HashDoubleHashing<>();

        //when
        hash.put(null);

        //then
        assert false;
    }

    @Test
    public void should_NotChangeNumsWhenAddingExistingElementsStrings() {
        //given
        HashTable<String> hash = new HashDoubleHashing<>();
        hash.put("Element");

        //when
        hash.put("Inny Element");
        hash.put("Element");
        hash.put("Element");
        hash.put("Element");

        //then
        assertEquals(2, getNumOfElems(hash));
        assertEquals("Element", hash.get("Element"));
        assertEquals("Inny Element", hash.get("Inny Element"));
    }

    @Test
    public void should_NotChangeNumsWhenAddingExistingElementsDouble() {
        //given
        HashTable<Double> hash = new HashDoubleHashing<>();
        hash.put(12.0);

        //when
        hash.put(13.0);
        hash.put(12.0);
        hash.put(12.0);
        hash.put(12.0);

        //then
        assertEquals(2, getNumOfElems(hash));
        assertEquals(12.0, hash.get(12.0), 0);
    }

    @Test
    public void AddingElemWithHashEqualsSize() {
        //given
        String elem = "Element";
        HashTable<String> hash = new HashDoubleHashing<>(elem.hashCode());

        //when
        hash.put(elem);

        //then
        assertEquals(1, getNumOfElems(hash));
    }

    @Test
    public void AddingElementsWithSameHashCode() {
        //given
        HashTable<Struct> hash = new HashDoubleHashing<>(25);
        Random rand = new Random();

        //when
        for (int i = 0; i < 10000; i++) {
            hash.put(new Struct(rand.nextInt()));
        }

        //then
        assertEquals(10000, getNumOfElems(hash));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowExceptionWhenSearchingForNull() {
        //given
        HashTable<String> hash = new HashDoubleHashing<>();
        hash.put("Not empty now");

        //when
        hash.get(null);

        //then
        assert false;
    }

    @Test
    public void getElementNotThere() {
        //given
        HashTable<Double> hash = new HashDoubleHashing<>(3);
        hash.put(1.0);
        hash.put(2.0);
        hash.put(3.0);

        //when-then
        assertNull(hash.get(4.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowExceptionWhenDeletingNull() {
        //given
        HashTable<String> hash = new HashDoubleHashing<>();
        hash.put("Not empty now");

        //when
        hash.delete(null);

        //then
        assert false;
    }

    @Test
    public void DeleteTest() {
        //given
        HashTable<Double> hash = new HashDoubleHashing<>(3);
        hash.put(1.0);
        hash.put(2.0);
        hash.put(3.0);

        //when
        hash.delete(2.0);

        //then
        assertEquals(2, getNumOfElems(hash));
    }

    @Test
    public void should_NotChangeNumsWhenAddingElementThatExistsAfterDeleted() {
        //given
        HashTable<Double> hash = new HashDoubleHashing<>(3);
        hash.put(1.0);
        hash.put(2.0);
        hash.put(3.0);

        //when
        hash.delete(2.0);
        hash.put(3.0);

        //then
        assertEquals(2, getNumOfElems(hash));
    }

    @Test
    public void DeletingFirstElementSameHashCode() {
        //given
        HashTable<Struct> hash = new HashDoubleHashing<>(3);
        Struct struct1 = new Struct(1);
        Struct struct2 = new Struct(2);
        Struct struct3 = new Struct(3);

        hash.put(struct1);
        hash.put(struct2);
        hash.put(struct3);

        //when
        hash.delete(struct1);

        //then
        assertEquals(2, getNumOfElems(hash));
        assertNull(hash.get(struct1));
        assertEquals(struct2, hash.get(struct2));
        assertEquals(struct3, hash.get(struct3));
    }

    @Test
    public void DeletingSecondElementSameHashCode() {
        //given
        HashTable<Struct> hash = new HashDoubleHashing<>(3);
        Struct struct1 = new Struct(1);
        Struct struct2 = new Struct(2);
        Struct struct3 = new Struct(3);

        hash.put(struct1);
        hash.put(struct2);
        hash.put(struct3);

        //when
        hash.delete(struct2);

        //then
        assertEquals(2, getNumOfElems(hash));
        assertNull(hash.get(struct2));
        assertEquals(struct1, hash.get(struct1));
        assertEquals(struct3, hash.get(struct3));
    }

    @Test
    public void DeletingLastElementSameHashCode() {
        //given
        HashTable<Struct> hash = new HashDoubleHashing<>(3);
        Struct struct1 = new Struct(1);
        Struct struct2 = new Struct(2);
        Struct struct3 = new Struct(3);

        hash.put(struct1);
        hash.put(struct2);
        hash.put(struct3);

        //when
        hash.delete(struct3);

        //then
        assertEquals(2, getNumOfElems(hash));
        assertNull(hash.get(struct3));
        assertEquals(struct1, hash.get(struct1));
        assertEquals(struct2, hash.get(struct2));
    }

    @Test
    public void deletingAllOfElementsWithSameCode() {
        //given
        HashTable<Struct> hash = new HashDoubleHashing<>(12);
        for (int i = 0; i < 1000; i++) {
            hash.put(new Struct(12 * i * i));
        }

        //when
        for (int i = 999; i >= 0; i--) {
            hash.delete(new Struct(12 * i * i));
        }

        //then
        assertEquals(0, getNumOfElems(hash));
    }

    @Test
    public void gettingAllOfElementsWithSameCode() {
        //given
        HashTable<Struct> hash = new HashDoubleHashing<>(12);
        for (int i = 0; i < 1000; i++) {
            hash.put(new Struct(12 * i * i));
        }

        //when-then
        for (int i = 999; i >= 0; i--) {
            assertEquals(new Struct(12 * i * i), hash.get(new Struct(12 * i * i)));
        }
    }

    private int getNumOfElems(HashTable<?> hash) {
        String fieldNumOfElems = "nElems";
        try {
            System.out.println(hash.getClass().getSuperclass().getName());
            Field field = hash.getClass().getSuperclass().getDeclaredField(fieldNumOfElems);
            field.setAccessible(true);

            int numOfElems = field.getInt(hash);

            return numOfElems;

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}