package com.cacl;

import javax.swing.JOptionPane;
import com.cacl.pojo.CalcFrame;


public class TestMyCalc {
	public static void main(String[] args) {
		try {
			javax.swing.UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "��۳�ʼ��ʧ��");
		}
		new CalcFrame();
	}
}
