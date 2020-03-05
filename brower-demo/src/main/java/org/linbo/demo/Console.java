package org.linbo.demo;

import com.sun.javafx.webkit.WebConsoleListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;

public class Console implements Closeable {

    private Stage stage;

    public Console(Stage stage) {
        this.stage = stage;
        stage.setTitle("开发者控制台");
        Group group = new Group();
        Scene scene = new Scene(group, 768, 250, Color.BLACK);
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setFont(Font.font("Consolas"));
        scene.setRoot(textArea);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        WebConsoleListener.setDefaultListener(new WebConsoleListener() {
            @Override
            public void messageAdded(WebView webView, String message, int lineNumber, String sourceId) {
                textArea.appendText(message + " [" + sourceId + ":" + lineNumber + "] ");
                textArea.appendText("\n\n");
                System.out.println("Console: " + message + " [" + sourceId + ":" + lineNumber + "] ");
            }
        });
    }

    @Override
    public void close() throws IOException {
        stage.close();
    }
}
