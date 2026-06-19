package grupo.proyecto.config;

import grupo.proyecto.Service.UserDetailsServiceImpl;
import grupo.proyecto.exception.RestAuthenticationEntryPoint;
import grupo.proyecto.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private final RestAuthenticationEntryPoint authEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          UserDetailsServiceImpl userDetailsService,
                          RestAuthenticationEntryPoint authEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
                .authorizeHttpRequests(auth -> auth


                        .requestMatchers(
                                        "/auth/**",
                                        "/api/v1/geocoding/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()

                        //Restricciones a restauranteController

                        .requestMatchers(HttpMethod.GET, "/api/v1/restaurantes/**")
                        .permitAll() //estos endpoints no requieren autenticacion

                        .requestMatchers(HttpMethod.DELETE, "/api/v1/restaurantes/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/v1/restaurantes/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE")

                        .requestMatchers(HttpMethod.DELETE, "/api/v1/restaurantes/*/platos/*")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE")

                        .requestMatchers(HttpMethod.POST, "/api/v1/restaurantes/*/platos")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE")

                        .requestMatchers(HttpMethod.POST, "/api/v1/restaurantes/cercanos")
                        .authenticated()

                        //Restricciones a usuarioController

                        .requestMatchers(
                                "/api/v1/usuarios/*", "/api/v1/usuarios/email/*")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers("/api/v1/usuarios/*/preferencias/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                        .requestMatchers(HttpMethod.PUT, "/api/v1/usuarios/*")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                        //Restricciones a Reseñas

                        .requestMatchers(HttpMethod.GET, "/api/v1/reseñas-platos/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/reseñas-platos")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                        .requestMatchers(HttpMethod.GET, "/api/v1/reseñas-restaurantes/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/reseñas-restaurantes")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")


                        // 🔐 TODO LO DEMÁS
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
//
//                                "/api/v1/usuarios/**",
//                                        "/api/v1/restaurantes/**",
//                                        "/api/v1/platos/**",
//                                        "/api/v1/reseñas-restaurantes/**",
//                                        "/api/v1/reseñas-platos/**",