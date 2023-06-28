package org.example.netty_chat.group_chat.cli_main;

import org.example.netty_chat.group_chat.client.LNettyClient;
import org.example.netty_chat.group_chat.logger.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CliNettyChatLoginFrame extends JFrame {
    static int WIDTH = 250;
    static int HEIGHT = 200;

    JLabel label;   //提示
    JTextField txtName; //文本框
    JButton btnOk;
    JOptionPane optionPane = new JOptionPane();

    public CliNettyChatLoginFrame() throws HeadlessException {
        this.setLayout(null);
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int width = toolkit.getScreenSize().width;
        int height = toolkit.getScreenSize().height;
        this.setBounds(width / 2 - WIDTH / 2, height / 2 - HEIGHT / 2, WIDTH, HEIGHT);

        this.setTitle("设置名称");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        txtName = new JTextField(100);
        this.add(txtName);
        txtName.setBounds(10, 10, 120, 25);

        btnOk = new JButton("OK");
        this.add(btnOk);
        btnOk.setBounds(140, 10, 80, 25);

        label = new JLabel(); // w:" + width + ",h:" + height + "]
        this.add(label);
        label.setBounds(10, 40, 200, 100);
        label.setText("<html>在上面的文本框中输入名字<br/>显示器宽度：" + width + "<br/>显示器高度：" + height + "</html>");

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    action();
                }
            }
        });

        btnOk.addActionListener(e -> {
                action();
//                    catch (IOException ex) {
//                        /*
//                        * 弹窗：
//                        * 1.普通消息弹窗
//                        *   JOptionPane.showMessageDialog(this,"login err");
//                        * 2.yes and ni 弹窗
//                        *   JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//                        * 3.Yes and no and cancel弹窗
//                        *   JOptionPane.showConfirmDialog(frame, "Continue printing?");
//                        * */
//                        JOptionPane.showMessageDialog(this,"login err");
//                        System.out.println("login err，" + ex.getMessage() );
//                    }
                }
        );

    }

    public void action(){

        String name = this.txtName.getText();
        if (name.trim().isEmpty()) {
            return;
        }
        new Thread(() -> {
            try {
                this.setVisible(false);
                LNettyClient.start(name);
            } catch (InterruptedException ex) {
                Debug.err("连接失败",ex);
            }
        }).start();
    }


    public static void main(String[] args) {
        CliNettyChatLoginFrame frame = new CliNettyChatLoginFrame();
        frame.setVisible(true);
    }
}
