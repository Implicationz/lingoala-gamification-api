package com.lingosphinx.gamification.service;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Profile("test")
public class MockUserService implements UserService {

    private final UUID mockUserId = UUID.randomUUID();

    @Override
    public UUID getCurrentUserId() {
        return mockUserId;
    }
}