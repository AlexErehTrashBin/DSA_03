package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model;

import java.util.Iterator;
import java.util.List;

public class FieldStatesIterator implements Iterator<FieldState> {
	private final List<FieldState> sourceList;
	private int index = 0;
	private int correctIndex = -1;

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

	public boolean hasNextCorrect() {
		if (!hasNext()) return false;
		int i = getWinStateOnRightIndex(index + 1);
		boolean exists = i != -1;
		if (exists) {
			correctIndex = i;
		}
		return exists;
	}

	public boolean hasPreviousCorrect() {
		if (!hasPrevious()) return false;
		int i = getWinStateOnLeftIndex();
		boolean exists = i != -1;
		if (exists) {
			correctIndex = i;
		}
		return exists;
	}

	private int getWinStateOnLeftIndex() {
		for (int i = index - 1; i >= 0; i--) {
			FieldState currentState = sourceList.get(i);
			if (currentState.haveWon()) return i;
		}
		return -1;
	}

	private int getWinStateOnRightIndex(int startIndex) {
		for (int i = startIndex; i < sourceList.size(); i++) {
			FieldState currentState = sourceList.get(i);
			if (currentState.haveWon()) return i;
		}
		return -1;
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

	public FieldState nextCorrect() {
		index = correctIndex;
		correctIndex = -1;
		return sourceList.get(index);
	}

	public FieldState previousCorrect() {
		index = correctIndex;
		correctIndex = -1;
		return sourceList.get(index);
	}
}
