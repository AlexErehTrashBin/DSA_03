package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model;

import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.stack.IntStack;

import java.util.ArrayList;
import java.util.List;

public class NQueensSolver {
	private final List<FieldState> states = new ArrayList<>();
	private final int size;

	public NQueensSolver(int size) {
		this.size = size;
	}

	public static List<FieldState> solveTask() {
		return solveTask(8);
	}

	public static List<FieldState> solveTask(int size) {
		NQueensSolver cf = new NQueensSolver(size);
		cf.hiddenSolve();
		return cf.getStates();
	}

	private void hiddenSolve() {
		// Уже нашли решения для данной конфигурации поля
		if (!states.isEmpty()) return;
		// Стек для хранения столбцов ферзей
		IntStack columnsStack = new IntStack();
		// Текущий рассматриваемый столбец
		int currentColumn = 0;
		while (true) {
			boolean foundValid = false;
			while (currentColumn < size) {
				FieldState state = new FieldState(columnsStack, currentColumn, size);
				if (state.haveWon()) {
					columnsStack.push(currentColumn);
					currentColumn = 0;
					foundValid = true;
				} else {
					currentColumn++;
				}
			}

			if (!foundValid) {
				if (columnsStack.isEmpty()) {
					// Конец
					return;
				} else {
					// Убираем последнюю колонку из стека, путём
					// вытаскивания, и далее прибавляем к этой колонке единицу
					currentColumn = columnsStack.pop();
					currentColumn++;
				}
			}
			// В стеке количество колонок ферзей совпадает с размером поля
			if (columnsStack.size() == size) {
				// Добавляем поле в список состояний
				states.add(new FieldState(columnsStack, currentColumn, size));
				// Убираем последнюю колонку из стека, путём
				// вытаскивания, и далее прибавляем к этой колонке единицу
				currentColumn = columnsStack.pop();
				currentColumn++;
			}
		}
	}

	public List<FieldState> getStates() {
		return new ArrayList<>(states);
	}

}
