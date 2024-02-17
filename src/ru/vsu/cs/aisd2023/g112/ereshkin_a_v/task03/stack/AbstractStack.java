package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.stack;

import java.util.Iterator;

public interface AbstractStack<T> {
	void push(T value);

	T pop() throws Exception;

	T peek() throws Exception;

	int count();
	void empty();
	Iterator<T> iterator();
	default boolean isEmpty() {
		return count() == 0;
	}
}
