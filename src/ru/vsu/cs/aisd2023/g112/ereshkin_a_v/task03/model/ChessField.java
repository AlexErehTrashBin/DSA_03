package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model;

import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.stack.MyStack;

import java.util.ArrayList;
import java.util.List;

public class ChessField {
	private static final int DEFAULT_FIELD_SIZE = 8;
	private final MyStack<Queen> queensStack;
	private final List<FieldState> states = new ArrayList<>();
	private final int size;
	private FieldState currentFieldState;

	public ChessField() {
		this.size = DEFAULT_FIELD_SIZE;
		this.currentFieldState = new FieldState(size);
		this.queensStack = new MyStack<>();
	}
	public ChessField(int size) {
		this.size = size;
		this.currentFieldState = new FieldState(size);
		this.queensStack = new MyStack<>();
	}

	public List<FieldState> getStates() {
		return new ArrayList<>(states);
	}

	/**
	 * Метод, который ставит ферзя.
	 * @param queen ферзь (а точнее берутся его координаты)
	 * */
	private void setQueen(Queen queen) {
		int queenRow = queen.getRow();
		int queenCol = queen.getCol();
		for (int i = 0; i < currentFieldState.getSize(); i++) {
			currentFieldState.incrementValue(queenRow, i);
			currentFieldState.incrementValue(i, queenCol);
			int diag = queenCol - queenRow + i;
			if (diag >= 0 && diag < currentFieldState.getSize()) {
				currentFieldState.incrementValue(i, diag);
			}
			diag = queenRow + queenCol - i;
			if (diag >= 0 && diag < currentFieldState.getSize()) {
				currentFieldState.incrementValue(i, diag);
			}
		}
		currentFieldState.setValue(queenRow, queenCol, -1);
		queensStack.push(queen);
	}

	/**
	 * Метод, который удаляет ферзя с поля и исправляет зависимые клетки
	 *
	 * @param queen ферзь
	 */
	private void deleteQueen(Queen queen) {
		int row = queen.getRow();
		int col = queen.getCol();
		for (int i = 0; i < currentFieldState.getSize(); i++) {
			currentFieldState.decrementValue(row, i);
			currentFieldState.decrementValue(i, col);
			int diag = col - row + i;
			if (diag >= 0 && diag < currentFieldState.getSize()) {
				currentFieldState.decrementValue(i, diag);
			}
			diag = row + col - i;
			if (diag >= 0 && diag < currentFieldState.getSize()) {
				currentFieldState.decrementValue(i, diag);
			}
		}
		currentFieldState.setValue(row, col, 0);
	}

	/**
	 * Метод, который пытается ставить ферзя
	 *
	 * @param row индекс строки, в которую попытаться поставить ферзя
	 * @param col индекс столбца, в который попытаться поставить ферзя
	 * @throws Exception если невозможно на данную строку поставить ферзя
	 */
	private boolean tryAndGetResultOfSettingQueen(int row, int col) throws Exception {
		if (row < 0 || row >= currentFieldState.getSize()) {
			throw new Exception("Невозможно поставить ферзя на данную строку (выход за пределы допустимых значений)");
		}
		for (int c = col; c < currentFieldState.getSize(); c++) {
			if (currentFieldState.getValue(row, c) == 0) {
				setQueen(new Queen(row, c));
				return true;
			}
		}
		return false;
	}
	private void setAllQueen(FieldState board) throws Exception{
		states.clear();
		this.currentFieldState = board;
		addBoardToStates(board);
		int row = writeSetQueen();
		int tempCol = 0;
		while (row < currentFieldState.getSize()) {
			// Пытаемся поставить ферзя по текущему адресу
			if (tryAndGetResultOfSettingQueen(row, tempCol)) {
				row++;
				tempCol = 0;
				addBoardToStates(board);
				continue;
			}
			/// Не удаётся поставить ферзя: откатываемся на строку назад и убираем одного ферзя
			tempCol = queensStack.peek().getCol() + 1;
			deleteQueen(queensStack.pop());
			row--;
			addBoardToStates(board);
			/// Пробуем, но уже на другой колонке
			if (tempCol < currentFieldState.getSize() && tryAndGetResultOfSettingQueen(row, tempCol)) {
				row++;
				tempCol = 0;
				addBoardToStates(board);
				continue;
			}
			tempCol = queensStack.peek().getCol() + 1;
			deleteQueen(queensStack.pop());
			row--;
			addBoardToStates(board);
		}
		board.empty();
		queensStack.empty();

	}

	private int writeSetQueen() {
		int row = 0;
		for (int i = 0; i < currentFieldState.getSize(); i++) {
			for (int j = 0; j < currentFieldState.getSize(); j++) {
				if (currentFieldState.getValue(i, j) == -1) {
					row++;
					setQueen(new Queen(i, j));
				}
			}
		}
		return row;
	}
	/**
	 * Добавить состояние поля в список состояний
	 * */
	private void addBoardToStates(FieldState state) {
		states.add(new FieldState(state.getField()));
	}
	/**
	 * Метод получения всех выигрышных состояний у данного игрового поля
	 * @return выигрышные состояния
	 * */
	public List<FieldState> getAllWinStates() {
		// TODO Find win states by iterating through all states
		List<FieldState> winStates = new ArrayList<>();
		FieldStatesIterator iterator = new FieldStatesIterator(states);
		while (iterator.hasNextCorrect()) {
			FieldState next = iterator.nextCorrect();
			if (next.haveWon()) winStates.add(next);
		}
		return winStates;
	}
	public static List<FieldState> getAllStates(int size) {
		ChessField cf = new ChessField(size);
		try {
			cf.setAllQueen(new FieldState(size));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new ArrayList<>(cf.getStates());
	}
	public static List<FieldState> solveTask() {
		return solveTask(8);
	}

	public static List<FieldState> solveTask(int size) {
		ChessField cf = new ChessField(size);
		try {
			cf.setAllQueen(new FieldState(size));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cf.getAllWinStates();
	}
}
