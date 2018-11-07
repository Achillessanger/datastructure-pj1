//import javafx.scene.layout.BorderPane;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class BplusTree {
//    private static int t = 4;
//    private BTNode root;
//
//    public void insert(String key, String value){
//        root.insert(key,value,this);
//    }
//
//    public class BTNode{
//        boolean isLeaf;
//        boolean isRoot;
//        BTNode previous;
//        BTNode next;
//        BTNode parent;
//        List<BTNode> childrenNodes;
//        List<Map.Entry<String,String>> entries;
//
//        public BTNode(boolean isLeaf) {
//            this.isLeaf = isLeaf;
//            entries = new ArrayList<Map.Entry<String, String>>();
//            if(!isLeaf) childrenNodes = new ArrayList<BTNode>();
//        }
//        public BTNode(boolean isLeaf, boolean isRoot){
//            this(isLeaf);
//            this.isRoot = isRoot;
//        }
//        public Object search(String key){
//            if(isLeaf){
//                for (Map.Entry<String, String> entry : entries){
//                    if(entry.getKey().compareTo(key) == 0)
//                        return entry.getValue();//找到了并返回中文
//                }
//                return null;//没找到
//            }else {
//                if(key.compareTo(entries.get(0).getKey()) <= 0) return childrenNodes.get(0).search(key);
//                else if(key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) return childrenNodes.get(childrenNodes.size() - 1).search(key);
//                else {
//                    for(int i = 0; i < entries.size(); i++){
//                        if(key.compareTo(entries.get(i).getKey()) >= 0 && key.compareTo(entries.get(i + 1).getKey()) < 0){
//                            return childrenNodes.get(i).search(key);
//                        }
//                    }
//                }
//            }
//            return null;
//        }
//
//        public void insert(String key,String value, BplusTree tree){
//            if(search(key) != null) return;
//            if(isLeaf){
//                if(entries.size() < tree.t){
//                    insert(key,value);/////////////////////////////////////////
//                    if(parent != null){
//                        parent.updateInsert(tree);/////////////////////////////////////////
//                    }
//                }else { //分裂
//                    BTNode left = new BTNode(true);
//                    BTNode right = new BTNode(true);
//                    if(previous != null){
//                        previous.next = left;
//                        left.previous = previous;
//                    }
//                    if(next != null){
//                        next.previous = right;
//                        right.next = next;
//                    }
//                    left.next = right;
//                    right.previous = left;
//                    previous = null; //清除自己
//                    next = null;
//
//                    int leftSize = (tree.t + 1) / 2 +(tree.t + 1) % 2;
//                    int rightSize = (tree.t + 1) / 2;
//                    insert(key,value);///////////////////////////////////////////////////////////??
//                    for (int i = 0; i < leftSize; i++){
//                        left.entries.add(entries.get(i));
//                    }
//                    for(int i = 0; i < rightSize; i++){
//                        right.entries.add(entries.get(leftSize + i));
//                    }
//                    //126行
//                    if(parent != null){
//                        int index = parent.childrenNodes.indexOf(this);//自己是第x个孩子
//                        parent.childrenNodes.remove(this);
//                        left.parent = parent;
//                        right.parent = parent;
//                        parent.childrenNodes.add(index, left);
//                        parent.childrenNodes.add(index + 1, right);
//                        entries = null;
//                        childrenNodes = null;
//
//                        parent.updateInsert(tree);//////////////////////////////////////
//                        parent = null;/////////////////////////////?????????
//                    }else {
//                        isRoot = false;
//                        BTNode parent = new BTNode(false,true);
//
//                    }
//                }
//
//            }
//        }
//    }
//
//
//
//}
