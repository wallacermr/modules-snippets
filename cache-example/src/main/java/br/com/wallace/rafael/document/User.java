package br.com.wallace.rafael.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
@Document(collection = "users")
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String name;
    @Field("birth_date")
    private LocalDate birthDate;
}
