package com.tarun.movieReviewApp.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private long id;
    @NotEmpty
    @Size(min = 4 ,message = "UserName must be min of 4 characters!!")
    private String name;
    @Email(message = "Email Address is not valid!!")
    private String email;
    @NotEmpty
    @Size(min = 3,max = 10,message = "Password must be min 3 characters and max 10 characters!!")
    private String password;
}
