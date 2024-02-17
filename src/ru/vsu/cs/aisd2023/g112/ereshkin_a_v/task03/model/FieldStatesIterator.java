package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model;

import java.util.Iterator;
import java.util.List;

public class FieldStatesIterator implements Iterator<FieldState> {
	private final List<FieldState> sourceList;
	private int index = 0;

	public FieldStatesIterator(List<FieldState> sourceList) {
		this.sourceList = sourceList;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public boolean hasNext() {
		return index + 1 < sourceList.size();
	}

	public boolean hasPrevious() {
		return index - 1 >= 0;
	}

	@Override
	public FieldState next() {
		index++;
		return sourceList.get(index);
	}

	public FieldState previous() {
		index--;
		return sourceList.get(index);
	}
}
