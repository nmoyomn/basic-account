package com.task.account.config.jwt;

import com.task.account.common.code.Constants;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("JWT 필터 통과 시도: {}", request.getRequestURI());
        if (!jwtTokenService.validateJwtToken(request)) {
            // 로그인 후, 서비스 접근 시 토큰 기반 제어
            log.debug("JWT Token invalid or missing - proceeding unauthenticated");
        };
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        // 같거나 하위 endpoint 허용
        return Constants.PUBLIC_PATHS.stream().anyMatch(uri::startsWith);
    }


}
