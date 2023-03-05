package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.stack;

public class MyStack<T> implements AbstractStack<T>{
	private static class MyLinkedListNode<T> {
		public T value;
		public MyLinkedListNode<T> next;

		public MyLinkedListNode(T value, MyLinkedListNode<T> next) {
			this.value = value;
			this.next = next;
		}
	}

	private MyLinkedListNode<T> head = null;
	private int count = 0;

	@Override
	public void push(T value) {
		head = new MyLinkedListNode<>(value, head);
		count++;
	}

	@Override
	public T pop() throws Exception {
		T result = this.peek();
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
}
