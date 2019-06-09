package common;

import java.util.Collection;

public class ListOperations {
	/**
	 * Compare la taille de deux listes.
	 * @param Collection list1, list2: Les listes que l'on veut comparer
	 * @return Integer: La taille de la liste la plus grande.
	 */
	public static<E> Integer largestListSize(Collection<E> list1, Collection<E> list2) {
		return Math.max(list1.size(), list2.size());
	}
}
