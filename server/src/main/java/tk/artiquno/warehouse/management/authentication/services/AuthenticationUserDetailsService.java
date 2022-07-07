package tk.artiquno.warehouse.management.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.artiquno.warehouse.management.authentication.entities.User;
import tk.artiquno.warehouse.management.authentication.mappers.UserDetailsMapper;

@Service
public class AuthenticationUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsMapper userDetailsMapper;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userService.getActiveUserByUsername(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("The given username does not exist");
        }

        return userDetailsMapper.toUserDetails(user);
    }
}
