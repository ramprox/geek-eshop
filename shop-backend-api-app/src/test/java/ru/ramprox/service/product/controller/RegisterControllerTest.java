//package ru.ramprox.service1.product.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import ru.ramprox.service1.product.controller.config.TestConfig;
//import ru.ramprox.service1.product.controller.dto.RegisterData;
//import ru.ramprox.persist.model.Role;
//import ru.ramprox.persist.model.User;
//import ru.ramprox.persist.repositories.RoleRepository;
//import ru.ramprox.persist.repositories.UserRepository;
//
//import java.util.*;
//
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ActiveProfiles("test")
//@TestPropertySource(locations = "classpath:application-test.properties")
//@AutoConfigureMockMvc(addFilters = false)
//@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Import(TestConfig.class)
//public class RegisterControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private RabbitTemplate rabbitTemplate;
//
//    @MockBean
//    private SimpMessagingTemplate template;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @BeforeAll
//    public void init() {
//        String roleName = "ROLE_USER";
//        Set<Role> roles = Set.of(new Role(roleName));
//        roleRepository.saveAll(roles);
//
//        List<User> users = new ArrayList<>();
//        User user = new User("user1", passwordEncoder.encode("password1"), 30);
//        users.add(user);
//        roles.forEach(user::addRole);
//        userRepository.saveAll(users);
//    }
//
//    @Test
//    public void testRegister() throws Exception {
//        Role expectedRole = roleRepository.findByName("ROLE_USER").get();
//        Set<Role> expectedRoles = new HashSet<>();
//        expectedRoles.add(expectedRole);
//        User expectedUser = new User("user3", passwordEncoder.encode("password3"), 50);
//        expectedUser.setAge(50);
//
////        User expectedUser = new User(3L, "user3",
////                passwordEncoder.encode("password3"), 50, expectedRoles);
//
//        Date birthDate = new Date(System.currentTimeMillis());
//        RegisterData registerData = new RegisterData();
//        registerData.setUsername(expectedUser.getUsername());
//        registerData.setPassword("password3");
//        registerData.setBirthDay(birthDate);
//        registerData.setFirstName("firstName1");
//        registerData.setLastName("lastName1");
//        registerData.setEmail("mail@mail.ru");
//
//        mvc.perform(MockMvcRequestBuilders
//                .post("/register")
//                .content(new ObjectMapper().writeValueAsBytes(registerData))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username", is(expectedUser.getUsername())));
//
//        registerData = new RegisterData();
//        registerData.setUsername(expectedUser.getUsername());
//        registerData.setPassword("password3");
//        registerData.setBirthDay(birthDate);
//        registerData.setFirstName("firstName1");
//        registerData.setLastName("lastName1");
//        registerData.setEmail("mail@mail.ru");
//
//        mvc.perform(MockMvcRequestBuilders
//                .post("/register")
//                .content(new ObjectMapper().writeValueAsBytes(registerData))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$.username", is("A user with the same name already exists")));
//
//        registerData = new RegisterData();
//        registerData.setUsername("");
//        registerData.setPassword("");
//        registerData.setBirthDay(null);
//        registerData.setFirstName("");
//        registerData.setLastName("");
//        registerData.setEmail("");
//
//        mvc.perform(MockMvcRequestBuilders
//                .post("/register")
//                .content(new ObjectMapper().writeValueAsBytes(registerData))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.username", is("Username must not be blank")))
//                .andExpect(jsonPath("$.password", is("Password must not be blank")))
//                .andExpect(jsonPath("$.firstName", is("Firstname must not be blank")))
//                .andExpect(jsonPath("$.lastName", is("Lastname must not be blank")))
//                .andExpect(jsonPath("$.email", is("Email must not be blank")))
//                .andExpect(jsonPath("$.birthDay", is("BirthDate is mandatory")))
//        ;
//    }
//}
