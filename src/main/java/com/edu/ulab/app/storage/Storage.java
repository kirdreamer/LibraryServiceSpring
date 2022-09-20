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
    private static long userIdCounter = 1;
    private static long bookIdCounter = 1;

    private Map<Long, UserEntity> userStorage = new HashMap<>();

    public void saveUserInStorage(UserEntity userEntity) {
        userEntity.setId(userIdCounter);
        getUserStorage().put(
                userIdCounter++,
                userEntity
        );
    }

    private Map<Long, BookEntity> bookStorage = new HashMap<>();

    public void saveBookInStorage(BookEntity bookEntity) {
        bookEntity.setId(bookIdCounter);
        getBookStorage().put(
                bookIdCounter++,
                bookEntity
        );
    }
}
