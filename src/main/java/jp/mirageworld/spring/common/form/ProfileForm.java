package jp.mirageworld.spring.common.form;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProfileForm {
    String username;
    String email;
    String password;    
}
