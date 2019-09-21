package com.cacl;
import com.cacl.dao.impl.MyQueue;
import com.cacl.dao.impl.MyStack;

public class Resolve {

	/**
	 * 替换中缀表达式中的负号
	 */
	public static String ReplaceMinusString(String string) {

		StringBuffer str = new StringBuffer(string);

		// 查找首字符为-,后面为数字的匹配
		if (string.startsWith("-")) {
			str.replace(str.indexOf("-"), str.indexOf("-") + 1, "@");
		}

		// 查找以（-开始后面为数字的匹配
		while (str.toString().contains("(-")) {
			str.replace(str.indexOf("(-") + 1, str.indexOf("(-") + 2, "@");
		}
		return str.toString();
	}

	/**
	 * 将参与运算的数中@换回负号 并识别π字符
	 */
	public static Double ReplaceBackMinusAndPi(String str) {
		if (str.contains("@")) {
			String s1 = str.replaceAll("@", "-");
			if (s1.equals("π")) {
				return Math.PI;
			} else if (s1.equals("-π")) {
				return -Math.PI;
			} else {
				return Double.parseDouble(s1);
			}
		} else if (str.equals("π")) {
			return Math.PI;
		} else if (str.equals("-π")) {
			return -Math.PI;
		} else {
			return Double.parseDouble(str);
		}

	}

	/**
	 * 中缀表达式转后缀表达式
	 */
	public static MyQueue<String> TransIntoSuffix(String Infix)// IFX为中缀表达式
	{
		MyQueue<String> Suffix = new MyQueue<String>();
		StringBuffer numBuffer = new StringBuffer();// 用来保存一个数的
		MyStack<String> operater = new MyStack<String>();// 放操作符
		String a;
		operater.push("=");// 第一个为等号
		int i = 0;
		char ch;
		for (i = 0; i < Infix.length();) {
			ch = Infix.charAt(i);
			switch (ch) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '@':
				while (Character.isDigit(ch) || ch == '.' || ch == '@'
						|| ch == 'π')// 拼数
				{
					numBuffer.append(ch); // 追加字符
					ch = Infix.charAt(++i);
				}
				Suffix.add(numBuffer.toString());// break;
				numBuffer = new StringBuffer(); // 清空已获取的运算数字
				continue; // 这里要重新循环，因为i已经增加过了
			case 'π':
				Suffix.add("π");
				break;
			case '(': // 左括号直接入栈
				operater.push("(");
				break;
			case ')': // 右括号，将左括号之前的操作符取出
				while (operater.peek() != "(")
					Suffix.add(operater.pop());
				operater.pop();
				break;
			case '+':
			case '-':
				while ((operater.size() > 1 && operater.peek() != "("))
					Suffix.add(operater.pop());
				a = String.valueOf(ch);
				operater.push(a);
				break;
			case '*':
			case '/':
				while (operater.size() > 1
						&& (operater.peek() == "*" || operater.peek() == "/"
								|| operater.peek() == "s"
								|| operater.peek() == "c"
								|| operater.peek() == "t"
								|| operater.peek() == "S"
								|| operater.peek() == "C"
								|| operater.peek() == "T"
								|| operater.peek() == "l"
								|| operater.peek() == "g"
								|| operater.peek() == "^" || operater.peek() == "√"))
					// 优先级比较，与栈顶比较，
					Suffix.add(operater.pop());// 当前操作符优先级大于等于栈顶的弹出栈顶
				a = String.valueOf(ch);
				operater.push(a);
				break;
			case 's':
			case 'c':
			case 't':// 三角函数
			case 'o':
			case 'i':
			case 'a':// 反三角函数
			case 'S':
			case 'T':
			case 'C':// 双曲函数
			case 'l':// 自然对数
			case 'g':// 10为底对数函数
				while (operater.size() > 1
						&& (operater.peek() == "s" || operater.peek() == "c"
								|| operater.peek() == "t"
								|| operater.peek() == "S"
								|| operater.peek() == "C"
								|| operater.peek() == "T"
								|| operater.peek() == "o"
								|| operater.peek() == "i"
								|| operater.peek() == "a"
								|| operater.peek() == "l"
								|| operater.peek() == "g"
								|| operater.peek() == "^" || operater.peek() == "√"))
					// 优先级比较，与栈顶，大于等于的弹出
					Suffix.add(operater.pop());
				a = String.valueOf(ch);
				operater.push(a);
				break;
			case '^':// 幂
			case '√':// 开方
				while (operater.size() > 1
						&& (operater.peek() == "^" || operater.peek() == "√"))
					Suffix.add(operater.pop());
				a = String.valueOf(ch);
				operater.push(a);
				break;
			}

			i++;
		}
		while (operater.size() > 0)
			Suffix.add(operater.pop());

		return Suffix;
	}

	/**
	 * 后缀表达式求值 角度运算
	 */
	public static Double AngleFigure(MyQueue<String> Suffix) {
		Double x1, x2, n;
		String str;
		MyStack<String> s = new MyStack<String>();
		while (Suffix.element() != "=") {
			str = Suffix.remove();
			switch (str.charAt(0)) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '@':
			case 'π':
				s.push(str);
				break;
			case '+':
				x1 = ReplaceBackMinusAndPi(s.pop());
				x2 = ReplaceBackMinusAndPi(s.pop());
				n = x2 + x1;
				//保留10位小数
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case '-':
				x1 = ReplaceBackMinusAndPi(s.pop());
				x2 = ReplaceBackMinusAndPi(s.pop());
				n = x2 - x1;
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case '*':
				x1 = ReplaceBackMinusAndPi(s.pop());
				x2 = ReplaceBackMinusAndPi(s.pop());
				n = x1 * x2;
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case '/':
				x1 = ReplaceBackMinusAndPi(s.pop());
				x2 = ReplaceBackMinusAndPi(s.pop());
				n = x2 / x1;
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 's':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.sin(x1 * Math.PI / 180);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'c':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.cos(x1 * Math.PI / 180);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 't':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.tan(x1 * Math.PI / 180);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'i':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.asin(x1) * 180 / Math.PI;
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'o':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.acos(x1) * 180 / Math.PI;
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'a':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.atan(x1) * 180 / Math.PI;
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'S':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.sinh(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'C':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.cosh(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'T':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.tanh(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'l':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.log(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'g':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.log10(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case '√':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.sqrt(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;// 开方
			case '^':
				x1 = ReplaceBackMinusAndPi(s.pop());
				x2 = ReplaceBackMinusAndPi(s.pop());
				n = Math.pow(x2, x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			}
		}
		return ReplaceBackMinusAndPi(s.pop());
	}

	/**
	 * 后缀表达式求值 弧度运算
	 */
	public static Double RadFigure(MyQueue<String> Suffix) {
		Double x1, x2, n;
		String str;
		MyStack<String> s = new MyStack<String>();
		while (Suffix.element() != "=") {
			str = Suffix.remove();
			switch (str.charAt(0)) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '@':
			case 'π':
				s.push(str);
				break;
			case '+':
				x1 = ReplaceBackMinusAndPi(s.pop());
				x2 = ReplaceBackMinusAndPi(s.pop());
				n = x2 + x1;
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case '-':
				x1 = ReplaceBackMinusAndPi(s.pop());
				x2 = ReplaceBackMinusAndPi(s.pop());
				n = x2 - x1;
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case '*':
				x1 = ReplaceBackMinusAndPi(s.pop());
				x2 = ReplaceBackMinusAndPi(s.pop());
				n = x1 * x2;
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case '/':
				x1 = ReplaceBackMinusAndPi(s.pop());
				x2 = ReplaceBackMinusAndPi(s.pop());
				n = x2 / x1;
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 's':
				x1 = ReplaceBackMinusAndPi(s.pop());
				if (x1 != Math.PI || x1 != -Math.PI) {
					n = Math.sin(x1);
				} else if (x1 == Math.PI) {
					n = Math.sin(Math.PI);
				} else {
					n = Math.sin(-Math.PI);
				}
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'c':
				x1 = ReplaceBackMinusAndPi(s.pop());
				if (x1 != Math.PI || x1 != -Math.PI) {
					n = Math.cos(x1);
				} else if (x1 == Math.PI) {
					n = Math.cos(Math.PI);
				} else {
					n = Math.cos(-Math.PI);
				}
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 't':
				x1 = ReplaceBackMinusAndPi(s.pop());
				if (x1 != Math.PI || x1 != -Math.PI) {
					n = Math.tan(x1);
				} else if (x1 == Math.PI) {
					n = Math.tan(Math.PI);
				} else {
					n = Math.tan(-Math.PI);
				}
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'i':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.asin(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'o':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.acos(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'a':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.atan(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'S':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.sinh(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'C':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.cosh(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'T':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.tanh(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'l':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.log(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case 'g':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.log10(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			case '√':
				x1 = ReplaceBackMinusAndPi(s.pop());
				n = Math.sqrt(x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;// 开方
			case '^':
				x1 = ReplaceBackMinusAndPi(s.pop());
				x2 = ReplaceBackMinusAndPi(s.pop());
				n = Math.pow(x2, x1);
				s.push(String.valueOf(String.format("%.10f", n)));
				break;
			}
		}
		return ReplaceBackMinusAndPi(s.pop());
	}
}
