package mediSsok.mediSsokspring.config;

import lombok.AllArgsConstructor;
import mediSsok.mediSsokspring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity      //시큐리티 필터 등록
@AllArgsConstructor     //생성자 제작
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private MemberService memberService;
    private DataSource dataSource;

    /* 로그인 실패 핸들러 의존성 주입 */
    private final AuthenticationFailureHandler customFailureHandler;

    // 암호화 빈 생성
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {  //비밀번호 암호화를 위해 사용, 시큐리티는 비밀번호가 암호화 되있어야 사용가능하다
        return new BCryptPasswordEncoder();                 //회원가입할때 사용
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    // 권환
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 토큰 세션 유지
        http.rememberMe()
                .userDetailsService(userDetailsService())
                .tokenRepository(tokenRepository());

        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/**")
                    .permitAll()     // 모든 경로 접근 허용
                .and() // 로그인 설정
                    .formLogin()
                    .loginPage("/user/login")
                    .loginProcessingUrl("/user/loginProc")
                    .failureHandler(customFailureHandler) // 로그인 실패 핸들러
                    .defaultSuccessUrl("/")     // 정상일때 메인페이지로
                .and() // 로그아웃 설정
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                .and()
                    .csrf().disable()
                // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/user/denied");
    }

    // 토큰 새션
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        return jdbcTokenRepository;
    }
}