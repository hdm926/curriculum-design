package com.cacl;


/**
 * 队列接口
 * @author Gavin
 *
 * @param <E>
 */
public interface IQueue<E> {
	public void add(E element); //对尾增加元素
	public E element(); // 访问队头元素
	public boolean isEmpty(); // 判断队列是否为空
	public E remove(); // 删除对头元素
	public int size(); // 返回元素个数
}
