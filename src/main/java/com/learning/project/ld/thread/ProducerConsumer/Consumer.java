package com.learning.project.ld.thread.ProducerConsumer;

/**
 * 消费者
 * @author lvdong
 * date 2019-11-22
 */
public class Consumer {
    Producer producer;
    public Consumer(Producer producer){
        this.producer = producer;
    }
    public void pop(){
        if(producer.cartridgeGroup.size()>0){
            synchronized (producer.cartridgeGroup){
                producer.cartridgeGroup.remove(0);
            }
        }else{
            producer.notify();
        }
    }
}
