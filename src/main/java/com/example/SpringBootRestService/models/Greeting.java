package com.example.SpringBootRestService.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component 
@NoArgsConstructor
public class Greeting {

    private Long id;
    private String content;

}
