package com.learning.project.gfq.privateNote;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DrawPic extends JFrame {
    private int[] area;
    private int[] waterArray;
    private int height;
    private int width;

    protected DrawPic(int[] area, int[] waterArray, int height) {
        super();
        this.area = area;
        this.waterArray = waterArray;
        this.width = (int)(area.length + 2 * area.length);
        this.height = (int)(height + height * 2);
        setTitle("Draw Pic");
        setBounds(0, 0, this.width, this.height);//x,y是窗口显示在屏幕上的位置，width，height是窗口的宽高
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        //绘制背景
        g2.setColor(Color.WHITE);//绘制白色背景
        g2.fillRect(0, 0, this.width, this.height);//绘制矩形图



        for (int i = 0; i < area.length; i++) {//绘制柱形图
            //绘制矩形
            g2.setColor(Color.PINK);
			g2.drawRect(2*i, this.height - area[i], 2, area[i]);
			g2.setColor(Color.blue);
            g2.fillRect(2*i, this.height - area[i] - waterArray[i], 2, waterArray[i]);

        }
    }


    public static void main(String[] args) {
        int[] area = {10,30,10,100,10,30,11};
        int[] waterArray = {0,0,20,0,20,0,0};
        DrawPic drawPic = new DrawPic(area, waterArray,100);
        drawPic.setVisible(true);
    }
}
