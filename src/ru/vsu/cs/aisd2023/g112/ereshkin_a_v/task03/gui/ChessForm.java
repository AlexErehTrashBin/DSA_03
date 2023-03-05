package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.gui;

import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model.ChessField;
import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model.ChessFieldState;
import ru.vsu.cs.util.JTableUtils;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ChessForm extends JFrame{
    private static final int DEFAULT_WINDOW_WIDTH = 800;
    private static final int DEFAULT_WINDOW_HEIGHT = 800;
    private JTable arrayTable;
    private JPanel mainPanel;
    private JButton startButton;
    private JButton nextButton;
    private JButton stopButton;
    private JButton prevButton;
    private JButton setQueenButton;
    private JButton resetButton;
    private JButton nextSolutionButton;
    private JButton prevSolutionButton;
    private JLabel solutionNumberLabel;

    private ChessFieldState board;
    private List<ChessFieldState> states = new ArrayList<>();
    private List<ChessFieldState> winStates = new ArrayList<>();
    private Timer timer;
    private final ChessField chess = new ChessField();
    private int currentStateIndex = 0;
    private int currentWinStateIndex = 0;
    public ChessForm() {
        this.setTitle("Chess");
        this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(arrayTable, 40, true, true, false, false);
        arrayTable.setRowHeight(40);
        board = new ChessFieldState(8);
        updateView(board);
        setQueenButton.addActionListener(actionEvent -> {
            try {
                int[][] boardMatrix = JTableUtils.readIntMatrixFromJTable(arrayTable);
                if (boardMatrix == null) throw new RuntimeException();
                board = new ChessFieldState(boardMatrix);
                states = chess.setAllQueen(board);
                winStates = chess.getWinStates();
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        GUIUtils.addActionListener(resetButton, () -> {
            states.clear();
            board.empty();
            updateView(board);
            currentStateIndex = 0;
        });
        GUIUtils.addActionListener(startButton, () -> timer.start());
        GUIUtils.addActionListener(stopButton, () -> {
            timer.stop();
            currentStateIndex--;
        });
        GUIUtils.addActionListener(nextButton, () -> {
            if (currentStateIndex == states.size() - 1){
                currentStateIndex = 0;
            } else if (currentStateIndex < states.size() - 1) {
                currentStateIndex++;
            }
            updateView();
        });
        GUIUtils.addActionListener(prevButton, () -> {
            if (currentStateIndex == 0){
                currentStateIndex = states.size() - 1;
            } else if (currentStateIndex > 0) {
                currentStateIndex--;
            }
            updateView();
        });

        GUIUtils.addActionListener(nextSolutionButton, () -> {
            if (currentWinStateIndex == winStates.size() - 1){
                currentWinStateIndex = 0;
            } else if (currentWinStateIndex < winStates.size() - 1) {
                currentWinStateIndex++;
            }
            updateWinView();
        });
        GUIUtils.addActionListener(prevSolutionButton, () -> {
            if (currentWinStateIndex == 0){
                currentWinStateIndex = winStates.size() - 1;
            } else if (currentWinStateIndex > 0) {
                currentWinStateIndex--;
            }
            updateWinView();
        });

        timer = new Timer(1000, actionEvent -> {
            if (currentStateIndex < states.size()) {
                updateView();
                currentStateIndex++;
                currentWinStateIndex++;
                return;
            }
            // Если текущий индекс уже выходит за границы:
            timer.stop();
        });
    }

    public void updateView() {
        solutionNumberLabel.setText(String.format("Промежуточный этап #%d из %d", currentStateIndex + 1, states.size()));
        JTableUtils.writeArrayToJTable(arrayTable, states.get(currentStateIndex).getField());
        for (int i = 0; i < 8; i++) {
            arrayTable.getColumnModel().getColumn(i).setCellRenderer(new CellColorRenderer());
        }
    }

    public void updateView(ChessFieldState board) {

        JTableUtils.writeArrayToJTable(arrayTable, board.getField());
        for (int i = 0; i < 8; i++) {
            arrayTable.getColumnModel().getColumn(i).setCellRenderer(new CellColorRenderer());
        }
    }
    public void updateWinView() {
        solutionNumberLabel.setText(String.format("Решение #%d из %d", currentWinStateIndex + 1, winStates.size()));
        JTableUtils.writeArrayToJTable(arrayTable, winStates.get(currentWinStateIndex).getField());
        for (int i = 0; i < 8; i++) {
            arrayTable.getColumnModel().getColumn(i).setCellRenderer(new CellColorRenderer());
        }
    }
}
