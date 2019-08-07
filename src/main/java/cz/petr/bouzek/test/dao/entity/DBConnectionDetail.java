package cz.petr.bouzek.test.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class DBConnectionDetail {

	@Id
	private String name;
	private String hostname;
	private String port;
	private String databaseName;
	private String username;
	private String password;
}
