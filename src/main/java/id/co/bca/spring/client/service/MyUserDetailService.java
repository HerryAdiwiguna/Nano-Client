package id.co.bca.spring.client.service;
import id.co.bca.spring.client.model.MyUserDetail;
import id.co.bca.spring.client.model.UserModel;
import id.co.bca.spring.client.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserModel userModel = userRepo.findByUsername(username);
        if (userModel == null){
            throw new UsernameNotFoundException("No User found!");
        }
        return new MyUserDetail(userModel);
    }
}
