package com.Prokeep.Prokeep.assessment1.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;



@Service
public class MessageService {

    //Async allows the controller to finish its execution leaving this method in a new thread
    @Async("executor")
    //synchronized so that the threads access this method concurrently
    public synchronized Long processMessage(String message) throws InterruptedException {
        //Makes sure to wait 1 second after predecessor finishes. If first thread, wasted second.
        Thread.sleep(1000);
        System.out.println(message);

        //Return value for testing purposes only
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }


}
