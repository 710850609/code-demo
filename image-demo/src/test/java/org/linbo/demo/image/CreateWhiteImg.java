package org.linbo.demo.image;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


public class CreateWhiteImg {

    public static void main(String[] args) {
        int width = 1000;
        int height = 1000;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            InputStream in = new ByteArrayInputStream(bos.toByteArray());
            File f = new File(CreateWhiteImg.class.getResource("").getPath() + "/" + width + "-" + height + ".jpg");
            f.createNewFile();
            FileOutputStream fin = new FileOutputStream(f);
            byte[] buf = new byte[1024];
            int read = in.read(buf);
            while (read > 0) {
                fin.write(buf);
                read = in.read(buf);
            }
            fin.flush();
            fin.close();
            System.out.println("保存路径: " + f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
