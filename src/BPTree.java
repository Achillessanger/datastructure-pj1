import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BPTree {
    private BPTNode root;////////
    private BPTNode theLeftestLeaf;
    private static int b = 5;
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
//        for(Map.Entry<String,String> entry : leafnodes.next.next.next.parent.entries){
//            System.out.print(entry.getKey()+"!!");
//        }
//        BPTNode walknodes = root;
//
//            for(int k = 0 ; k < root.childrenNodes.size(); k++){
//                for(int i =0;i<root.childrenNodes.get(k).entries.size();i++){
//                    System.out.print(root.childrenNodes.get(k).entries.get(i).getKey()+" ");
//                }
//                System.out.print("[   ]");
//            }
//          // walknodes = walknodes.childrenNodes.get(0);


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
        if(findWhereToInsertNode.entries.size() == 0){//但是每个叶节点都应该有东西的吧……
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
            }else {
                BPTNode tmpNode = correctNode.parent;
                if(tmpNode.parent != null){
                    if (tmpNode.parent.childrenNodes.indexOf(tmpNode) >= 1){ //这个循环也不一定对！飙泪
                        tmpNode = tmpNode.parent.childrenNodes.get(tmpNode.parent.childrenNodes.indexOf(tmpNode)-1);
                        tmpNode = tmpNode.childrenNodes.get(tmpNode.childrenNodes.size()-1);
                        tmpNode.next = left;
                    }
                }

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
                for (int i = 0; i < (b+1)/2+1; i++){ //////我也不知道这个i对不对？？
                    correctNode.childrenNodes.get(i).parent = left;
                    left.childrenNodes.add(correctNode.childrenNodes.get(i));
                }
                for (int i = (b+1)/2+1; i < b + 1; i++){
                    correctNode.childrenNodes.get(i).parent = right;                ////////////////////bug!!!只在b=4时有用qwq
                    right.childrenNodes.add(correctNode.childrenNodes.get(i));
                }
            }
            correctNode = correctNode.parent;
        }
    }

    public void delete(String key){
        //↓要从root开始找key属于哪个叶节点,并记录下如果在中间节点有的那个中间节点
        if(search(key) == null) return;//不能删除原来就没有的东西

        BPTNode findKeyInWhichLeafNode = root;
        BPTNode keyInInerLeaf = null;
        while (!findKeyInWhichLeafNode.isLeaf){
            for (int i = 0; i <= findKeyInWhichLeafNode.entries.size(); i++){
                if(i == 0){
                    if(key.compareTo(findKeyInWhichLeafNode.entries.get(i).getKey()) < 0){
                        findKeyInWhichLeafNode = findKeyInWhichLeafNode.childrenNodes.get(i);
                        break;
                    }
                }else if(i == findKeyInWhichLeafNode.entries.size()){
                    if(key.compareTo(findKeyInWhichLeafNode.entries.get(i - 1).getKey()) >= 0){
                        if(key.compareTo(findKeyInWhichLeafNode.entries.get(i - 1).getKey()) == 0){
                            keyInInerLeaf = findKeyInWhichLeafNode; ///逻辑不一定对！！
                        }
                        findKeyInWhichLeafNode = findKeyInWhichLeafNode.childrenNodes.get(i);
                        break;
                    }
                }else if(key.compareTo(findKeyInWhichLeafNode.entries.get(i - 1).getKey()) >= 0 && key.compareTo(findKeyInWhichLeafNode.entries.get(i).getKey()) < 0) {
                    if(key.compareTo(findKeyInWhichLeafNode.entries.get(i - 1).getKey()) == 0){
                        keyInInerLeaf = findKeyInWhichLeafNode;
                    }
                    findKeyInWhichLeafNode = findKeyInWhichLeafNode.childrenNodes.get(i);
                    break;
                }
            }
        }
        //↑找到了key所在的叶节点和中间节点 5要有3个及以上，4要有2个
        //如果L至少半满，可直接删除
        int index = 0; //被删除的key在叶节点中的index
        for(Map.Entry<String,String> entry : findKeyInWhichLeafNode.entries) {
            if (entry.getKey().equals(key)) {
                index = findKeyInWhichLeafNode.entries.indexOf(entry);
                break;
            }
        }
        findKeyInWhichLeafNode.entries.remove(index);
        if(findKeyInWhichLeafNode.entries.size() >= (b+1)/2 - 1){
            deleteInter(key,keyInInerLeaf);
            return;
        }else {
            BPTNode leftNode = getLeftSibling(findKeyInWhichLeafNode);
            BPTNode rightNode = getRightSibiling(findKeyInWhichLeafNode);
            if(leftNode != null && leftNode.entries.size() >= (b+1)/2 ){
                Map.Entry<String,String > o = new AbstractMap.SimpleEntry<String, String>(leftNode.entries.get(leftNode.entries.size()-1).getKey(),leftNode.entries.get(leftNode.entries.size()-1).getValue());
                leftNode.entries.remove(leftNode.entries.size()-1);
                findKeyInWhichLeafNode.entries.add(0,o);
                int indexUpdate = leftNode.parent.childrenNodes.indexOf(leftNode);
                leftNode.parent.entries.set(indexUpdate,new AbstractMap.SimpleEntry<String, String>(o.getKey(),null));
            }else if(rightNode != null && rightNode.entries.size() >= (b+1)/2 ){
                Map.Entry<String,String > o = new AbstractMap.SimpleEntry<String, String>(rightNode.entries.get(0).getKey(),rightNode.entries.get(0).getValue());
                rightNode.entries.remove(0);
                findKeyInWhichLeafNode.entries.add(o);
                int indexUpdate = findKeyInWhichLeafNode.parent.childrenNodes.indexOf(findKeyInWhichLeafNode);
                findKeyInWhichLeafNode.parent.entries.set(indexUpdate,new AbstractMap.SimpleEntry<String, String>(o.getKey(),null));
            }else if(leftNode != null){
                for(int i = 0; i < findKeyInWhichLeafNode.entries.size(); i++){
                    leftNode.entries.add(findKeyInWhichLeafNode.entries.get(i));
                }
                ///////////////////////有问题↓
                leftNode.parent.childrenNodes.remove(theLeftestLeaf);//索引页的key怎么删啊！！
            }else { ///////////////////////有问题↓
                for(int i = 0; i < rightNode.entries.size(); i++){
                    findKeyInWhichLeafNode.entries.add(rightNode.entries.get(i));
                }
                findKeyInWhichLeafNode.parent.childrenNodes.remove(rightNode);
            }
            deleteInter(key,keyInInerLeaf);
        }


    }

    private BPTNode getLeftSibling(BPTNode node){
        BPTNode searchNode = theLeftestLeaf;
        while (searchNode.next != null){
            if(searchNode.next == node) {
                if(searchNode.parent == node.parent){
                    return searchNode;
                }
            }
            searchNode = searchNode.next;
        }
        return null;
    }
    private BPTNode getRightSibiling(BPTNode node){
       if(node.next != null && node.next.parent == node.parent) {
           return node.next;
       }
        return null;
    }

    private void deleteInter(String key,BPTNode innerNode){ //b=5 >=3 减完
        int index = 0;  //key在内节点里的index
        for(Map.Entry<String,String> entry : innerNode.entries) {
            if (entry.getKey().equals(key)) {
                index = innerNode.entries.indexOf(entry);
                break;
            }
        }

        innerNode.entries.remove(index);
        //还要删childern

        if(innerNode.childrenNodes.size() >= (b+1)/2){  //b=5 3 ?????????????
            return;
        }else {
            BPTNode leftnode = getLeftSibling(innerNode);
            BPTNode rightnode = getRightSibiling(innerNode);
            if(leftnode != null && leftnode.childrenNodes.size() >= (b+1)/2 + 1){ //?????????
                BPTNode o = leftnode.childrenNodes.get(leftnode.childrenNodes.size()-1);
                leftnode.childrenNodes.remove(o);
                innerNode.childrenNodes.add(0,o);
                o.parent = innerNode.parent;
            }
        }

    }
    public BPTNode findInnerNode(String key){
        BPTNode findKeyInWhichLeafNode = root;
        BPTNode keyInInerLeaf = null;
        while (!findKeyInWhichLeafNode.isLeaf){
            for (int i = 0; i <= findKeyInWhichLeafNode.entries.size(); i++){
                if(i == 0){
                    if(key.compareTo(findKeyInWhichLeafNode.entries.get(i).getKey()) < 0){
                        findKeyInWhichLeafNode = findKeyInWhichLeafNode.childrenNodes.get(i);
                        break;
                    }
                }else if(i == findKeyInWhichLeafNode.entries.size()){
                    if(key.compareTo(findKeyInWhichLeafNode.entries.get(i - 1).getKey()) >= 0){
                        if(key.compareTo(findKeyInWhichLeafNode.entries.get(i - 1).getKey()) == 0){
                            keyInInerLeaf = findKeyInWhichLeafNode; ///逻辑不一定对！！
                        }
                        findKeyInWhichLeafNode = findKeyInWhichLeafNode.childrenNodes.get(i);
                        break;
                    }
                }else if(key.compareTo(findKeyInWhichLeafNode.entries.get(i - 1).getKey()) >= 0 && key.compareTo(findKeyInWhichLeafNode.entries.get(i).getKey()) < 0) {
                    if(key.compareTo(findKeyInWhichLeafNode.entries.get(i - 1).getKey()) == 0){
                        keyInInerLeaf = findKeyInWhichLeafNode;
                    }
                    findKeyInWhichLeafNode = findKeyInWhichLeafNode.childrenNodes.get(i);
                    break;
                }
            }
        }
        return keyInInerLeaf;
    }
}
