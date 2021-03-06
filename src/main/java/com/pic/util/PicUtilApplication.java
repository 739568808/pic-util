package com.pic.util;

import com.pic.util.util.Constant;
import com.pic.util.util.PicUtils;
import org.apache.commons.lang.StringUtils;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;


//@SpringBootApplication
//@Slf4j
public class PicUtilApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(PicUtilApplication.class, args);
//    }

    private static String PIC_FORMAT = "jpg|png";


    public  PicUtilApplication(String phone) throws Exception {

        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        UIManager.put("RootPane.setupButtonVisible",false);
        // 创建 JFrame 实例
        JFrame frame = new JFrame(Constant.TITLE+"   欢迎,"+phone);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        // Setting the width and height of frame
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();

        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        int x = (screen.width-frame.getWidth()) / 2;
        int y = (screen.height-frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(frame,panel);

        // 设置界面可见
        frame.setVisible(true);
    }


    private static void placeComponents(JFrame frame,JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        JLabel filePathLabel = new JLabel("扫描文件夹:");
        filePathLabel.setBounds(10,20,80,25);
        panel.add(filePathLabel);
        JTextField filePathText = new JTextField();
//        JFileChooser fileChooser = new JFileChooser();
//        FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
//        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
//        fileChooser.setDialogTitle("请选择要上传的文件...");
//        fileChooser.setApproveButtonText("确定");
//        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        filePathText.setBounds(100,20,180,25);
        filePathText.setEditable(false);
        filePathText.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                {

                    try {
                        String path= null;
                        JFileChooser fc = new JFileChooser();



                        fc.setDialogTitle("请选择要扫描的文件夹...");

                        fc.setApproveButtonText("确定");
                        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(frame)) {
                            path=fc.getSelectedFile().getPath();
                        }
                        if (StringUtils.isEmpty(path)){
                            //JOptionPane.showMessageDialog(panel,"文件未上传！","提示",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        filePathText.setText(path);
                    }catch (Exception ee){
                        JOptionPane.showMessageDialog(panel,"文件夹选择失败！","提示",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            public void actionPerformed(ActionEvent e) {

            }
        });
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

        JTextArea textArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBounds(10,150,328,365);
        scroll.setForeground(Color.gray);
        scroll.setFont(new Font("宋体", Font.PLAIN, 11));



        // 创建登录按钮
        JButton button = new JButton("开始压缩图片");
        button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        button.setFont(new Font("宋体", Font.PLAIN, 15));
        button.setForeground(Color.WHITE);
        button.setBounds(110, 120, 120, 25);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String path = filePathText.getText();
                int sfzMin = Integer.valueOf(sfzMinText.getText());
                int sfzMax = Integer.valueOf(sfzMaxText.getText());
                int czMin = Integer.valueOf(czMinText.getText());
                int czMax = Integer.valueOf(czMaxText.getText());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        button.setEnabled(false);
                        //图片格式转换
                        conver(textArea,path);
                        //开始压缩图片
                        compress(textArea,panel,path,sfzMin,sfzMax,czMin,czMax);

                        JOptionPane.showMessageDialog(panel,"图片处理完成！","提示",JOptionPane.INFORMATION_MESSAGE);
                        button.setEnabled(true);
                    }
                }).start();

            }
        });
        panel.add(button);


        panel.add(scroll);
    }

    public static void compress(JTextArea textArea,JPanel panel,String path,int sfzMin,int sfzMax,int czMin,int czMax) {
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
                            compress(textArea,panel,f.getAbsolutePath(),sfzMin,sfzMax,czMin,czMax);
                        } else {
                            System.out.println("文件:" + f.getAbsolutePath()+"大小："+f.length());
                            String fileName = f.getName();
                            int kb =  (int)Math.ceil(f.length()/1024);
                            //文件名后缀
                            String[] split = fileName.split("\\.");
                            if (split.length==1){
                                continue;
                            }
                            String prx = split[1].toLowerCase();
                            if (prx.contains("jpg") || prx.contains("png")){
                                //判断图片名包含，身份证、 寸照
                                if (fileName.contains("身份证")){
                                    if (kb>sfzMax){
                                        //TODO 当前文件大小大于指定身份证大小
                                        //TODO 修改方法，传入文件路径+文件名，方便起一个别名

                                        PicUtils.compress(textArea,f,sfzMax);
                                    }
                                }
                                if (fileName.contains("寸照")){
                                    if (kb>czMax){
                                        //当前文件大小大于指定寸照大小
                                        PicUtils.compress(textArea,f,czMax);
                                    }
                                }
                            }
                        }
                    }
                }
            }else {
                System.out.println("文件不存在！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }


    public static void conver(JTextArea textArea,String path) {
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
                            conver(textArea,f.getAbsolutePath());
                        } else {
                            String fileName = f.getName();
                            //文件名后缀
                            String[] split = fileName.split("\\.");
                            if (split.length==1){
                                continue;
                            }
                            String prx = split[1].toLowerCase();
                            if (prx.toLowerCase().contains("png")){
                                //将png转换为jpg
                                PicUtils.conver(textArea,f.getAbsolutePath());

                            }
                        }
                    }
                }
            }else {
                System.out.println("文件不存在！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }





}
