package fr.steve.fresh_api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    public User create(User user) {
        if (this.repository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        return this.repository.save(user);
    }

    public User update(Long id, User user) {
        User target = this.repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        target.setFirstname(user.getFirstname());
        target.setName(user.getName());
        target.setEmail(user.getEmail());
        target.setRole(user.getRole());

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
