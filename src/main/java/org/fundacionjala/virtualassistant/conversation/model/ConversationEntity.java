package org.fundacionjala.virtualassistant.conversation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import lombok.Value;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "view_request_with_response")
@NoArgsConstructor(force = true)
@Value
public class ConversationEntity {
    @Id
    @Column(name = "id_request")
    Long idRequest;
    @Column(name = "text_request")
    String textRequest;
    @Column(name = "date_request")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    ZonedDateTime dateRequest;
    @Column(name = "id_audio")
    String idAudio;
    @Column(name = "id_user")
    Long idUser;
    @Column(name = "id_context")
    Long idContext;
    @Column(name = "text_response")
    String textResponse;
    @Column(name = "date_response")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    ZonedDateTime dateResponse;
}
