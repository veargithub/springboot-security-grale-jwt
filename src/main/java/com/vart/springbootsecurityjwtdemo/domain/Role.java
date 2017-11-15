package com.vart.springbootsecurityjwtdemo.domain;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.AUTO)
    public Long id;

    @Column (name = "rolename")
    public String rolename;
}
