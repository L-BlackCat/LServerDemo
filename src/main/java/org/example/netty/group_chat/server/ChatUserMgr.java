package org.example.netty.group_chat.server;

import java.util.ArrayList;
import java.util.List;

public enum ChatUserMgr {
    Instance;

    List<String> names = new ArrayList<>();


    public List<String> getNames() {
        return names;
    }
}
