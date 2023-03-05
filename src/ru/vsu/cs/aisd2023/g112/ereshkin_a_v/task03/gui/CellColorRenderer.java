package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.gui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CellColorRenderer extends JLabel implements TableCellRenderer {

    public CellColorRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (Integer.parseInt(table.getValueAt(row, column).toString()) < 0) {
            setBackground(Color.red);
        }
        if (Integer.parseInt(table.getValueAt(row, column).toString()) == 0) {
            setBackground(Color.white);
        }
        if (Integer.parseInt(table.getValueAt(row, column).toString()) > 0) {
            setBackground(Color.pink);
        }
        return this;
    }
}
