package com.cacl;


/**
 * 栈接口
 * @param <E>
 */
public interface IStack <E>{
	public boolean isEmpty();//判断是否为空
	public E peek();//访问栈顶元素
	public E pop();//删除栈顶元素，并返回栈顶元素
	public void push(E element);//新元素进栈
	public int size();//返回元素个数
}
