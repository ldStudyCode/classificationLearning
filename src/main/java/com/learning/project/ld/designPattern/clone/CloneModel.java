package com.learning.project.ld.designPattern.clone;

public class CloneModel implements Cloneable{
    private String name;
    private String desc;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    CloneModel(String name,String desc){
        this.name=name;
        this.desc=desc;
        System.out.println("CloneModel 原型创建成功");}
    public Object clone() throws CloneNotSupportedException {
        System.out.println("CloneModel 原型复制成功");
        return super.clone();
    }
    public static void main(String[] args){
        CloneModel cloneModel1=new CloneModel("张三","a java engineer");
        try {
            CloneModel cloneMode2 = (CloneModel)cloneModel1.clone();

            System.out.println(cloneMode2.getName());
            System.out.println(cloneModel1.getName());
            System.out.println(cloneModel1==cloneMode2);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
