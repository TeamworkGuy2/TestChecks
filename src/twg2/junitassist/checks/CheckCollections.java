package twg2.junitassist.checks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;

/**
 * @author TeamworkGuy2
 * @since 2015-7-12
 */
public class CheckCollections {


	public static <E> void assertLooseEquals(Iterable<? extends E> e1, Iterable<? extends E> e2) {
		assertLooseEquals(null, e1, e2);
	}


	public static <E> void assertLooseEquals(String message, Iterable<? extends E> e1, Iterable<? extends E> e2) {
		List<E> c1 = toList(e1);
		@SuppressWarnings("unchecked")
		Collection<E> c1Copy = e1 instanceof Collection ? (Collection<E>) e1 : new ArrayList<>(c1);

		List<E> c2 = toList(e2);
		@SuppressWarnings("unchecked")
		Collection<E> c2Copy = e2 instanceof Collection ? (Collection<E>) e2 : new ArrayList<>(c2);

		c1.removeAll(c2Copy);
		c2.removeAll(c1Copy);

		String msg = (message != null ? message + ": " : "") + toList(e1) + ", not equal to: " + toList(e2) + ", remaining arg1=" + c1 + ", arg2=" + c2;
		Assert.assertTrue(msg, c1.size() == 0 && c2.size() == 0);
	}


	public static <E> void assertLooseEqualsNested(Iterable<? extends Iterable<? extends E>> e1, Iterable<? extends Iterable<? extends E>> e2) {
		Iterator<? extends Iterable<? extends E>> i1 = e1.iterator();
		Iterator<? extends Iterable<? extends E>> i2 = e2.iterator();
		int baseCount = 0;
		while(i1.hasNext() && i2.hasNext()) {
			Iterable<? extends E> list1 = i1.next();
			Iterable<? extends E> list2 = i2.next();

			assertLooseEquals("sub-collection " + baseCount, list1, list2);

			baseCount++;
		}

		int count1 = baseCount;
		int count2 = baseCount;

		while(i1.hasNext()) {
			i1.next();
			count1++;
		}

		while(i2.hasNext()) {
			i2.next();
			count2++;
		}

		if(i1.hasNext() || i2.hasNext()) {
			Assert.assertTrue("collection 1 size=" + count1 + ", collection 2 size=" + count2 + " do not match", false);
		}
	}


	static final <E> List<E> toList(Iterable<? extends E> iter) {
		List<E> res = new ArrayList<>();
		for(E e : iter) {
			res.add(e);
		}
		return res;
	}

}
