package org.linbo.demo.image;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import sun.misc.IOUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.Assert.*;

public class VerificationCodeTest {

    @Test
    public void createImage() {
        Map<String, BufferedImage> image = VerificationCode.createImage();
        System.out.print(image.keySet());
        image.forEach((k, v) -> {
            System.out.println(k);
            try {
                InputStream in = VerificationCode.getInputStream(v);
                File f = new File(this.getClass().getClassLoader().getResource("").getPath() + "/" + k + ".jpg");
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
        });
    }
}