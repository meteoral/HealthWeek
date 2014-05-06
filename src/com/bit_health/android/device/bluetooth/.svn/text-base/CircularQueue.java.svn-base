package com.bit_health.android.device.bluetooth;
  
 

public class CircularQueue {
	//队列原型数组
	private int[] queue;
	//入队与出队游标
	private int inIndex,outIndex = 0;
	boolean empty,full;
	
	public CircularQueue(int size) {
		queue = new int[size];
		for(int i=0; i<size; i++){
			queue[i]=-2;
		}
		empty = true;
		full = false;
	}
	/*
	* 返回队列长度
	*/
	public int length() {
		return queue.length;
	}
	/*
	* 入队
	*/
	public boolean in(int obj) {
		if(isFull()) {
			return false;
		}
		//设置当前队列入队节点为传入对象
		queue[inIndex] = obj;
		//指向下一个节点
		nextInIndex();
		return true;
	}
	/*
	* 出队
	*/
	public int out() {
		if(isEmpty()) {
			return -1;
		}
		//获取当前出队节点的对象
		int result = queue[outIndex];
		//清空当前位置
		queue[outIndex] = -2;
		//指向下一个节点
		nextOutIndex();
		return result;
	}
	/*
	* 是否为空
	*/
	public boolean isEmpty() {
		if(inIndex == outIndex && queue[inIndex] == -2) {
			return true;
		}
		return false;
	}
	/*
	* 是否已满
	*/
	public boolean isFull() {
		if(inIndex == outIndex && queue[inIndex] != -2) {
			return true;
		}
		return false;
	}
	//入队游标指向下一个节点
	private int nextInIndex() {
		if(inIndex + 1 < queue.length) {
			inIndex += 1;
		} else {
			inIndex = 0;
		}
		return inIndex;
	}
	//出队游标指向下一个节点
	private int nextOutIndex() {
		if(outIndex + 1 < queue.length) {
			outIndex += 1;
		} else {
			outIndex = 0;
		}
		return outIndex;
	} 

}