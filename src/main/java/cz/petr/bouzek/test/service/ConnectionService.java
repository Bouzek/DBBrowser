package cz.petr.bouzek.test.service;

import cz.petr.bouzek.test.dao.entity.DBConnectionDetail;
import org.springframework.stereotype.Service;

import java.sql.Connection;

public interface ConnectionService {

    Connection getConnection(DBConnectionDetail connectionDetail);

    void closeConnection(DBConnectionDetail con);
}
