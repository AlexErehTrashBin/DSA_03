package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.gui;

import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model.ChessField;
import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model.FieldState;
import ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.model.FieldStatesIterator;
import ru.vsu.cs.util.JTableUtils;

import javax.swing.*;
import java.text.ParseException;
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

    private FieldState board;
    private List<FieldState> states = new ArrayList<>();
    private Timer timer;
    //private final ChessField chess = new ChessField(8);
    private final int size = 8;
    private FieldStatesIterator statesIterator;
    public ChessForm() {
        this.setTitle("Chess");
        this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(arrayTable, 40, true, true, false, false);
        arrayTable.setRowHeight(40);
        init();
        GUIUtils.addActionListener(setQueenButton, this::init);
        GUIUtils.addActionListener(resetButton, this::reset);
        GUIUtils.addActionListener(startButton, () -> timer.start());
        GUIUtils.addActionListener(stopButton, () -> {
            timer.stop();
            if (statesIterator.hasPrevious()) updateView(statesIterator.previous());
        });
        GUIUtils.addActionListener(nextButton, () -> {
            boolean nextElementExists = statesIterator.hasNext();
            if (nextElementExists){
                updateView(statesIterator.next());
            }
            updateControlsAccordingToIterator();
        });
        GUIUtils.addActionListener(prevButton, () -> {
            boolean prevElementExists = statesIterator.hasPrevious();
            if (prevElementExists){
                updateView(statesIterator.previous());
            }
            updateControlsAccordingToIterator();
        });

        GUIUtils.addActionListener(nextSolutionButton, () -> {
            if (statesIterator.hasNextCorrect()){
                updateView(statesIterator.nextCorrect());
            }
            updateControlsAccordingToIterator();
        });
        GUIUtils.addActionListener(prevSolutionButton, () -> {
            if (statesIterator.hasPreviousCorrect()){
                updateView(statesIterator.previousCorrect());
            }
            updateControlsAccordingToIterator();
        });

        timer = new Timer(1000, actionEvent -> {
            if (statesIterator.hasNext()){
                updateView(statesIterator.next());
            } else {
                // Если текущий индекс уже выходит за границы:
                timer.stop();
            }
        });
    }
    private void init(){
        int[][] boardMatrix;
        try {
            boardMatrix = JTableUtils.readIntMatrixFromJTable(arrayTable);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (boardMatrix == null) throw new RuntimeException();
        board = new FieldState(boardMatrix);
        states = ChessField.getAllStates(size);
        board = states.get(0);
        statesIterator = new FieldStatesIterator(states);
        updateView(board);
        updateControlsAccordingToIterator();
    }
    private void reset(){
        states.clear();
        board.empty();
        updateView(board);
        statesIterator = new FieldStatesIterator(states);
    }
    private void updateControlsAccordingToIterator(){
        nextButton.setEnabled(statesIterator.hasNext());
        prevButton.setEnabled(statesIterator.hasPrevious());
        nextSolutionButton.setEnabled(statesIterator.hasNextCorrect());
        prevSolutionButton.setEnabled(statesIterator.hasPreviousCorrect());
    }
    private void updateView(FieldState board) {
        solutionNumberLabel.setText(String.format("Промежуточный этап #%d из %d", statesIterator.getIndex() + 1, states.size()));
        JTableUtils.writeArrayToJTable(arrayTable, board.getField());
        for (int i = 0; i < size; i++) {
            arrayTable.getColumnModel().getColumn(i).setCellRenderer(new CellColorRenderer());
        }
    }
}
