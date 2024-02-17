package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.cli;

import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model.FieldState;
import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model.NQueensSolver;
import ru.vsu.cs.util.ArrayUtils;

import java.util.List;

public class SolveTaskCLI {
	public static void displaySolution(FieldState fieldState){
		System.out.println(ArrayUtils.toString(fieldState.getField()) + '\n');
	}
	public static void solve(){
		List<FieldState> winStates = NQueensSolver.solveTask();
		for (FieldState winState : winStates) {
			displaySolution(winState);
		}
	}
}
