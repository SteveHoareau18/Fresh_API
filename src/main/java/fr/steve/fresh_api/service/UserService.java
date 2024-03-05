package fr.steve.fresh_api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.steve.fresh_api.dto.CreateUserDto;
import fr.steve.fresh_api.exception.UserAlreadyExistsException;
import fr.steve.fresh_api.exception.UserNotFoundException;
import fr.steve.fresh_api.model.entity.User;
import fr.steve.fresh_api.model.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public Iterable<User> getAll() {
        return this.repository.findAll();
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
                .build();
        return this.repository.save(user);
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
