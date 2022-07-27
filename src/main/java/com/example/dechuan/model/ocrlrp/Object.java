package com.example.dechuan.model.ocrlrp;

import org.bytedeco.opencv.opencv_core.Mat;


public class Object {
	private int id = -1;//物体对象的id
	
	private ObjectType type = ObjectType.BACKPACK;//空
	
	private float probability = 0;//相似度
	
	private Mat mat;//该对象的截图
	
	private int x = 0;//中心点x
	
	private int y = 0;//中心点y
	
	private int w = 0;//矩形宽度
	
	private int h = 0;//矩形高度
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ObjectType getType() {
		return type;
	}

	public void setType(ObjectType type) {
		this.type = type;
	}

	public float getProbability() {
		return probability;
	}

	public void setProbability(float probability) {
		this.probability = probability;
	}

	public Mat getMat() {
		return mat;
	}

	public void setMat(Mat mat) {
		this.mat = mat;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	/**
	 * 释放mat资源
	 */
	public void close() {
		if(this.mat != null) {
			try {
				this.mat.close();
			} catch (Exception e) {
			}
		}
	}
	
}
