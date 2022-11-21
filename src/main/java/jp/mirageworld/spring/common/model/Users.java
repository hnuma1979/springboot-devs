package jp.mirageworld.spring.common.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Users {
    private Integer id;

    private String username;

    private String email;

    private String password;

    private boolean enabled;

    private boolean locked;

    private LocalDateTime created;

    private LocalDateTime updated;

    private LocalDate deleted;

    private String hash;

    private LocalDateTime hashExp;
}