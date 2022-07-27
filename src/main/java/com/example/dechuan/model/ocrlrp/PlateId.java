package com.example.dechuan.model.ocrlrp;

import org.bytedeco.opencv.opencv_core.Mat;

public class PlateId {
	private String id = "";//车牌号
	
	private double probabilitie = 0;//概率
	
	private double allProbabilitie;
	
	private Mat rect;//车牌截图

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getProbabilitie() {
		return probabilitie;
	}

	public void setProbabilitie(double probabilitie) {
		this.probabilitie = probabilitie;
	}
	
	public Mat getRect() {
		return rect;
	}

	public void setRect(Mat rect) {
		this.rect = rect;
	}

	public void addNumber(String name, double probabilitie) {
		this.id = this.id + name;
		
		//计算平均概率
		this.allProbabilitie = this.allProbabilitie + probabilitie;
		this.probabilitie = this.allProbabilitie / this.id.length();
	}
	
}
