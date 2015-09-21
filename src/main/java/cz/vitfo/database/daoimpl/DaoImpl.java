package cz.vitfo.database.daoimpl;

import org.apache.commons.dbcp.BasicDataSource;

public abstract class DaoImpl {
	
	protected static BasicDataSource dataSource = null;
	
	public DaoImpl() {
		if (dataSource == null) {
			dataSource = new BasicDataSource();
			
			dataSource.setDriverClassName("org.postgresql.Driver");
			dataSource.setUsername("postgres");
			dataSource.setPassword("password");
			dataSource.setUrl("jdbc:postgresql://localhost/weasy");
			dataSource.setMaxActive(10);
			dataSource.setMaxIdle(5);
			dataSource.setInitialSize(5);
			dataSource.setValidationQuery("SELECT 1");
		}
	}
}
