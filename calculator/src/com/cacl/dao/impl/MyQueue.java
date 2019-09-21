package com.cacl.dao.impl;

import com.cacl.dao.IQueue;


    // 创建循环队列 实现IQueue接口
public class MyQueue<E> implements IQueue<E> {
	private E[] elements;
	private int front; // 队头指针
	private int rear; // 队尾指针
	private static final int INITIAL_CAPACITY = 40;
	
	
	//默认构造方法
	public MyQueue() {
		elements = (E[]) new Object[INITIAL_CAPACITY];
		rear = front = 0;
	}

	
	// 指定队列长度为capacity的构造方法
	public MyQueue(int capacity) {
		elements = (E[]) new Object[capacity];
		rear = front = 0;
	}

	
	// 队尾增加元素
	public void add(E element) {
		if (size() == elements.length - 1) {
			resize();
		}
		elements[rear] = element;
		if (rear < elements.length - 1) {
			rear++;
		} else {
			rear = 0; // 到队头
		}
	}

	
	//访问队头元素
	public E element() {
		if (size() == 0) {
			throw new java.util.NoSuchElementException();
		}
		return elements[front];
	}
	
	
	//判断是否为空
	public boolean isEmpty() {
		return (size() == 0);
	}

	
	// 删除队头元素
	public E remove() {
		if (size() == 0) {
			throw new java.util.NoSuchElementException();
		}
		E element = elements[front];
		elements[front] = null;
		front++;
		if (front == rear) { // 队列为空
			front = rear = 0;
		}
		if (front == elements.length) { // 回到队头
			front = 0;
		}
		return element;
	}

	
	// 队列中元素个数
	public int size() {
		if (front < rear) {
			return rear - front;
		} else {
			return rear - front + elements.length;
		}
	}

	
	// 重新分配长度
	private void resize() {
		int size = size();
		int len = elements.length;
		assert size == len;
		Object[] a = new Object[2 * size];
		System.arraycopy(elements, front, a, 0, len - front);
		System.arraycopy(elements, 0, a, len - front, front);
		elements = (E[]) a;
		front = 0;
		rear = size;
	}

}
