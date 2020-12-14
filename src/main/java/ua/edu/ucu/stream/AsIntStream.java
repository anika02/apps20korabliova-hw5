package ua.edu.ucu.stream;

import ua.edu.ucu.function.IntBinaryOperator;
import ua.edu.ucu.function.IntConsumer;
import ua.edu.ucu.function.IntPredicate;
import ua.edu.ucu.function.IntToIntStreamFunction;
import ua.edu.ucu.function.IntUnaryOperator;

import java.util.ArrayList;

public class AsIntStream implements IntStream {

    private final ArrayList<Integer> values = new ArrayList<>();

    private AsIntStream(int... values) {
        for (int value : values) {
            this.values.add(value);
        }
    }

    private AsIntStream(ArrayList<Integer> values) {
        this.values.addAll(values);
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    @Override
    public double average() {
        checkEmptyException();
        return (double) sum() / count();
    }

    @Override
    public int max() {
        checkEmptyException();
        return this.reduce(Integer.MIN_VALUE, Math::max);
    }

    @Override
    public int min() {
        checkEmptyException();
        return this.reduce(Integer.MAX_VALUE, Math::min);
    }

    @Override
    public long count() {
        checkEmptyException();
        return this.reduce(0, (sum, x) -> sum += 1);
    }

    @Override
    public int sum() {
        checkEmptyException();
        return this.reduce(0, (sum, x) -> sum += x);

    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        checkEmptyException();
        ArrayList<Integer> array = new ArrayList<>(values);
        array.removeIf(item -> !predicate.test(item));
        return new AsIntStream(array);
    }

    @Override
    public void forEach(IntConsumer action) {
        checkEmptyException();
        for (int value : values) {
            action.accept(value);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        checkEmptyException();
        ArrayList<Integer> array = new ArrayList<>(values);
        for (int i = 0; i < array.size(); ++i) {
            array.set(i, mapper.apply(array.get(i)));
        }
        return new AsIntStream(array);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        checkEmptyException();
        ArrayList<Integer> array = new ArrayList<>();
        for (int value : values) {
            for (int elem : func.applyAsIntStream(value).toArray()) {
                array.add(elem);
            }
        }
        return new AsIntStream(array);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        checkEmptyException();
        int result = identity;
        for (int value : values) {
            result = op.apply(result, value);
        }
        return result;
    }

    @Override
    public int[] toArray() {
        int[] array = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            array[i] = values.get(i);
        }
        return array;
    }

    private void checkEmptyException() {
        if (values.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
