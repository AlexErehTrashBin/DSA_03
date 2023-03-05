package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model;

import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.stack.MyStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChessField {
	private static final int DEFAULT_FIELD_SIZE = 8;
	private final MyStack<Queen> queensStack;
	private final ArrayList<ChessFieldState> states = new ArrayList<>();
	private final Set<ChessFieldState> winStates = new HashSet<>();
	private ChessFieldState currentFieldState;

	public ChessField() {
		this.currentFieldState = new ChessFieldState(DEFAULT_FIELD_SIZE);
		this.queensStack = new MyStack<>();
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
	public List<ChessFieldState> setAllQueen(ChessFieldState board) throws Exception{
		this.currentFieldState = board;
		addBoardToStates(board);
		for (int startRow = 0; startRow < currentFieldState.getSize(); startRow++) {
			int row = startRow;
			for (int startCol = 0; startCol < currentFieldState.getSize(); startCol++) {
				int tempCol = startCol;
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
				row = writeSetQueen();
			}
		}


		return new ArrayList<>(states);
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

	private void addBoardToStates(ChessFieldState board) {
		ChessFieldState state = new ChessFieldState(board.getField());
		if (state.haveWon()){
			List<ChessFieldState> statesToAdd = state.getRotatedStatesIncludingSelf();
			winStates.addAll(statesToAdd);
		}
		states.add(state);
	}

	public ArrayList<ChessFieldState> getWinStates() {
		return new ArrayList<>(winStates);
	}
}
