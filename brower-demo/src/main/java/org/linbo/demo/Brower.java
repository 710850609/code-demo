package org.linbo.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/**
 * Hello world!
 */
public class Brower extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Group());

        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();

        ScrollPane scrollPane = new ScrollPane();
        // 自适应
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(webView);
        engine.getLoadWorker().stateProperty()
                .addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                        if (newValue == Worker.State.SUCCEEDED) {
                            System.out.println(oldValue + " ================ " + newValue);
                            primaryStage.setTitle(engine.getLocation());
                        } else {
                            System.out.println(oldValue + " ================ " + newValue);

                        }
                    }
                });

//        String url = "http://www.baidu.com";
        String url = Brower.class.getResource("/index.html").toExternalForm();
        engine.load(url);

        scene.setRoot(scrollPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
