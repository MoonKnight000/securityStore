package uz.softex.securitystore.security;


import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import uz.softex.securitystore.position.entity.Position;

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
public class JwtProvider {
    private static final Long expireTime = 36_000_000L;
    private static final String fkey = "BuTokenniMAxfiySuziHEchKimBilmasin1234567890";

    public String generateToken(String username, Position position) {
        Date date = new Date(System.currentTimeMillis() + expireTime);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(date)
//                .claim("position",position.toString())
                .signWith(HS512, fkey)
                .compact();
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(fkey)
                    .parseClaimsJws(token);
//                .getBody().getSubject()//usernameni olasiz
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    public static void main(String[] args) {
//        System.out.println(new JwtProvider().generateToken("lalakuuu"));
//    }

    public String getUserNameFromToken(String token) {
        String subject = Jwts.parser()
                .setSigningKey(fkey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return subject;
    }
}
