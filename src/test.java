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

        for(int i = 0; i < r.length; i++){
            rbtree.insert(r[i],r[i]);
        }
        for(int i = 0; i < r.length; i++){
            bpTree.insert(r[i],r[i]);
        }
//
//        rbtree.inorder_tree_walk("a","b");
//       System.out.println( rbtree.result);
//
//       System.out.println("\nhi:\n"+bpTree.searchRange("a","b"));

        rbtree.inorder_tree_walk("c","m");
        System.out.println( rbtree.result);
        System.out.println("\nhi:\n"+bpTree.searchRange("c","m"));



    }
}
