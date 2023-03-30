package bg.softuni.myrealestateproject.config;

import bg.softuni.myrealestateproject.model.enums.RoleTypeEnum;
import bg.softuni.myrealestateproject.repository.UserRepository;
import bg.softuni.myrealestateproject.service.ApplicationUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, SecurityContextRepository securityContextRepository) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/fonts/**", "/users/signin-error").permitAll()
                .requestMatchers("/", "/users/signin", "/users/register", "/offers/sales", "/offers/rents", "/offers/search", "/contact", "/about-us", "/offers/details/**").permitAll()
                .requestMatchers("/offers/add", "/offers/search").hasRole(RoleTypeEnum.USER.name())
                .requestMatchers("/offers/add", "/offers/search").hasRole(RoleTypeEnum.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/users/signin")
                .usernameParameter("email")
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/", true)//use true argument if you always want to go there, otherwise go to previous page
                .failureForwardUrl("/users/signin-error")
                .and()
                .logout()//configure logout
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/")//go to homepage after logout
                .invalidateHttpSession(true)
                .and()
                .securityContext()
                .securityContextRepository(securityContextRepository);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Primary
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new ApplicationUserDetailsService(userRepository);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }
}
