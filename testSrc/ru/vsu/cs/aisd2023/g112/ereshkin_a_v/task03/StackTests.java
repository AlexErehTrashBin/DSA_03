package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03;

import org.junit.Test;
import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.stack.IntStack;
import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.stack.MyStack;
import ru.vsu.cs.util.ArrayUtils;

public class StackTests {
	@Test
	public void test1(){
		MyStack<Integer> myStack = new MyStack<>();
		myStack.push(5);
		myStack.push(4);
		myStack.push(3);
		myStack.push(2);
		myStack.iterator().forEachRemaining(it -> System.out.printf("%d ", it));
	}
	@Test
	public void test2(){
		IntStack intStack = new IntStack();
		intStack.push(5);
		intStack.push(4);
		intStack.push(3);
		intStack.push(2);
		System.out.println(ArrayUtils.toString(intStack.toArray()));
	}
}
