package com.example.dechuan.model.ocrlrp;

import java.io.File;

public class Base {
	public static boolean isDebug= false;
	public static String debugPath = "/lpr_debug";
	public static String separator = File.separator;
	public static int maxBatch = 10;//最多支持并发量
	
	public static char[] chars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			                                  'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
			                                  'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			                                  'W', 'X', 'Y', 'Z', '京','沪', '津', '渝', '冀', '晋',
			                                  '蒙', '辽', '吉', '黑', '苏', '浙', '皖', '闽', '赣', '鲁', 
			                                  '豫', '鄂', '湘', '粤', '桂', '琼', '川', '贵', '云', '藏', 
			                                  '陕', '甘', '青', '宁', '新', '学', '警', '港', '澳', '领' };
	
	/**
	 * 根据字符获取lab
	 * @param num
	 * @return
	 */
	public static int getLabelByIdNum(String num) {
		int lab = -1;
		if(num == null) {
			return lab;
		}
		
		for(int i = 0; i < chars.length; i++) {
			if(num.equals(chars[i] + "")) {
				lab = i;
				break;
			}
		}
		
		return lab;
	}
}
