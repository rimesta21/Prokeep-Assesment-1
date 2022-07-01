package com.Prokeep.Prokeep.assessment1.controller;

import com.Prokeep.Prokeep.assessment1.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    //Map that stores queues and messages
    private final Map<String, String> map;

    public MessageController() {
        map = new HashMap<>();
    }


    @GetMapping("/receive-message")
    public ResponseEntity<String> processMessage(@RequestParam String queue, @RequestParam(required = false) String message) throws InterruptedException {
        if(map.containsKey(queue)) {
            messageService.processMessage(map.get(queue));
        } else {
            if(message == null) {
                return new ResponseEntity<>("No message attached to this new queue.", HttpStatus.BAD_REQUEST);
            }
            map.put(queue, message);
            messageService.processMessage(message);
        }

        return new ResponseEntity<>("Message will be processed.", HttpStatus.OK);
    }

}
