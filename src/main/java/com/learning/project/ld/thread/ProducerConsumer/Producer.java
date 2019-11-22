package com.learning.project.ld.thread.ProducerConsumer;

import java.util.LinkedList;
import java.util.List;

/**
 * 采用多线程技术，例如wait/notify，设计实现一个符合生产者和消费者问题的程序，对某一个对象（枪膛）进行操作，
 * 其最大容量是20颗子弹，生产者线程是一个压入线程，它不断向枪膛中压入子弹，消费者线程是一个射出线程，它不断从枪膛中射出子弹。
 * @author lvdong
 * date 2019-11-22
 */
public class Producer {
    List<Cartridge> cartridgeGroup = new LinkedList<Cartridge>();
    public void push(Cartridge cartridge){
        if(cartridgeGroup.size()>=10){
            try {
                synchronized (this){
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            synchronized (cartridgeGroup){
                cartridgeGroup.add(cartridge);
            }

        }
    }
}
