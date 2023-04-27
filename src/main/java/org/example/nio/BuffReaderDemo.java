package org.example.nio;

import java.io.*;

public class BuffReaderDemo {
    public static void main(String[] args) {
        String relativelyPath = System.getProperty("user.dir");
        BufferedReader reader = null;

        try{
            FileInputStream input = new FileInputStream(relativelyPath + "/resources/textin.txt");

            reader = new BufferedReader(new InputStreamReader(input));

            System.out.println(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
