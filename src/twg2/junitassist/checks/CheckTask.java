package twg2.junitassist.checks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Assert;

/**
 * @author TeamworkGuy2
 * @since 2015-5-26
 */
public final class CheckTask {
	private static float FLOAT_EPSILON = 0;
	private static double DOUBLE_EPSILON = 0;


	/** Assert that the task throws an {@link Exception}
	 * @param task the action to perform
	 */
	public static final void assertException(Runnable task) {
		assertException(null, task);
	}


	/** Assert that the task throws an {@link Exception}
	 * @param errMsg a message to include with the assertion
	 * @param task the action to perform
	 */
	public static final void assertException(String errMsg, Runnable task) {
		Throwable error = null;
		try {
			task.run();
		} catch(Exception e) {
			error = e;
		} finally {
			Assert.assertTrue("task " + (errMsg != null ? "'" + errMsg + "' " : "") + "was expected to throw exception but did not", error != null);
		}
	}


	/** Assert that the task throws a {@link Throwable}
	 * @param task the action to perform
	 */
	public static final void assertThrowable(Runnable task) {
		assertThrowable(null, task);
	}


	/** Assert that the task throws a {@link Throwable}
	 * @param errMsg a message to include with the assertion
	 * @param task the action to perform
	 */
	public static final void assertThrowable(String errMsg, Runnable task) {
		Throwable error = null;
		try {
			task.run();
		} catch(Throwable e) {
			error = e;
		} finally {
			Assert.assertTrue("task " + (errMsg != null ? "'" + errMsg + "' " : "") + "was expected to throw error but did not", error != null);
		}
	}


	// ==== list wrappers ====
	public static final <T, R> void assertTests(Iterable<T> inputs, Iterable<R> expected,
			BiFunction<T, R, String> errorMessageGen, Function<T, R> action) {
		assertTests(toArray(inputs), toArray(expected), errorMessageGen, action);
	}


	public static final <T, R> void assertTests(Iterable<T> inputs, Iterable<R> expected,
			BiFunction<T, R, String> errorMessageGen, BiFunction<T, Integer, R> action) {
		assertTests(toArray(inputs), toArray(expected), errorMessageGen, action);
	}


	public static final <T, R> void assertTests(Iterable<T> inputs, Iterable<R> expected, Function<T, R> action) {
		assertTests(toArray(inputs), toArray(expected), null, action);
	}


	public static final <T, R> void assertTests(Iterable<T> inputs, Iterable<R> expected, BiFunction<T, Integer, R> action) {
		assertTests(toArray(inputs), toArray(expected), null, action);
	}


	// ==== defaults ====
	public static final <T, R> void assertTests(T[] inputs, R[] expected, Function<T, R> action) {
		assertTests(inputs, expected, null, action);
	}


	public static final <T, R> void assertTests(T[] inputs, R[] expected, BiFunction<T, Integer, R> action) {
		assertTests(inputs, expected, null, action);
	}


	/** Run a series of test against a set of inputs and expected results.
	 * Each input is converted to a result via a function and the result is compared
	 * to the expected result using {@link #equals(Object)}
	 * @param inputs the array of inputs to process
	 * @param expected the expected results of processing each input
	 * @param errorMessageGen the message generate to run if the result of one of
	 * the inputs does not match the expected result 
	 * @param action the action that takes an input from {@code inputs} and converts
	 * it to a result value to compare to {@code expected}
	 */
	public static final <T, R> void assertTests(T[] inputs, R[] expected, BiFunction<T, R, String> errorMessageGen, Function<T, R> action) {
		assertTests(inputs, expected, errorMessageGen, (input, idx) -> action.apply(input));
	}


	public static final <T, R> void assertTests(T[] inputs, R[] expected, BiFunction<T, R, String> errorMessageGen,
			BiFunction<T, Integer, R> action) {
		Check.assertLengthEqual(inputs.length, expected.length);

		for(int i = 0, size = inputs.length; i < size; i++) {
			T input = inputs[i];
			R expect = expected[i];
			R res = action.apply(input, i);

			String errorMsg = (errorMessageGen != null ? errorMessageGen.apply(input, expect) : "") + ", input " + i + ": '" + Check.toDescriptiveString(res) +
					"' does not match expected: '" + Check.toDescriptiveString(expect) + "'";
			// if the results are arrays, compare arrays, else compare objects
			if(CheckArrays.isArrays(expect, res)) {
				CheckArrays.assertEquals(errorMsg, expect, res, FLOAT_EPSILON, DOUBLE_EPSILON);
			}
			else {
				Assert.assertEquals(errorMsg, expected[i], res);
			}
		}
	}


	public static final <I, E, R> void assertTests(Iterable<? extends TestData<I, R>> testData, Function<I, R> action) {
		@SuppressWarnings("rawtypes")
		List<TestData<I, R>> data = (testData instanceof List ? new ArrayList<>(((List)testData).size()) : new ArrayList<>());
		for(TestData<I, R> dat : testData) {
			data.add(dat);
		}
		@SuppressWarnings("unchecked")
		TestData<I, R>[] dataAry = data.toArray(new TestData[data.size()]);
		assertTests(dataAry, (BiFunction<I, R, String>)null, (input, idx) -> action.apply(input));
	}


	public static final <I, E, R> void assertTests(TestData<I, R>[] testData,
			BiFunction<I, R, String> errorMessageGen, Function<I, R> action) {
		assertTests(testData, errorMessageGen, (input, idx) -> action.apply(input));
	}


	public static final <I, R> void assertTests(TestData<I, R>[] testData,
			BiFunction<I, R, String> errorMessageGen, BiFunction<I, Integer, R> action) {
		for(int i = 0, size = testData.length; i < size; i++) {
			final int counter = i;
			final TestData<I, R> dat = testData[i];
			final AtomicReference<R> resRef = new AtomicReference<>();

			if(dat.isExpectThrow()) {
				CheckTask.assertException(() -> resRef.set(action.apply(dat.getInput(), counter)));
			}
			else {
				resRef.set(action.apply(dat.getInput(), i));
				R res = resRef.get();
				R expected = dat.getExpected();
				String msg = (errorMessageGen != null ? errorMessageGen.apply(dat.getInput(), dat.getExpected()) : "") + ", input " + i + ": '" + Check.toDescriptiveString(res) +
						"' does not match expected: '" + Check.toDescriptiveString(dat.getExpected()) + "'";

				if(dat.isShouldInputEqualExpected()) {
					if(CheckArrays.isArrays(expected, res)) {
						CheckArrays.assertEquals(msg, expected, res, FLOAT_EPSILON, DOUBLE_EPSILON);
					}
					else {
						Assert.assertEquals(msg, expected, res);
					}
				}
				else {
					// TODO JUnit doesn't have assertArrayNotEquals() methods, so we'll just assume if the objects are different they don't have the same content if they're arrays
					Assert.assertNotEquals(msg, expected, res);
				}
			}
		}
	}


	private static final <T> T[] toArray(Iterable<T> iter) {
		List<T> values = new ArrayList<>();
		for(T value : iter) {
			values.add(value);
		}
		@SuppressWarnings("unchecked")
		T[] res = (T[])values.toArray();
		return res;
	}

}
