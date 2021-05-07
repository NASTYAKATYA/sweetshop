package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.User;
import ru.mirea.work.repositories.IUserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final IUserRepository iur;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u = iur.findByLogin(s);
        return u;
    }
    public void create(String login,String pass) {
        User u = new User();
        u.setLogin(login);
        u.setPassword(bCryptPasswordEncoder.encode(pass));
        iur.save(u);
    }
    public List<User> readAll() {
        return iur.findAll();
    }
}
