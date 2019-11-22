package com.learning.project.ld.thread.ProducerConsumer;

/**
 * @author lvdong
 * date 2019-11-22
 */
public class Test {
    public static void main(String[] args){
        Producer producer = new Producer();
        for(int i = 0;i<50;i++){
            Cartridge cartridge = new Cartridge();
            producer.push(cartridge);
            System.out.println(producer.cartridgeGroup.size());
        }

        Consumer consumer = new Consumer(producer);
        System.out.println(consumer.producer.cartridgeGroup.size());
        for(int i = 0;i<consumer.producer.cartridgeGroup.size();i++){
            System.out.println("i获取次数："+i);
            consumer.pop();
        }
    }
}
