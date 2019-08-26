package com.learning.project.gfq.pvOperation.pipeExample;

import java.io.PipedOutputStream;

public class PipeWrite extends Thread {
	
	PipedOutputStream pos;
	
	public PipeWrite(PipedOutputStream pos) {
		this.pos = pos;
	}
	
	@Override
	public void run() {
		int i = 0;
        try {
            while(true)
            {
//            this.sleep(3000);
            System.out.println("writer1:" + i);
            pos.write(++i);
            pos.write(++i);
            pos.write(++i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

}
