package vn.edu.iuh.fit.configs;

import vn.edu.iuh.fit.models.Account;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vn.edu.iuh.fit.repositories.AccountRepository;
import vn.edu.iuh.fit.services.UserService;

@Configuration
public class SecurityConfig {
    @Autowired
    private AccountRepository accountRepository;
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/", "/home", "/register").permitAll()
                        .requestMatchers("/jobs/{jobId}/apply").authenticated()
                        .requestMatchers("/jobs", "/jobs/**").permitAll()
                        .requestMatchers("/apply/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/recruiter/**").hasRole("RECRUITER")
                        .requestMatchers("/candidates/**").hasRole("CANDIDATE")
                        .requestMatchers("/dashboard/jobs/new").permitAll()
                        .requestMatchers("/candidates/jobs/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            String username = authentication.getName();

                            // Truy xuất thông tin tài khoản từ cơ sở dữ liệu
                            Account account = accountRepository.findByUsername(username)
                                    .orElseThrow(() -> new RuntimeException("Account not found"));

                            HttpSession session = request.getSession();
                            session.setAttribute("accountId", account.getId());
                            System.out.println("Saved accountId to session: " + account.getId());

                            // Chuyển hướng dựa trên trạng thái tài khoản
                            if (account.getRole() != null && account.getRole().getRoleName().equals("ROLE_CANDIDATE")) {
                                if (account.getCandidate() == null) {
                                    response.sendRedirect("/candidates/register/full-info");
                                } else {
                                    response.sendRedirect("/candidates/dashboard");
                                }
                            } else if (account.getRole() != null && account.getRole().getRoleName().equals("ROLE_RECRUITER")) {
                                response.sendRedirect("/recruiter/dashboard");
                            } else {
                                response.sendRedirect("/");
                            }
                        })
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession(false);
                            if (session != null) {
                                session.invalidate();
                            }
                            response.sendRedirect("/login?logout=true");
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(customizer -> customizer
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/403");
                        })
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/login");
                        })
                );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}