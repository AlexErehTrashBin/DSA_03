package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03;

import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.cli.InputArgs;
import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.cli.SolveTaskCLI;
import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.gui.ChessForm;
import ru.vsu.cs.util.SwingUtils;

import java.util.Locale;

import static ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.cli.CLIUtils.*;

public class Program {

	public static void winMain() {
		//SwingUtils.setLookAndFeelByName("Windows");
		Locale.setDefault(Locale.ROOT);

		SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

		java.awt.EventQueue.invokeLater(() -> new ChessForm().setVisible(true));
	}

	public static void main(String[] args) {
		InputArgs params = parseCmdArgs(args);
		//SolveTaskCLI.solve();
		if (params.window) {
			winMain();
		}
		else if (params.runIndividualFileCheck) {
			SolveTaskCLI.solve();
		}
	}
}
