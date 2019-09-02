package com.learning.project.gfq.pvOperation.pipeExample;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.Arrays;

public class PipeRead extends Thread {
	
	PipedInputStream pis;
	
	public PipeRead(PipedInputStream pis) {
		this.pis = pis;
	}
	
	@Override
	public void run() {
		try {
            while(true)
            {
	            byte[] b = new byte[3];
	            pis.read(b,0, b.length);
	            System.out.println("consumer1:" + Arrays.toString(b));
	            sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
	}

}
