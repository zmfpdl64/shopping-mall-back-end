package com.supercoding.shoppingmallbackend.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtUtiles jwtUtiles;
//    private final ProfileRepository profileRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!header.startsWith("Bearer ")){
            log.error("유효하지 않은 토큰이거나 유효하지 않는 경로입니다. {}", request.getRequestURL());
            filterChain.doFilter(request, response);
            return ;
        }
        try {
            final String token = header.split(" ")[1].trim();
            log.info("Token Filter, token: {}", token);

            if (!jwtUtiles.validateToken(token)) {
                log.error("트큰이 만료되었습니다.: {}", token);
                filterChain.doFilter(request, response);
                return ;
            }
            Long profileIdx = jwtUtiles.getProfileIdx(token);
            AuthHolder.setUserIdx(profileIdx);
            log.info("auth idx: {}", profileIdx);
            UsernamePasswordAuthenticationToken authentication = jwtUtiles.getAuthentication(profileIdx);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (RuntimeException e) {
            log.error("예상치 못한 오류가 발생했습니다. {}", e.toString());
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request,response);
    }
}
