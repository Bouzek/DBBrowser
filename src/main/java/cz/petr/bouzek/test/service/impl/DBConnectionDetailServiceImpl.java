package cz.petr.bouzek.test.service.impl;

import cz.petr.bouzek.test.dao.entity.DBConnectionDetail;
import cz.petr.bouzek.test.dao.repository.ConnectionDetailRepository;
import cz.petr.bouzek.test.service.ConnectionService;
import cz.petr.bouzek.test.service.DBConnectionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DBConnectionDetailServiceImpl implements DBConnectionDetailService {

    private final ConnectionDetailRepository connectionDetailRepository;
    private final ConnectionService connectionService;

    public List<DBConnectionDetail> findAllConnections() {
        return connectionDetailRepository.findAll();
    }

    @Override
    public DBConnectionDetail findByName(String name) {
        return connectionDetailRepository.findOne(name);
    }

    @Override
    public DBConnectionDetail create(DBConnectionDetail connectionDetail) {
        return connectionDetailRepository.save(connectionDetail);
    }

    @Override
    public DBConnectionDetail update(DBConnectionDetail connectionDetail) {
        connectionService.closeConnection(connectionDetail);
        return connectionDetailRepository.save(connectionDetail);
    }

    @Override
    public void delete(String connectionName) {
        connectionService.closeConnection(findByName(connectionName));
        connectionDetailRepository.delete(connectionName);
    }
}
