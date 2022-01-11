package com.zechariah.springbootmongodb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="todos")
public class TodoDTO {

    @Id
    private String id;

    @NotNull(message = "Todo Name Cannot Be Empty")
    private String todo;

    @NotNull(message = "Todo Description Cannot Be Empty")
    private String description;

    @NotNull(message = "Todo Completion Status Cannot Be Empty")
    private Boolean completed;
    private Date createdAt;
    private Date updatedAt;

}
