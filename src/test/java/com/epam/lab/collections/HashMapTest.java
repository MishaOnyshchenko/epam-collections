package com.epam.lab.collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;

public class HashMapTest
{
    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, String> spy = Mockito.spy(map);

    @Test
    public void testParametrizedConstructorCreatesCopiedMap() {
        //GIVEN
        Map<String, String> expected = new HashMap<>();
        expected.put("Key", "Value");
        //WHEN
        HashMap<String, String> result = new HashMap<>(expected);
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testGetReturnsCorrectValue() {
        //GIVEN
        String expected = "Value";
        map.put("Key", "Value");
        //WHEN
        String result = map.get("Key");
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testGetReturnsNull() {
        //GIVEN
        String expected = null;
        map.put("Key", "Value");
        //WHEN
        String result = map.get("wrongKey");
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testPut() {
        //GIVEN
        String expected = "expectedValue";
        //WHEN
        map.put("Key", "expectedValue");
        String result = map.get("Key");
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testPutExistingKeySetsNewValue() {
        //GIVEN
        String expected = "newValue";
        //WHEN
        map.put("Key", "firstValue");
        map.put("Key", "newValue");
        String result = map.get("Key");
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testPutRunsResize() {
        //GIVEN
        spy.put("Key1", "Value");
        spy.put("Key2", "Value");
        spy.put("Key3", "Value");
        spy.put("Key4", "Value");
        //WHEN
        spy.put("Key5", "Value");
        //THEN
        Mockito.verify(spy, times(1)).resize();
    }

    @Test
    public void testPutMapEntriesCopiesEntryFromMap() {
        //GIVEN
        String expected = "valueShouldBeCopiedToCopyMap";
        map.put("Key", "valueShouldBeCopiedToCopyMap");
        HashMap<String, String> copyMap = new HashMap<>();
        //WHEN
        copyMap.putMapEntries(map);
        String result = copyMap.get("Key");
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testResizeIncreasesMapCapacity() {
        //GIVEN
        int expected = 10;
        map.put("Key1", "Value");
        map.put("Key2", "Value");
        map.put("Key3", "Value");
        map.put("Key4", "Value");
        //WHEN
        map.put("Key5", "Value");
        int result = map.getMapCapacity();
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testResizeIncreasesHashTableCapacity() {
        //GIVEN
        int expected = 15;
        map.put("Key1", "Value");
        map.put("Key2", "Value");
        map.put("Key3", "Value");
        map.put("Key4", "Value");
        //WHEN
        map.put("Key5", "Value");
        int result = map.getHashTableCapacity();
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testHashReturnsReminderFromDivisionHashCodeOf_G_onHashTableSize() {
        //GIVEN
        int expected = 71 % 7;
        //WHEN
        int result = map.hash("G");
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testSizeReturnsOne() {
        //GIVEN
        int expected = 1;
        map.put("Key", "Value");
        //WHEN
        int result = map.size();
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testEqualsReturnsTrue() {
        //GIVEN
        boolean expected = true;
        map.put("Key", "Value");
        Map<String, String> map2 = new HashMap<>();
        map2.put("Key", "Value");
        //WHEN
        boolean result = map.equals(map2);
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testEqualsReturnFalse() {
        //GIVEN
        boolean expected = false;
        map.put("Key", "Value");
        map.put("", "");
        Map<String, String> map2 = new HashMap<>();
        map2.put("Key", "Value");
        map2.put("Key2", "Value");
        //WHEN
        boolean result = map.equals(map2);
        //THEN
        assertThat(expected, equalTo(result));
    }

    @Test
    public void testHashCodeEquals() {
        //GIVEN
        Map<String, String> x = new HashMap<>();
        x.put("Key", "Value");
        Map<String, String> y = new HashMap<>();
        y.put("Key", "Value");
        //WHEN
        int hashCodeX = x.hashCode();
        int hashCodeY = y.hashCode();
        //THEN

        assertEquals(hashCodeX, hashCodeY);
    }

    @Test
    public void testHashCodeNotEquals() {
        //GIVEN
        Map<String, String> x = new HashMap<>();
        x.put("KeyA", "Value");
        Map<String, String> y = new HashMap<>();
        y.put("KeyB", "Value");
        //WHEN
        int hashCodeX = x.hashCode();
        int hashCodeY = y.hashCode();
        //THEN
        assertNotEquals(hashCodeX, hashCodeY);
    }
}