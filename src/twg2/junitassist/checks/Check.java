package twg2.junitassist.checks;

import java.util.Arrays;

import org.junit.Assert;

/** Static methods for checking type, bounds, and other requirements
 * @author TeamworkGuy2
 * @since 2014-6-3
 */
public final class Check {

	/** Throw an array index out of bounds exception if the {@code index} is less
	 * than {@code 0} or greater than or equal to {@code length} 
	 * @param index the index to check
	 * @param length the maximum value of the index (exclusive)
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range specified
	 */
	public static final void bounds(int index, int length) {
		if(index < 0 || index > length) {
			throw new ArrayIndexOutOfBoundsException(index + ", length=" + length);
		}
	}


	/** Throw an index out of bounds exception if the {@code index} is less
	 * than {@code min} or greater than or equal to {@code max} 
	 * @param index the index to check
	 * @param min the minimum value of the index (inclusive)
	 * @param max the maximum value of the index (exclusive)
	 * @throws IndexOutOfBoundsException if the index is outside the range specified
	 */
	public static final void bounds(int index, int min, int max) {
		if(index < min || index >= max) {
			throw new IndexOutOfBoundsException(index + ", min" + min + ", max=" + max);
		}
	}


	/** Clamp the specified value to the specified range. For example:<br/>
	 * {@code clamp(24, 0, 10) returns 10}<br/>
	 * {@code clamp(4, 1, 20) returns 4}<br/>
	 * {@code clamp(-31, 0, 100) returns 0}
	 * @param num the number to clamp
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return {@code num}, or {@code min} if {@code num} is less than {@code min},
	 * or {@code max} if {@code num} is greater than {@code max}
	 */
	public static final int clamp(int num, int min, int max) {
		return Math.max(Math.min(num, max), min);
	}


	/**
	 * @see #clamp(int, int, int)
	 */
	public static final float clamp(float num, float min, float max) {
		return Math.max(Math.min(num, max), min);
	}


	/**
	 * @see #clamp(int, int, int)
	 */
	public static final long clamp(long num, long min, long max) {
		return Math.max(Math.min(num, max), min);
	}


	/**
	 * @see #clamp(int, int, int)
	 */
	public static final double clamp(double num, double min, double max) {
		return Math.max(Math.min(num, max), min);
	}


	/** Throw a null pointer exception if the object is null
	 * @param obj the object to check
	 * @throws NullPointerException if the object is null
	 */
	public static final void nullArg(Object obj) {
		if(obj == null) {
			throw new NullPointerException();
		}
	}


	/** Check if the string is null, length zero, or contains only whitespace characters.<br/>
	 * Equivalent to: {@code str == null ? true : (str.trim().length() == 0 ? true : false);}
	 * @param str the string to check
	 * @return true if the string is null, length zero, or contains only whitespace characters, false otherwise
	 */
	public static final boolean isEmpty(String str) {
		if(str == null || str.length() == 0) { return true; }
		int len = str.length();
		int start = 0;
		
		while((start < len) && (str.charAt(start) <= ' ')) {
			start++;
		}
		while((start < len) && (str.charAt(len - 1) <= ' ' )) {
			len--;
		}
		if(start == len) { return true; }
		return false;
	}


	/** Return an empty string if the string is null, length zero, or contains only whitespace characters.<br/>
	 * Equivalent to: {@code str == null ? "" : (str.trim().length() == 0 ? "" : str);}
	 * @param str the string to check
	 * @return an empty string ({@code ""}) if the string is null, length zero, or contains only
	 * whitespace characters, returns {@code str} otherwise
	 */
	public static final String checkEmpty(String str) {
		if(str == null || str.length() == 0) { return ""; }
		int len = str.length();
		int start = 0;
		
		while((start < len) && (str.charAt(start) <= ' ')) {
			start++;
		}
		while((start < len) && (str.charAt(len - 1) <= ' ' )) {
			len--;
		}
		if(start == len) { return ""; }
		return str;
	}


	/** Check if two strings are equal
	 * @param str the first string to compare
	 * @param str2 the second string to compare
	 * @param msg a message to include in the error if the two strings are not equal
	 * @throws Error if {@code str} does not equal {@code str2}
	 */
	public static final void checkEqual(String str, String str2, String msg) {
		if(!str.equals(str2)) {
			throw new Error("input: '" + str + "' does not match expected: '" + str2 + "', " + msg);
		}
	}


	public static final void assertEqual(String str, String str2, String msg) {
		Assert.assertEquals("input: '" + str + "' does not match expected: '" + str2 + "', " + msg, str, str2);
	}


	@SafeVarargs
	public static final void assertLengths(int... lens) {
		assertLengths((String)null, lens);
	}


	@SafeVarargs
	public static final void assertLengths(String errMsg, int... lens) {
		if(lens == null || lens.length > 1) {
			return;
		}
		int firstLen = lens[0];
		for(int i = 1, size = lens.length; i < size; i++) {
			int lenI = lens[i];
			if(lenI != firstLen) {
				Assert.assertTrue(errMsg != null ? errMsg : "array " + i + " length (" + lenI + ") not equal expected length (" + firstLen + ")", false);
			}
		}
	}


	// package-private
	static final void expectLength(int len1, int len2) {
		if(len1 != len2) {
			throw new IllegalArgumentException("inputs (length: " + len1 + ") should be equal in length to (length: " + len2 + ")");
		}

	}


	// package-private
	static final void assertLengthEqual(int len1, int len2) {
		Assert.assertEquals("inputs (length: " + len1 + ") should be equal in length to (length: " + len2 + ")", len1, len2);
	}


	// package-private
	static final <T> String toDescriptiveString(T obj) {
		if(obj.getClass().isArray()) {
			if(obj.getClass().getComponentType() == Boolean.TYPE) { return Arrays.toString((boolean[])obj); }
			else if(obj.getClass().getComponentType() == Character.TYPE) { return Arrays.toString((char[])obj); }
			else if(obj.getClass().getComponentType() == Byte.TYPE) { return Arrays.toString((byte[])obj); }
			else if(obj.getClass().getComponentType() == Short.TYPE) { return Arrays.toString((short[])obj); }
			else if(obj.getClass().getComponentType() == Integer.TYPE) { return Arrays.toString((int[])obj); }
			else if(obj.getClass().getComponentType() == Long.TYPE) { return Arrays.toString((long[])obj); }
			else if(obj.getClass().getComponentType() == Float.TYPE) { return Arrays.toString((float[])obj); }
			else if(obj.getClass().getComponentType() == Double.TYPE) { return Arrays.toString((double[])obj); }
			return Arrays.toString((Object[])obj);
		}
		return obj.toString();
	}

}
