package com.deepanshu.FullstackApp.model;

import jakarta.persistence.Entity;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Nonprofit extends User {

    private String name;
    private String address;
}
