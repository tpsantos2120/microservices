package com.projectx.model;

import com.projectx.enums.EmailStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = "EMAIL_HISTORY")
@Data
public class EmailModel {

    @Id
    @Indexed(unique = true)
    private String id;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String emailSubject;
    private String text;
    private LocalDateTime sendDateEmail;
    private EmailStatus emailStatus;
}
