package com.cly.backend.config;

import com.cly.backend.filter.CorsAnonymousFilter;
import com.cly.backend.filter.JwtFilter;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //use LinkedHashMap instead of hashMap，because hashMap is unordered.
        Map<String, Filter> myFilters = new LinkedHashMap<>();
        myFilters.put("cors", new CorsAnonymousFilter());
        myFilters.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(myFilters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/swagger-ui/**", "cors");
        filterMap.put("/webjars/**", "cors");
        filterMap.put("/swagger-resources/**", "cors");
        filterMap.put("/v3/**", "cors");
        filterMap.put("/customers/register/**", "cors");
        filterMap.put("/customers/login", "cors");
        filterMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        List<String> globalFilters = new ArrayList<>();
        globalFilters.add(DefaultFilter.invalidRequest.name());
        globalFilters.add("noSessionCreation");
        shiroFilterFactoryBean.setGlobalFilters(globalFilters);

        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    @Bean
    public Authorizer authorizer(){
        return new ModularRealmAuthorizer();
    }
}
