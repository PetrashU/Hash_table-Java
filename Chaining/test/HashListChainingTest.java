
import org.junit.Assert;
import org.junit.Test;

public class HashListChainingTest {
    @Test
    public void testAdd() {
        //given
        HashListChaining<Double> list = new HashListChaining<>(1);

        //when
        list.add(12.0);

        //then
        Assert.assertEquals(1, list.countLoadFactor(), 0);
    }

    @Test
    public void gdyUsuwamyIstniejacyString() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("Ala");
        list.add("Ola");
        list.add("Pies");

        //when
        list.delete("Ola");

        //then
        Assert.assertEquals(2, list.countLoadFactor(), 0);
        Assert.assertNull(list.get("Ola"));
        Assert.assertEquals("Ala", list.get("Ala"));
        Assert.assertEquals("Pies", list.get("Pies"));
    }

    @Test
    public void gdyUsuwamyIstniejacyDouble() {
        //given
        HashListChaining<Double> list = new HashListChaining<>(1);
        list.add(12.0);
        list.add(1.5);
        list.add(-10.6);

        //when
        list.delete(-10.6);

        //then
        Assert.assertEquals(2, list.countLoadFactor(), 0);
        Assert.assertNull(list.get(-10.6));
        Assert.assertEquals(1.5, list.get(1.5), 0);
        Assert.assertEquals(12.0, list.get(12.0), 0);
    }

    @Test
    public void gdyUsuwamyZPustego() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);

        //when
        list.delete("Ola");

        //then
        Assert.assertNull(list.get("Ola"));
        Assert.assertEquals(0, list.countLoadFactor(), 0);
    }

    @Test
    public void gdyUsuwamyJedynaWartosc() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("Ala");

        //when
        list.delete("Ala");

        //then
        Assert.assertNull(list.get("Ala"));
        Assert.assertEquals(0, list.countLoadFactor(), 0);
    }

    @Test
    public void gdyUsuwamyPierwszaWartosc() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("A");
        list.add("O");
        list.add("P");

        //when
        list.delete("P");

        //then
        Assert.assertNull(list.get("P"));
        Assert.assertEquals("A", list.get("A"));
        Assert.assertEquals("O", list.get("O"));

    }

    @Test
    public void gdyUsuwamySrodkowaWartosci() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("A");
        list.add("O");
        list.add("P");

        //when
        list.delete("O");

        //then
        Assert.assertNull(list.get("O"));
        Assert.assertEquals("A", list.get("A"));
        Assert.assertEquals("P", list.get("P"));
    }

    @Test
    public void gdyUsuwamyOstatniaWartosc() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("A");
        list.add("O");
        list.add("P");

        //when
        list.delete("A");

        //then
        Assert.assertNull(list.get("A"));
        Assert.assertEquals("P", list.get("P"));
        Assert.assertEquals("O", list.get("O"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void gdyDodajemyNull() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("Ala");
        list.add("Ola");

        //when
        list.add(null);

        //then
        assert (false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void gdySzukamyNull() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("Ala");
        list.add("Ola");

        //when
        list.get(null);

        //then
        assert (false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void gdyUsuwamyNull() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("Ala");
        list.add("Ola");

        //when
        list.delete(null);

        //then
        assert (false);
    }

    @Test
    public void gdyUsuwamyWartoscKtorejNieMa() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("Ala");
        list.add("Ola");
        list.add("Pies");

        //when
        list.delete("Kot");

        //then
        Assert.assertEquals(3, list.countLoadFactor(), 0);
    }

    @Test
    public void gdyUsuwamyJuzUsunietaWartosc() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("Ala");
        list.add("Ola");
        list.add("Pies");

        //when
        list.delete("Pies");
        list.delete("Pies");

        //then
        Assert.assertEquals(2, list.countLoadFactor(), 0);
    }

    @Test
    public void testGet() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("Ala");
        list.add("Ola");
        list.add("Pies");

        //then
        Assert.assertEquals("Ala", list.get("Ala"));
    }

    @Test
    public void gdyPobieramyElementZPustegoHash() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);

        //then
        Assert.assertNull(list.get("Ala"));
    }

    @Test
    public void gdyPobieramyElementKtoregoNieMa() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("Ala");
        list.add("Ola");
        list.add("Pies");

        //then
        Assert.assertNull(list.get("Kot"));
    }

    @Test
    public void gdyPobieramyElementJedyny() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("Ala");

        //then
        Assert.assertEquals("Ala", list.get("Ala"));
    }

    @Test
    public void gdyDodajemyJuzIstniejacyElement() {
        //given
        HashListChaining<String> list = new HashListChaining<>(1);
        list.add("Ala");
        list.add("Ola");
        list.add("Pies");

        //when
        list.add("Ola");

        //then
        Assert.assertEquals(3, list.countLoadFactor(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void InicjalizacjaNieprawidlowegoRozmiaru() {
        //given
        HashListChaining<String> list = new HashListChaining<>(-12);
        list.add("Ala");
        list.add("Ola");
        list.add("Pies");

        //then
        assert (false);
    }

}
