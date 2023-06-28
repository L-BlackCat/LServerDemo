package org.example.netty_chat.group_chat.cli_main;

import io.netty.channel.Channel;
import org.example.netty_chat.group_chat.bean.RequestPacket;
import org.example.netty_chat.group_chat.bean.ResponsePacket;
import org.example.netty_chat.group_chat.client.IAttributes;
import org.example.netty_chat.group_chat.engine.ClientProtocolID;
import org.example.netty_chat.group_chat.engine.entity.Session;
import org.example.netty_chat.group_chat.logger.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;

public class CliNettyChatMainFrame {
    static int WIDTH = 800;
    static int HEIGHT = 600;

    JFrame frame = new JFrame("CliChatMainFrame");

    public JTextArea readContext = new JTextArea(18,30);   //显示消息文本框
    JTextArea writeContext = new JTextArea(6,30);   //发送消息文本框
    JTextField txtName; //文本框
    JButton btnSend = new JButton("发送");
    JButton btnExit = new JButton("关闭");

    //  LFH 对model的增删可以，list也会增删
    public DefaultListModel<Session> model = new DefaultListModel<>();
    JList<Session> list = new JList<>(model);

    Channel channel;

    String groupName;

//    CliNettyChatLoginFrame loginFrame;


    public CliNettyChatMainFrame() {
    }

    public CliNettyChatMainFrame(String groupName) {
        frame = new JFrame(groupName);
    }


    public void init(Channel channel){
        this.channel = channel;
        String name = channel.attr(IAttributes.NAME).get();

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int width = toolkit.getScreenSize().width;
        int height = toolkit.getScreenSize().height;
        frame.setBounds(width / 2 - WIDTH / 2, height / 2 - HEIGHT / 2, WIDTH, HEIGHT);

        frame.setLayout(null);
        frame.setTitle(groupName + " - " +name + " 聊天窗口");
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
                quitGroup();
                frame.setVisible(false);
            }
        });

        btnExit.addActionListener(e -> {
            // LFH  通知聊天服务器断开连接
            quitGroup();
            frame.setVisible(false);
        });

        btnSend.addActionListener(e -> {
            //  LFH 将消息发送给聊天服务器，交给聊天服务器发送给其他客户端
            writeContext.setText(writeContext.getText() + "\n");
            writeMsg();
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
                    writeMsg();
                    writeContext.setText("");
                    writeContext.requestFocus();

                }
            }
        });

    }

    public void quitGroup(){
        String name = channel.attr(IAttributes.NAME).get();
        Debug.info(name + " 退出群组：" + groupName);
        RequestPacket packet = new RequestPacket();
        packet.setRequestId(ClientProtocolID.Quit_Group_Request.getId());
        packet.getMap().put("group_name",groupName);
        channel.writeAndFlush(packet);
    }

    public void writeMsg(){
        String msg = writeContext.getText();
        if(!msg.trim().isEmpty()){
            ResponsePacket packet = new ResponsePacket();
            packet.getMap().put("msg",msg);
            packet.getMap().put("group_name",groupName);
            packet.setRequestId(ClientProtocolID.Chat_Message_Request.getId());
            channel.writeAndFlush(packet);
        }
    }

    public void updateModel(Collection<Session> sessions){
        this.model.removeAllElements();
        for (Session session : sessions) {
            this.model.addElement(session);
        }
    }

    public void show(Channel channel){
        init(channel);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
