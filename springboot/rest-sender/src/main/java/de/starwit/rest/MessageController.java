package de.starwit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${rest.base-path}/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Operation(summary = "Forward text message")
    @PostMapping
    public ResponseEntity<Boolean> forwardMessage(@RequestBody String message) {
        var result = messageService.forwardMessage(message);
        if(result) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    } 
    
    @Operation(summary = "test service")
    @GetMapping
    public String testMessage() {
        return "Hello World";
    }
}
