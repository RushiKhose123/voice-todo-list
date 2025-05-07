package com.example.demo.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notify {
    private String email;
    private String phone;
    private String message;
    private String metaData;
}
