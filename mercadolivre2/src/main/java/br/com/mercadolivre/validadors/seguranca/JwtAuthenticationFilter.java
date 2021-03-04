package br.com.mercadolivre.validadors.seguranca;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private TokenManager tokenManager;
    private UsersService usersService;

    public JwtAuthenticationFilter(TokenManager tokenManager, UsersService usersService) {
        this.tokenManager = tokenManager;
        this.usersService = usersService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    		
    	  String possibleToken = getTokenFromRequest(request);

          if (tokenManager.isValid(possibleToken)) {

              String userName = tokenManager.getUserName(possibleToken);
              UserDetails userDetails = usersService.loadUserByUsername(userName);

              UsernamePasswordAuthenticationToken authentication =
                      new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

              SecurityContextHolder.getContext().setAuthentication(authentication);
          }

          chain.doFilter(request, response);
      }
		/*
		 * Optional<String> possibleToken = getTokenFromRequest(request);
		 * 
		 * if (possibleToken.isPresent() && tokenManager.isValid(possibleToken.get())) {
		 * 
		 * String userName = tokenManager.getUserName(possibleToken.get()); UserDetails
		 * userDetails = usersService.loadUserByUsername(userName);
		 * 
		 * UsernamePasswordAuthenticationToken authentication = new
		 * UsernamePasswordAuthenticationToken(userDetails, null,
		 * userDetails.getAuthorities());
		 * 
		 * SecurityContextHolder.getContext().setAuthentication(authentication); }
		 */

        //chain.doFilter(request, response);
    
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length()); // 7 porq "Bearer " conta
    }

	/*
	 * private Optional<String> getTokenFromRequest(HttpServletRequest request) {
	 * String authToken = request.getHeader("Authorization");
	 * 
	 * return Optional.ofNullable(authToken); }
	 */
}