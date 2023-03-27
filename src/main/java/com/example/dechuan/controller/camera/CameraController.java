package com.example.dechuan.controller.camera;
import com.example.dechuan.service.camera.CustomMultiThreadingService;
import com.example.dechuan.utils.camera.CameraInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 摄像头控制类
 * @Date: 2020/10/31 9:11
 * @Version: 1.0
 */
@Controller
@Component
public class CameraController implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(CameraController.class);

    @Autowired
    private CustomMultiThreadingService customMultiThreadingService;


    @RequestMapping(value = "/index")
    public String index(){
        System.out.println("我来了哦");
        return "index";

    }

    @RequestMapping("/healthy")
    @ResponseBody
    public String healthy() throws InterruptedException {
        return "启动成功";

    }

    @Override
    public void run(String... args) throws Exception {
        //进厂192.168.1.244
     /*  Thread thread1 = new Thread(new Runnable1());
        thread1.run();
        //出厂192.168.1.240
        Thread thread2 = new Thread(new Runnable2());
        thread2.run();
        //直通192.168.1.247
        Thread thread3 = new Thread(new Runnable3());
        thread3.run();
      */
    }



    @ResponseBody
    @RequestMapping(value="/dotask")
    public String doTask() throws InterruptedException{
        //进厂192.168.1.244
        Thread thread1 = new Thread(new Runnable1());
        thread1.run();
        //出厂192.168.1.240
        Thread thread2 = new Thread(new Runnable2());
        thread2.run();
        //直通192.168.1.247
        Thread thread3 = new Thread(new Runnable3());
        thread3.run();
        return "success";
    }


    class Runnable1 implements Runnable{
        @Override
        public void run() {
            try {
                customMultiThreadingService.executeAysncTask1(CameraInfo.cameraInfo[0][0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Runnable2 implements Runnable{
        @Override
        public void run() {
            try {
                customMultiThreadingService.executeAsyncTask2(CameraInfo.cameraInfo[1][0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class Runnable3 implements Runnable{
        @Override
        public void run() {
            try {
                customMultiThreadingService.executeAsyncTask3(CameraInfo.cameraInfo[2][0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
