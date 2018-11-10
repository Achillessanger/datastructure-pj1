import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args){
        BPTree tree = new BPTree();
//
//        tree.insert("p","你好j");
//        tree.insert("f","你好f");
//        tree.insert("g","你好g");
//        tree.insert("l","你好j");
//        tree.insert("m","你好j");
//        tree.insert("i","你好i");
//        tree.insert("k","你好j");
//        tree.insert("j","你好j");
//        tree.insert("a","你好a");//1
//        tree.insert("b","你好b");
//        tree.insert("h","你好h");
//        tree.insert("c","你好c");
//        tree.insert("n","你好j");
//        tree.insert("o","你好j");
//        tree.insert("d","你好d");
//        tree.insert("e","你好e");
        tree.insert("1",null);
        tree.insert("12",null);
        tree.insert("2",null);

        //tree.insert("l","你好j");

//        List<Map.Entry<String,String>> entriesAfterInsert = new ArrayList<Map.Entry<String, String>>();
//        Map.Entry o = new AbstractMap.SimpleEntry<String, String>("bye","再见");
//        entriesAfterInsert.add(0,new AbstractMap.SimpleEntry<String, String>("hello","你好"));
//        entriesAfterInsert.add(0,o);
//        entriesAfterInsert.add(1,new AbstractMap.SimpleEntry<String, String>("just","才"));

        tree.treeWalk();



    }
}
