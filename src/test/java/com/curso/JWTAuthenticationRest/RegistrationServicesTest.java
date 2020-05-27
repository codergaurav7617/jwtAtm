//package com.curso.JWTAuthenticationRest;
//
//import com.curso.JWTAuthenticationRest.exception.RegistrationFailure;
//import com.curso.JWTAuthenticationRest.model.Login;
//import com.curso.JWTAuthenticationRest.repositories.AccountRepository;
//import com.curso.JWTAuthenticationRest.repositories.LoginRepository;
//import com.curso.JWTAuthenticationRest.services.RegistrationService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class RegistrationServicesTest {
//
//    private RegistrationService registrationService;
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    @Mock
//    private LoginRepository loginRepository;
//
//    @Before
//    public void setup() {
//
//        registrationService = new RegistrationService(loginRepository, accountRepository);
//    }
//
//    @Test
//    public void createUserTest() throws RegistrationFailure {
//        when(loginRepository.findByUser("gaurav")).thenReturn(null);
//        registrationService.createUser("gaurav", "1234");
//       verify(loginRepository).findByUser("gaurav");
//    }
//
////    @Test
////    public void createUserTest_when_user_already_exist() throws RegistrationFailure {
////        when(loginRepository.findByUser("gaurav")).thenReturn(new Login("gaurav","1234"));
////        registrationService.createUser("gaurav", "1234");
////        verify(loginRepository).findByUser("gaurav");
////    }
//
//}