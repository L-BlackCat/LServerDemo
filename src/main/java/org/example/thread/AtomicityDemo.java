package org.example.thread;

public class AtomicityDemo {
    HostInfo hostInfo;

    public AtomicityDemo(String ip,int port) {
        this.hostInfo = new HostInfo();
        hostInfo.setIp(ip);
        hostInfo.setPort(port);
    }

    public void connectToHost(){
        String ip = hostInfo.getIp();
        int port = hostInfo.getPort();
        connectToHost(ip,port);
    }

    private void connectToHost(String ip,int port){
        //...
    }

    public void updateIpAndPort(String ip,int port){

        hostInfo.setIp(ip);  //操作1
        hostInfo.setPort(port);  //操作2

    }


    public static void main(String[] args) {
        AtomicityDemo atomicityDemo = new AtomicityDemo("192.168.1.1", 10086);
        Thread thread = new Thread(() -> {
            atomicityDemo.connectToHost();
        });
        thread.start();
        atomicityDemo.updateIpAndPort("192.168.1.3",3304);

        String command = System.getProperty("sun.java.command");
        System.out.println("Java执行的执行命令：" + command);
    }
}

class HostInfo{
    String ip;
    int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

