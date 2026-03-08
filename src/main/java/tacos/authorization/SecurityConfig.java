package tacos.authorization;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.
              HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.
              EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.authorization.users.UserRepository;
import tacos.authorization.users.User;

@EnableWebSecurity
public class SecurityConfig {

 @Bean
 SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
  return http
   .authorizeRequests(authorizeRequests ->
    authorizeRequests.anyRequest().authenticated()
   )
   .formLogin()
   .and()
   .build();
 }

 @Bean
 public PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder();
 }

 @Bean
 public UserDetailsService userDetailsService(UserRepository userRepo) {
  return username -> {
   User user = userRepo.findByUsername(username);
   if (user != null) {
    return user;
   }
   throw new UsernameNotFoundException("User '" + username + "' not found");
  };
 }
}