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
        int index = 0;
        for(Map.Entry<String,String> entry : findKeyInWhichLeafNode.entries) {
            if (entry.getKey().equals(key)) {
                index = findKeyInWhichLeafNode.entries.indexOf(entry);
                break;
            }
        }
        if(findKeyInWhichLeafNode.entries.size() >= (b+1)/2){
            if(keyInInerLeaf == null){
                findKeyInWhichLeafNode.entries.remove(index);
            }else {     //应该满足这个条件的话就不会是叶节点的最后一个吧
                String successor;
                if(index != findKeyInWhichLeafNode.entries.size() - 1){
                    successor = findKeyInWhichLeafNode.entries.get(index + 1).getKey();
                }else {
                    successor = findKeyInWhichLeafNode.entries.get(index - 1).getKey();
                }
                findKeyInWhichLeafNode.entries.remove(index);
                for (Map.Entry<String ,String> innerentry : keyInInerLeaf.entries){
                    if(innerentry.getKey().equals(key)){
                        int indexInner = keyInInerLeaf.entries.indexOf(innerentry);
                        keyInInerLeaf.entries.remove(innerentry);
                        keyInInerLeaf.entries.add(indexInner,new AbstractMap.SimpleEntry<String, String>(successor,null));
                        break;
                    }
                }
            }
        }else {
            BPTNode changeNode = findKeyInWhichLeafNode;
                //向兄弟借一个
            if(changeNode.parent != null && changeNode.parent.childrenNodes.indexOf(changeNode) < changeNode.parent.childrenNodes.size() - 1 && changeNode.parent.childrenNodes.get(changeNode.parent.childrenNodes.indexOf(changeNode) + 1).entries.size() >= (b+1)/2){
                //像右边的兄弟节点借一个
                changeNode.entries.remove(index);
                BPTNode brotherNode = changeNode.parent.childrenNodes.get(changeNode.parent.childrenNodes.indexOf(changeNode) + 1);
                String shouldBeReplaced = brotherNode.entries.get(0).getKey();
                changeNode.entries.add(new AbstractMap.SimpleEntry<String, String>(brotherNode.entries.get(0).getKey(),brotherNode.entries.get(0).getValue()));
                brotherNode.entries.remove(0);
                String successor = brotherNode.entries.get(0).getKey();
                BPTNode preBrotherInner = findInnerNode(shouldBeReplaced);
                if(preBrotherInner != null){
                    int indexReplaced = 0;
                    for (Map.Entry<String ,String> innerentry : preBrotherInner.entries){
                        if(innerentry.getKey().equals(shouldBeReplaced)){
                            indexReplaced = preBrotherInner.entries.indexOf(innerentry);
                            break;
                        }
                    }
                    preBrotherInner.entries.remove(indexReplaced);
                    preBrotherInner.entries.add(indexReplaced,new AbstractMap.SimpleEntry<String, String>(successor,null));
                    //这个逻辑完不完整不一定
                }
            }else if(changeNode.parent != null && changeNode.parent.childrenNodes.indexOf(changeNode) - 1 >= 0 && changeNode.parent.childrenNodes.get(changeNode.parent.childrenNodes.indexOf(changeNode) - 1).entries.size() >= (b+1)/2){
                changeNode.entries.remove(index);
                BPTNode brotherNode = changeNode.parent.childrenNodes.get(changeNode.parent.childrenNodes.indexOf(changeNode) - 1);
                String shouldBeReplaced = brotherNode.entries.get(brotherNode.entries.size() - 1).getKey();
                changeNode.entries.add(new AbstractMap.SimpleEntry<String, String>(brotherNode.entries.get(brotherNode.entries.size() - 1).getKey(),brotherNode.entries.get(brotherNode.entries.size() - 1).getValue()));
                brotherNode.entries.remove(brotherNode.entries.size() - 1);
                String successor = brotherNode.entries.get(brotherNode.entries.size() - 1).getKey();
                BPTNode preBrotherInner = findInnerNode(shouldBeReplaced);
                if(preBrotherInner != null){
                    int indexReplaced = 0;
                    for (Map.Entry<String ,String> innerentry : preBrotherInner.entries){
                        if(innerentry.getKey().equals(shouldBeReplaced)){
                            indexReplaced = preBrotherInner.entries.indexOf(innerentry);
                            break;
                        }
                    }
                    preBrotherInner.entries.remove(indexReplaced);
                    preBrotherInner.entries.add(indexReplaced,new AbstractMap.SimpleEntry<String, String>(successor,null));
                    //这个逻辑完不完整不一定 对不对也不一定
                }
            }else {
                if(keyInInerLeaf != null){
                    for (Map.Entry<String ,String> innerentry :keyInInerLeaf.entries){
                        if(innerentry.getKey().equals(key)){
                            keyInInerLeaf.entries.remove(innerentry);
                            break;
                        }
                    }
                }
                //和兄弟合并

                BPTNode deleteInnerNode = keyInInerLeaf;
                if(changeNode.parent.childrenNodes.indexOf(changeNode) < changeNode.parent.childrenNodes.size() - 1 && changeNode.parent.childrenNodes.get(changeNode.parent.childrenNodes.indexOf(changeNode) + 1).entries.size() < (b+1)/2){
                    //和右边兄弟合并
                    BPTNode brotherNode = changeNode.parent.childrenNodes.get(changeNode.parent.childrenNodes.indexOf(changeNode) + 1);

//                    for (Map.Entry<String ,String> entry : changeNode.entries){
//                        if(entry.getKey().equals(key)){
//                            int keyIndex = changeNode.entries.indexOf(entry);
//                            changeNode.entries.remove(entry);
//                            String successor = "";
//                            if(keyIndex < changeNode.entries.size() - 1){
//                                successor = changeNode.entries.get(keyIndex + 1).getKey();
//                            }else {
//                                successor = brotherNode.entries.get(0).getKey();
//                            }
//                            break;
//                        }
//                    }
//                    changeNode.entries.remove(index);
//                    for(int i = 0; i < changeNode.entries.size(); i++){
//                        brotherNode.entries.add(i,new AbstractMap.SimpleEntry<String, String>(changeNode.entries.get(i).getKey(),changeNode.entries.get(i).getValue()));
//                    }
//                    if(theLeftestLeaf == changeNode) theLeftestLeaf = brotherNode;
//                    changeNode.parent.childrenNodes.remove(changeNode);

                    BPTNode mergedNode = new BPTNode(changeNode.isLeaf);
                    mergedNode.parent = changeNode.parent;
                    int indexOf2MergedNodes = changeNode.parent.childrenNodes.indexOf(changeNode);
                    mergedNode.parent.childrenNodes.remove(changeNode);
                    mergedNode.parent.childrenNodes.remove(brotherNode);
                    mergedNode.parent.childrenNodes.add(indexOf2MergedNodes,mergedNode);
                    mergedNode.parent.entries.remove(indexOf2MergedNodes);//不一定对呜呜呜
                    if(changeNode == theLeftestLeaf) theLeftestLeaf = mergedNode;
                    for(int i = 0; i < changeNode.entries.size(); i++){
                        if(i == index) continue; //不复制要删除的key了
                        mergedNode.entries.add(new AbstractMap.SimpleEntry<String, String>(changeNode.entries.get(i).getKey(),changeNode.entries.get(i).getValue()));
                    }
                    for(int i = 0; i < brotherNode.entries.size(); i++){
                        mergedNode.entries.add(new AbstractMap.SimpleEntry<String, String>(brotherNode.entries.get(i).getKey(),brotherNode.entries.get(i).getValue()));
                    }

                }else if(changeNode.parent.childrenNodes.indexOf(changeNode) - 1 >= 0 && changeNode.parent.childrenNodes.get(changeNode.parent.childrenNodes.indexOf(changeNode) - 1).entries.size() < (b+1)/2){
                    //左边兄弟
                    BPTNode brotherNode = changeNode.parent.childrenNodes.get(changeNode.parent.childrenNodes.indexOf(changeNode) - 1);
                    BPTNode mergedNode = new BPTNode(changeNode.isLeaf);
                    mergedNode.parent = changeNode.parent;
                    int indexOf2MergedNodes = changeNode.parent.childrenNodes.indexOf(changeNode);
                    mergedNode.parent.childrenNodes.remove(changeNode);
                    mergedNode.parent.childrenNodes.remove(brotherNode);
                    mergedNode.parent.childrenNodes.add(indexOf2MergedNodes,mergedNode);
                    mergedNode.parent.entries.remove(indexOf2MergedNodes);
                    if(brotherNode == theLeftestLeaf) theLeftestLeaf = mergedNode;
                    for(int i = 0; i < brotherNode.entries.size(); i++){
                        mergedNode.entries.add(new AbstractMap.SimpleEntry<String, String>(brotherNode.entries.get(i).getKey(),brotherNode.entries.get(i).getValue()));
                    }
                    for(int i = 0; i < changeNode.entries.size(); i++){
                        if(i == index) continue;
                        mergedNode.entries.add(new AbstractMap.SimpleEntry<String, String>(changeNode.entries.get(i).getKey(),changeNode.entries.get(i).getValue()));
                    }
                }

                BPTNode checkNode = findKeyInWhichLeafNode.parent;


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
