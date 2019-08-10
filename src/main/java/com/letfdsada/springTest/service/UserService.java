package com.letfdsada.springTest.service;

import com.letfdsada.springTest.domain.Role;
import com.letfdsada.springTest.domain.User;
import com.letfdsada.springTest.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    private final MailSender mailSender;


    public UserService(UserRepo userRepo, MailSender mailSender) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByUsername(s);
    }

    public boolean addUser(User user){
        User loadedUser = userRepo.findByUsername(user.getUsername());

        if(loadedUser != null) return false;

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());


        userRepo.save(user);

        sendMessage(user);

        return true;
    }

    private void sendMessage(User user){
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s.\nWelcome to springTest!\n" +
                            "Please visit this link: " +
                            "http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if(user == null) return false;

        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();
        //отображается в значение роли
        user.setRoles(form.keySet().stream() //поток данных из множества ключей формы
                .filter(roles::contains)    //фильтруется условием содержания в roles
                .map(Role::valueOf)     //отображается в роль
                .collect(Collectors.toSet())); //собирается в множество

        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = !Objects.equals(email, userEmail);

        if(isEmailChanged){
            user.setEmail(email);

            if(!StringUtils.isEmpty(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if(!StringUtils.isEmpty(password)){
            user.setPassword(password);
        }
        userRepo.save(user);

        if(isEmailChanged) {
            sendMessage(user);
        }
    }
}
