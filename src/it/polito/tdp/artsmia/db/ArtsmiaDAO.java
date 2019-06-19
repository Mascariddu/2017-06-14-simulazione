package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Mostra;

public class ArtsmiaDAO {

	public List<ArtObject> listObject(HashMap<Integer, ArtObject> idMap) {
		
		String sql = "SELECT * from objects";

		List<ArtObject> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title")));
				idMap.put(res.getInt("object_id"), new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Integer> getAnni() {
		
		String sql = "SELECT DISTINCT e.`begin` AS b FROM exhibitions e  ORDER BY b DESC";

		List<Integer> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(res.getInt("b"));
			}

			conn.close();
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Mostra> getVertex(HashMap<Integer, Mostra> idMap,int anno) {
		// TODO Auto-generated method stub
		String sql = "SELECT e.exhibition_id AS id,e.exhibition_title AS title,e.`begin` AS b FROM exhibitions e  WHERE e.`begin` >= ? ORDER BY b";

		List<Mostra> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, anno);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				Mostra mostra = new Mostra(res.getInt("id"), res.getString("title"), res.getInt("b"));
				
				idMap.put(res.getInt("id"),mostra);
				result.add(mostra);
			}

			conn.close();
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Arco> getArchi(HashMap<Integer, Mostra> idMap, int anno) {
		String sql = "SELECT e.exhibition_id AS prima, e2.exhibition_id AS dopo FROM exhibitions e,exhibitions e2 WHERE e.`begin` < e2.`begin` AND e.`begin` > ?";

		List<Arco> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				Arco arco = new Arco(idMap.get(res.getInt("prima")),idMap.get(res.getInt("dopo")));
				
				result.add(arco);
			}

			conn.close();
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int getOpere(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT COUNT(*) as tot FROM exhibition_objects e WHERE e.exhibition_id = ?";

		int value = 0;

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet res = st.executeQuery();

			if(res.next()) {
				
				value = res.getInt("tot");
				
			}

			conn.close();
			return value;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public List<Mostra> getMostre(int anno) {
		// TODO Auto-generated method stub
				String sql = "SELECT e.exhibition_id AS id,e.exhibition_title AS title,e.`begin` AS b FROM exhibitions e  WHERE e.`begin` = ? ORDER BY b";

				List<Mostra> result = new ArrayList<>();

				Connection conn = DBConnect.getConnection();

				try {
					PreparedStatement st = conn.prepareStatement(sql);
					
					st.setInt(1, anno);

					ResultSet res = st.executeQuery();

					while (res.next()) {
						
						Mostra mostra = new Mostra(res.getInt("id"), res.getString("title"), res.getInt("b"));
						
						result.add(mostra);
					}

					conn.close();
					return result;
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
	}

	public List<ArtObject> getArtObjects(int id, HashMap<Integer, ArtObject> idMap) {
		// TODO Auto-generated method stub
		String sql = "SELECT e.object_id as id FROM exhibition_objects e WHERE e.exhibition_id = ?";

		List<ArtObject> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, id);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(idMap.get(res.getInt("id")));
			}

			conn.close();
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
