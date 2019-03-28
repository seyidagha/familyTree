package dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.family.models.Person;

@Repository
public interface PersonDAO {

	int getRelationID(String relation);

	// int setPersonRelation(Person person, Person relative, int relation_id);

	int insert(Person p);

	int getPersonID(Person p);

	int insertRelation(int p1_id, int p2_id, int relation_id);

	int cleanDatabase();

	Person getPersonByID(int id);

	String delete(int id);

	String update(Person p);

	List<String> getNamesList();

	List<String> getRelationList();

	String getRelationByID(int id);
	//
	// Person getTree(int id, List<Integer> visitedList);

	List<Integer> dfs(int id);

	String toJson(Object obj);

	public boolean userExists(Person person);

	public boolean relationExists(int id1, int id2, int rid);

	List<List<Map<String, Object>>> nodesEdges(int id, List<Integer> visitedList, List<Map<String, Object>> myNodes,
			List<Map<String, Object>> myEdges);

	List<Map<String, Object>> getAdjacentRelations(Person p);

	void insertBothRelations(int p1_id, int p2_id, int relation_id, Date date1, Date date2, String gender1,
			String gender2);

}
