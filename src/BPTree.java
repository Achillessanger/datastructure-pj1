import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BPTree {
    private BPTNode root;////////我靠难道这个树对大小还有限制的吗？？？b=5的时候运行不完？？
    private BPTNode theLeftestLeaf;
    private static int b = 5;
    public int size = 0;
    public class BPTNode{
        boolean isLeaf;
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
    public void tree_walk(){
        int level = 0;
        System.out.println("================================================================");
        treeWalkprint(level,root,0);
        System.out.println("================================================================\n\n\n");
    }
    private void treeWalkprint(int level,BPTNode node,int order){
        if(node != null){
            System.out.print("level="+level+"  child="+order+"  /");
            for(int i = 0; i < node.entries.size(); i++){
                if (node.isLeaf)
                    System.out.print(node.entries.get(i).getKey()+":"+node.entries.get(i).getValue()+"/");
                else
                    System.out.print(node.entries.get(i).getKey()+"/");
            }
            System.out.print("\n");
            level++;
            for (int i = 0; i < node.childrenNodes.size(); i++){
                treeWalkprint(level,node.childrenNodes.get(i),i);
            }
        }
    }

    public int search2(){
        BPTNode leafnodes = theLeftestLeaf;         ///////////theleftest没变
        int total = 0;
        do{
            total += leafnodes.entries.size();
            leafnodes = leafnodes.next;
        }while (leafnodes != null);
        return total;
    }
    public String search(String key) {
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
                    if(key.compareTo(findWhereToInsertNode.entries.get(i - 1).getKey()) >= 0){
                        findWhereToInsertNode = findWhereToInsertNode.childrenNodes.get(i);
                        break;
                    }
                }
                else if(key.compareTo(findWhereToInsertNode.entries.get(i - 1).getKey()) >= 0 && key.compareTo(findWhereToInsertNode.entries.get(i).getKey()) < 0){
                    findWhereToInsertNode = findWhereToInsertNode.childrenNodes.get(i);
                    break;
                }
            }
        }
        for(Map.Entry<String,String> entry : findWhereToInsertNode.entries){
            if(entry.getKey().compareTo(key) == 0){
                return entry.getValue();
            }
        }
        return null;
    }

    public BPTNode searchNode(String key) {
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
                    if(key.compareTo(findWhereToInsertNode.entries.get(i - 1).getKey()) >= 0){
                        findWhereToInsertNode = findWhereToInsertNode.childrenNodes.get(i);
                        break;
                    }
                }
                else if(key.compareTo(findWhereToInsertNode.entries.get(i - 1).getKey()) >= 0 && key.compareTo(findWhereToInsertNode.entries.get(i).getKey()) < 0){
                    findWhereToInsertNode = findWhereToInsertNode.childrenNodes.get(i);
                    break;
                }
            }
        }
        return findWhereToInsertNode;
    }

    public String searchRange(String from,String to){
        BPTNode fromnode = searchNode(from);
        BPTNode tonode = searchNode(to);
        String result = "";
        if(from.compareTo(to) <= 0){
            BPTNode leafnodes = fromnode;
            do{
                for(Map.Entry<String,String> entry : leafnodes.entries){
                    if(entry.getKey().compareTo(to) > 0)
                        return result;
                    if(entry.getKey().compareTo(from) >= 0 && entry.getKey().compareTo(to) <= 0) {
                        result += entry.getKey()+" : "+entry.getValue()+"\n";
                    }
                }
                leafnodes = leafnodes.next;

            }while (leafnodes != null || leafnodes.next == tonode.next);
        }
        return result;
    }

    public void insert(String key, String chinese){
        if(root == null){
            BPTNode creatRoot = new BPTNode(true);
            root = creatRoot;
            theLeftestLeaf = root;
        }

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
                    if(key.compareTo(findWhereToInsertNode.entries.get(i - 1).getKey()) >= 0){
                        findWhereToInsertNode = findWhereToInsertNode.childrenNodes.get(i);
                        break;
                    }
                }
                else if(key.compareTo(findWhereToInsertNode.entries.get(i - 1).getKey()) >= 0 && key.compareTo(findWhereToInsertNode.entries.get(i).getKey()) < 0){
                    findWhereToInsertNode = findWhereToInsertNode.childrenNodes.get(i);
                    break;
                }
            }
        }
        //↑找到了插入的叶节点

        for(int i = 0; i < findWhereToInsertNode.entries.size(); i++){
            if(key.compareTo(findWhereToInsertNode.entries.get(i).getKey()) == 0)   //如果重复就不插入了
                return;
        }

        size++;

        //↓插入
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
            int leftSize = (b)/2;  //5-3
            int rightSize = (b+1)/2;    //5-2
            BPTNode left = new BPTNode(false);
            BPTNode right = new BPTNode(false);
            left.parent = correctNode.parent;
            right.parent = correctNode.parent;
            left.next = right;
            if(correctNode.next != null) right.next = correctNode.next;

            BPTNode tmpnode = theLeftestLeaf;
            while (tmpnode.next != null){
                if(tmpnode.next == correctNode)
                    tmpnode.next = left;
                tmpnode = tmpnode.next;
            }
//            if(correctNode.parent.childrenNodes.indexOf(correctNode) - 1 >= 0){
//                BPTNode preCorrectNode = correctNode.parent.childrenNodes.get(correctNode.parent.childrenNodes.indexOf(correctNode) - 1);
//                preCorrectNode.next = left;
//            }else {
//                BPTNode tmpNode = correctNode.parent;
//                if(tmpNode.parent != null){
//                    if (tmpNode.parent.childrenNodes.indexOf(tmpNode) >= 1){ //这个循环也不一定对！飙泪
//                        tmpNode = tmpNode.parent.childrenNodes.get(tmpNode.parent.childrenNodes.indexOf(tmpNode)-1);
//                        tmpNode = tmpNode.childrenNodes.get(tmpNode.childrenNodes.size()-1);
//                        tmpNode.next = left;
//                    }
//                }
//
//            }

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
                    correctNode.childrenNodes.get(i).parent = right;                ////////////////////bug!!!只在b=4,5时有用qwq但是现在好像又都有用了……
                    right.childrenNodes.add(correctNode.childrenNodes.get(i));
                }
            }
            correctNode = correctNode.parent;
        }
    }

    public void delete(String key){
        //↓要从root开始找key属于哪个叶节点,并记录下如果在中间节点有的那个中间节点
        BPTNode findKeyInWhichLeafNode = root;
        if(root == null|| root.entries.size() == 0) {
            return;
        }
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
        boolean ifAlreadyHave = false;
        int index = 0; //被删除的key在叶节点中的index
        for(Map.Entry<String,String> entry : findKeyInWhichLeafNode.entries){
            if(entry.getKey().compareTo(key) == 0){
                index = findKeyInWhichLeafNode.entries.indexOf(entry);
                ifAlreadyHave = true;
                break;
            }
        }

        if(!ifAlreadyHave){
            return;
        } else
            size--;
        //↑找到了key所在的叶节点和中间节点 5要有3个及以上，4要有2个
        //如果L至少半满，可直接删除

        int min = (b+1)/2 -1;  //b=5  2
        String successor = "";
        BPTNode findSuccessorNode = findKeyInWhichLeafNode;
        if(findSuccessorNode.entries.size()-1 >= index+1){
            successor = findSuccessorNode.entries.get(index+1).getKey();
        }else {
            successor = findSuccessorNode.entries.get(index - 1).getKey();
        }

        findKeyInWhichLeafNode.entries.remove(index);

        int innerIndex = 0;
        if(keyInInerLeaf != null && !keyInInerLeaf.isLeaf) {
            for (Map.Entry<String, String> entry : keyInInerLeaf.entries) {
                if (entry.getKey().equals(key)) {
                    innerIndex = keyInInerLeaf.entries.indexOf(entry);
                    break;
                }
            }
            if(findKeyInWhichLeafNode != root)
                keyInInerLeaf.entries.set(innerIndex, new AbstractMap.SimpleEntry<String, String>(successor, null));

          //  这个判断我也不知道要不要,感觉挺必要的但是模拟一种情况的时候又感觉不需要这个？？
            if(successor.compareTo(keyInInerLeaf.childrenNodes.get(innerIndex+1).entries.get(0).getKey()) > 0){
                keyInInerLeaf.entries.set(innerIndex,new AbstractMap.SimpleEntry<String, String>(keyInInerLeaf.childrenNodes.get(innerIndex+1).entries.get(0).getKey(), null));
            }
            if(successor.compareTo(keyInInerLeaf.childrenNodes.get(innerIndex).entries.get(keyInInerLeaf.childrenNodes.get(innerIndex).entries.size()-1).getKey()) <= 0){
                keyInInerLeaf.entries.set(innerIndex,new AbstractMap.SimpleEntry<String, String>(keyInInerLeaf.childrenNodes.get(innerIndex).entries.get(keyInInerLeaf.childrenNodes.get(innerIndex).entries.size()-1).getKey(),null));
            }
        }

        if(findKeyInWhichLeafNode == root && findKeyInWhichLeafNode.entries.size() < min){
            findKeyInWhichLeafNode.entries.remove(index);
            return;
        }

        if(findKeyInWhichLeafNode.entries.size() >= min){

            return;
        }else {
            BPTNode leftNode = getLeftSibling(findKeyInWhichLeafNode);
            BPTNode rightNode = getRightSibiling(findKeyInWhichLeafNode);

            if(leftNode != null && leftNode.entries.size() >= min+1 ){ //向左边借key
                Map.Entry<String,String > o = new AbstractMap.SimpleEntry<String, String>(leftNode.entries.get(leftNode.entries.size()-1).getKey(),leftNode.entries.get(leftNode.entries.size()-1).getValue());
                leftNode.entries.remove(leftNode.entries.size()-1);
                findKeyInWhichLeafNode.entries.add(0,o);
                int indexUpdate = leftNode.parent.childrenNodes.indexOf(leftNode);
                leftNode.parent.entries.set(indexUpdate,new AbstractMap.SimpleEntry<String, String>(o.getKey(),null));
            }else if(rightNode != null && rightNode.entries.size() >= min+1 ){
                Map.Entry<String,String > o = new AbstractMap.SimpleEntry<String, String>(rightNode.entries.get(0).getKey(),rightNode.entries.get(0).getValue());
                rightNode.entries.remove(0);
                findKeyInWhichLeafNode.entries.add(o);
                int indexUpdate = findKeyInWhichLeafNode.parent.childrenNodes.indexOf(findKeyInWhichLeafNode);
                findKeyInWhichLeafNode.parent.entries.set(indexUpdate,new AbstractMap.SimpleEntry<String, String>(rightNode.entries.get(0).getKey(),null));
            }else if(leftNode != null){//向左合并

                if(leftNode.parent == root && leftNode.parent.entries.size() == 1){
                    rotation(findKeyInWhichLeafNode);/////////////////////////////////////////////////////////////////////////
                }else {
                    for (int i = 0; i < findKeyInWhichLeafNode.entries.size(); i++) {
                        leftNode.entries.add(findKeyInWhichLeafNode.entries.get(i));
                    }
                    ///////////////////////有问题↓
                    leftNode.next = findKeyInWhichLeafNode.next;//不一定对！
                    int deleteIndex = leftNode.parent.childrenNodes.indexOf(leftNode);
                    leftNode.parent.entries.remove(deleteIndex);
                    leftNode.parent.childrenNodes.remove(findKeyInWhichLeafNode);//索引页的key怎么删啊！！
                    rotation(leftNode.parent);
                }
            }else { ///////////////////////有问题↓
                if (rightNode.parent == root && root.entries.size() == 1) {
                    rotation(findKeyInWhichLeafNode);//////////////////////////
                } else {
                    for (int i = 0; i < rightNode.entries.size(); i++) {
                        findKeyInWhichLeafNode.entries.add(rightNode.entries.get(i));
                    }
                    BPTNode tmpNode = theLeftestLeaf;
                    while (tmpNode.next != null) { //不一定对！
                        if (tmpNode.next == rightNode) {
                            tmpNode.next = rightNode.next;
                            break;
                        }
                        tmpNode = tmpNode.next;
                    }
                    int deleteIndex = findKeyInWhichLeafNode.parent.childrenNodes.indexOf(findKeyInWhichLeafNode);
                    findKeyInWhichLeafNode.parent.entries.remove(deleteIndex);
                    findKeyInWhichLeafNode.parent.childrenNodes.remove(rightNode);
                    rotation(findKeyInWhichLeafNode.parent);
                }
            }

        }
    }


    private BPTNode getLeftSibling(BPTNode node){
        if(node.parent != null && node.parent.childrenNodes.indexOf(node) > 0 )
            return node.parent.childrenNodes.get(node.parent.childrenNodes.indexOf(node) - 1);
        else
            return null;
    }
    private BPTNode getRightSibiling(BPTNode node){
        if(node.parent != null && node.parent.childrenNodes.indexOf(node) < node.parent.childrenNodes.size() - 1 )
            return node.parent.childrenNodes.get(node.parent.childrenNodes.indexOf(node) + 1);
        else
            return null;
    }


    private void rotation(BPTNode innerNode){ //b=5 children>=3 减完

        if(innerNode == root){
            return;
        }

        int min = (b+1)/2 -1;  //b=5  2
        if(innerNode.parent == null) return;
        if(innerNode.entries.size() >= min){
            return;
        }else {
            BPTNode leftnode = getLeftSibling(innerNode);
            BPTNode rightnode = getRightSibiling(innerNode);
            if(leftnode != null && leftnode.entries.size() >= min+1){//向右转
                int index = leftnode.parent.childrenNodes.indexOf(leftnode);
                Map.Entry<String ,String > oup = leftnode.parent.entries.get(index);//上面的要转到右边
                Map.Entry<String ,String > odown = leftnode.entries.get(leftnode.entries.size()-1);
                leftnode.parent.entries.set(index,odown);
                leftnode.entries.remove(leftnode.entries.size()-1);
                innerNode.entries.add(0,oup);
                innerNode.childrenNodes.add(0,leftnode.childrenNodes.get(leftnode.childrenNodes.size()-1));
                leftnode.childrenNodes.get(leftnode.childrenNodes.size()-1).parent = innerNode;
                leftnode.childrenNodes.remove(leftnode.childrenNodes.size()-1);
            }else if(rightnode != null && rightnode.entries.size() >= min+1 ){
                int index = innerNode.parent.childrenNodes.indexOf(innerNode);
                Map.Entry<String ,String > oup = innerNode.parent.entries.get(index);
                Map.Entry<String ,String > odown = rightnode.entries.get(0);
                innerNode.parent.entries.set(index,odown);
                rightnode.entries.remove(0);
                innerNode.entries.add(oup);
                innerNode.childrenNodes.add(rightnode.childrenNodes.get(0));
                rightnode.childrenNodes.get(0).parent = innerNode;
                rightnode.childrenNodes.remove(0);
            }else if(leftnode != null){
                int index = leftnode.parent.childrenNodes.indexOf(leftnode);
                Map.Entry<String ,String > oup = leftnode.parent.entries.get(index);
                leftnode.entries.add(oup);
                leftnode.parent.entries.remove(index);
                for(int i = 0; i < innerNode.entries.size(); i++){
                    leftnode.entries.add(innerNode.entries.get(i));
                }
                for(int i = 0; i < innerNode.childrenNodes.size(); i++){
                    leftnode.childrenNodes.add(innerNode.childrenNodes.get(i));
                    innerNode.childrenNodes.get(i).parent = leftnode;
                }
                leftnode.parent.childrenNodes.remove(innerNode);
                innerNode.parent = null;

                if(leftnode.parent == root && leftnode.parent.entries.size() == 0){
                    root = leftnode;
                    return;
                }else if(leftnode.parent.entries.size() == 0){
                    leftnode.parent = leftnode.parent.parent;
                    leftnode.parent.parent.childrenNodes.set(leftnode.parent.parent.childrenNodes.indexOf(leftnode.parent),leftnode);
                    leftnode.parent.parent.childrenNodes.remove(leftnode.parent);
                }
                innerNode = leftnode.parent;
                rotation(innerNode);
            }else {
                int index = innerNode.parent.childrenNodes.indexOf(innerNode);
                Map.Entry<String ,String > oup = innerNode.parent.entries.get(index);//////////////////////////////////////////
                innerNode.entries.add(oup);
                innerNode.parent.entries.remove(index);
                for(int i = 0; i < rightnode.entries.size(); i++){
                    innerNode.entries.add(rightnode.entries.get(i));
                }
                for(int i = 0; i < rightnode.childrenNodes.size(); i++){
                    innerNode.childrenNodes.add(rightnode.childrenNodes.get(i));
                    rightnode.childrenNodes.get(i).parent = innerNode;
                }
                innerNode.parent.childrenNodes.remove(rightnode);
                rightnode.parent = null;
                if(innerNode.parent == root && innerNode.parent.entries.size() == 0){ //不一定……对
                    root = innerNode;
                    return;
                }else if(innerNode.parent.entries.size() == 0){
                    innerNode.parent = innerNode.parent.parent;  //不一定对
                    innerNode.parent.parent.childrenNodes.set(innerNode.parent.parent.childrenNodes.indexOf(innerNode.parent),innerNode);
                    innerNode.parent.parent.childrenNodes.remove(innerNode.parent);
                }
                innerNode = innerNode.parent;
                rotation(innerNode);
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
