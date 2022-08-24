package com.zamsan.myspringboot.config.auth;

import com.zamsan.myspringboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()//h2-console 사용하기 위해 해당옵션 disabled
                        .and().authorizeRequests()//URL별 권한 관리설정 시작점 authorizeRequests선언되어야지만 antMatchers옵션 사용가능
                        .antMatchers("/","/css/**","*/images/**",
                                "/js**","/h2-console/**").permitAll()//권한관리 대상 지정 "/"등 지정된 URL들은 permitAll()을 통해 전체 열람권한 
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())//"/api/v1/**" 주소를 가진 API는 USER 권한 가진 사람만 사용 가능
                        .anyRequest().authenticated()//"anyRequest()"설정된 값을 외의 URL,authenticated() 나머지 URL 로그인된 사용자들에게 허용
                        .and().logout().logoutSuccessUrl("/")//로그아웃 기능 진입점, 로그아웃시 "/" 로 이동
                        .and().oauth2Login().userInfoEndpoint()//OAuth2 로그인 성공 이후 사용자 정보 가져올때 설정
                .userService(customOAuth2UserService); //소셜 로그인 성공시 후속조치를 진행할 UserService인터페이스 구현체 등록

    }
}
