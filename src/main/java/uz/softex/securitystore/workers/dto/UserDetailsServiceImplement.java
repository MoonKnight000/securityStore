package uz.softex.securitystore.workers.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.softex.securitystore.workers.entity.Workers;
import uz.softex.securitystore.workers.repository.WorkersRepository;

import java.util.Optional;
@Service
public class UserDetailsServiceImplement implements UserDetailsService {
    @Autowired
    WorkersRepository workersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Workers> byUsername = workersRepository.findByUsername(username);
        if (byUsername.isPresent()) return byUsername.get();
        throw new UsernameNotFoundException(username);
    }

}
