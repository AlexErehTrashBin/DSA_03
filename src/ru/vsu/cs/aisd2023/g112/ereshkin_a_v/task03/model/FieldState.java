package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model;

import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.stack.IntStack;

public class FieldState {
	private final int size;
	private final int[] stackArray;
	private final int currentColumn;

	public FieldState(IntStack stack, int currentColumn, int size) {
		this.size = size;
		this.stackArray = stack.toArray();
		this.currentColumn = currentColumn;
	}

	public int[][] getField() {
		int[][] resultMatrix = new int[size][size];
		for (int i = 0; i < stackArray.length; i++) {
			int col = stackArray[i];
			for (int j = 0; j < stackArray.length; j++) {
				resultMatrix[j][i] = j == col ? 1 : 0;
			}
		}
		return resultMatrix;
	}
	public static int[][] getField(IntStack stack, int size){
		int[] stackArray = stack.toArray();
		int[][] resultMatrix = new int[stack.size()][stack.size()];
		for (int i = 0; i < size; i++) {
			int col = stackArray[i];
			for (int j = 0; j < size; j++) {
				resultMatrix[j][i] = j == col ? 1 : 0;
			}
		}
		return resultMatrix;
	}

	private boolean isValid(int[] stack, int currentColumn) {
		if (stack.length == 0) {
			return true;
		}
		int[][] matrix = new int[size][size];
		int[] colsArray = stack;
		int positionRow = size - stack.length - 1;
		for (int i = 0; i < stack.length; i++) { // row is i, col is stack.pop()
			if (colsArray[i] == currentColumn) {
				return false;
			}
		}
		for (int i = 0, row = size - stack.length; i < stack.length; i++, row++) {
			matrix[row][colsArray[i]] = 1;
		}
		// Побочная диагональ
		for (int i = currentColumn - 1, j = positionRow + 1; i >= 0 && j < size; i--, j++) {
			if (matrix[j][i] == 1) {
				return false;
			}
		}
		// Главная диагональ
		for (int i = currentColumn + 1, j = positionRow + 1; i < size && j < size; i++, j++) {
			if (matrix[j][i] == 1) {
				return false;
			}
		}
		return true;
	}

	public boolean haveWon(){
		return isValid(stackArray, currentColumn);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FieldState state = (FieldState) o;

		if (size != state.size) return false;
		boolean haveWon = haveWon();
		return haveWon == state.haveWon();
	}
}
