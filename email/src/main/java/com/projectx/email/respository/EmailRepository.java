package com.projectx.email.respository;

import com.projectx.email.model.EmailModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends MongoRepository<EmailModel, String> {
}
