package com.tcc5.car_price_compare.domain.user.converters;

import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.user.dto.NotificationRequestDto;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class NotificationRequestDtoToNotificationConverter implements Converter<NotificationRequestDto, Notification> {
    @Override
    public Notification convert(NotificationRequestDto source) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Notification notification = new Notification();
        notification.setNotificationType(source.notificationType());
        notification.setCurrentFipePrice(source.currentFipePrice());
        notification.setUser(user);
        return notification;
    }
}
