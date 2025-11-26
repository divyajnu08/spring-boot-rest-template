package com.example.demo.auth.filter;

import com.example.demo.auth.service.JWTServiceImpl;
import com.example.demo.auth.service.UserDetailsByPhoneService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * A custom authentication filter responsible for validating incoming JWT tokens.
 * <p>
 * This filter runs once per request and checks whether the request contains a
 * valid Authorization header with a Bearer token. If the token is valid, the
 * filter populates the Spring Security context with an authenticated user.
 * </p>
 *
 * <p>
 * Requests that match configured public URLs are bypassed, allowing anonymous
 * access (e.g., login endpoints).
 * </p>
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JWTServiceImpl jwtService;
    private final UserDetailsByPhoneService userDetailsByPhoneService;

    /**
     * URLs that should bypass JWT authentication.
     * <p>
     * Any request whose path {@code startsWith()} these values will be treated
     * as public and will not require authentication.
     * </p>
     */
    private static final List<String> PUBLIC_URLS = List.of(
            "/api/auth"
    );

    /**
     * Constructs a new {@link JwtAuthFilter}.
     *
     * @param jwtService                service for verifying and extracting information from JWT tokens
     * @param userDetailsByPhoneService service for loading user details based on phone number (username)
     */
    public JwtAuthFilter(JWTServiceImpl jwtService,
                         UserDetailsByPhoneService userDetailsByPhoneService) {
        this.jwtService = jwtService;
        this.userDetailsByPhoneService = userDetailsByPhoneService;
    }

    /**
     * Performs JWT validation for incoming requests.
     * <p>
     * The filter applies the following logic:
     * </p>
     * <ol>
     *     <li>Skip filtering for public URLs.</li>
     *     <li>Check the Authorization header for a Bearer token.</li>
     *     <li>If a token is present and valid:
     *         <ul>
     *             <li>Extract the user identifier (phone number).</li>
     *             <li>Load the corresponding user details.</li>
     *             <li>Populate the Spring Security context with an authenticated token.</li>
     *         </ul>
     *     </li>
     *     <li>Continue the filter chain.</li>
     * </ol>
     *
     * @param request     the incoming HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain to continue execution
     * @throws ServletException if filtering fails
     * @throws IOException      if an input/output error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        boolean isPublic = PUBLIC_URLS.stream().anyMatch(path::startsWith);
        if (isPublic) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtService.isTokenValid(token)) {
                String username = jwtService.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var userDetails = userDetailsByPhoneService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
