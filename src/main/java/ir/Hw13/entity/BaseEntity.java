package ir.Hw13.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity<ID extends Number> implements Serializable {

    @Id
    @GeneratedValue
    private ID id;



}
