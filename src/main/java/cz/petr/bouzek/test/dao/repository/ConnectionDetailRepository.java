package cz.petr.bouzek.test.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import cz.petr.bouzek.test.dao.entity.DBConnectionDetail;

@RestResource(exported = false)
public interface ConnectionDetailRepository extends JpaRepository<DBConnectionDetail, String> {

}
