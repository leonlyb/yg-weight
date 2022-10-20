package com.example.dechuan.utils;

import com.example.dechuan.controller.carimage.AsyncImageTask;
import com.example.dechuan.utils.camera.CarNoImage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/*******************************************************************************
 * Description: 图片水印工具类
 * @author eden
 * @version 1.0
 */
@Slf4j
public class ImageRemarkUtil {

    // 水印透明度
    private static float alpha = 0.5f;
    // 水印横向位置
    private static int positionWidth = 150;
    // 水印纵向位置
    private static int positionHeight = 300;
    // 水印文字字体
    private static Font font = new Font("宋体", Font.BOLD, 120);
    // 水印文字颜色
    private static Color color = Color.red;

    /**
     *
     * @param alpha
     *            水印透明度
     * @param positionWidth
     *            水印横向位置
     * @param positionHeight
     *            水印纵向位置
     * @param font
     *            水印文字字体
     * @param color
     *            水印文字颜色
     */
    public static void setImageMarkOptions(float alpha, int positionWidth,
                                           int positionHeight, Font font, Color color) {
        if (alpha != 0.0f)
            ImageRemarkUtil.alpha = alpha;
        if (positionWidth != 0)
            ImageRemarkUtil.positionWidth = positionWidth;
        if (positionHeight != 0)
            ImageRemarkUtil.positionHeight = positionHeight;
        if (font != null)
            ImageRemarkUtil.font = font;
        if (color != null)
            ImageRemarkUtil.color = color;
    }



    /**
     * 给图片添加水印文字
     *
     * @param logoText
     *            水印文字
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     */
    public static void markImageByText(String logoText, String srcImgPath,
                                       String targerPath,double entranceWeight,double exitWeight, double netWeight) {
        markImageByText(logoText, srcImgPath, targerPath, entranceWeight, exitWeight, netWeight,null);
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     *
     * @param logoText
     * @param srcImgPath
     * @param targerPath
     * @param degree
     */
    public static void markImageByText(String logoText, String srcImgPath, String targerPath, double entranceWeight,double exitWeight, double netWeight,Integer degree) {

        InputStream is = null;
        OutputStream os = null;
        try {
            // 1、源图片
//            File file = new File(srcImgPath);
//            if(file.exists() == true){
//                System.out.println("文件存在");
//            }
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            g.drawString(logoText, positionWidth, positionHeight);
            g.drawString("毛重:"+entranceWeight, positionWidth+5, positionHeight+200);
            g.drawString("皮重:"+exitWeight, positionWidth+5, positionHeight+400);
            g.drawString("净重:"+netWeight, positionWidth+5, positionHeight+600);
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);
            log.info("<------------ 图片完成添加水印文字 -------->");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        String srcImgPath = "D:/wallhaven-zml6vg.png";
//        String logoText = "复 印 无 效";
        String logoText = "车牌号:10087";
//        String iconPath = "d:/2.jpg";

        String targerTextPath = "d:/qie_text.jpg";
        String targerTextPath2 = "d:/qie_text_rotate.jpg";
//        String targerIconPath = "d:/qie_icon.jpg";
//        String targerIconPath2 = "d:/qie_icon_rotate.jp
////g";

        System.out.println("给图片添加水印文字开始...");
        // 给图片添加水印文字
//        markImageByText(logoText, srcImgPath, targerTextPath);
        // 给图片添加水印文字,水印文字旋转-45
//        markImageByText(logoText, srcImgPath, targerTextPath, -45);
//        System.out.println("给图片添加水印文字结束...");

//        System.out.println("给图片添加水印图片开始...");
//        setImageMarkOptions(0.3f, 1, 1, null, null);
//        // 给图片添加水印图片
//        markImageByIcon(iconPath, srcImgPath, targerIconPath);
//        // 给图片添加水印图片,水印图片旋转-45
//        markImageByIcon(iconPath, srcImgPath, targerIconPath2, -45);
//        System.out.println("给图片添加水印图片结束...");

    }

    public static void main2(String[] args) {
        String a ="/view/image/244/1234";
        System.out.println(a.substring(16));
        System.out.println(a.substring(12,15));
//        CarNoImage.closeworkorder("皖AG2701",DateUtils.getCurrentDate());
    }

}
