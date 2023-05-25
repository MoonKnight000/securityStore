package uz.softex.securitystore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.softex.securitystore.workers.dto.UserDetailsServiceImplement;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider provider;
    @Autowired
    UserDetailsServiceImplement authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            System.out.println(authorization);
            authorization = authorization.substring(7);
            if (provider.validateToken(authorization)) {
                String userNameFromToken = provider.getUserNameFromToken(authorization);

                UserDetails userDetails = authService.loadUserByUsername(userNameFromToken);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                System.out.println(SecurityContextHolder.getContext().getAuthentication());
                SecurityContextHolder.getContext().setAuthentication(token);
                System.out.println(SecurityContextHolder.getContext().getAuthentication());
            }
        }
        filterChain.doFilter(request, response);
    }
}
