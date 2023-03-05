package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model;

public class Queen {
	private final int row;
	private final int column;

	public Queen(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return column;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Queen queen = (Queen) o;

		if (row != queen.row) return false;
		return column == queen.column;
	}

	@Override
	public int hashCode() {
		int result = row;
		result = 31 * result + column;
		return result;
	}
}
