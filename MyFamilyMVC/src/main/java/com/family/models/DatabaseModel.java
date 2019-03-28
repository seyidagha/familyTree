package com.family.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.PersonDAO;

@Configuration
@Component("databaseModel")
public class DatabaseModel implements PersonDAO {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseModel.class);
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setDataSource2(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Bean
	public static DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		// dataSource.setDriverClassName("org.sqlite.JDBC");
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		// dataSource.setUrl("jdbc:sqlite:db/springDB.db");
		ds.setUrl("jdbc:mysql://localhost:3306/familyDB");
		ds.setUsername("root");
		ds.setPassword("");
		return ds;
	}

	@Override
	public String delete(int id) {
		logger.info("deleting person " + id);
		Object[] params = { id, id };
		String deleteSql1 = "DELETE FROM person_relation WHERE person1_id = ? or person2_id = ?";
		int[] argTypes1 = { Types.INTEGER, Types.INTEGER };
		int rows1 = jdbcTemplate.update(deleteSql1, params, argTypes1);
		String deleteSql2 = "DELETE FROM person WHERE id = ?";
		Object[] params2 = { id };
		int[] argTypes2 = { Types.INTEGER };
		int rows2 = jdbcTemplate.update(deleteSql2, params2, argTypes2);
		System.out.println(rows2 + "rows deleted");
		return rows1 + " rows deleted from person_relation " + rows2 + " rows deleted from person";
	}

	@Override
	public String update(Person p) {
		// TODO Auto-generated method stub
		return null;
	}

	private static final class PersonRowMapper implements RowMapper<Person> {

		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Person p = new Person(null, null);
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setBirthDate(rs.getDate("birthdate"));
			p.setGender(rs.getString("gender"));
			return p;
		}

	}

	@Override
	public Person getPersonByID(int id) {
		logger.info("getting person by id = " + id);
		String sql = "select * from person where id=?";
		Object[] params = { id };
		return jdbcTemplate.queryForObject(sql, new PersonRowMapper(), params);
	}

	@Override
	public int insertRelation(int p1_id, int p2_id, int relation_id) {
		logger.info("inserting relation " + p1_id + " to " + p2_id + " as " + relation_id);
		KeyHolder holder = new GeneratedKeyHolder();
		String sql = "insert into person_relation (person1_id, person2_id, relation_id) values (:p1_id, :p2_id, :relation_id)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("p1_id", p1_id);
		params.addValue("p2_id", p2_id);
		params.addValue("relation_id", relation_id);
		namedParameterJdbcTemplate.update(sql, params, holder);
		return holder.getKey().intValue();
	}

	@Override
	public void insertBothRelations(int p1_id, int p2_id, int relation_id, Date date1, Date date2, String gender1,
			String gender2) {
		logger.info("inserting both relations with dates " + p1_id + " to " + p2_id + " as " + relation_id);
		switch (relation_id) {
		case 1:
			insertRelation(p1_id, p2_id, relation_id);
			insertRelation(p2_id, p1_id, 2);
			break;
		case 2:
			insertRelation(p1_id, p2_id, relation_id);
			insertRelation(p2_id, p1_id, 1);
			break;
		case 3:
			if (date2.after(date1)) {
				insertRelation(p1_id, p2_id, relation_id);
				if (gender2.equals("male")) {
					insertRelation(p2_id, p1_id, 5);
				} else if (gender2.equals("female")) {
					insertRelation(p2_id, p1_id, 6);
				}
			}
			// else
			// throw new FamilyException("error1", "parent " + date1 + " should
			// be older than children " + date2);
			break;
		case 4:
			if (date2.after(date1)) {
				insertRelation(p1_id, p2_id, relation_id);
				if (gender2.equals("male")) {
					insertRelation(p2_id, p1_id, 5);
				} else if (gender2.equals("female")) {
					insertRelation(p2_id, p1_id, 6);
				}
			}
			// else
			// throw new FamilyException("error1", "parent " + date1 + " should
			// be older than children " + date2);
			break;
		case 5:
			if (date1.after(date2)) {
				insertRelation(p1_id, p2_id, relation_id);
				if (gender2.equals("male")) {
					insertRelation(p2_id, p1_id, 3);
				} else if (gender2.equals("female")) {
					insertRelation(p2_id, p1_id, 4);
				}
			}
			// else
			// throw new FamilyException("error1", "parent " + date2 + " should
			// be older than children " + date1);
			break;
		case 6:
			if (date1.after(date2)) {
				insertRelation(p1_id, p2_id, relation_id);
				if (gender2.equals("male")) {
					insertRelation(p2_id, p1_id, 3);
				} else if (gender2.equals("female")) {
					insertRelation(p2_id, p1_id, 4);
				}
			}
			// else
			// throw new FamilyException("error1", "parent " + date2 + " should
			// be older than children " + date1);
			break;
		case 7:
			if (date2.after(date1)) {
				insertRelation(p1_id, p2_id, relation_id);
				if (gender2.equals("male")) {
					insertRelation(p2_id, p1_id, 9);
				} else if (gender2.equals("female")) {
					insertRelation(p2_id, p1_id, 10);
				}
			}
			// else
			// throw new FamilyException("error1", "parent " + date1 + " should
			// be older than children " + date2);
			break;
		case 8:
			if (date2.after(date1)) {
				insertRelation(p1_id, p2_id, relation_id);
				if (gender2.equals("male")) {
					insertRelation(p2_id, p1_id, 9);
				} else if (gender2.equals("female")) {
					insertRelation(p2_id, p1_id, 10);
				}
			}
			// else
			// throw new FamilyException("error1", "parent " + date1 + " should
			// be older than children " + date2);
			break;

		case 19:
			insertRelation(p1_id, p2_id, relation_id);
			if (gender2.equals("male")) {
				insertRelation(p2_id, p1_id, 19);
			} else {
				insertRelation(p2_id, p1_id, 20);
			}
			break;

		case 20:
			insertRelation(p1_id, p2_id, relation_id);
			if (gender2.equals("male")) {
				insertRelation(p2_id, p1_id, 19);
			} else {
				insertRelation(p2_id, p1_id, 20);
			}
			break;

		default:
			break;
		}

	}

	@Override
	public int cleanDatabase() {
		logger.info("cleaning database");
		String sql1 = "TRUNCATE person_relation";
		jdbcTemplate.batchUpdate(sql1);
		String sql2 = "delete from person";
		jdbcTemplate.batchUpdate(sql2);
		return 0;
	}

	@Override
	public int insert(Person p) {
		logger.info("inserting person " + p.getName());
		KeyHolder holder = new GeneratedKeyHolder();
		String sql = "insert into person (name, gender, birthdate) VALUES (:name, :gender, :birthdate)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", p.getName());
		params.addValue("gender", p.getGender());
		params.addValue("birthdate", p.getBirthDate());
		namedParameterJdbcTemplate.update(sql, params, holder);
		logger.info(p.getName() + " inserted successfully");
		return holder.getKey().intValue();
	}

	@Override
	public int getPersonID(Person p) {
		logger.info("getting id of person " + p.getName());
		String sql = "select id from person where name = ?";
		int id = (Integer) jdbcTemplate.queryForObject(sql, new Object[] { p.getName() }, Integer.class);
		return id;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public List<Map<String, Object>> getAdjacentRelations(Person p) {
		// ListMultimap<Integer, Person> myMap = ArrayListMultimap.create();
		int id = p.getId();
		String sql = "SELECT person2_id, relation_id FROM person_relation WHERE person1_id = ? and relation_id in (1,2,5,6,3,4,19,20)";
		Object[] params = { id };
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, params);
		// result.forEach(item -> myMap.put((int) item.get("relation_id"),
		// getPersonByID((int) item.get("person2_id"))));

		return result;
	}

	@Override
	public List<List<Map<String, Object>>> nodesEdges(int id, List<Integer> visitedList,
			List<Map<String, Object>> myNodes, List<Map<String, Object>> myEdges) {
		List<Map<String, Object>> nodes = myNodes;
		List<Map<String, Object>> edges = myEdges;
		List<List<Map<String, Object>>> result = new ArrayList<>();
		Map<String, Object> m = new HashMap<>();
		Person p = getPersonByID(id);
		m.put("id", id);
		m.put("label", p.getName());
		nodes.add(m);
		List<Integer> myVisitedList = visitedList;
		myVisitedList.add(id);
		List<Map<String, Object>> adjacentList = getAdjacentRelations(p);
		for (Map<String, Object> map : adjacentList) {
			m = new HashMap<>();
			Person p2 = getPersonByID((int) map.get("person2_id"));
			if (p.getBirthDate().before(p2.getBirthDate())) {
				m.put("from", id);
				m.put("to", (int) map.get("person2_id"));
				m.put("arrows", "to");
				Map<String, Object> m2 = new HashMap<>();
				m2.put("type", "curvedCW");
				m2.put("roundness", -0.4);
				m.put("smooth", m2);
			} else {
				m.put("to", id);
				m.put("from", (int) map.get("person2_id"));
				m.put("arrows", "from");
				m.put("dashes", true);
				Map<String, Object> m2 = new HashMap<>();
				m2.put("type", "curvedCW");
				m2.put("roundness", 0.4);
				m.put("smooth", m2);
			}
			m.put("label", getRelationByID((int) map.get("relation_id")));

			edges.add(m);
			if (!myVisitedList.contains((int) map.get("person2_id"))) {
				nodesEdges((int) map.get("person2_id"), myVisitedList, nodes, edges);
			}
		}
		result.add(nodes);
		result.add(edges);
		return result;
	}

	@Override
	public String toJson(Object obj) {
		String jsonInString = "Empty";
		try {
			ObjectMapper mapper = new ObjectMapper();
			jsonInString = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonInString;
	}

	@Override
	public List<Integer> dfs(int id) {
		List<Integer> visitedList = new LinkedList<Integer>();
		List<Integer> unvisitedList = new LinkedList<Integer>();
		unvisitedList.add(id);
		while (!unvisitedList.isEmpty()) {
			// Person curPer = unvisitedList.remove(0);
			Integer curPer = unvisitedList.remove(0);
			// Person pTemp = getPersonByID(curPer);
			List<Map<String, Object>> adjacentList = getAdjacentRelations(getPersonByID(curPer));
			// for (Map<String, Object> map : adjacentList) {
			// setPersonRelation(pTemp, getPersonByID((int)
			// map.get("person2_id")), (int) map.get("person2_id"));
			// }
			List<Integer> newP = new ArrayList<Integer>();
			adjacentList.forEach(item -> newP.add((Integer) item.get("person2_id")));
			List<Integer> newPeople = newP.stream().filter(person -> !visitedList.contains(person))
					.collect(Collectors.toList());
			unvisitedList.addAll(0, newPeople);
			visitedList.add(curPer);
		}

		return visitedList;
	}

	// @Override
	// public Person getTree(int id, List<Integer> visitedList) {
	// Person p = getPersonByID(id);
	// List<Integer> myVisitedList = visitedList;
	// myVisitedList.add(id);
	// List<Map<String, Object>> adjacentList = getAdjacentRelations(p);
	// for (Map<String, Object> map : adjacentList) {
	// if (!myVisitedList.contains((int) map.get("person2_id"))) {
	// Person pTemp = getTree((int) map.get("person2_id"), myVisitedList);
	// setPersonRelation(p, pTemp, (int) map.get("relation_id"));
	// }
	// }
	// return p;
	// }

	// @Override
	// public int setPersonRelation(Person person, Person relative, int
	// relation_id) {
	// if (relation_id == 19 || relation_id == 20) {
	// person.setSibling(relative);
	// } else if (relation_id == 3 || relation_id == 4) {
	// if (person.getChildren() == null) {
	// List<Person> children = new ArrayList<Person>();
	// children.add(relative);
	// person.setChildren(children);
	// } else {
	// person.getChildren().add(relative);
	// }
	//
	// } else if (relation_id == 5 || relation_id == 6) {
	// if (relative.getGender().equals("male")) {
	// person.setFather(relative);
	// } else if (relative.getGender().equals("female")) {
	// person.setMother(relative);
	// }
	// }
	//
	// return 0;
	// }

	@Override
	public int getRelationID(String relation) {
		logger.info("getting id of relation " + relation);
		String sql = "select id from relation where type = ?";
		int id = (Integer) jdbcTemplate.queryForObject(sql, new Object[] { relation }, Integer.class);
		return id;
	}

	@Override
	public String getRelationByID(int id) {
		logger.info("getting relation name of id= " + id);
		String sql = "select type from relation where id = ?";
		String relation = jdbcTemplate.queryForObject(sql, new Object[] { id }, String.class);
		return relation;
	}

	@Override
	public List<String> getRelationList() {
		logger.info("getting all relation types ");
		String sql = "select type from relation";
		List<String> myList = new ArrayList<>();
		myList = jdbcTemplate.queryForList(sql, String.class);
		return myList;
	}

	@Override
	public List<String> getNamesList() {
		logger.info("getting all people names ");
		String sql = "select name from person";
		List<String> myList = new ArrayList<>();
		myList = jdbcTemplate.queryForList(sql, String.class);
		return myList;
	}

	@Override
	public boolean userExists(Person person) {
		logger.info("checking user existence");
		String sql = "select * from person where name = ? and birthdate LIKE ?";
		Object[] params = { person.getName(), person.getBirthDate() };
		int[] argTypes = { Types.VARCHAR, Types.DATE };
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params, argTypes);
		if (list.size() > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean relationExists(int id1, int id2, int rid) {
		logger.info("checking relation existence");
		String sql = "select * from person_relation where person1_id = ? and person2_id = ? and relation_id = ?";
		Object[] params = { id1, id2, rid };
		int[] argTypes = { Types.INTEGER, Types.INTEGER, Types.INTEGER };
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params, argTypes);
		if (list.size() > 0)
			return true;
		else
			return false;
	}

}
