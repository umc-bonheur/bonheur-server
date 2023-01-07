package com.bonheur.domain.tag.model;

import com.bonheur.domain.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Tag extends BaseEntity {
    @Column(nullable = false, length = 10)
    private String name;
}