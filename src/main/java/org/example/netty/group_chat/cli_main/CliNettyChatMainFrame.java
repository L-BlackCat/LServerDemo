package org.example.netty.group_chat.cli_main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CliNettyChatMainFrame {
    static int WIDTH = 800;
    static int HEIGHT = 600;

    JFrame frame = new JFrame("CliChatMainFrame");

    JTextArea readContext = new JTextArea(18,30);   //显示消息文本框
    JTextArea writeContext = new JTextArea(6,30);   //发送消息文本框
    JTextField txtName; //文本框
    JButton btnSend = new JButton("发送");
    JButton btnExit = new JButton("关闭");
    String name;

    //  LFH 对model的增删可以，list也会增删
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);


    CliNettyChatLoginFrame loginFrame;

    boolean isRun;

//    public CliNettyChatMainFrame(NioClientService clientService, CliNettyChatLoginFrame loginFrame) throws HeadlessException {
//        this.name = clientService.getName();
//        this.loginFrame = loginFrame;
//        isRun = true;
//    }

    public CliNettyChatMainFrame(CliNettyChatLoginFrame loginFrame) throws HeadlessException {
        this.loginFrame = loginFrame;
        isRun = true;
    }
    public void init(){

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int width = toolkit.getScreenSize().width;
        int height = toolkit.getScreenSize().height;
        frame.setBounds(width / 2 - WIDTH / 2, height / 2 - HEIGHT / 2, WIDTH, HEIGHT);

        frame.setLayout(null);
        frame.setTitle(name + " 聊天窗口");
        frame.setSize(WIDTH,HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JScrollPane readScroll = new JScrollPane(readContext);
        readScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(readScroll);


        JScrollPane writeScroll = new JScrollPane(writeContext);
        writeScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(writeScroll);

        frame.add(list);
        frame.add(btnSend);
        frame.add(btnExit);

        readScroll.setBounds(10,10,520,300);
        readContext.setBounds(0,0,520,300);
        readContext.setEditable(false); //设置为不可编辑
        readContext.setLineWrap(true);  //自己主动换行


        writeScroll.setBounds(10,315,520,200);
        writeContext.setBounds(10,10,520,200);
        writeContext.setLineWrap(true);  //自己主动换行

        list.setBounds(540,10,240,545);
        btnSend.setBounds(350, 520, 80, 30);
        btnExit.setBounds(450, 520, 80, 30);
        //窗口关闭事件

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //  LFH 通知聊天服务器断开连接
                isRun = false;
//                clientService.sendInfo("exit_" + name);
                System.exit(0);

            }
        });

        btnExit.addActionListener(e -> {
            // LFH  通知聊天服务器断开连接
            isRun = false;
//            clientService.sendInfo("exit_" + name);
            System.exit(0);
        });

        btnSend.addActionListener(e -> {
            //  LFH 将消息发送给聊天服务器，交给聊天服务器发送给其他客户端
            String msg = writeContext.getText();
            if(!msg.trim().isEmpty()){
//                clientService.sendInfo(name + "^" + msg);
            }
            writeContext.setText("");
            writeContext.requestFocus();
        });

        writeContext.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    String msg = writeContext.getText();
                    if(msg.trim().length() > 0){
//                        clientService.sendInfo(name + "^" + msg);
                    }
                    writeContext.setText("");
                    writeContext.requestFocus();
                }
            }
        });



    }

    public void show(){
        init();
//        clientService.sendInfo("login_" + name);
        new MyThread().start();
        frame.setVisible(true);
    }

    class MyThread extends Thread{

        @Override
        public void run() {
            while(isRun){
//                String content = clientService.readInfo();
                String content = "";
                if(content != null){
                    System.out.println(content);
                    if(content.startsWith("[") && content.endsWith("]")){

                        content = content.substring(1,content.length() - 1);
                        String[] split = content.split(",");
                        model.removeAllElements();
                        for (String name : split) {
                            model.addElement(name);
                        }
                    }else {
                        String str = readContext.getText() + content;
                        readContext.setText(str);
                        readContext.selectAll();
                    }
                }

            }
        }
    }

}
