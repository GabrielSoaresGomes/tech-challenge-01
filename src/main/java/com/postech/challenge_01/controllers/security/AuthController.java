package com.postech.challenge_01.controllers.security;

import com.postech.challenge_01.dtos.security.AccountCredentialsDTO;
import com.postech.challenge_01.dtos.security.TokenDTO;
import com.postech.challenge_01.usecases.security.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthUseCase authUseCase;

    @PostMapping
    public ResponseEntity<TokenDTO> signin(@RequestBody @Valid AccountCredentialsDTO accountCredentialsDTO) {
        var tokenDTO = authUseCase.execute(accountCredentialsDTO);
        return ResponseEntity.ok(tokenDTO);
    }
}
