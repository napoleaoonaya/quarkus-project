package br.com.onaya.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "Users")
public class User extends PanacheEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
}
