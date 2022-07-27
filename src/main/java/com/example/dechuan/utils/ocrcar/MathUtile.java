/*
 * Copyright (c) 2020 Shenzhen DaMing Technology Co., Ltd.
 * All Rights Reserved.
 */
package com.example.dechuan.utils.ocrcar;


import com.example.dechuan.model.ocrlrp.P;

public class MathUtile {
	/**
	 * 直角三角形中已知斜角度数，和下边长度，求另一斜边长度。
	 * @param angle
	 * @param size
	 * @return
	 */
	public static int duibianZise(double angle, int size) {
		angle = angle * -1;
		double du = Math.toRadians(angle);//把数字90 转换成 90度
		return (int)(Math.tan(du) * size);
	}
	
	/**
	 * 计算连点构成的值角三角形边角读数
	 * @param start
	 * @param end
	 * @return
	 */
	public static double angle(P start, P end) {
		double angle = -1;
		//0度
		if(start.getX() == end.getX() && end.getY() < start.getY()) {
			angle = 0;
			return angle;
		}
		
		//90度
		if (start.getX() < end.getX() && end.getY() == start.getY()) {
			angle = 90;
			return angle;
		}
		
		
		//180度
		if (start.getX() == end.getX()  && end.getY() > start.getY()) {
			angle = 180;
			return angle;
		}
		
		//小于90度 大于0度
		if (start.getX() < end.getX()  && end.getY() < start.getY()) {
			double tan = (double)(end.getX() - start.getX()) / (double)(start.getY() - end.getY());
			angle = Math.atan(tan)/Math.PI*180;
			return angle;
		}
		
		
		//大于90度 小于180度
		if (start.getX() < end.getX()  && end.getY() > start.getY()) {
			double tan = (double)(end.getX() - start.getX()) / (double)(end.getY() - start.getY());
			angle = Math.atan(tan)/Math.PI*180;
			return 180 - angle;
		}
		
		
		//大于180度 小于270度
		if (start.getX() > end.getX()  && end.getY() > start.getY()) {
			double tan = (double)(start.getX() - end.getX()) / (double)(end.getY() - start.getY());
			angle = Math.atan(tan)/Math.PI*180;
			return 180 + angle;
		}
		
		
		//大于270度 小于360度
		if (start.getX() > end.getX()  && end.getY() < start.getY()) {
			double tan = (double)(start.getX() - end.getX()) / (double)(start.getY() - end.getY());
			angle = Math.atan(tan)/Math.PI*180;
			return 360 - angle;
		}
		
		return angle;
	}
	
	/**
	 * 计算两点距离
	 * @param start
	 * @param end
	 * @return
	 */
	public static double size(P start, P end) {
		double size = 0;
		//0度
		if(start.getX() == end.getX() && end.getY() < start.getY()) {
			if(end.getY() < start.getY()) {
				size = start.getY() - end.getY();
			}else {
				size = end.getY() - start.getY();
			}
			return size;
		}
		
		//90度
		if (start.getX() < end.getX() && end.getY() == start.getY()) {
			if(end.getX() < start.getX()) {
				size = start.getX() - end.getX();
			}else {
				size = end.getX() - start.getX();
			}
			return size;
		}
		
		
		//180度
		if (start.getX() == end.getX()  && end.getY() > start.getY()) {
			if(end.getY() < start.getY()) {
				size = start.getY() - end.getY();
			}else {
				size = end.getY() - start.getY();
			}
			return size;
		}
		
		//小于90度 大于0度
		if (start.getX() < end.getX()  && end.getY() < start.getY()) {
			double a = end.getX() - start.getX();
			double b = start.getY() - end.getY();
			size = Math.sqrt(a*a+b*b);
			return size;
		}
		
		
		//大于90度 小于180度
		if (start.getX() < end.getX()  && end.getY() > start.getY()) {
			double a = end.getX() - start.getX();
			double b = end.getY() - start.getY();
			size = Math.sqrt(a*a+b*b);
			return size;
		}
		
		return size;
	}
	
//	public static void main(String[] args) {
//		System.out.println(angle(new Piont(0,10), new Piont(10,5)));
//	}
}
