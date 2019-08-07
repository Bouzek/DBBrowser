package cz.petr.bouzek.test.service;

import cz.petr.bouzek.test.dao.entity.DBConnectionDetail;
import cz.petr.bouzek.test.dto.DBColumnDTO;
import cz.petr.bouzek.test.dto.DBSchemaDTO;
import cz.petr.bouzek.test.dto.DBTableDTO;
import cz.petr.bouzek.test.dto.DBTablePreviewDTO;

import java.util.List;

public interface BrowserService {

    List<DBSchemaDTO> listSchemas(DBConnectionDetail connectionDetail);

    List<DBTableDTO> listTables(DBConnectionDetail connectionDetail, String catalog);

    List<DBColumnDTO> listColumns(DBConnectionDetail connectionDetail, String catalog, String table);

    DBTablePreviewDTO getTablePreview(DBConnectionDetail connectionDetail, String catalog, String table);
}
