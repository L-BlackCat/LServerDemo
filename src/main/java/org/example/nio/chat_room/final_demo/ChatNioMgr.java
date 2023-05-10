package org.example.nio.chat_room.final_demo;

import org.example.nio.chat_room.final_demo.cli_main.CliChatLoginFrame;

public enum ChatNioMgr {
    Instance;


    public static void main(String[] args) {
        CliChatLoginFrame loginFrame = new CliChatLoginFrame();
        loginFrame.setVisible(true);
    }
}
