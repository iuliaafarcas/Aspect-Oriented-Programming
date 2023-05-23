package com.example.a1.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Aspect
@Component
public class UIAuthenticationAspect {
    @Pointcut("@annotation(RequiresAuthentication)")
    public void authenticationRequired(){}

    @Before("authenticationRequired()")
    public void authenticate() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication != null) {
            return;
        }
        Authentication authenticationToken = getAuthenticationFromUI();
        authentication = authenticateUser(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication getAuthenticationFromUI() {
        try {
            String username = JOptionPane.showInputDialog("Username:");
            String password = JOptionPane.showInputDialog("Password:");
            return new UsernamePasswordAuthenticationToken(username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private Authentication authenticateUser(Authentication authenticationToken) {
        if (authenticationToken instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
            if ("iulia".equals(usernamePasswordAuthenticationToken.getPrincipal()) && "iulia".equals(usernamePasswordAuthenticationToken.getCredentials())) {
                return new UsernamePasswordAuthenticationToken(usernamePasswordAuthenticationToken.getPrincipal(), usernamePasswordAuthenticationToken.getCredentials());
            }
        }
        throw new UsernameNotFoundException("User not found: " + authenticationToken.getPrincipal().toString());
    }
}
