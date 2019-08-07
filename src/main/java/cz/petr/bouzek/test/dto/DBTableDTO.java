package cz.petr.bouzek.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBTableDTO {

    private String tableName;
    private String schema;
    private String tableType;

}
