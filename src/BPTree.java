import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BPTree {
    private BPTNode root;////////
    private BPTNode theLeftestLeaf;
    private static int b = 4;
    public class BPTNode{
        boolean isLeaf;
        boolean isRoot;
        BPTNode next;
        BPTNode parent;
        List<BPTNode> childrenNodes;
        List<Map.Entry<String,String>> entries;

        public BPTNode(boolean isLeaf) {
            this.isLeaf = isLeaf;
            entries = new ArrayList<Map.Entry<String, String>>();
            childrenNodes = new ArrayList<BPTNode>();
        }
    }
    public void treeWalk(){
        BPTNode leafnodes = theLeftestLeaf;
        for(Map.Entry<String,String> entry : leafnodes.next.next.next.parent.entries){
            System.out.print(entry.getKey()+"!!");
        }
        do{
            for(Map.Entry<String,String> entry : leafnodes.entries){
                System.out.print(entry.getKey()+" ");
                }
            leafnodes = leafnodes.next;
            System.out.print("[   ]");
        }while (leafnodes != null);
    }

    public String search(String key){
        BPTNode leafnodes = theLeftestLeaf;
        do{
            for(Map.Entry<String,String> entry : leafnodes.entries){
                if(entry.getKey().compareTo(key) == 0){
                    return entry.getValue();
                }
            }
            leafnodes = leafnodes.next;
        }while (leafnodes != null);

        return null;
    }

    public void insert(String key, String chinese){
        if(root == null){
            BPTNode creatRoot = new BPTNode(true);
            root = creatRoot;
            theLeftestLeaf = root;
        }
        if (search(key) != null) return; //不能插入重复的

        BPTNode findWhereToInsertNode = root;

        while (!findWhereToInsertNode.isLeaf) { //////这个判断不一定对，不知道是不是null
            for (int i = 0; i <= findWhereToInsertNode.entries.size(); i++){
                if(i == 0){
                    if(key.compareTo(findWhereToInsertNode.entries.get(i).getKey()) < 0){ //比最左边数小
                        findWhereToInsertNode = findWhereToInsertNode.childrenNodes.get(i);
                        break;
                    }
                }
                else if(i == findWhereToInsertNode.entries.size()){
                    if(key.compareTo(findWhereToInsertNode.entries.get(i - 1).getKey()) > 0){
                        findWhereToInsertNode = findWhereToInsertNode.childrenNodes.get(i);
                        break;
                    }
                }
                else if(key.compareTo(findWhereToInsertNode.entries.get(i - 1).getKey()) > 0 && key.compareTo(findWhereToInsertNode.entries.get(i).getKey()) < 0){
                    findWhereToInsertNode = findWhereToInsertNode.childrenNodes.get(i);
                    break;
                }
            }
        }
        //↑找到了插入的叶节点
        if(findWhereToInsertNode.entries.size() == 0){
            findWhereToInsertNode.entries.add(new AbstractMap.SimpleEntry<String, String>(key,chinese));
        }
        for(int i = 0; i <= findWhereToInsertNode.entries.size(); i++){
            if(i == 0){
                if(key.compareTo(findWhereToInsertNode.entries.get(i).getKey()) < 0){ //比最左边数小
                    findWhereToInsertNode.entries.add(i,new AbstractMap.SimpleEntry<String, String>(key,chinese));
                    break;
                }
            }
            else if(i == findWhereToInsertNode.entries.size()){
                if(key.compareTo(findWhereToInsertNode.entries.get(i - 1).getKey()) > 0){
                    findWhereToInsertNode.entries.add(new AbstractMap.SimpleEntry<String, String>(key,chinese));
                    break;
                }
            }
            else if(key.compareTo(findWhereToInsertNode.entries.get(i - 1).getKey()) > 0 && key.compareTo(findWhereToInsertNode.entries.get(i).getKey()) < 0){
                findWhereToInsertNode.entries.add(i,new AbstractMap.SimpleEntry<String, String>(key,chinese));
                break;
            }
        }

        //↑将key value插入了叶节点
        //↓接下来要拆分满节点了
        BPTNode correctNode = findWhereToInsertNode;
        while (correctNode.entries.size() == b){
            if(correctNode == root){
                BPTNode newRoot = new BPTNode(false);
                root = newRoot;
                correctNode.parent = root;
                newRoot.childrenNodes.add(correctNode);
            }
            int leftSize = (b+1)/2;
            int rightSize = b/2;
            BPTNode left = new BPTNode(false);
            BPTNode right = new BPTNode(false);
            left.parent = correctNode.parent;
            right.parent = correctNode.parent;
            left.next = right;
            if(correctNode.next != null) right.next = correctNode.next;
            if(correctNode.parent.childrenNodes.indexOf(correctNode) - 1 >= 0){
                BPTNode preCorrectNode = correctNode.parent.childrenNodes.get(correctNode.parent.childrenNodes.indexOf(correctNode) - 1);
                preCorrectNode.next = left;
            }

            if(correctNode.isLeaf){
                left.isLeaf = true;
                right.isLeaf = true;

                for(int i = 0; i < leftSize; i++){
                    left.entries.add(correctNode.entries.get(i));
                }
                for(int i = 0; i < rightSize; i++){
                    right.entries.add(correctNode.entries.get(leftSize + i));
                }
                String upKey = right.entries.get(0).getKey();
                int upIndex = correctNode.parent.childrenNodes.indexOf(correctNode);
                correctNode.parent.childrenNodes.remove(correctNode);
                correctNode.parent.childrenNodes.add(upIndex,left);
                correctNode.parent.childrenNodes.add(upIndex+1,right);
                correctNode.parent.entries.add(upIndex,new AbstractMap.SimpleEntry<String, String>(upKey,null));
                if(theLeftestLeaf == correctNode){
                    theLeftestLeaf = left;
                }
            }else {
                for(int i = 0; i < leftSize; i++){
                    left.entries.add(correctNode.entries.get(i));
                }
                for(int i = 0; i < rightSize; i++){
                    right.entries.add(correctNode.entries.get(leftSize + i));
                }
                String upKey = right.entries.get(0).getKey();
                right.entries.remove(right.entries.get(0));
                int upIndex = correctNode.parent.childrenNodes.indexOf(correctNode);
                correctNode.parent.childrenNodes.remove(correctNode);
                correctNode.parent.childrenNodes.add(upIndex,left);
                correctNode.parent.childrenNodes.add(upIndex+1,right);
                correctNode.parent.entries.add(upIndex,new AbstractMap.SimpleEntry<String, String>(upKey,null));
                for (int i = 0; i < (b+2)/2; i++){ //////我也不知道这个i对不对？？
                    correctNode.childrenNodes.get(i).parent = left;
                    left.childrenNodes.add(correctNode.childrenNodes.get(i));
                }
                for (int i = (b+2)/2; i < b + 1; i++){
                    correctNode.childrenNodes.get(i).parent = right;
                    right.childrenNodes.add(correctNode.childrenNodes.get(i));
                }
            }
            correctNode = correctNode.parent;
        }
    }

    public void delete(){

    }
}
