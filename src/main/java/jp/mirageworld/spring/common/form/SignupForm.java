package jp.mirageworld.spring.common.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignupForm {

    @NotNull
    @NotBlank
    @Size(min = 8, max = 32)
    @Pattern(regexp = "^[\\w]+$")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 255)
    @Email
    private String email;

    // 半角英数記号を許容する。
    @NotNull
    @NotBlank
    @Size(min = 8, max = 32)
    @Pattern(regexp = "^[\\x21-\\x7e\\s]+$")
    private String password;
}
