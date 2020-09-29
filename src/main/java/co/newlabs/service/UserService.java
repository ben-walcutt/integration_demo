package co.newlabs.service;

import co.newlabs.dto.UserDTO;
import co.newlabs.repository.user.UserEntity;
import co.newlabs.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;
    private MapperFacade mapper;
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO getUserByEmail(String email) {
        UserDTO user =  mapper.map(repository.getUserByEmail(email), UserDTO.class);
        user.setRole(repository.getRole(email));
        return user;
    }

    public void saveUser(UserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.saveUser(mapper.map(user, UserEntity.class));
    }
}
