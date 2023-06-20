package org.example.netty.group_chat.cli_main;

import io.netty.channel.Channel;
import org.example.netty.group_chat.bean.RequestPacket;
import org.example.netty.group_chat.client.IAttributes;
import org.example.netty.group_chat.engine.ClientProtocolID;
import org.example.netty.group_chat.engine.chat_channel.GroupChatEnum;
import org.example.netty.group_chat.engine.entity.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CliNettyChatHallFrame {
    static int WIDTH = 300;
    static int HEIGHT = 600;
    JFrame frame = new JFrame("CliChatHallFrame");

    public DefaultListModel<Session> model = new DefaultListModel<>();
    JList<Session> list = new JList<>(model);

    public JTextArea readContext = new JTextArea(18,30);   //显示消息文本框

    JPopupMenu popupMenu = new JPopupMenu();

    JMenuItem privateMenuItem = new JMenuItem("发起私聊");
    JMenuItem publicMenuItem = new JMenuItem("发起群聊");
    Channel channel;

    Session session;


    public CliNettyChatHallFrame() {
    }


    public void init(Session session){


        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int width = toolkit.getScreenSize().width;
        int height = toolkit.getScreenSize().height;

        //  设置界面的左上角的x和y
        frame.setBounds((int) (width - (WIDTH * 1.5)),  40, WIDTH, HEIGHT);

        frame.setLayout(null);
        frame.setTitle(session.getName() + " 聊天窗口");
        frame.setSize(WIDTH,HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        readContext.setText(session.toString());
        JScrollPane selfScroll = new JScrollPane(readContext);
        selfScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        selfScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(selfScroll);

        selfScroll.setBounds(5,5,270,25);

        readContext.setBounds(0,0,270,25);
        readContext.setEnabled(false);


        JScrollPane hallScroll = new JScrollPane(list);
        hallScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(hallScroll);


        hallScroll.setBounds(5,45,270,500);
        list.setBounds(0,0,270,500);

        addMouseListener();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                //  LFH 通知聊天服务器断开连接
                logout();
                System.exit(0);
            }
        });


    }

    public void addMouseListener(){
        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    System.out.println("双击事件");
                    //  创建聊天框
                    List<Session> selectSessionList = list.getSelectedValuesList();
                    requestCreateGroup(GroupChatEnum.Private_Chat,selectSessionList);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()){
                    System.out.println("点击了鼠标右键");
                    redrawPopMenu(e);
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void redrawPopMenu(MouseEvent e){
        popupMenu.removeAll();
        List<Session> selectedValuesList = list.getSelectedValuesList();
        int size = selectedValuesList.size();
        if(size == 0){
            return;
        }
        if(size > 1){
            popupMenu.add(publicMenuItem);
        }else {
            popupMenu.add(privateMenuItem);
        }
        popupMenu.show(e.getComponent(),e.getX(),e.getY());
    }

    public void requestCreateGroup(GroupChatEnum groupChatEnum, List<Session> sessionList){
        RequestPacket requestPacket = new RequestPacket();
        requestPacket.setRequestId(ClientProtocolID.Chat_Create_Group_Request.getId());
        requestPacket.getMap().put("session_list",sessionList);
        requestPacket.getMap().put("chat_type", groupChatEnum.toInt());
        channel.writeAndFlush(requestPacket);
    }

    public void updateModel(Collection<Session> sessions){
        this.model.removeAllElements();
        for (Session tempSession : sessions) {
            if(tempSession.getUid() != this.session.getUid()){
                this.model.addElement(tempSession);
            }
        }
    }

    public void logout(){
        RequestPacket packet = new RequestPacket();
        packet.setRequestId(ClientProtocolID.Chat_Logout_Request.getId());
        channel.writeAndFlush(packet);
    }


    public void show(Channel channel,Session session,List<Session> sessionList){
        this.channel = channel;
        this.session = session;
        init(session);
        updateModel(sessionList);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}
