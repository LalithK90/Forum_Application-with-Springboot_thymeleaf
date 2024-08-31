package cyou.chat_app.common.controller;


import cyou.chat_app.common.dto.MessageDTO;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Controller
@AllArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat.private.{username}")
    public void sendPrivateMessage(@DestinationVariable String username, MessageDTO message) {
        messagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
    }

    @MessageMapping("/chat.group")
    public void sendGroupMessage(MessageDTO message) {
        messagingTemplate.convertAndSend("/topic/group", message);
    }


    @GetMapping("/chat")
    public String message(Model model) throws Exception {

        final String secretKey = "N7MtcRGcI1HvPPf8zIstGtkoGEFeiMTeT2YRhK6qAn8=";
        // Add the key to the model, which will be passed to the Thymeleaf template
        model.addAttribute("secretKey", secretKey);
        model.addAttribute("currentUser", SecurityContextHolder.getContext().getAuthentication().getName());
        return "message/message";
    }

    private String generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // 256-bit key for strong encryption
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}

