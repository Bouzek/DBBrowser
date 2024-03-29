package cz.petr.bouzek.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBTablePreviewDTO {

    private List<DBColumnDTO> columnsInfo;
    private List<DBrowDTO> data = new ArrayList<>();
}
