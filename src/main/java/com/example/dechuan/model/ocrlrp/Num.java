package com.example.dechuan.model.ocrlrp;

public class Num {
    private String id = "";//车牌字符
	
	private double probabilitie = 0;//概率

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
	
}
