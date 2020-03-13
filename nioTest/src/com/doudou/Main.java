package com.doudou;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static List<Item> list = new ArrayList<>();
    public static Map<String, String> map = new HashMap<>();
    /**
     * @Title: main
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param args    参数
     * @return void    返回类型
     * @throws
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String input = "123456";
        char arr[] = input.toCharArray();
        initMap();
        //首先去创建这个二叉树
        //1.定义一个根节点
        Item root = new Item();
        root.setIndex(-1);
        createChileItem(root, arr);
        //看这个二叉树，从根结点到每一个叶子节点的经过的值，其实就是每一个解
        System.out.println(input);
        for(Item item: list){
            String path = getPath(item);
            //因为是从叶子节点向上找，所以要取一下反
            path = new StringBuilder(path).reverse().toString();
            System.out.println(path);
        }
    }

    public static String getPath(Item item){
        if(item.getParent() != null){
            return item.getCode() + getPath(item.getParent());
        }else{
            return item.getCode();
        }
    }

    /**
     *
     * @Title: createChileItem
     * @Description: 为每一个节点创建左右子节点
     * @param @param item
     * @param @param arr    参数
     * @return void    返回类型
     * @throws
     */
    public static void createChileItem(Item item, char[] arr){
        //表示这个节点已经是最后一个了，判断为叶子节点
        if(item.getIndex() == arr.length - 1){
            //把叶子节点放进list里头去
            list.add(item);
        }else{//表示这个节点不是最后一个
            char next1 = arr[item.getIndex() + 1];
            if(item.getIndex() + 2 < arr.length){
                char next2 = arr[item.getIndex() + 2];
                String key = String.valueOf(next1) + next2;
                if(key.equals("00")){
                    item.setIndex(item.getIndex() + 2);
                    createChileItem(item, arr);
                    return;
                }
            }

            //先创建左子节点(走一步)

            Item leftItem = new Item();
            leftItem.setIndex(item.getIndex() + 1);
            leftItem.setParent(item);
            leftItem.setKey(String.valueOf(next1));
            //左子节点继续创建子节点
            createChileItem(leftItem, arr);
            //然后创建右子节点,需要判断一下，走两步获得的值满足<=26
            //保证走两步是可以的
            if(item.getIndex() + 2 < arr.length){
                char next2 = arr[item.getIndex() + 2];
                String key = String.valueOf(next1) + next2;
                if(0 < Integer.parseInt(key) && Integer.parseInt(key) <= 26){
                    Item rightItem = new Item();
                    rightItem.setIndex(item.getIndex() + 2);
                    rightItem.setParent(item);
                    rightItem.setKey(Integer.parseInt(key) + "");
                    //然后给右子节点继续创建子节点
                    createChileItem(rightItem, arr);
                }
            }
        }
    }


    /**
     * 初始化密码对应map
     */
    public static void initMap(){
        String t = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
        String arr[] = t.split(",");
        for(int i = 0; i < 26; i++){
            map.put("" + (i + 1), arr[i]);
        }
    }
}

