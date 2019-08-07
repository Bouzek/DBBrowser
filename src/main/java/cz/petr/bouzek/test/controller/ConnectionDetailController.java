package cz.petr.bouzek.test.controller;

import cz.petr.bouzek.test.dao.entity.DBConnectionDetail;
import cz.petr.bouzek.test.service.DBConnectionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.tools.web.BadHttpRequest;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/connections")
public class ConnectionDetailController {

	private final DBConnectionDetailService dbConnectionDetailService;

	@GetMapping
	public Iterable<DBConnectionDetail> findAll() {
		return dbConnectionDetailService.findAllConnections();
	}

	@GetMapping(path = "/{name}")
	public DBConnectionDetail find(@PathVariable("name") String name) {
		return dbConnectionDetailService.findByName(name);
	}

	@PostMapping(consumes = "application/json")
	public DBConnectionDetail create(@RequestBody @Valid DBConnectionDetail connectionDetail) {
		return dbConnectionDetailService.create(connectionDetail);
	}

	@DeleteMapping(path = "/{name}")
	public void delete(@PathVariable("name") String name) {
		dbConnectionDetailService.delete(name);
	}

	@PutMapping(path = "/{name}")
	public DBConnectionDetail update(@PathVariable("name") String name, @RequestBody @Valid DBConnectionDetail connectionDetail) throws BadHttpRequest {
		if (dbConnectionDetailService.findByName(name) != null) {
			connectionDetail.setName(name);
			return dbConnectionDetailService.update(connectionDetail);
		} else {
			throw new BadHttpRequest();
		}
	}

}