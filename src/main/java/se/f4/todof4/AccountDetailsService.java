package se.f4.todof4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import se.f4.todof4.entity.User;
import se.f4.todof4.repository.UserRepository;

public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = repository.findByEmail(email);

            if(user ==null) {
                throw new UsernameNotFoundException("User Not Found");
            }
        return new AccountDetails(user);
    }
}