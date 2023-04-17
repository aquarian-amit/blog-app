package com.blogapp.practice.blogappapis.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService detailsService;

	@Autowired
	private JwtTokenHelper tokenHelper;

	/*
	 * @Override protected void doFilterInternal(HttpServletRequest request,
	 * HttpServletResponse response, FilterChain filterChain) throws
	 * ServletException, IOException {
	 * 
	 * // get Token String requestToken = request.getHeader("Authorization");
	 * 
	 * // Bearer 4567898uyghui9098
	 * 
	 * System.out.println(requestToken);
	 * 
	 * String username = null;
	 * 
	 * String token = null;
	 * 
	 * if (requestToken != null && requestToken.startsWith("Bearer")) { token =
	 * requestToken.substring(7); try { username =
	 * this.tokenHelper.getUsernameFromToken(token); } catch
	 * (IllegalArgumentException e) {
	 * System.out.println("Unable tyo get JWT Token"); } catch (ExpiredJwtException
	 * e) { System.out.println("JWT Token has expired"); } catch
	 * (MalformedJwtException e) { System.out.println("JWT Token is Invalid"); } }
	 * else { System.out.println("JWT Token doesn't start with bearer");
	 * 
	 * }
	 * 
	 * // validate Token
	 * 
	 * if (username != null &&
	 * SecurityContextHolder.getContext().getAuthentication() == null) {
	 * 
	 * UserDetails userDetails = this.detailsService.loadUserByUsername(username);
	 * 
	 * if (this.tokenHelper.validateToken(token, userDetails)) {
	 * 
	 * UsernamePasswordAuthenticationToken authenticationToken = new
	 * UsernamePasswordAuthenticationToken( userDetails, null,
	 * userDetails.getAuthorities()); authenticationToken.setDetails(new
	 * WebAuthenticationDetailsSource().buildDetails(request));
	 * SecurityContextHolder.getContext().setAuthentication(null); } else {
	 * System.out.println("Invalid jwt token"); } } else {
	 * System.out.println("Username is null or context is not null"); }
	 * 
	 * filterChain.doFilter(request, response); }
	 */

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// get Token
		String requestToken = request.getHeader("Authorization");

		// Bearer 4567898uyghui9098

		System.out.println("RequestToken " + requestToken);
		System.out.println("RequestToken1 " + requestToken);
		String username = null;

		String token = null;

		if (requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			try {
				username = this.tokenHelper.getUsernameFromToken(token);
				System.out.println("Username : " + username);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			} catch (MalformedJwtException e) {
				System.out.println("JWT Token is Invalid");
			}
		} else {
			System.out.println("JWT Token doesn't start with bearer");

		}

		// validate Token

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.detailsService.loadUserByUsername(username);

			if (this.tokenHelper.validateToken(token, userDetails)) {

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			} else {
				System.out.println("Invalid jwt token");
			}
		} else {
			System.out.println("Username is null or context is not null");
		}

		filterChain.doFilter(request, response);

	}

}
