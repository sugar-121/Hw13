package ir.Hw13.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestion extends BaseEntity<Long> {

    @ManyToOne(fetch = LAZY,
            cascade = {MERGE, PERSIST})
    private Tests tests;

    @ManyToOne(fetch = LAZY,
            cascade = {MERGE, PERSIST})
    private Questions questions;


    private long score;


}
