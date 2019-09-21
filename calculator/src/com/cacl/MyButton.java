package com.cacl;


import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;

/**
 * MyButton类，继承JButton
 */
public class MyButton extends JButton {
	
	public MyButton(String text, int a, int b, int c, int d) {
		super(text);// 设置按钮文本
		this.setActionCommand(this.getText());// 设置按钮命令符
		this.setForeground(Color.black);
		this.setBounds(a, b, c, d);// 设置按钮的位置及大小
		this.setFocusPainted(false);// 设置按钮点击时不显示焦点框
		this.setMargin(new Insets(0, 0, 0, 0));// 设置按钮的文字距离各边的距离为0
		this.setFont(new Font("宋体",Font.PLAIN,15));
	}
}
