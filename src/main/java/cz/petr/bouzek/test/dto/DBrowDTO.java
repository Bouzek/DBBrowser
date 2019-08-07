package cz.petr.bouzek.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBrowDTO {
    private List<String> values = new ArrayList<>();

    public void addValue(String value){
        values.add(value);
    }
}
