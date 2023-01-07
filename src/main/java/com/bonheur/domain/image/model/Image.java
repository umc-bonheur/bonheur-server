package com.bonheur.domain.image.model;

import com.bonheur.domain.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseEntity {
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Long order;
}
