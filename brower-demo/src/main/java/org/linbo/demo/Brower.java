package org.linbo.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Hello world!
 */
public class Brower extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Console console;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Group());

        WebView webView = new WebView();
        webView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.F12) {
                    if (console == null) {
                        console = new Console(new Stage());
                    } else {
                        try {
                            console.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        console = null;
                    }
                }
            }
        });
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
                            primaryStage.setTitle("JavaFx浏览器   " + engine.getLocation());
                        }
                    }
                });
        engine.locationProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observableValue, String oldLoc, String newLoc) {
                // check if the newLoc corresponds to a file you want to be downloadable
                // and if so trigger some code and dialogs to handle the download.
                System.out.println(observableValue.getValue() + " ====2========= " + oldLoc + " =====2====== " + newLoc);
            }
        });
        engine.onAlertProperty().addListener(new ChangeListener<EventHandler<WebEvent<String>>>() {
            @Override
            public void changed(ObservableValue<? extends EventHandler<WebEvent<String>>> observableValue, EventHandler<WebEvent<String>> webEventEventHandler, EventHandler<WebEvent<String>> t1) {
                System.out.println();
            }
        });

//        String url = "https://tomcat.apache.org/download-10.cgi";
        String url = Brower.class.getResource("/index.html").toExternalForm();
        engine.load(url);

        scene.setRoot(scrollPane);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Brower.class.getResourceAsStream("/logo.jpg")));
        primaryStage.setTitle("JavaFx浏览器");
        primaryStage.show();
    }

}
