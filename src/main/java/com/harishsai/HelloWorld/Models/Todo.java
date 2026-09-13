package com.harishsai.HelloWorld.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Data
public class Todo {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Schema(name = "title" , example = "learn springboot")
    private String title;
    private String description;
    private Boolean isCompleted;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
}
