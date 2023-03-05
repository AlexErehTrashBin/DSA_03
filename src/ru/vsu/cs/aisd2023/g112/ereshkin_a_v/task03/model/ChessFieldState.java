package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessFieldState {
	private int[][] field;
	private final int size;
	private boolean won = false;

	public ChessFieldState(int[][] field) {
		this.size = field.length;
		this.field = field;
		won = setWon();
	}
	public List<ChessFieldState> getRotatedStatesIncludingSelf(){
		List<ChessFieldState> result = new ArrayList<>();
		ChessFieldState rotatedZero = new ChessFieldState(this.getField());
		ChessFieldState rotatedNinety = new ChessFieldState(getRotatedClockwise(rotatedZero.getField()));
		ChessFieldState rotatedOneEighty = new ChessFieldState(getRotatedClockwise(rotatedNinety.getField()));
		ChessFieldState rotatedTwoSeventy = new ChessFieldState(getRotatedClockwise(rotatedOneEighty.getField()));
		result.add(rotatedZero);
		result.add(rotatedNinety);
		result.add(rotatedOneEighty);
		result.add(rotatedTwoSeventy);
		return result;
	}
	private int[][] getRotatedClockwise(int[][] field){
		int side = field.length;
		int[][] result = new int [side][side];
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				result[i][j] = field[side - j - 1][i];
			}
		}
		return result;
	}
	private boolean setWon() {
		int queens = 0;
		for (int[] row : field) {
			for (int element : row) {
				if (element == -1) {
					queens++;
				}
				if (queens == 8) return true;
			}
		}
		return false;
	}

	public ChessFieldState(int size){
		this.size = size;
		this.field = new int[size][size];
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
		field = new int[8][8];
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

		ChessFieldState state = (ChessFieldState) o;

		if (size != state.size) return false;
		if (won != state.won) return false;
		return Arrays.deepEquals(field, state.field);
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(field);
	}
}
