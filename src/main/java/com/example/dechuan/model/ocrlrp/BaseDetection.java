package com.example.dechuan.model.ocrlrp;

import com.example.dechuan.utils.ocrcar.FileUtiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class BaseDetection extends Base{
    /**
     * 从resources目录下读取的模型文件到项目的临时文件
     * @param model
     */
    protected static void setModelFile(String model) {
    	InputStream path = Thread.currentThread().getContextClassLoader().getResourceAsStream(model);
    	FileOutputStream outTem = null;
    	try {
    		outTem = new FileOutputStream(model);
			byte[] b = new byte[1024];
			int end = -1;
			while((end = path.read(b)) > 0) {
				outTem.write(b, 0, end);
				outTem.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			FileUtiles.close(outTem);
		}
	}
    
    
    /**
     * 用完即删除临时的文件
     * @param model
     */
    protected static void removeModelFile(String model) {
    	File tem = new File(model);
    	tem.delete();
    }

}
