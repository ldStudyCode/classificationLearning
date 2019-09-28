package com.learning.project.gfq.privateNote;

import javax.swing.*;
import java.util.Random;
import java.awt.*;

public class Solution {

    public static void main(String[] args) {
        JFrame jf = new JFrame();//相框
        JPanel jp = new JPanel() {//画布
            //序列号（可省略）
            private static final long serialVersionUID = 1L;
            // 重写paint方法
            @Override
            public void paint(Graphics graphics) {
                // 必须先调用父类的paint方法
                super.paint(graphics);
                // 用画笔Graphics，在画板JPanel上画一个小人
                graphics.drawOval(100, 70, 30, 30);// 头部（画圆形）
                graphics.drawRect(105, 100, 20, 30);// 身体（画矩形）
                graphics.drawLine(105, 100, 75, 120);// 左臂（画直线）
                graphics.drawLine(125, 100, 150, 120);// 右臂（画直线）
                graphics.drawLine(105, 130, 75, 150);// 左腿（画直线）
                graphics.drawLine(125, 130, 150, 150);// 右腿（画直线）
            }
        };
        jf.add(jp);
        jf.setSize(300, 300);
        jf.setVisible(true);

    }


//    public static int[] randomIntArray(int len, int high) {
//        int[] area = new int[len];
//        Random rad = new Random();
//        for(int i = 0; i < len; ++i)
//            area[i] = rad.nextInt(high);
//        return area;
//    }
}



