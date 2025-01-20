package org.fundacionjala.virtualassistant.user_intetions.client.response;


import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class IntentEntity {
    String entity;
    String value;
}
