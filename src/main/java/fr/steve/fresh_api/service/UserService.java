package fr.steve.fresh_api.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.steve.fresh_api.dto.CreateUserDto;
import fr.steve.fresh_api.dto.UpdateUserDto;
import fr.steve.fresh_api.enums.Role;
import fr.steve.fresh_api.exception.UserAlreadyExistsException;
import fr.steve.fresh_api.exception.UserNotFoundException;
import fr.steve.fresh_api.model.entity.User;
import fr.steve.fresh_api.model.repository.UserRepository;
import fr.steve.fresh_api.util.ListUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public List<User> getAll() {
        return ListUtils.iteratorToList(this.repository.findAll());
    }

    public User create(CreateUserDto dto) {
        if (this.repository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException(dto.getEmail());
        }

        User user = User.builder()
                .firstname(dto.getFirstname())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(Role.USER)
                .build();

        return this.repository.save(user);
    }

    public User update(Long id, UpdateUserDto dto) {
        User target = this.repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (dto.getFirstname() != null) {
            target.setFirstname(dto.getFirstname());
        }
        if (dto.getName() != null) {
            target.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            target.setEmail(dto.getEmail());
        }
        if (dto.getRole() != null) {
            target.setRole(dto.getRole());
        }
        return this.repository.save(target);
    }

    public User get(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void delete(Long id) {
        this.repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        this.repository.deleteById(id);
    }

    public User save(User user) {
        return this.repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Vous n'êtes pas enregistré."));
    }

}
