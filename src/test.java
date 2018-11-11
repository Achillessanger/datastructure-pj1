import java.io.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args) throws IOException {
        BPTree bpTree = new BPTree();
        RBTree rbtree = new RBTree();
        String[] r = {"c","g","l","n","q","m","t","d","p","r","h","j","k","i","f","a","b"};//17
        String[] x = {"c","n","g","a","h","e","k","q","m","f","w","l","t","z","d","p","r","x","y","s","b"};
        String[] k = {"7","3","5","1","6"};

//        for(int i = 0; i < r.length; i++){
//            bpTree.insert(r[i],r[i]);
//        }bpTree.tree_walk();


        //initial
        int time1 = 0;
        FileInputStream fis = new FileInputStream("./src/1_initial.txt");
        InputStreamReader reader = new InputStreamReader(fis,"GBK");
        BufferedReader br = new BufferedReader(reader);
        String s = br.readLine();//insert
        while ((s = br.readLine()) != null){//
            String s2 = br.readLine();
            rbtree.insert(s,s2);
            bpTree.insert(s,s2);
        }
        br.close();
//        if( bpTree.search("limestone") == null) System.out.println("wgrwg\nefwgrg\nfeqvqef\n\n\n\n\n\n");
//        else System.out.println( bpTree.search("limestone"));

        int time2 = 0;
        //delete
        FileInputStream fisDelete = new FileInputStream("./src/2_delete.txt");
        InputStreamReader readerDelete = new InputStreamReader(fisDelete,"GBK");
        BufferedReader brDelete = new BufferedReader(readerDelete);
        String sDelete = brDelete.readLine();//insert
        while ((sDelete = brDelete.readLine()) != null){
            rbtree.delete(sDelete);
            bpTree.delete(sDelete);
            time2++;

            if(time2 == 100 && rbtree.size >= 500){
                // rbtree.preorder_tree_walk();
           //     bpTree.tree_walk();
//                System.out.println(bpTree.size);
                time2 = 0;
            }
        }
        brDelete.close();

//        if( bpTree.search("limestone") == null) System.out.println("wgrwg\nefwgrg\nfeqvqef\n\n\n\n\n\n");
//        else System.out.println( bpTree.search("limestone"));
//
        //insert
//        int time3 = 0;
//        FileInputStream fisInsert = new FileInputStream("./src/3_insert.txt");
//        InputStreamReader readerInsert = new InputStreamReader(fisInsert,"GBK");
//        BufferedReader brInsert = new BufferedReader(readerInsert);
//        String sInsert = brInsert.readLine();//insert
//
//        while ((sInsert = brInsert.readLine()) != null){
//            String s2 = brInsert.readLine();
//            rbtree.insert(sInsert,s2);
//            bpTree.insert(sInsert,s2);
//            time3++;
//
//            if(time3 == 100 && rbtree.size >= 500){
//               // rbtree.preorder_tree_walk();
//                bpTree.tree_walk();
//                time3 = 0;
//            }
//        }
//        brInsert.close();







    }
}
