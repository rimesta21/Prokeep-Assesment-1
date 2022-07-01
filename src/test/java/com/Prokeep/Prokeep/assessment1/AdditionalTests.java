package com.Prokeep.Prokeep.assessment1;

import com.Prokeep.Prokeep.assessment1.controller.MessageController;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/*
************* Additional tests for my sanity but left here if curious *******************
*/


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdditionalTests {

    @Autowired
    private MessageController messageController;

    private final PrintStream standardOut = System.out;
    private ByteArrayOutputStream out;

    @BeforeEach
    public void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    /*
    Test that messages are printed in order and that the app saves the queues with the correct message. Note that Async is turned on.
     */
    @Test
    public void messagesProcessedInOrderTest() throws InterruptedException {
        //Arbitrary number
        for(int i = 0; i < 25; i++) {
            //not necessary for first run through but cleans out the print stream for successive tests
            setUp();
            messageProcessor();
            //Gives the threads enough time to finish
            Thread.sleep(15000);
            String expected = "message1\r\nmessage2\r\nmessage2\r\nmessage1\r\nmessage2\r\nmessage2\r\nmessage1\r\nmessage3\r\nmessage2\r\nmessage3\r\nmessage1\r\n";
            Assertions.assertEquals(expected, out.toString());
        }
    }

    private void messageProcessor() throws InterruptedException {
        String queue1 = "test1";
        String message1 = "message1";
        String queue2 = "test2";
        String message2 = "message2";
        String queue3 = "test3";
        String message3 = "message3";
        messageController.processMessage(queue1, message1);
        messageController.processMessage(queue2, message2);
        messageController.processMessage(queue2, null);
        messageController.processMessage(queue1, null);
        messageController.processMessage(queue2, null);
        messageController.processMessage(queue2, null);
        messageController.processMessage(queue1, null);
        messageController.processMessage(queue3, message3);
        messageController.processMessage(queue2, null);
        messageController.processMessage(queue3, null);
        messageController.processMessage(queue1, null);
    }


    //Test that the controller and the message service are asynchronous
    @Test
    public void statusCodeBeforeMessageProcessedTest() throws InterruptedException {
        ResponseEntity<String> re1 = messageController.processMessage("test1", "message1");
        ResponseEntity<String> re2 = messageController.processMessage("test2", "message2");
        Assertions.assertEquals(200, re1.getStatusCodeValue());
        Assertions.assertEquals(200, re2.getStatusCodeValue());
        //No output means status code is returned before messages are still being processed
        Assertions.assertNull(out);
    }

}
