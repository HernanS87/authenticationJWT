package com.example.authenticationJWT.config.filter;

import com.example.authenticationJWT.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {

    //TODO esto se puede inyectar con @Autowired, hacer pruebas para ver que pasa con el filtro
    private final JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(jwtToken != null) {
            //en el encabezado antes del token viene la palabra bearer (esquema de autenticación)
            //por lo que debemos sacarlo
            jwtToken = jwtToken.substring(7); //son 6 letras + 1 espacio
            var decodedJWT = jwtUtils.validateToken(jwtToken);

            //si el token es válido, le concedemos el acceso
            var username = jwtUtils.extractUsername(decodedJWT);
            //me devuelve claim, necesito pasarlo a String
            var authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

            //Si todoo está ok, hay que setearlo en el Context Holder
            //Para eso tengo que convertirlos a GrantedAuthority
            Collection<? extends GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            //Si se valida el token, le damos acceso al usuario en el context holder
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authoritiesList);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context); // TODO esta linea se podría eliminar?

        }

        // si no viene token, va al siguiente filtro
        //si no viene el token, eso arroja error
        filterChain.doFilter(request,response);
    }
}
