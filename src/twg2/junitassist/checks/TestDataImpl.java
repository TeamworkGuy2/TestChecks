package twg2.junitassist.checks;

/** Object for storing test case input data, result data, expected data, and flags indicating what test result is expected
 * @param <I> the input data type
 * @param <R> the comparison result data type
 * @author TeamworkGuy2
 * @since 2014-12-19
 */
public class TestDataImpl<I, R> implements TestData<I, R> {
	private I input;
	private R expected;
	private R result;
	private boolean shouldInputEqualExpected;
	private boolean expectThrow;


	/** Create a test object with input and result data
	 * @param input the input data used by the test
	 * @param expected the expected result
	 * @param result the actual result
	 * @param shouldInputEqualExpected whether the input should equal the expected result
	 * @param expectThrow whether the test is expected to throw an exception
	 */
	public TestDataImpl(I input, R expected, R result, boolean shouldInputEqualExpected, boolean expectThrow) {
		this.input = input;
		this.expected = expected;
		this.result = result;
		this.shouldInputEqualExpected = shouldInputEqualExpected;
		this.expectThrow = expectThrow;
	}


	@Override
	public I getInput() {
		return input;
	}


	@Override
	public void setInput(I input) {
		this.input = input;
	}


	@Override
	public R getExpected() {
		return expected;
	}


	@Override
	public void setExpected(R expected) {
		this.expected = expected;
	}


	@Override
	public R getResult() {
		return result;
	}


	@Override
	public void setResult(R result) {
		this.result = result;
	}


	@Override
	public boolean isShouldInputEqualExpected() {
		return shouldInputEqualExpected;
	}


	@Override
	public void setShouldInputEqualExpected(boolean shouldInputEqualExpected) {
		this.shouldInputEqualExpected = shouldInputEqualExpected;
	}


	@Override
	public boolean isExpectThrow() {
		return expectThrow;
	}


	@Override
	public void setExpectThrow(boolean expectThrow) {
		this.expectThrow = expectThrow;
	}


	public static final <I, R> TestData<I, R> matchTrue(I input, R expected) {
		return new TestDataImpl<>(input, expected, null, true, false);
	}


	public static final <I, R> TestData<I, R> matchFalse(I input, R expected) {
		return new TestDataImpl<>(input, expected, null, false, false);
	}


	public static final <I, R> TestData<I, R> shouldThrow(I input, R expected) {
		return new TestDataImpl<>(input, expected, null, false, true);
	}

}
