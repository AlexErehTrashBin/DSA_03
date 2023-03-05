package ru.vsu.cs.aisd2023.g112.ereshkin_a_v.task03.gui;

import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;

public class GUIUtils {
	public static void runWithCatching(Runnable r){
		try {
			r.run();
		} catch (Exception e){
			SwingUtils.showErrorMessageBox(e);
		}
	}
	public static <T extends AbstractButton> void addActionListener(T element, Runnable r){
		element.addActionListener(e -> GUIUtils.runWithCatching(r));
	}
}
