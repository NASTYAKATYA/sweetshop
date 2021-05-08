package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.User;
import ru.mirea.work.repositories.IUserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private IUserRepository iur;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(IUserRepository iur) {
        this.iur = iur;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return iur.findByUsername(s);
    }

    public void create(String email, String username, String password) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        u.setEmail(email);
        u.setRole("USER");
        iur.save(u);
    }
}
