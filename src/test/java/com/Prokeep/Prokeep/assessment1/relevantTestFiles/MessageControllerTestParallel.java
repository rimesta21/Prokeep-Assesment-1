package com.Prokeep.Prokeep.assessment1.relevantTestFiles;


import com.Prokeep.Prokeep.assessment1.service.MessageService;
import com.google.code.tempusfugit.concurrency.ConcurrentTestRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;



/*
    This tests the edge case in which there are simultaneous parallel calls to the processMessage method. After every message is processed
     its time is saved and verified after all tests. Note that since the JUnit test finishes before the thread, Asnyc was turned off. I ran this
    test directly on the method because I did not want to change the controller too much just for a test.
*/

@SpringBootTest
@ActiveProfiles(profiles = "non-async")
@RunWith(ConcurrentTestRunner.class)
public class MessageControllerTestParallel {

    private static MessageService messageService;

    @BeforeClass
    public static void setUp() {
        messageService = new MessageService();
    }

    private static Long time1;

    private static Long time2;

    private static Long time3;


    //The print statement can be used to verify that the calls are indeed happening at the same time

    @Test
    public void runInParallel1() throws InterruptedException {
        //System.out.println(System.currentTimeMillis());
        time1 = messageService.processMessage("hello1");
    }

    @Test
    public void runInParallel2() throws InterruptedException {
        //System.out.println(System.currentTimeMillis());
        time2 = messageService.processMessage("hello2");
    }

    @Test
    public void runInParallel3() throws InterruptedException {
        //System.out.println(System.currentTimeMillis());
        time3 = messageService.processMessage("hello3");
    }

    @AfterClass
    public static void cleanUp() {
        if(time1 != null && time2 != null && time3 != null) {
            //System.out.println(time1 + " " + time2 + " " + time3);
            //I check all three combinations because there is no preference since the calls are all simultaneous
            Assertions.assertTrue(1 <= Math.abs(time1 - time2));
            Assertions.assertTrue(1 <= Math.abs(time2 - time3));
            Assertions.assertTrue(1 <= Math.abs(time1 - time3));
        }
    }
}
