package com.sda.dto;

import lombok.Builder;

public record UserDTO (String username, String name, String surname, int age, String email){

@Builder public UserDTO{};
}
