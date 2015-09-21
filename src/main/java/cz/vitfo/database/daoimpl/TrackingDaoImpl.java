package cz.vitfo.database.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import cz.vitfo.database.dao.TrackingDao;
import cz.vitfo.database.model.TrackInfo;

public class TrackingDaoImpl extends DaoImpl implements TrackingDao {

	@Override
	public void save(TrackInfo info) {
		
		try (Connection con = dataSource.getConnection())  {
			PreparedStatement ps = con.prepareStatement("insert into " + TableEnum.T_TRACKING + " (ip, url, session, time) values (?, ?, ?, ?)");
			ps.setString(1, info.getIp());
			ps.setString(2, info.getUrl());
			ps.setString(3, info.getSession());
			ps.setTimestamp(4, new Timestamp(info.getDate().getTime()));
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
