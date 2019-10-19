package org.linbo.demo.image;

import com.google.zxing.WriterException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class QrCodeTest {

    @Test
    public void createQRCode() throws IOException, WriterException {
        String content = "测试二维码生成";
        String path = QrCode.class.getClassLoader().getResource("").getPath() + "qr.jpg";
        QrCode.createQRCode(content, path, QrCode.DEFAULT_WIDTH, QrCode.DEFAULT_HEIGHT);
        System.out.printf("二维码保存路径: " + path);
    }

}