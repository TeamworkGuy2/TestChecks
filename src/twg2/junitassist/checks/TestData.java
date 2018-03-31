package twg2.junitassist.checks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author TeamworkGuy2
 * @since 2015-6-26
 * @param <I> the input data type
 * @param <R> the result data type
 */
public interface TestData<I, R> {

	public I getInput();

	public void setInput(I input);

	public R getExpected();

	public void setExpected(R expected);

	public R getResult();

	public void setResult(R result);

	public boolean isShouldInputEqualExpected();

	public void setShouldInputEqualExpected(boolean shouldInputEqualExpected);

	public boolean isExpectThrow();

	public void setExpectThrow(boolean expectThrow);


	public static <I, R> TestData<I, R> matchTrue(I input, R expected) {
		return new TestDataImpl<>(input, expected, null, true, false);
	}


	public static <I, R> TestData<I, R> matchFalse(I input, R expected) {
		return new TestDataImpl<>(input, expected, null, false, false);
	}


	public static <I, R> TestData<I, R> shouldThrow(I input, R expected) {
		return new TestDataImpl<>(input, expected, null, false, true);
	}


	/** Convert a sequence of input and expected data entries to {@link TestData} objects
	 * @param inputsAndExpected sequence of input and expected data entries
	 * @param createTestData for example {@link TestData#matchTrue(Object, Object)}
	 * @return
	 */
	public static <I, R> List<TestData<I, R>> fromInputsAndExpected(Iterable<? extends Map.Entry<? extends I, ? extends R>> inputsAndExpected, BiFunction<I, R, ? extends TestData<I, R>> createTestData) {
		Stream<Map.Entry<? extends I, ? extends R>> iter = StreamSupport.stream(Spliterators.spliteratorUnknownSize(inputsAndExpected.iterator(), 0), false);

		return fromInputsAndExpected(
				iter.map((e) -> e.getKey()).iterator(),
				iter.map((e) -> e.getValue()).iterator(),
				createTestData);
	}


	/** Convert a sequence of inputs and a sequence of expected data to {@link TestData} objects
	 * @param inputs sequence of input data elements
	 * @param expected sequence of expected data elements
	 * @param createTestData for example {@link TestData#matchTrue(Object, Object)}
	 * @return
	 */
	public static <I, R> List<TestData<I, R>> fromInputsAndExpected(Iterable<? extends I> inputs, Iterable<? extends R> expected, BiFunction<I, R, ? extends TestData<I, R>> createTestData) {
		return fromInputsAndExpected(inputs.iterator(), expected.iterator(), createTestData);
	}


	/** Convert a sequence of inputs and a sequence of expected data to {@link TestData} objects
	 * @param inputs sequence of input data elements
	 * @param expected sequence of expected data elements
	 * @param createTestData for example {@link TestData#matchTrue(Object, Object)}
	 * @return
	 */
	public static <I, R> List<TestData<I, R>> fromInputsAndExpected(Iterator<? extends I> inputsIter, Iterator<? extends R> expectedIter, BiFunction<I, R, ? extends TestData<I, R>> createTestData) {
		List<TestData<I, R>> res = new ArrayList<>();
		while(inputsIter.hasNext() && expectedIter.hasNext()) {
			I input = inputsIter.next();
			R expect = expectedIter.next();
			res.add(createTestData.apply(input, expect));
		}
		if(inputsIter.hasNext()) {
			throw new IllegalArgumentException("'expected' sequence shorter than 'inputs' sequence");
		}
		if(expectedIter.hasNext()) {
			throw new IllegalArgumentException("'inputs' sequence shorter than 'expected' sequence");
		}
		return res;
	}

}
