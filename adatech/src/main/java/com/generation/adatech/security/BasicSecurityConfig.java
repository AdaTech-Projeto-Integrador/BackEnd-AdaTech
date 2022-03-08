package com.generation.adatech.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.inMemoryAuthentication()
			.withUser("root")
			.password(passwordEncoder().encode("root"))
			.authorities("ROLE_USER");	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // Sessão para autorização do usuário
			.antMatchers("/usuarios/logar").permitAll() // Permite com que todes tenham acesso ao login
			.antMatchers("/usuarios/cadastrar").permitAll() // Permite com que todes tenham acesso ao cadastro
			.antMatchers(HttpMethod.OPTIONS).permitAll() // Indicar quais opções estão disponíveis
			.anyRequest().authenticated() // Camada que envia uma cadeia de caracteres em Base64 que contenham usuário e senha
			.and().httpBasic()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Filtro de segurança
			.and().cors() // Informa o navegador para permitir a execução do domínio, com algumas permissões que foram selecionadas
			.and().csrf().disable(); // Desabilitando o CSRF, porque ainda não estamos fazendo teste no navegador 
	}
	
	
}
