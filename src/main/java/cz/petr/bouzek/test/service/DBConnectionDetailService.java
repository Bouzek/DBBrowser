package cz.petr.bouzek.test.service;

import cz.petr.bouzek.test.dao.entity.DBConnectionDetail;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DBConnectionDetailService {

    List<DBConnectionDetail> findAllConnections();

    DBConnectionDetail findByName(String name);

    DBConnectionDetail create(DBConnectionDetail connectionDetail);

    DBConnectionDetail update(DBConnectionDetail connectionDetail);

    void delete(String connectionName);
}
