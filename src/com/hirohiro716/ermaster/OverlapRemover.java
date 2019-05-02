package com.hirohiro716.ermaster;

import java.util.ArrayList;

import com.hirohiro716.javafx.LayoutHelper;
import com.hirohiro716.javafx.control.IMEOffButton;
import com.hirohiro716.javafx.dialog.alert.InstantAlert;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * ERMasterで不意に発生している重複行を削除するプログラム.
 * @author hiro
 */
public class OverlapRemover extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.anchorPane = new AnchorPane();
        this.textArea = new TextArea();
        LayoutHelper.setAnchor(this.textArea, 5, 5, 35, 5);
        this.anchorPane.getChildren().add(this.textArea);
        Button button = new IMEOffButton("重複削除");
        button.setOnAction(this.buttonAction);
        this.anchorPane.getChildren().add(button);
        LayoutHelper.setAnchor(button, null, 5d, 5d, null);
        primaryStage.setScene(new Scene(this.anchorPane));
        primaryStage.setTitle("ERMasterの重複行削除プログラム");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }
    
    private AnchorPane anchorPane;
    
    private TextArea textArea;
    
    private EventHandler<ActionEvent> buttonAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            OverlapRemover run = OverlapRemover.this;
            String original = run.textArea.getText();
            // 改行コード特定
            String lineSeparater = null;
            if (original.indexOf("\r\n") > -1) {
                lineSeparater = "\r\n";
            } else {
                if (original.indexOf("\r") > -1) {
                    lineSeparater = "\r";
                } else {
                    if (original.indexOf("\n") > -1) {
                        lineSeparater = "\n";
                    } else {
                        return;
                    }
                }
            }
            // 分割
            String[] lines = original.split(lineSeparater);
            ArrayList<String> newLines = new ArrayList<>();
            String oldLine = "";
            for (String line: lines) {
                if (oldLine.equals(line) == false) {
                    newLines.add(line);
                }
                oldLine = line;
            }
            // 結果表示
            run.textArea.setText(String.join(lineSeparater, newLines.toArray(new String[] {})));
            InstantAlert.show(run.anchorPane, lines.length - newLines.size() + "行削除しました。", Pos.CENTER, 3000);
        }
    };
    
    /**
     * アプリケーションの開始.
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

}
