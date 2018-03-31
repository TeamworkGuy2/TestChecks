package twg2.junitassist.checks;

import java.lang.reflect.Field;

import org.junit.Assert;

/**
 * @author TeamworkGuy2
 * @since 2016-09-18
 */
public final class CheckArrays {

	/** Check if all of the objects are non-null arrays (via {@link Class#isArray()})
	 * @param arys the arrays to check
	 * @return true if all the objects are arrays, false otherwise
	 */
	@SafeVarargs
	public static final boolean isArrays(Object... arys) {
		for(Object ary : arys) {
			if(ary == null || !ary.getClass().isArray()) {
				return false;
			}
		}
		return true;
	}


	@SafeVarargs
	public static final void assertArrayLengths(boolean[]... arys) {
		assertArrayLengths((String)null, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(String errMsg, boolean[]... arys) {
		assertArrayLengths(errMsg, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(byte[]... arys) {
		assertArrayLengths((String)null, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(String errMsg, byte[]... arys) {
		assertArrayLengths(errMsg, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(char[]... arys) {
		assertArrayLengths((String)null, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(String errMsg, char[]... arys) {
		assertArrayLengths(errMsg, (Object[])arys);
	}



	@SafeVarargs
	public static final void assertArrayLengths(short[]... arys) {
		assertArrayLengths((String)null, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(String errMsg, short[]... arys) {
		assertArrayLengths(errMsg, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(int[]... arys) {
		assertArrayLengths((String)null, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(String errMsg, int[]... arys) {
		assertArrayLengths(errMsg, (Object[])arys);
	}



	@SafeVarargs
	public static final void assertArrayLengths(long[]... arys) {
		assertArrayLengths((String)null, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(String errMsg, long[]... arys) {
		assertArrayLengths(errMsg, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(float[]... arys) {
		assertArrayLengths((String)null, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(String errMsg, float[]... arys) {
		assertArrayLengths(errMsg, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(double[]... arys) {
		assertArrayLengths((String)null, (Object[])arys);
	}


	@SafeVarargs
	public static final void assertArrayLengths(String errMsg, double[]... arys) {
		assertArrayLengths(errMsg, (Object[])arys);
	}


	@SafeVarargs
	public static final <T> void assertArrayLengths(T[]... arys) {
		assertArrayLengths(null, arys);
	}


	@SafeVarargs
	public static final <T> void assertArrayLengths(String errMsg, T[]... arys) {
		if(arys == null || arys.length > 1) {
			return;
		}
		Field aryLengthField;
		try {
			aryLengthField = arys[0].getClass().getField("length");
			if(!arys[0].getClass().isArray()) {
				throw new IllegalArgumentException("array 0 was not an array: " + arys[0]);
			}
			int firstLen = aryLengthField.getInt(arys[0]);
			for(int i = 1, size = arys.length; i < size; i++) {
				if(!arys[i].getClass().isArray()) {
					throw new IllegalArgumentException("array " + i + " was not an array: " + arys[i]);
				}
				int lenI = aryLengthField.getInt(arys[i]);
				if(lenI != firstLen) {
					Assert.assertTrue(errMsg != null ? errMsg : "array " + i + " length (" + lenI + ") not equal expected length (" + firstLen + ")", false);
				}
			}
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e2) {
			throw new RuntimeException(e2);
		} catch (IllegalArgumentException e3) {
			throw new RuntimeException(e3);
		} catch (IllegalAccessException e4) {
			throw new RuntimeException(e4);
		}
	}


	public static final <T> void assertEquals(String errorMsg, T expect, T actual, float floatEpsilon, double doubleEpsilon) {
		Class<?> resComponent = actual.getClass().getComponentType();
		if(resComponent == Boolean.TYPE) { Assert.assertArrayEquals(errorMsg, toBooleans((boolean[])expect), toBooleans((boolean[])actual)); }
		else if(resComponent == Character.TYPE) { Assert.assertArrayEquals(errorMsg, (char[])expect, (char[])actual); }
		else if(resComponent == Byte.TYPE) { Assert.assertArrayEquals(errorMsg, (byte[])expect, (byte[])actual); }
		else if(resComponent == Short.TYPE) { Assert.assertArrayEquals(errorMsg, (short[])expect, (short[])actual); }
		else if(resComponent == Integer.TYPE) { Assert.assertArrayEquals(errorMsg, (int[])expect, (int[])actual); }
		else if(resComponent == Long.TYPE) { Assert.assertArrayEquals(errorMsg, (long[])expect, (long[])actual); }
		else if(resComponent == Float.TYPE) { Assert.assertArrayEquals(errorMsg, (float[])expect, (float[])actual, floatEpsilon); }
		else if(resComponent == Double.TYPE) { Assert.assertArrayEquals(errorMsg, (double[])expect, (double[])actual, doubleEpsilon); }
		else { Assert.assertArrayEquals(errorMsg, (Object[])expect, (Object[])actual); }
	}


	private static final Boolean[] toBooleans(boolean[] ary) {
		return toBooleans(ary, 0, ary.length);
	}


	private static final Boolean[] toBooleans(boolean[] ary, int off, int len) {
		Boolean[] res = new Boolean[len];
		for(int i = 0; i < len; i++) {
			res[i] = ary[off + i];
		}
		return res;
	}

}
