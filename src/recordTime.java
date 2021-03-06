import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
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
public class recordTime extends Application{


        public int time = 0;
        public RBTree rbTree;
        public BPTree bpTree;
        public boolean isRedBlackTree = true;
        @Override
        public void start(Stage primaryStage) throws IOException {
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
                        result.setText( "搜索时间(ns) ： "+consumingTime+"\n"+ searchtf.getText()+" : "+rbTree.search(searchtf.getText()).value.toString());
                    }else {
                        result.setText("couldn't find this word");
                    }
                }else {
                    long startTime = System.nanoTime();
                    String resultt = bpTree.search(searchtf.getText());
                    long consumingTime = System.nanoTime() - startTime;
                    if(resultt != null){
                        result.setText( "搜索时间(ns) ： "+consumingTime+"\n"+searchtf.getText()+" : "+resultt);
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
                        result.setText("搜索时间(ns) ： "+consumingTime+"\n"+rbTree.result);
                    }else {
                        result.setText("couldn't find words");
                    }
                }else {
                    long startTime = System.nanoTime();
                    String results = bpTree.searchRange(from.getText(),too.getText());
                    long consumingTime = System.nanoTime() - startTime;
                    if(!"".equals(results)){
                        result.setText("搜索时间(ns) ： "+consumingTime+"\n" + results);
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
                File file = fileChooser.showOpenDialog(primaryStage);
                path.setText(file.toString());
            });



            Button Submitleft = new Button("Submit");

            Submitleft.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(path.getText());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    InputStreamReader reader = null;
                    try {
                        reader = new InputStreamReader(fis,"GBK");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    BufferedReader br = new BufferedReader(reader);
                    String s = null;//insert
                    try {
                        s = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if("INSERT".equals(s)){
                        try {
                            while ((s = br.readLine()) != null){//
                                String s2 = br.readLine();
                                if (isRedBlackTree){
                                    rbTree.insert(s,s2);
                                    time++;
                                    if(time == 100 && rbTree.size <= 500){
                                        rbTree.preorder_tree_walk();
                                        time = 0;
                                    }
                                } else{
                                    bpTree.insert(s,s2);
                                    time++;
                                    if(time == 100 && bpTree.size <= 500){
                                        bpTree.tree_walk();
                                        time = 0;
                                    }
                                }


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if("DELETE".equals(s)){
                        try {
                            while ((s = br.readLine()) != null){
                                if(isRedBlackTree){
                                    rbTree.delete(s);
                                    time++;
                                    if(time == 100 && rbTree.size <= 500){
                                        rbTree.preorder_tree_walk();
                                        time = 0;
                                    }
                                }else {
                                    bpTree.delete(s);
                                    time++;
                                    if(time == 100 && bpTree.size <= 500){
                                        bpTree.tree_walk();
                                        time = 0;
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    path.setText("finished");

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
                    from.setText("");
                    too.setText("");
                    result.setText("here shows the result");
                    try {
                        rbTree = initializeRB();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            bpbt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    isRedBlackTree = false;
                    from.setText("");
                    too.setText("");
                    result.setText("here shows the result");
                    try {
                        bpTree = initializeBP();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
        public static void main(String[] args){
            Application.launch(args);
        }
        public  RBTree initializeRB() throws IOException{
            System.out.println("Red-black tree:");
            time = 0;
            RBTree rbtree = new RBTree();
            //initial
            FileInputStream fis = new FileInputStream("./src/1_initial.txt");
            InputStreamReader reader = new InputStreamReader(fis,"GBK");
            BufferedReader br = new BufferedReader(reader);
            String s = br.readLine();//insert
            int time1 = 0;
            long startTime = 0;
            long endTime = 0;
            int num = 0;
            long total = 0;
            System.out.println("操作1(ns):");
            while ((s = br.readLine()) != null) {
                String s2 = br.readLine();
                if (time1 == 0) {
                    startTime = System.nanoTime();
                }
                rbtree.insert(s, s2);
                time++;
                time1++;
                if (time1 == 100) {
                    num++;
                    endTime = System.nanoTime();
                    total += endTime - startTime;
                    System.out.print((endTime - startTime) + ",");
                    time1 = 0;
                }
            }
            br.close();
            System.out.println("平均时间："+total/num);

            //delete
            num = 0;
            total = 0;
            FileInputStream fisDelete = new FileInputStream("./src/2_delete.txt");
            InputStreamReader readerDelete = new InputStreamReader(fisDelete,"UTF-8");
            BufferedReader brDelete = new BufferedReader(readerDelete);
            String sDelete = brDelete.readLine();//insert
            int time2 = 0;
            System.out.println("操作2(ns):");
            while ((sDelete = brDelete.readLine()) != null){
                if(time2 == 0){
                    startTime = System.nanoTime();
                }
                rbtree.delete(sDelete);
                time++;
                time2++;
                if(time2 == 100){
                    num++;
                    endTime = System.nanoTime();
                    total += endTime - startTime;
                    System.out.print((endTime - startTime)+"," );
                    time2 = 0;
                }
            }
            brDelete.close();
            System.out.println("平均时间："+total/num);

            //insert
            total = 0;
            num = 0;
            FileInputStream fisInsert = new FileInputStream("./src/3_insert.txt");
            InputStreamReader readerInsert = new InputStreamReader(fisInsert,"GBK");
            BufferedReader brInsert = new BufferedReader(readerInsert);
            String sInsert = brInsert.readLine();//insert

            int time3 = 0;
            System.out.println("操作3(ns):");
            while ((sInsert = brInsert.readLine()) != null){
                String s2 = brInsert.readLine();
                if(time3 == 0){
                    startTime = System.nanoTime();
                }
                rbtree.insert(sInsert,s2);
                time++;
                time3++;
                if(time3 == 100){
                    num++;
                    endTime = System.nanoTime();
                    total += endTime - startTime;
                    System.out.print((endTime - startTime)+"," );
                    time3 = 0;
                }

            }
            brInsert.close();
            System.out.println("平均时间："+total/num);
            return rbtree;
        }
        public  BPTree initializeBP() throws IOException{
            System.out.println("B+ tree:");
            time = 0;
            int num = 0;
            long total = 0;
            BPTree bptree = new BPTree();
            //initial
            FileInputStream fis = new FileInputStream("./src/1_initial.txt");
            InputStreamReader reader = new InputStreamReader(fis,"GBK");
            BufferedReader br = new BufferedReader(reader);
            String s = br.readLine();//insert
            int time1 = 0;
            long startTime = 0;
            long endTime = 0;
            System.out.println("操作1(ns):");
            while ((s = br.readLine()) != null){//
                String s2 = br.readLine();
                if(time1 == 0){
                    startTime = System.nanoTime();
                }
                bptree.insert(s,s2);
                time++;
                time1++;
                if(time1 == 100){
                    num++;
                    endTime = System.nanoTime();
                    total += (endTime - startTime);
                    System.out.print( (endTime - startTime)+"," );
                    time1 = 0;
                }
            }
            System.out.println("平均时间："+total/num);
            br.close();

            //delete
            num = 0;
            total = 0;
            FileInputStream fisDelete = new FileInputStream("./src/2_delete.txt");
            InputStreamReader readerDelete = new InputStreamReader(fisDelete,"UTF-8");
            BufferedReader brDelete = new BufferedReader(readerDelete);
            String sDelete = brDelete.readLine();//insert
            int time2 = 0;
            int debug = 0;
            System.out.println("操作2(ns):");
            while ((sDelete = brDelete.readLine()) != null){
                debug++;
                if(time2 == 0){
                    startTime = System.nanoTime();
                }
                bptree.delete(sDelete);
                time++;
                time2++;
                if(time2 == 100){
                    num++;
                    endTime = System.nanoTime();
                    total += (endTime - startTime);
                    System.out.print((endTime - startTime)+"," );
                    time2 = 0;
                }
            }
            System.out.println("平均时间："+total/num);
            brDelete.close();

//        insert
            num = 0;
            total = 0;
            FileInputStream fisInsert = new FileInputStream("./src/3_insert.txt");
            InputStreamReader readerInsert = new InputStreamReader(fisInsert,"GBK");
            BufferedReader brInsert = new BufferedReader(readerInsert);
            String sInsert = brInsert.readLine();//insert

            int time3 = 0;
            System.out.println("操作3(ns):");
            while ((sInsert = brInsert.readLine()) != null){
                String s2 = brInsert.readLine();
                if(time3 == 0){
                    startTime = System.nanoTime();
                }
                bptree.insert(sInsert,s2);
                time++;
                time3++;
                if(time3 == 100){
                    num++;
                    endTime = System.nanoTime();
                    total += (endTime - startTime);
                    System.out.print( (endTime - startTime)+"," );
                    time3 = 0;
                }

            }
            brInsert.close();
            System.out.println("平均时间："+total/num);
            return bptree;
        }


}
