package com.fatec.dsm.tharseo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_roles")
public class Role {

    private Long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum Values{

        BASIC(2L),
        ADMIN(1L);

        long roleId;

        Values(long roleId){
            this.roleId = roleId;
        }

        public long getRoleId() {
            return roleId;
        }


    }
}
