//package community.community.Service;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("username: " + username);
//        UserDetails userDetails = User.withUsername("matianyang").password("mtyzzhlwj950603").build();
//        return (UserDetails) new InMemoryUserDetailsManager(userDetails);
//    }
//}
