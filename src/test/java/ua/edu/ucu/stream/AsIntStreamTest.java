package ua.edu.ucu.stream;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AsIntStreamTest {
    private final static Double DELTA = 0.00001;
    private IntStream intStream;

    @Before
    public void init() {
        int[] intArr = {-1, 0, 1, 3};
        intStream = AsIntStream.of(intArr);
    }

    @Test
    public void testStreamConstructor() {
        intStream = AsIntStream.of(-1, 0, 1, 3);
        int[] expResult = {-1, 0, 1, 3};
        int[] result = intStream.toArray();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testStreamAverage() {
        double result = intStream.average();
        double expResult = 0.75;
        assertEquals(expResult, result, DELTA);
    }

    @Test
    public void testStreamMax() {
        int result = intStream.max();
        int expResult = 3;
        assertEquals(expResult, result);
    }

    @Test
    public void testStreamMin() {
        int result = intStream.min();
        int expResult = -1;
        assertEquals(expResult, result);
    }

    @Test
    public void testStreamCount() {
        long result = intStream.count();
        long expResult = 4;
        assertEquals(expResult, result);
    }

    @Test
    public void testStreamSum() {
        int result = intStream.reduce(0, (sum, x) -> sum += x);
        int expResult = 3;
        assertEquals(expResult, result);
    }

    @Test
    public void testStreamFilter() {
        IntStream res = intStream.filter(x -> x > 0);
        int[] expResult = {1, 3};
        int[] result = res.toArray();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testAsIntStreamForEach() {
        StringBuilder str = new StringBuilder();
        intStream.forEach(str::append);
        String expResult = "-1013";
        String result = str.toString();
        assertEquals(expResult, result);
    }

    @Test
    public void testStreamMap() {
        IntStream res = intStream.map(x -> x * x);
        int[] expResult = {1, 0, 1, 9};
        int[] result = res.toArray();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testStreamFlatMap() {
        IntStream res = intStream.flatMap(x -> AsIntStream.of(x - 1, x, x + 1));
        int[] expResult = {-2, -1, 0, -1, 0, 1, 0, 1, 2, 2, 3, 4};
        int[] result = res.toArray();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testStreamToArray() {
        int[] result = intStream.toArray();
        int[] expResult = {-1, 0, 1, 3};
        assertArrayEquals(expResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStreamCheckEmptyException() {
        intStream = AsIntStream.of();
        intStream.sum();
    }
}
