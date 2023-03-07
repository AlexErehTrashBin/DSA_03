package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model;

import java.util.*;

public class FieldState {
	private int[][] field;
	private final int size;
	private boolean won = false;

	public FieldState(int[][] field) {
		this.size = field.length;
		this.field = field;
		won = calculateHaveWon();
	}

	public FieldState(int size){
		this.size = size;
		this.field = new int[size][size];
	}
	private boolean calculateHaveWon() {
		int queens = 0;
		for (int[] row : field) {
			for (int element : row) {
				if (element == -1) {
					queens++;
				}
			}
		}
		return queens == size;
	}
	public void setValue(int row, int column, int value){
		field[row][column] = value;
	}
	public void incrementValue(int row, int column){
		field[row][column]++;
	}
	public void decrementValue(int row, int column){
		field[row][column]--;
	}
	public int getValue(int row, int column) {
		return field[row][column];
	}
	public int[][] getField(){
		return copy(field);
	}
	public int getSize(){
		return size;
	}
	public void empty(){
		field = new int[size][size];
	}
	public boolean haveWon(){
		return won;
	}
	private int[][] copy(int[][] matrix) {
		int size = matrix.length;
		int[][] matrixCopy = new int[size][size];
		for (int i = 0; i < size; i++)  {
			int[] array = matrix[i];
			System.arraycopy(array, 0, matrixCopy[i], 0, size);
		}
		return matrixCopy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FieldState state = (FieldState) o;

		if (size != state.size) return false;
		if (won != state.won) return false;
		return Arrays.deepEquals(field, state.field);
	}
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(field);
	}
}
