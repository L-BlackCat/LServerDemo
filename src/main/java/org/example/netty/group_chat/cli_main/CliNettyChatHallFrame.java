package org.example.netty.group_chat.cli_main;

import io.netty.channel.Channel;
import org.example.netty.group_chat.client.IAttributes;
import org.example.netty.group_chat.engine.entity.Session;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.util.List;

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

    CliNettyChatLoginFrame loginFrame;

    public void init(Channel channel){
        this.channel = channel;

//        String name = channel.attr(IAttributes.NAME).get();

        String name = "li";
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int width = toolkit.getScreenSize().width;
        int height = toolkit.getScreenSize().height;

        //  设置界面的左上角的x和y
        frame.setBounds((int) (width - (WIDTH * 1.5)),  40, WIDTH, HEIGHT);

        frame.setLayout(null);
        frame.setTitle(name + " 聊天窗口");
        frame.setSize(WIDTH,HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

//        readContext.setText(name);
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



        for (int i = 0; i < 50; i++) {
            model.addElement(Session.create(i,i+"_hello"));
        }
        hallScroll.setBounds(5,45,270,500);
        list.setBounds(0,0,270,500);

        addMouseListener();


    }

    public void addMouseListener(){
        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    System.out.println("双击事件");
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



    public void show(Channel channel){
        init(channel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        CliNettyChatHallFrame hallFrame = new CliNettyChatHallFrame();
        hallFrame.show(null);
    }
}
