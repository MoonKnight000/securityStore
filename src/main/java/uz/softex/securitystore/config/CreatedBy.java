package uz.softex.securitystore.config;


import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.softex.securitystore.workers.entity.Workers;

import java.util.Optional;

public class CreatedBy implements AuditorAware<Workers> {
    @Override
    public Optional<Workers> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            Workers user = (Workers) authentication.getPrincipal();
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
