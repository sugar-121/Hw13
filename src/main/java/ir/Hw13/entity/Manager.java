package ir.Hw13.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;


@Getter
public class Manager{

    private static Manager manager;

    private String userName = "admin";

    private String password = "121";

    private Manager(){}

    public static Manager getInstance() {
        if (Objects.isNull(manager)){
            manager = new Manager();
        }
        return manager;
    }
}
