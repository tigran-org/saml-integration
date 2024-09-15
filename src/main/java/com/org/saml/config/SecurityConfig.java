package com.org.saml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/saml2/**","/saml/**").permitAll()
                                .anyRequest().authenticated()
                )
                .saml2Login(saml2 -> {
                            try {
                                saml2
                                        .relyingPartyRegistrationRepository(relyingPartyRegistrations());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }
    @Bean
    public RelyingPartyRegistrationRepository relyingPartyRegistrations() {
        RelyingPartyRegistration registration = RelyingPartyRegistrations
                .fromMetadataLocation("idp-metadata.xml")
                .registrationId("sAMAccountName")
                .build();
        return new InMemoryRelyingPartyRegistrationRepository(registration);
    }
}
