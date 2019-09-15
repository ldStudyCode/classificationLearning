package com.learning.project.ld.designPattern.ModelMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 模板方法设计模式
 * TransactionTemplate
 * spring 中模板方法的应用https://www.cnblogs.com/soundcode/p/6478512.html
 */
public class ModelMethodFactory {
    public static void main(String args[]) throws ServletException, IOException {
        MyServlet ms = new MyServlet();
        ms.doPost(null,null);
        Game game=new RetroSnaker();
        game.play();
        game = new PlaneWars();
        game.play();
    }

}
class MyServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
    }
}
abstract class Game{
    abstract void initialize();
    abstract void startPlay();
    abstract void endPlay();
    //模板方法
    public final void play(){
        initialize();
        startPlay();
        endPlay();
    }
}
/**
 * 贪吃蛇
 */
class RetroSnaker extends Game{
    @Override
    void initialize() {
        System.out.println("RetroSnaker initialize");
    }

    @Override
    void startPlay() {
        System.out.println("RetroSnaker startPlay");
    }

    @Override
    void endPlay() {
        System.out.println("RetroSnaker endPlay");
    }
}

/**
 * 飞机大战
 */
class PlaneWars extends Game{
    @Override
    void initialize() {
        System.out.println("PlaneWars initialize");
    }

    @Override
    void startPlay() {
        System.out.println("PlaneWars startPlay");
    }

    @Override
    void endPlay() {
        System.out.println("PlaneWars endPlay");
    }
}
