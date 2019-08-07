package cz.petr.bouzek.test.controller;

import cz.petr.bouzek.test.dao.entity.DBConnectionDetail;
import cz.petr.bouzek.test.dto.DBColumnDTO;
import cz.petr.bouzek.test.dto.DBSchemaDTO;
import cz.petr.bouzek.test.dto.DBTableDTO;
import cz.petr.bouzek.test.service.BrowserService;
import cz.petr.bouzek.test.service.DBConnectionDetailService;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/browser")
public class BrowserController {

    private final DBConnectionDetailService dbConnectionDetailService;
    private final BrowserService browserService;

    @GetMapping(path = "/{connectionName}/schemas")
    public Iterable<DBSchemaDTO> listSchemas(@PathVariable("connectionName") String connectionName) throws BadHttpRequest {
        DBConnectionDetail connectionDetail = getConnectionDetail(connectionName);
        return browserService.listSchemas(connectionDetail);
    }

    @GetMapping(path = "/{connectionName}/tables")
    public Iterable<DBTableDTO> listTables(@PathVariable("connectionName") String connectionName) throws BadHttpRequest {
        DBConnectionDetail connectionDetail = getConnectionDetail(connectionName);
        return browserService.listTables(connectionDetail, null);
    }

    @GetMapping(path = "/{connectionName}/{schema}/tables")
    public Iterable<DBTableDTO> listTablesInSchema(@PathVariable("connectionName") String connectionName,
                                                   @PathVariable("schema") String schema) throws BadHttpRequest {
        DBConnectionDetail connectionDetail = getConnectionDetail(connectionName);
        return browserService.listTables(connectionDetail, schema);
    }

    @GetMapping(path = "/{connectionName}/{schema}/{table}/columns")
    public Iterable<DBColumnDTO> listColumns(@PathVariable("connectionName") String connectionName,
                                             @PathVariable("schema") String schema, @PathVariable("table") String table) throws BadHttpRequest {
        DBConnectionDetail connectionDetail = getConnectionDetail(connectionName);
        return browserService.listColumns(connectionDetail, schema, table);
    }


    private DBConnectionDetail getConnectionDetail(String connectionName) throws BadHttpRequest {
        DBConnectionDetail connectionDetail = dbConnectionDetailService.findByName(connectionName);
        if (connectionDetail != null) {
            return connectionDetail;
        } else {
            throw new BadHttpRequest();
        }
    }
}
