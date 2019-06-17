package pt.saudemin.hds.config.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.mgt.SecurityManager;

import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import pt.saudemin.hds.config.Constants;
import pt.saudemin.hds.config.filters.JWTVerifyingFilter;

import javax.servlet.Filter;

import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm() {
        SimpleAccountRealm realm = new SimpleAccountRealm();
        realm.addRole("admin");
        realm.addRole("user");
        DefaultSecurityManager securityManager = new DefaultSecurityManager(realm);
        SecurityUtils.setSecurityManager(securityManager);
        return realm;
    }

    @Bean
    public Filter jwtv() {
        return new JWTVerifyingFilter();
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition(Constants.ADMIN_PATH + "/**", "jwtv[admin]");
        chainDefinition.addPathDefinition(Constants.USER_PATH + "/**", "jwtv[user]");
        chainDefinition.addPathDefinition("/**", "jwtv[admin, user]");
        return chainDefinition;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition,
                                                         Map<String, Filter> filterMap) {

        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());
        filterFactoryBean.setFilters(filterMap);

        return filterFactoryBean;
    }

    @Bean
    @Lazy
    public Subject getCurrentlyAuthenticatedSubject() {
        return SecurityUtils.getSubject();
    }
}
