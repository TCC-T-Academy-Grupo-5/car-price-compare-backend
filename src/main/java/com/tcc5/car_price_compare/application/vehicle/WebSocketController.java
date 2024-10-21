package com.tcc5.car_price_compare.application.vehicle;

import com.tcc5.car_price_compare.domain.response.user.NotificationResponseDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/notify")
    @SendTo("/topic/notification")
    public NotificationResponseDTO sendNotification(@Payload NotificationResponseDTO notificationResponseDTO){
        return notificationResponseDTO;
    }
}
