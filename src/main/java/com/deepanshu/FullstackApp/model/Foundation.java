package com.deepanshu.FullstackApp.model;

import jakarta.persistence.Entity;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Foundation extends User{
    private String name;
}
