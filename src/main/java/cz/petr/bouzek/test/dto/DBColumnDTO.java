package cz.petr.bouzek.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBColumnDTO {

    private String columnName;
    private int typeCode;
    private String typeName;
    private int columnSize;
    private String comment;
    private String defaultValue;
    private String nullable;
}
