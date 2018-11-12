import java.io.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args) throws IOException {
        BPTree bpTree = new BPTree();
        RBTree rbtree = new RBTree();

        // System.out.println("\nhi:\n"+);
        //2824019,1592887,1516670,1544751,1625343,1421126,1469992,1227486,1043326,543361,
        //470427,453652,441617,607543,488296,603167,573629,984250,573629,574723,

//1350745,1334335,1403987,1363508,1496978,1880978,625412,578734,657139,683030,
        //782951,307418,302313,458758,265117,270951,270222,412809,247977,280068,
        //189265,124718,131647,117425,102838,75852,67829,76946,92262,62724
        //131282,136752,86063,176866,83875,86428,84240,189630,120706,128000

        int[] xs = {131282,136752,86063,176866,83875,86428,84240,189630,120706,128000};
        int sum = 0;
        for (int i = 0; i < xs.length; i++) {
            sum += xs[i];
        }
        System.out.println(sum / xs.length);


//        int time = 0;
//        BPTree bptree = new BPTree();
//        //initial
//        FileInputStream fis = new FileInputStream("./src/1_initial.txt");
//        InputStreamReader reader = new InputStreamReader(fis,"GBK");
//        BufferedReader br = new BufferedReader(reader);
//        String s = br.readLine();//insert
//        int time1 = 0;
//        long startTime = 0;
//        long endTime = 0;
//        int n = 0;
//        while (n < 368){//
//            n++;
//            s = br.readLine();
//            String s2 = br.readLine();
////            if(time1 == 0){
////                startTime = System.nanoTime();
////            }
//            bptree.insert(s,s2);
//            time++;
//            time1++;
////            if(time1 == 100){    //为了记录插100次的时间 最好把下面那个循环删了！
////                endTime = System.nanoTime();
////                System.out.println("★★用1_initial.txt插入每100次花费(ns)："+ (endTime - startTime) );
////                time1 = 0;
////            }
//            if(time == 100 && bptree.size <= 500){
//                bptree.tree_walk();
//                time = 0;
//            }
//        }
//        s = br.readLine();
//        String s2 = br.readLine();
//        bptree.insert(s,s2);
//
//        br.close();
//
//    }
    }


}
