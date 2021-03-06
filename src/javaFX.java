import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.awt.Desktop;
import java.awt.*;
import java.io.*;
import java.util.Map;

public class javaFX extends Application{
    public int time1 = 0;
    public int time2 = 0;
    public RBTree rbTree;
    public BPTree bpTree;
    public boolean isRedBlackTree = true;
    @Override
    public void start(Stage primaryStage) throws IOException {
        rbTree = initializeRB();
        bpTree = initializeBP();
        BorderPane pane = new BorderPane();

        Scene scene = new Scene(pane);
        primaryStage.setTitle("English-Chinese Dictionary");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);

        //右边
        VBox rightPane = new VBox(15);


        //单选框
        RadioButton rbbt = new RadioButton("red-black tree");
        rbbt.setSelected(true);
       // rbbt.setPadding(new Insets(10,8,8,10));
        RadioButton bpbt = new RadioButton("B+ tree");
        ToggleGroup group = new ToggleGroup();
        rbbt.setToggleGroup(group);
        bpbt.setToggleGroup(group);
        HBox choosePane = new HBox(10);
        //choosePane.setPadding(new Insets(15,25,15,10));
        choosePane.getChildren().addAll(rbbt,bpbt);

        VBox rightdown = new VBox(10);
        rightdown.setStyle("-fx-border-color:#a19e9d");
        Label LOOKUP = new Label("LOOK-UP");
        Label searchfrom = new Label("search from");
        Label to = new Label("to");
        TextField searchtf = new TextField();   //551
        Button translate = new Button("Translate");
        Button submit = new Button("Submit");
        TextField from = new TextField();
        from.setMaxWidth(70);
        TextField too = new TextField();
        too.setMaxWidth(70);
        HBox lay1 = new HBox(10);
       // lay1.setPadding(new Insets(15,25,15,10));
        lay1.getChildren().addAll(searchtf,translate);  //476
        HBox lay2 = new HBox(10);
      //  lay2.setPadding(new Insets(15,25,15,10));
        lay2.getChildren().addAll(searchfrom,from,to,too,submit);
        rightdown.setPadding(new Insets(15,25,30,10));

        TextArea result = new TextArea("here shows the result");
        result.setMaxWidth(400);
        result.setEditable(false);

        translate.setOnAction(event -> {
            if(isRedBlackTree){
                long startTime = System.nanoTime();
                RBTree.RBTNode resulttt = rbTree.search(searchtf.getText());
                long consumingTime = System.nanoTime() - startTime;
                if(rbTree.search(searchtf.getText()) != null){
                    result.setText(searchtf.getText()+" : "+rbTree.search(searchtf.getText()).value.toString());
                }else {
                    result.setText("couldn't find this word");
                }
            }else {
                long startTime = System.nanoTime();
                String resultt = bpTree.search(searchtf.getText());
                long consumingTime = System.nanoTime() - startTime;
                if(resultt != null){
                    result.setText(searchtf.getText()+" : "+resultt);
                }else {
                    result.setText("couldn't find this word");
                }
            }
        });
        submit.setOnAction(event -> {
            if(isRedBlackTree){
                long startTime = System.nanoTime();
                rbTree.inorder_tree_walk(from.getText(),too.getText());
                long consumingTime = System.nanoTime() - startTime;
                if(!"".equals(rbTree.result)){
                    result.setText(rbTree.result);
                }else {
                    result.setText("couldn't find words");
                }
            }else {
                long startTime = System.nanoTime();
                String results = bpTree.searchRange(from.getText(),too.getText());
                long consumingTime = System.nanoTime() - startTime;
                if(!"".equals(results)){
                    result.setText(results);
                }else {
                    result.setText("couldn't find words");
                }
            }
        });

        rightPane.setPadding(new Insets(15,25,15,10));
        rightdown.getChildren().addAll(LOOKUP,lay1,lay2);

        rightPane.getChildren().addAll(choosePane,rightdown,result);

        VBox leftPane = new VBox(30);
        leftPane.setPadding(new Insets(15,10,15,25));
        Label MANAGEMENT = new Label("MANAGEMENT");
        TextField path = new TextField();
        path.setEditable(false);
        Button browser = new Button("Browser");

        browser.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Browser");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT","*.txt"));
            try{
                File file = fileChooser.showOpenDialog(primaryStage);
                path.setText(file.toString());
            }catch (Exception e){

            }

        });



        Button Submitleft = new Button("Submit");

        Submitleft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileInputStream fis = null;
                try {
                    if(path.getText().equals("finished")){
                        return;
                    }
                    fis = new FileInputStream(path.getText());
                } catch (FileNotFoundException e) {
                    //e.printStackTrace();
                }
                InputStreamReader reader = null;
                try {
                    reader = new InputStreamReader(fis,"GBK");
                } catch (UnsupportedEncodingException e) {
                   // e.printStackTrace();
                }
                BufferedReader br = new BufferedReader(reader);
                String s = null;//insert
                try {
                    s = br.readLine();
                } catch (IOException e) {
                   // e.printStackTrace();
                }
                if("INSERT".equals(s)){
                    try {
                        while ((s = br.readLine()) != null){//
                            String s2 = br.readLine();
                            s = s.toLowerCase();
                            s2 = s2.toLowerCase();
                            if (isRedBlackTree){
                                rbTree.insert(s,s2);
                                time1++;
                                if(time1 == 100 && rbTree.size <= 500){
                                    rbTree.preorder_tree_walk();
                                    time1 = 0;
                                }
                            } else{
                                bpTree.insert(s,s2);
                                time2++;
                                if(time2 == 100 && bpTree.size <= 500){
                                    bpTree.tree_walk();
                                    time2 = 0;
                                }
                            }


                        }
                    } catch (IOException e) {
                       // e.printStackTrace();
                    }
                    try {
                        br.close();
                    } catch (IOException e) {
                      //  e.printStackTrace();
                    }
                    path.setText("finished");
                }else if("DELETE".equals(s)){
                    try {
                        while ((s = br.readLine()) != null){
                            s = s.toLowerCase();
                            if(isRedBlackTree){
                                rbTree.delete(s);
                                time1++;
                                if(time1 == 100 && rbTree.size <= 500){//
                                    rbTree.preorder_tree_walk();
                                    time1 = 0;
                                }
                            }else {
                                bpTree.delete(s);
                                time2++;
                                if(time2 == 100 && bpTree.size <= 500){
                                    bpTree.tree_walk();
                                    time2 = 0;
                                }
                            }
                        }

                    } catch (IOException e) {
                       // e.printStackTrace();
                    }
                    path.setText("finished");
                }else {
                    path.setText("oooooooooooooooooh no I don't know what to do!");
                }
//                rbTree.preorder_tree_walk();
//                if(isRedBlackTree)
//                    System.out.println("树内目前含有： "+rbTree.size+" 个数");
//                else
//                    System.out.println("树内目前含有： "+bpTree.size+" 个数");
            }
        });

        VBox leftup = new VBox(20);
        leftup.setPadding(new Insets(15,15,70,15));
        leftup.setMinWidth(500);
        leftup.setStyle("-fx-border-color:#a19e9d");
        HBox BaS = new HBox(15);
        BaS.getChildren().addAll(browser,Submitleft);
        leftup.getChildren().addAll(MANAGEMENT,path,BaS);
        BaS.setAlignment(Pos.CENTER);

        VBox leftdown = new VBox(20);
        Label english = new Label("English:");
        Label chinese = new Label("Chinese:");
        Button add = new Button("Add");
        Button delete = new Button("Delete");
        TextField englishtf = new TextField();
        TextField chinesetf = new TextField();
        englishtf.setMaxWidth(120);
        chinesetf.setMaxWidth(120);
        HBox lline1 = new HBox(15);
        lline1.getChildren().addAll(english,englishtf,chinese,chinesetf);
        HBox lline2 = new HBox(15);
        lline2.getChildren().addAll(add,delete);
        lline1.setAlignment(Pos.CENTER);
        lline2.setAlignment(Pos.CENTER);

        leftdown.setStyle("-fx-border-color:#a19e9d");
        leftdown.setPadding(new Insets(30,15,65,15));

        leftdown.getChildren().addAll(lline1,lline2);

        leftPane.getChildren().addAll(leftup,leftdown);

        pane.setRight(rightPane);
        pane.setLeft(leftPane);
        primaryStage.show();

        rbTree = initializeRB();

        add.setOnAction(event -> {
            if(isRedBlackTree){
                rbTree.insert(englishtf.getText(),chinesetf.getText());
            }else {
                bpTree.insert(englishtf.getText(),chinesetf.getText());
            }
            englishtf.setText("");
            chinesetf.setText("");
        });
        delete.setOnAction(event -> {
            if(!"".equals(englishtf.getText())){
                if(isRedBlackTree){
                    rbTree.delete(englishtf.getText());
                }else {
                    bpTree.delete(englishtf.getText());
                }
                englishtf.setText("");
                chinesetf.setText("");
            }

        });


        rbbt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isRedBlackTree = true;
                result.setText("here shows the result");
//                try {
//                    rbTree = initializeRB();
//                } catch (IOException e) {
//                  //  e.printStackTrace();
//                }
            }
        });
        bpbt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isRedBlackTree = false;
                result.setText("here shows the result");
//                try {
//                    bpTree = initializeBP();
//                } catch (IOException e) {
//                   // e.printStackTrace();
//                }
            }
        });


    }
    public static void main(String[] args){
        Application.launch(args);
    }
    public  RBTree initializeRB() throws IOException{
        time1 = 0;
        RBTree rbtree = new RBTree();
        return rbtree;
    }
    public  BPTree initializeBP() throws IOException{
        time2 = 0;
        BPTree bptree = new BPTree();
        return bptree;
    }
}
