package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.stack;

import java.util.List;

public class IntStack extends MyStack<Integer> {
	public IntStack() {
		super();
	}
	public IntStack(IntStack stack) {
		super();
		for (Integer element : stack) {
			this.push(element);
		}
	}
	private int[] toArray(List<Integer> list){
		int[] arr = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}
	public int[] toArray() {
		return toArray(toList());
	}
}
