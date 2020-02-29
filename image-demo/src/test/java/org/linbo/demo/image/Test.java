package org.linbo.demo.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Test {

    public static void main(String[] args) {
        File f = new File("D:\\Users\\linbo.GTLAND\\Desktop\\temp\\30.001.png");
        System.out.println(f.getUsableSpace() / 1024 /1024 );
        System.out.println(f.getTotalSpace() / 1024 /1024 );

        try {
            FileInputStream inputStream = new FileInputStream(f);
            FileChannel channel = inputStream.getChannel();
            long size = channel.size();
            System.out.println(size / 1024 /1024);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
