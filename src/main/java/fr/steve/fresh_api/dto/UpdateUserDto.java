package fr.steve.fresh_api.dto;

import fr.steve.fresh_api.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

    @Size(max = 50)
    private String firstname;

    @Size(max = 50)
    private String name;

    @Email
    private String email;

    private Role role;
}
