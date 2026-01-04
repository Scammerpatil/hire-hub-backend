package com.hirehub.SpringServer.UtilityClasses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtParameter {
    private long id;
    private String name;
    private String email;
    private String role;
}
