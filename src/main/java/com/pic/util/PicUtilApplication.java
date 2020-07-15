package com.pic.util;

import com.pic.util.util.PicUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//@SpringBootApplication
//@Slf4j
public class PicUtilApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(PicUtilApplication.class, args);
//    }

    private static String PIC_FORMAT = "jpg|png";
    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("创森教育-图片压缩工具V1.0");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        // Setting the width and height of frame
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }


    private static void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        JLabel filePathLabel = new JLabel("扫描文件夹:");
        filePathLabel.setBounds(10,20,80,25);
        panel.add(filePathLabel);
        JTextField filePathText = new JTextField(20);
//        JFileChooser fileChooser = new JFileChooser();
//        FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
//        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
//        fileChooser.setDialogTitle("请选择要上传的文件...");
//        fileChooser.setApproveButtonText("确定");
//        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        filePathText.setBounds(100,20,180,25);
        panel.add(filePathText);


        // 创建 JLabel
        JLabel userLabel = new JLabel("身份证:");
        userLabel.setBounds(10,50,80,25);
        panel.add(userLabel);
        JTextField sfzMinText = new JTextField(20);
        sfzMinText.setText("50");
        sfzMinText.setBounds(100,50,80,25);
        panel.add(sfzMinText);
        JLabel h = new JLabel("-");
        h.setBounds(180,50,10,25);
        panel.add(h);
        JTextField sfzMaxText = new JTextField(20);
        sfzMaxText.setText("200");
        sfzMaxText.setBounds(190,50,80,25);
        panel.add(sfzMaxText);
        JLabel kb = new JLabel("KB");
        kb.setBounds(270,50,20,25);
        panel.add(kb);


        // 创建 JLabel
        JLabel czLabel = new JLabel("寸照:");
        czLabel.setBounds(10,80,80,25);
        panel.add(czLabel);
        JTextField czMinText = new JTextField(20);
        czMinText.setText("20");
        czMinText.setBounds(100,80,80,25);
        panel.add(czMinText);
        JLabel h2 = new JLabel("-");
        h2.setBounds(180,80,10,25);
        panel.add(h2);
        JTextField czMaxText = new JTextField(20);
        czMaxText.setText("50");
        czMaxText.setBounds(190,80,80,25);
        panel.add(czMaxText);
        JLabel kb2 = new JLabel("KB");
        kb2.setBounds(270,80,20,25);
        panel.add(kb2);

        // 创建登录按钮
        JButton button = new JButton("开始压缩图片");
        button.setBounds(10, 110, 120, 25);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String path = filePathText.getText();
                int sfzMin = Integer.valueOf(sfzMinText.getText());
                int sfzMax = Integer.valueOf(sfzMaxText.getText());
                int czMin = Integer.valueOf(czMinText.getText());
                int czMax = Integer.valueOf(czMaxText.getText());


                //开始压缩图片
                compress(path,sfzMin,sfzMax,czMin,czMax);

            }
        });
        panel.add(button);

        JLabel desLable = new JLabel("<html>提示：开始压缩图片后，请勿重复提交。<br> 使用过程中出现任何问题，联系QQ：739568808</html>");
        desLable.setBounds(10,130,300,50);
        desLable.setForeground(Color.gray);
        desLable.setFont(new Font("宋体", Font.PLAIN, 11));

        panel.add(desLable);
    }

    public static void compress(String path,int sfzMin,int sfzMax,int czMin,int czMax) {
        try {
            File file = new File(path);
            if (file.exists()) {
                File[] files = file.listFiles();
                if (null == files || files.length == 0) {
                    System.out.println("文件夹是空的!");
                    return;
                } else {
                    for (File f : files) {
                        if (f.isDirectory()) {
                            compress(f.getAbsolutePath(),sfzMin,sfzMax,czMin,czMax);
                        } else {
                            System.out.println("文件:" + f.getAbsolutePath()+"大小："+f.length());
                            String fileName = f.getName();
                            int kb =  (int)Math.ceil(f.length()/1024);
                            //文件名后缀
                            String prx = fileName.split("\\.")[1].toLowerCase();
                            if (prx.contains("jpg") || prx.contains("png")){
                                //判断图片名包含，身份证、 寸照
                                if (fileName.contains("身份证")){
                                    if (kb>sfzMax){
                                        //TODO 当前文件大小大于指定身份证大小
                                        //TODO 修改方法，传入文件路径+文件名，方便起一个别名

                                        PicUtils.compress(f,190);
                                    }
                                }
                                if (fileName.contains("寸照")){
                                    if (kb>czMax){
                                        //当前文件大小大于指定寸照大小
                                        PicUtils.compress(f,40);
                                    }
                                }
                            }


                            //PIC_FORMAT
                        }
                    }
                }
            }else {
                System.out.println("文件不存在！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }




    }
}
