package com.example.dechuan.utils.camera;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Map;

/***
  * @author stk
  * @description 数据源
  * @date: 2020年11月4日
  */
public class ConnectDatabase {
	
	/**
	 * 连接MySQL数据库
	 */
	public static JdbcTemplate mysql() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/ulhf_weighbridg?useUnicode=true&serverTimezone=Asia/Shanghai");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return new JdbcTemplate(dataSource);
	}

	public static void insertClsb(Map<String,String> parmasMap,String imgPath,String clImgPath){
		JdbcTemplate mysql = mysql();
		String sql="INSERT INTO tbl_hikvision_clsb_hikvision_clsb (PlateType,PlateColor,byCountry,sLicense, byVehicleType,wSpeed,byIllegalType,picTime,picUrl,cameraIp,picVehicleUrl ) VALUES"+
				"('"+parmasMap.get("type")+"','"+parmasMap.get("byColor")+"','"+parmasMap.get("byCountry")+"','"+parmasMap.get("plateNumber")+"',"+
				"'无','"+parmasMap.get("wSpeed")+"','"+parmasMap.get("byIllegalType")+"','"+parmasMap.get("picTime")+"','"+imgPath+"','"+parmasMap.get("cameraIp")+"','"+clImgPath+"')";
		mysql.execute(sql);
	}
}
