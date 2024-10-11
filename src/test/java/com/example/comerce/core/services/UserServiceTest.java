package com.example.comerce.core.services;

import com.example.comerce.core.dto.AddressDTO;
import com.example.comerce.core.dto.OrderDTO;
import com.example.comerce.core.dto.UserDTO;
import com.example.comerce.core.entities.LoginResponse;
import com.example.comerce.core.entities.User;
import com.example.comerce.core.repository.UserRepository;
import com.example.comerce.shared.security.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

final class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private UserDTO userDTO;
    private User user;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();
        userDTO = new UserDTO();
        userDTO.setName("Alessandro Lima");
        userDTO.setEmail("alessandro@email.com");
        userDTO.setCpf("12345678901");
        userDTO.setTelephone("12345678901");
        userDTO.setPassword("senha123");

        final AddressDTO address = new AddressDTO();
        address.setPostal_code("12345678");
        address.setStreet("Rua Exemplo");
        address.setNumber("123");
        address.setNeighborhood("Bairro Exemplo");
        address.setComplement("Apto 456");
        address.setCity("Cidade Exemplo");
        address.setState("Estado Exemplo");
        userDTO.setAddress(address);

        final OrderDTO order = new OrderDTO();
        order.setDate(new Date());
        order.setDiscount(10.0);
        order.setTotal_price(100.0);
        userDTO.setOrders(List.of(order));

        user = userDTO.toEntity();
        user.setUser_id(userId);
    }

    @Test
    public void testFindById_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getUser_id());
    }

    @Test
    public void testFindById_UserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findById(userId);

        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testSaveUser() {
        // Não testa a senha, pois a senha é criptografada no método save
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.save(userDTO);

        assertNotNull(savedUser, "Não é possível salvar dados vazios");
        assertEquals(user.getUser_id(), savedUser.getUser_id());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(userId);

        userService.delete(userId);

        // Verifica se o método deleteById foi chamado com o ID correto
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testLogin_Success() throws Exception {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateToken(anyString())).thenReturn("fakeToken");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        LoginResponse response = userService.login(user.getEmail(), user.getPassword());

        assertNotNull(response);
        assertEquals("Bearer fakeToken", response.getToken());
        assertEquals(user, response.getUser());
    }

    @Test
    public void testLogin_UserNotFound() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Bad credentials"));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> userService.login("wrongemail@email.com", "wrongpassword"));

        assertEquals("User not found", exception.getMessage());
    }
}
