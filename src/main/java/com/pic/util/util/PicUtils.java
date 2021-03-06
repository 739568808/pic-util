package com.pic.util.util;


import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

/**
 * 图片压缩Utils
 *
 * @author worstEzreal
 * @version V1.1.0
 * @date 2018/3/12
 */
public class PicUtils {

    private static Logger logger = LoggerFactory.getLogger(PicUtils.class);


    public static byte[] getByteByPic(String imageUrl) throws IOException {
        File imageFile = new File(imageUrl);
        InputStream inStream = new FileInputStream(imageFile);
        BufferedInputStream bis = new BufferedInputStream(inStream);
        BufferedImage bm = ImageIO.read(bis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String type = imageUrl.substring(imageUrl.length() - 3);
        ImageIO.write(bm, type, bos);
        bos.flush();
        byte[] data = bos.toByteArray();
        return data;
    }

    public static byte[] fileToByte(File img) throws Exception {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage bi;
            bi = ImageIO.read(img);
            ImageIO.write(bi, "JPG", baos);
            bytes = baos.toByteArray();
            System.err.println(bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        return bytes;
    }

    /**
     * 将图片压缩到指定大小以内
     *
     * @param srcImgData 源图片数据
     * @param maxSize 目的图片大小
     * @return 压缩后的图片数据
     */
    public static byte[] compressUnderSize(byte[] srcImgData, long maxSize) {
        double scale = 0.99;
        byte[] imgData = Arrays.copyOf(srcImgData, srcImgData.length);

        if (imgData.length > maxSize) {
            do {
                try {
                    imgData = compress(imgData, scale);

                } catch (IOException e) {
                    throw new IllegalStateException("压缩图片过程中出错，请及时联系管理员！", e);
                }

            } while (imgData.length > maxSize);
        }

        return imgData;
    }

    /**
     * 按照 宽高 比例压缩
     *
     * @param scale 压缩刻度
     * @return 压缩后图片数据
     * @throws IOException 压缩图片过程中出错
     */
    public static byte[] compress(byte[] srcImgData, double scale) throws IOException {
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(srcImgData));
        int width = (int) (bi.getWidth() * scale); // 源图宽度
        int height = (int) (bi.getHeight() * scale); // 源图高度

        Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null); // 绘制处理后的图
        g.dispose();

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ImageIO.write(tag, "JPEG", bOut);

        return bOut.toByteArray();
    }

    //byte数组到图片
    public static void byte2image(byte[] data,String path){
        if(data.length<3||path.equals("")) return;
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    public static void compress(JTextArea textArea,File f, int max)  {
        String text = textArea.getText();
        try {
//            byte[] imgBytes = getByteByPic(f.getAbsolutePath());
//            byte[] resultImg = compressUnderSize(imgBytes,max);
            String name = f.getName();
            String[]split = name.split("\\.");
            String fileName =split[0]+"_压缩后."+split[1];
//            byte2image(resultImg,f.getAbsolutePath().replaceAll(name.trim(),"")+fileName);

            byte[] bytes = FileUtils.readFileToByteArray(new File(f.getAbsolutePath()));
            bytes = PicUt.compressPicForScale(bytes, max, "x");// 图片小于300kb
            FileUtils.writeByteArrayToFile(new File(f.getAbsolutePath().replaceAll(name.trim(),"")+fileName), bytes);
            textArea.append("压缩成功："+f.getAbsolutePath()+"\r\n");

        }catch (Exception e){
            e.printStackTrace();
            textArea.append("【压缩失败】："+f.getAbsolutePath()+"\r\n");
        }

    }

    public static void conver(JTextArea textArea,String pngUrl){
        BufferedImage bufferedImage;
        try {
            // read image file
            bufferedImage = ImageIO.read(new File(pngUrl));
            // create a blank, RGB, same width and height, and a white
            // background
            BufferedImage newBufferedImage = new BufferedImage(
                    bufferedImage.getWidth(), bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0,
                    Color.WHITE, null);
            // write to jpeg file
            String[]split = pngUrl.split("\\.");
            String filePath =split[0]+".jpg";

            ImageIO.write(newBufferedImage, "jpg", new File(filePath));
            textArea.append("格式转换成功："+filePath+"\r\n");
        } catch (IOException e) {
            e.printStackTrace();

            textArea.append("【格式转换失败】："+pngUrl+"\r\n");
        }
    }

}
