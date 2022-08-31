package th.ac.ku.restaurant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import th.ac.ku.restaurant.service.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/"
                        , "/signup"
                        , "/css/**"
                        , "/js/**"
                        ).permitAll()
                .anyRequest().authenticated()

                //บอก Spring ให้ใช้ /login ในการล็อคอิน
        .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/", true)   //กำหนด path ที่ให้ Spring ไปเมื่อ login สำเร็จ
                .permitAll()

                //บอก Spring ให้ใช้ /logout ในการล็อคเอาท์
        .and()
                .logout().logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll();
    }



    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    // for h2 database   ******SQL ไม่ต้องเติม
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

}
