import com.sun.org.apache.regexp.internal.RE;

import java.math.BigInteger;
import java.util.Stack;

public class RBTree {
    private RBTNode root;
    private RBTNode guard = new RBTNode(null,BLACK,null,null,null,null);
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    public int size = 0;

    public class RBTNode{
        boolean color;
        String key;
        String value;
        RBTNode left;
        RBTNode right;
        RBTNode parent;

        public RBTNode(String key,boolean color, RBTNode parent, RBTNode left, RBTNode right,String value) {
            this.key = key;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.value = value;
        }
    }

    private void leftRoatate(RBTNode z){ //左旋,节点是z
        RBTNode zPreRight = z.right;
        z.right = zPreRight.left;//将节点右儿子的左儿子变成节点的右儿子
        if(zPreRight.left != null) zPreRight.left.parent = z;
        zPreRight.parent = z.parent; //将原来节点的右儿子提上来
        if(z.parent == null) this.root = zPreRight;  //改变zPreRight新父亲
        else if (z.parent.left == z) z.parent.left = zPreRight;
        else z.parent.right = zPreRight;
        zPreRight.left = z;
        z.parent = zPreRight;
    }
    private void rightRoatate(RBTNode z){
        RBTNode zPreLeft = z.left;
        z.left = zPreLeft.right;
        if(zPreLeft.right != null) zPreLeft.right.parent = z;
        zPreLeft.parent = z.parent;
        if(z.parent == null) this.root = zPreLeft;
        else if (z.parent.right == z) z.parent.right = zPreLeft;
        else z.parent.left = zPreLeft;
        zPreLeft.right = z;
        z.parent = zPreLeft;
    }
    public void insert(String key, String value){
        RBTNode node = new RBTNode(key,RED,null,null,null,value);
        if(node != null) insert(node);
    }
    private void insert(RBTNode node){
        if(search(node.key) != null) return; //重复就不插入
        size++;
        RBTNode y = null;
        RBTNode x = this.root;
        while (x != null){
            y = x;
            if(node.key.compareTo(x.key) < 0) x = x.left; //小于
            else x = x.right;
        }
        node.parent = y;
        if(y != null){
            if(node.key.compareTo(y.key) < 0) y.left = node;
            else y.right = node;
        }else {
            this.root = node;
        }
        node.color = RED;
        insertFixUp(node);
    }
    private void insertFixUp(RBTNode z){
        RBTNode parent, grandparent;

        Label1:   while (((parent = z.parent) != null) && parent.color){  //父亲也是红的
            grandparent = parent.parent;

            if(parent == grandparent.left){ //父节点是左孩子
                RBTNode uncle = grandparent.right;
                if ((uncle != null) && uncle.color){  //父亲和叔叔都是红的
                    uncle.color = BLACK;
                    parent.color = BLACK;
                    grandparent.color = RED;
                    z = grandparent;
                    continue Label1;
                }
                if(parent.right == z){  //父亲红，叔叔黑，父亲是左儿子，自己是右儿子
                    RBTNode tmp;
                    leftRoatate(parent);
                    tmp = parent;
                    parent = z;
                    z = tmp;
                    continue Label1;
                }
                //父亲红，叔叔黑，自己是左儿子，父亲是左儿子
                parent.color = BLACK;
                grandparent.color = RED;
                rightRoatate(grandparent);
            }else {//父节点是右儿子
                RBTNode uncle = grandparent.left;
                if((uncle != null) && uncle.color){ //父亲红，叔叔红
                    uncle.color = BLACK;
                    parent.color = BLACK;
                    grandparent.color = RED;
                    z = grandparent;
                    continue Label1;
                }
                if(parent.left == z){ //父亲红，叔叔黑，父亲是右儿子，自己是左儿子
                    RBTNode tmp;
                    rightRoatate(parent);
                    tmp = parent;
                    parent = z;
                    z = tmp;
                    continue Label1;
                }
                //父亲红，叔叔黑，父亲是右儿子，自己是右儿子
                parent.color = BLACK;
                grandparent.color = RED;
                leftRoatate(grandparent);

            }
        }

        this.root.color = BLACK;
    }
    public void delete(String key){
        RBTNode node;
        if((node = search(root,key)) != null) delete(node);
    }
    private void delete(RBTNode node){
        RBTNode child, parent;
        boolean color;

        if((node.left != null) && (node.right != null)){
            RBTNode successor = node.right;
            while (successor.left != null){
                successor = successor.left;
            }

            if(node.parent != null){
                if(node.parent.left == node)
                    node.parent.left = successor; //处理了后继上来后和新父亲的关系
                else
                    node.parent.right = successor;
            }else
                this.root = successor;

            child = successor.right; //后继可能有一个右儿子
            parent = successor.parent;
            color = successor.color;
            if(parent == node)
                parent = successor;
            else {
                if(child != null)
                    child.parent = parent; //如果有右儿子就把右儿子接上去
                parent.left = child;
                successor.right = node.right;
                node.right.parent = successor;
            }
            successor.parent = node.parent;
            successor.color = node.color;
            successor.left = node.left;
            node.left.parent = successor;

            if(!color)
                removeFixUp(child,parent);

            node = null;
            return;
        }
        if(node.left != null) child = node.left; //node有左儿子，没有右儿子
        else child = node.right; //node没有左儿子，有or没有右儿子

        parent = node.parent;
        color = node.color;

        if(child != null)
            child.parent = parent;
        if(parent != null){
            if(parent.left == node) parent.left = child;
            else parent.right = child;
        }else {
            this.root = child; //node是根节点
        }

        // if(child == null) child = guard;///////////////////////////////////////////////////////
        if(!color) removeFixUp(child,parent); //child变成了需要调整的节点
        node = null;
        size--;
    }
    private void removeFixUp(RBTNode node, RBTNode parent){
        RBTNode sibling;
        while ((node == null || !node.color) && (node != this.root)){ //节点是黑的且不是根节点
            if(parent.left == node){ //节点是左儿子
                sibling = parent.right;
                if(sibling.color){ //节点是黑的，兄弟节点是红的 case1以parent为轴向左转
                    sibling.color = BLACK;
                    parent.color = RED;
                    leftRoatate(parent);
                    sibling = parent.right;
                }
                if((sibling.left == null || !sibling.left.color) && (sibling.right == null || !sibling.right.color)){
                    //节点是黑的，兄弟黑，两个侄子是黑的 case2
                    sibling.color = RED;
                    node = parent;  //换节点
                    parent = node.parent;
                }else {
                    if(sibling.right == null || !sibling.right.color){//节点黑,节点是左儿子，兄弟黑，右侄子黑，左侄子红 case3
                        sibling.color = RED;
                        sibling.left.color = BLACK;
                        rightRoatate(sibling);
                        sibling = parent.right;
                    }
                    //节点黑，兄弟黑，右侄子红 case4
                    sibling.color = parent.color;
                    parent.color = BLACK;
                    sibling.right.color = BLACK;
                    leftRoatate(parent);
                    node = this.root;//fixup可以终止了，这里设成root是为了后面统一将node变黑
                    break;
                }
            }else { //节点是右儿子
                sibling = parent.left;
                if(sibling.color){
                    sibling.color = BLACK;
                    parent.color = RED;
                    rightRoatate(parent);
                    sibling = parent.left;
                }
                if((sibling.left == null || !sibling.left.color) && (sibling.right == null || !sibling.right.color)){
                    sibling.color = RED;
                    node = parent;
                    parent = node.parent;
                }else {
                    if(sibling.left == null || !sibling.left.color){
                        sibling.right.color = BLACK;
                        sibling.color = RED;
                        leftRoatate(sibling);
                        sibling = parent.left;
                    }
                    sibling.color = parent.color;
                    parent.color = BLACK;
                    sibling.left.color = BLACK;
                    rightRoatate(parent);
                    node = this.root;
                    break;
                }
            }
        }
        if(node != null){
            node.color = BLACK;
        }
    }
    public void preorder_tree_walk(){
        int level = 0;
        System.out.println("================================================================");
        preorder(root,level,0);
        System.out.println("================================================================");
    }
    private void preorder(RBTNode T,int level,int order){
        if(T != null){
            if(T.color == BLACK)
                System.out.println("level="+level+"  child="+order+"  "+ T.key+":"+T.value+"(black)");
            else if(T.color == RED)
                System.out.println("level="+level+"  child="+order+"  "+ T.key+":"+T.value+"(red)");
            level++;
            preorder(T.left,level,0);
            preorder(T.right,level,1);
        }
        if(T == null){
            System.out.println("level="+ level+"  child="+order+"  null"+"(black)");
        }
    }
    public RBTNode search(String key){
        return search(root,key);
    }
    private RBTNode search(RBTNode x, String key){
        while (x != null){
            if(key.compareTo(x.key) < 0) x = x.left;
            else if (key.compareTo(x.key) > 0) x = x.right;
            else return x;
        }
        return x;

    }
}
