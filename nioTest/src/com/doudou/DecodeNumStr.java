package com.doudou;


import java.util.*;

/**
 * 此处尝试用二叉树实现如下问题：
 * 1-26对应a-z ，给一个数字串，不改变顺序，返回有多少种可能的解码。
 * 例 123： abc， lc ，aw 三种
 */
public class DecodeNumStr {

    public static Map<String,String> map = new HashMap();//存放数字 字母 映射表
    public static List<BinaryTree> list = new ArrayList<>();//存放叶子节点的列表
    public static void main(String[] args) {
        initMap();
        String numStr = "123456";
        System.out.println("输入的数字串为：" + numStr);
        char[] chars = numStr.toCharArray();
        //将数字串转化为二叉树 左子树一个数 右子树两个数
        BinaryTree bTree = new BinaryTree();
        bTree.setIndex(-1);//根节点
        buildAllTree(bTree, chars);
        //从叶子节点倒序遍历
        list.forEach(tree ->{
            String path = getPath(tree);
            System.out.println(new StringBuilder(path).reverse().toString());
        });


    }
    //倒序遍历
    private static String getPath(BinaryTree tree){
        BinaryTree parent = tree.getParent();
        if(parent != null){
            return tree.getCode()+getPath(parent);
        }else{
            return tree.getCode();
        }
    }

    //使用递归
    private static void buildAllTree(BinaryTree tree, char[] chars){
        //如果是叶子节点
        if(tree.getIndex()+1 == chars.length){
            list.add(tree);
        }else{
            //取数组里面的数字
            char num = chars[tree.getIndex()+1];
            //不是最后一个
            if(tree.getIndex()+2<chars.length){
                String key = String.valueOf(num)+chars[tree.getIndex()+2];
                if("00".equals(key)){//考虑00的情况
                    tree.setIndex(tree.getIndex()+2);
                    buildAllTree(tree, chars);
                    return;
                }
            }
            //创建左子树
            BinaryTree leftTree = new BinaryTree();
            leftTree.setParent(tree);
            leftTree.setIndex(tree.getIndex()+1);
            leftTree.setCode(map.get(chars[tree.getIndex()+1] + ""));
            buildAllTree(leftTree, chars);

            //创建右子树
            if(tree.getIndex()+2 < chars.length){
                String key = String.valueOf(chars[tree.getIndex()+1]) + chars[tree.getIndex()+2];
                if(0< Integer.parseInt(key) && Integer.parseInt(key) <= 26){
                    BinaryTree rightTree = new BinaryTree();
                    rightTree.setParent(tree);
                    rightTree.setIndex(tree.getIndex()+2);
                    rightTree.setCode(map.get(key));
                    buildAllTree(rightTree, chars);
                }
            }
        }


    }

    /**
     * 初始化映射关系表
     */
    public static void initMap(){
        String wordStr = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
        String[] words = wordStr.split(",");
        for (int i = 0; i < words.length; i++) {
            map.put(String.valueOf(i+1),words[i]);
        }
    }
}
