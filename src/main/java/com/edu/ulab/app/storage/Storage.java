package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Setter(AccessLevel.NONE)
@Component
public class Storage {
    public static long userIdCounter = 1;
    public static long bookIdCounter = 1;

    private Map<Long, UserEntity> userStorage = new HashMap<>();
    private Map<Long, BookEntity> bookStorage = new HashMap<>();
}
