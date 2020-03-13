package com.doudou;

class Item {
    //指向父节点
    private Item parent;

    //该节点的字符串的值
    private String key;
    //转义后的值
    private String code;
    //这个节点在字符串中的位置
    //如1在123中index=0,12在123中index=1
    private int index;

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public Item getParent() {
        return parent;
    }
    public void setParent(Item parent) {
        this.parent = parent;
    }
    public String getKey() {
        System.out.println(key);
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getCode() {
        if(index == -1 || !Main.map.containsKey(key)){
            return "";
        }
        return Main.map.get(key);
    }
    public void setCode(String code) {
        this.code = code;
    }


}