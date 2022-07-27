package com.example.dechuan.model.ocrlrp;

public class ObjRect implements Comparable<ObjRect>{
	private int minX;
	
	private int minY;
	
    private int maxX;
	
	private int maxY;
	
	private double val;//相似度

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

	@Override
	public int compareTo(ObjRect o) {
		if(o.getMinX() < this.getMinX()) {
			return 1;
		}
		return -1;	
	}
	
	
}
