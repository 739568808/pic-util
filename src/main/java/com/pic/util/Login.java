package com.pic.util;

/**
 * @Auther: lihy
 * @Date: 2020/7/17
 * @Description:
 */





import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pic.util.util.Constant;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.util.ResourceUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

//@SpringBootApplication
//@Slf4j
public class Login {

    public static void main(String[] args) {
        new Login();
    }



    public Login(){
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible",false);
            JFrame frame = new JFrame(Constant.TITLE+" - 登陆");
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);


            Toolkit toolkit=Toolkit.getDefaultToolkit();


//            String classPath = getClassPath();
            //Image icon = new ImageIcon("images/logo.png").getImage();
//            toolkit.getImage(classPath+"logo.png");

            //frame.setIconImage(icon);
            // Setting the width and height of frame
            frame.setSize(400, 230);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            /* 创建面板，这个类似于 HTML 的 div 标签
             * 我们可以创建多个面板并在 JFrame 中指定位置
             * 面板中我们可以添加文本字段，按钮及其他组件。
             */
            JPanel panel = new JPanel();
            // 添加面板
            frame.add(panel);
            Dimension screen = toolkit.getScreenSize();
            int x = (screen.width-frame.getWidth()) / 2;
            int y = (screen.height-frame.getHeight()) / 2;
            frame.setLocation(x, y);
            /*
             * 调用用户定义的方法并添加组件到面板
             */
            login(panel,frame);

            // 设置界面可见
            frame.setVisible(true);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private static void login(JPanel panel,JFrame frame) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        JLabel phoneLable = new JLabel("手机号:");
        phoneLable.setBounds(40,20,80,25);
        panel.add(phoneLable);
        JTextField phoneText = new JTextField(20);
        phoneText.setBounds(110,20,170,25);
        panel.add(phoneText);

        JLabel pwdLable = new JLabel("密码:");
        pwdLable.setBounds(40,60,80,25);
        panel.add(pwdLable);
        JPasswordField pwdText = new JPasswordField(20);
        pwdText.setBounds(110,60,170,25);
        panel.add(pwdText);





        // 创建登录按钮
        JButton button = new JButton("登录");
        button.setBounds(120, 110, 120, 25);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button.setEnabled(false);
                String phone = phoneText.getText();
                String pwd = pwdText.getText();
                try {
                    Connection.Response execute = Jsoup.connect(Constant.LOGIN_URL+phone+"/"+pwd).ignoreContentType(true)
                            .method(Connection.Method.POST).execute();
                    String body = execute.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    int code = jsonObject.getInteger("code");
                    if (code==200){
                        System.out.println("登陆成功");
//                        frame.setVisible(false);
                        frame.dispose();
                        new PicUtilApplication(phone);
                        return;
                    }
                    JOptionPane.showMessageDialog(panel,jsonObject.getString("msg"),"提示",JOptionPane.ERROR_MESSAGE);

                }catch (Exception ee){
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(panel,"登陆失败！","提示",JOptionPane.ERROR_MESSAGE);
                }finally {
                    button.setEnabled(true);
                }


            }
        });
        panel.add(button);


    }

    public static String getClassPath() {
        // 获取跟目录
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            // nothing to do
        }
        if (path == null || !path.exists()) {
            path = new File("");
        }

        String pathStr = path.getAbsolutePath();
        // 如果是在eclipse中运行，则和target同级目录,如果是jar部署到服务器，则默认和jar包同级
        pathStr = pathStr.replace("\\target\\classes", "");

        return pathStr+"/images/";
    }
}




