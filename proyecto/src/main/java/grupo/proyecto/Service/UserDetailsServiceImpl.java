package grupo.proyecto.Service;

import grupo.proyecto.Repositorys.CredentialsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements
        org.springframework.security.core.userdetails.UserDetailsService {

    private final CredentialsRepository credentialsRepository;

    public UserDetailsServiceImpl(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return credentialsRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado: " + username)
                );
    }
}
