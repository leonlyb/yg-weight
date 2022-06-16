package com.example.dechuan.service.camera;


import com.example.dechuan.hktv.basics.HikVisionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Description: 创建线程任务服务
 * @Author SONGtiank
 * @Date: 2020/11/2 16:55
 * @Version: 1.0
 */
@Service
public class CustomMultiThreadingService {
    private Logger logger = LoggerFactory.getLogger(CustomMultiThreadingService.class);

    /**
     * @param m_ip
     * @Description:通过@Async注解表明该方法是一个异步方法， 如果注解在类级别上，则表明该类所有的方法都是异步方法，而这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor
     * @Author
     * @Date: 2020/11/2 16:55
     * @Version: 1.0
     * @Throws
     */
    @Async
    public void executeAysncTask1(String m_ip) throws InterruptedException {
        HikVisionService hikVisionService = new HikVisionService();
        hikVisionService.initMemberFlowUpload(m_ip, 100);
        logger.info("<------------" + m_ip + "启动-------->");
    }
//    @Async
//    public void executeAsyncTask2(String m_ip)throws InterruptedException{
//        HikVisionService hikVisionService = new HikVisionService();
//        hikVisionService.initMemberFlowUpload(m_ip, 100);
//        logger.info("<------------"+m_ip+"启动-------->");
//    }
}