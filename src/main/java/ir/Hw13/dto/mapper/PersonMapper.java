package ir.Hw13.dto.mapper;

import ir.Hw13.dto.PersonSignUpDto;
import ir.Hw13.entity.Person;

public interface PersonMapper<T extends Person> {

    public T toEntity(PersonSignUpDto dto);

}
