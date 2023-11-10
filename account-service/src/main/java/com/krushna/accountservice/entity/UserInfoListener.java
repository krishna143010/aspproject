package com.krushna.accountservice.entity;


import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Component
public class UserInfoListener {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int STRING_LENGTH = 6;
    @PrePersist
    public void prePersist(UserInfo entity) {
        // Generate a random string here
        Date dateTime = generateDateTime();
        String randomString = generateRandomString();
        entity.setAuthenticatioCode(randomString);
        entity.setAuthenticatioCodeExpiry(dateTime);
    }

    private String generateRandomString() {
        // Implement your logic to generate a random string here
        // For example, you can use UUID.randomUUID().toString() to generate a random string
        Random random = new Random();
        StringBuilder randomString = new StringBuilder(STRING_LENGTH);

        for (int i = 0; i < STRING_LENGTH; i++) {
            int index = random.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(index);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }
    public static Date generateDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10); // Add 10 minutes to the current time

        return calendar.getTime();
    }
}