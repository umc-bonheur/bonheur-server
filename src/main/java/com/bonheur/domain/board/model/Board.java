package com.bonheur.domain.board.model;

import com.bonheur.domain.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseEntity {

    @Column(length = 3000)
    private String contents;
}
