package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.stack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyStack<T> implements AbstractStack<T>, Iterable<T> {
	private MyStackNode<T> head = null;
	private int count = 0;
	public int size(){
		return count;
	}
	@Override
	public void push(T value) {
		head = new MyStackNode<>(value, head);
		count++;
	}

	@Override
	public T pop(){
		T result = null;
		try {
			result = this.peek();
		} catch (Exception e) {
			return result;
		}
		head = head.next;
		count--;
		return result;
	}

	@Override
	public T peek() throws Exception {
		if (count == 0) {
			throw new Exception("Стек пустой!");
		}
		return head.value;
	}
	public List<T> toList() {
		List<T> list = new ArrayList<>();
		for (T element : this) {
			list.add(element);
		}
		return list;
	}

	@Override
	public Iterator<T> iterator() {
		class StackIterator implements Iterator<T> {
			MyStackNode<T> curr = head;

			@Override
			public boolean hasNext() {
				return curr != null;
			}

			@Override
			public T next() {
				T value = curr.value;
				curr = curr.next;
				return value;
			}
		}
		return new StackIterator();
	}

	@Override
	public int count() {
		return count;
	}

	@Override
	public void empty() {
		head = null;
		count = 0;
	}

	@Override
	public boolean isEmpty() {
		return AbstractStack.super.isEmpty();
	}

	private static class MyStackNode<T> {
		public T value;
		public MyStackNode<T> next;

		public MyStackNode(T value, MyStackNode<T> next) {
			this.value = value;
			this.next = next;
		}
	}
}
