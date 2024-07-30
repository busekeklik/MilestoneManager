package com.etiya.milestonemanager.audit;
/*
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

// LOMBOK
@Log4j2

// StereoType
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(authentication!=null&&authentication.isAuthenticated()){
            log.warn("Sistemde Kullanıcı mevcut"+authentication.getName());
            System.out.println("Sistemde Kullanıcı mevcut"+authentication.getName());
            log.warn("Sistemde Kullanıcı bilgileri"+authentication.getPrincipal());
            return Optional.of(authentication.getName());
        }

        //return Optional.empty();
        return Optional.of("HamitMizrak");
    }
}

 */