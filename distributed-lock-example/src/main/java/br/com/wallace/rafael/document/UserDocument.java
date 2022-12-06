package br.com.wallace.rafael.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document
@Data
public class UserDocument {

    @Id
    private String id;
    private String name;
    @Field(name = "birth_date")
    private LocalDate birthDate;
}
