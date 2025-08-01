package com.postech.challenge_01.exceptions;

public class MenuItemNotFoundException extends ResourceNotFoundException {
    public MenuItemNotFoundException(Long id) {
        super("Item do menu com ID %d não foi encontrado".formatted(id));
    }
}

