package com.bonheur.domain.member.model.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MemberProperties {
    @Value("${member.default-profile}")
    private String defaultProfileURL;
}