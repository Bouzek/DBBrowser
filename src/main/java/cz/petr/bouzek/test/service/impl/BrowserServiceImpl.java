package cz.petr.bouzek.test.service.impl;

import cz.petr.bouzek.test.dao.entity.DBConnectionDetail;
import cz.petr.bouzek.test.dto.*;
import cz.petr.bouzek.test.service.BrowserService;
import cz.petr.bouzek.test.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class BrowserServiceImpl implements BrowserService {

    private static final int PREVIEW_ROW_LIMIT = 10;

    private final ConnectionService connectionService;


    @Override
    public List<DBSchemaDTO> listSchemas(DBConnectionDetail connectionDetail) {
        log.info("Listing schemas");
        Connection connection = connectionService.getConnection(connectionDetail);
        List<DBSchemaDTO> schemas = new ArrayList<DBSchemaDTO>();

        try {
            ResultSet result = connection.getMetaData().getCatalogs();
            while (result.next()) {
                log.info("Schema found");
                String name = result.getString("TABLE_CAT").toLowerCase();
                schemas.add(new DBSchemaDTO(name));
            }

        } catch (SQLException e) {
            log.error("SQL error listing schemas - " + e.getMessage());
        }

        log.info("Finished listing schemas");
        return schemas;
    }

    @Override
    public List<DBTableDTO> listTables(DBConnectionDetail connectionDetail, String catalog) {
        log.info("Listing tables");
        Connection connection = connectionService.getConnection(connectionDetail);
        List<DBTableDTO> tables = new ArrayList<DBTableDTO>();

        try {
            ResultSet result = connection.getMetaData().getTables(catalog, null, "%", null);
            while (result.next()) {
                log.info("Table found");
                String name = result.getString(3);
                String scheme = result.getString(1);
                String type = result.getString(4);

                tables.add(new DBTableDTO(name, scheme, type));
            }

        } catch (SQLException e) {
            log.error("SQL error listing tables - " + e.getMessage());
        }

        log.info("Finished listing tables");
        return tables;
    }

    @Override
    public List<DBColumnDTO> listColumns(DBConnectionDetail connectionDetail, String catalog, String table) {
        log.info("Listing columns");
        Connection connection = connectionService.getConnection(connectionDetail);
        List<DBColumnDTO> columns = new ArrayList<DBColumnDTO>();

        try {
            ResultSet result = connection.getMetaData().getColumns(catalog, null, table, "%");
            while (result.next()) {
                log.info("Column found");
                String name = result.getString(4);
                int typeCode = result.getInt(5);
                String typeName = result.getString(6);
                int columnSize = result.getInt(7);
                String comment = result.getString(12);
                String defaultValue = result.getString(13);
                String nullable = result.getString(1);

                columns.add(new DBColumnDTO(name, typeCode, typeName, columnSize, comment, defaultValue, nullable));
            }

        } catch (SQLException e) {
            log.error("SQL error listing columns - " + e.getMessage());
        }

        log.info("Finished listing columns");
        return columns;
    }

    @Override
    public DBTablePreviewDTO getTablePreview(DBConnectionDetail connectionDetail, String catalog, String table) {
        DBTablePreviewDTO preview = new DBTablePreviewDTO();
        preview.setColumnsInfo(listColumns(connectionDetail, catalog, table));

        Connection connection = connectionService.getConnection(connectionDetail);

        try {
            PreparedStatement statement = connection.prepareStatement("select * from ?.? LIMIT ?");
            statement.setString(1, catalog);
            statement.setString(2, table);
            statement.setInt(3, PREVIEW_ROW_LIMIT);
            
            ResultSet result = statement.executeQuery();

            int columns = result.getMetaData().getColumnCount();

            while (result.next()) {
                int column = 1;
                DBrowDTO row = new DBrowDTO();

                while (column < columns) {
                    row.addValue(result.getObject(column++).toString());
                }

                preview.getData().add(row);
            }
            statement.close();
        } catch (SQLException e) {
            log.error("SQL error listing preview of table - " + e.getMessage());
        }

        log.info("Finished listing preview of table");


        return preview;
    }

}
