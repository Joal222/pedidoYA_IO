package com.jaax.springsecurity.service;

import com.jaax.springsecurity.DTO.AuthResponse;
import com.jaax.springsecurity.DTO.AuthenticationRequest;
import com.jaax.springsecurity.DTO.RegisterRequest;

public interface AuthService {

    AuthResponse register( RegisterRequest request );

    AuthResponse authenticate(AuthenticationRequest request);
}
