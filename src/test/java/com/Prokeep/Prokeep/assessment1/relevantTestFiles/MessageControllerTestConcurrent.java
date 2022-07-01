package com.Prokeep.Prokeep.assessment1.relevantTestFiles;

import com.Prokeep.Prokeep.assessment1.service.MessageService;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;



/*
    This tests the edge case in which there are simultaneous concurrent calls to the processMessage method. After every message is processed its time is returned and
    saved to the times array. Note that since the JUnit test finishes before the thread, Asnyc was turned off. I ran this
    test directly on the method because I did not want to change the controller too much just for a test.
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "non-async")
public class MessageControllerTestConcurrent {

    @Autowired
    private MessageService messageService;

    @Rule
    public ConcurrentRule concurrently = new ConcurrentRule();
    @Rule
    public RepeatingRule rule = new RepeatingRule();

    public static final List<Long> times = new ArrayList<>();

    //The print statement can be used to verify that the calls are indeed happening at the same time

    @Test
    @Concurrent(count = 3)
    @Repeating(repetition = 2)
    public void runsConcurrentTimes() throws InterruptedException {
        //System.out.println(System.currentTimeMillis());
        String message = "hello";
        Long time = messageService.processMessage(message);
        times.add(time);
    }

    //Asserts that all messages are at least one second apart
    @AfterClass
    public static void testTimes() {
        for(int i = 0; i < times.size() - 1; i++) {
            long t1 = times.get(i);
            long t2 = times.get(i + 1);
            //System.out.println(t2 + " " + t1);
            Assert.assertTrue(1 <= t2 - t1);
        }
    }

}
