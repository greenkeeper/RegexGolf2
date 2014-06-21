package regexgolf2.services.persistence.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import regexgolf2.model.Word;
import regexgolf2.services.persistence.Database;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public class WordMapper
{
	private final Database _db;
	
	
	
	@Requires("db != null")
	public WordMapper(Database db)
	{
		_db = db;
	}
	
	
	
	@Ensures("result != null")
	public List<Word> getAll() throws SQLException
	{
		List<Word> result = new ArrayList<>();
		
		String sql = "SELECT id, text FROM words; ";
		
		PreparedStatement ps = _db.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next())
		{
			Word word = new Word();
			word.setId(rs.getInt(1));
			word.trySetText(rs.getString(2));
			
			result.add(word);
		}
		ps.close();
		
		return result;
	}

	@Requires("word != null")
	public void insert(Word word) throws SQLException
	{
		int id = getNextWordId(); //Cache id in field first, only set to word if insert was successful
		
		String sql = "INSERT INTO words (id, text) VALUES (?, ?); "; 
		
		PreparedStatement ps = _db.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.setString(2, word.getText());
		ps.execute();
		ps.close();
		
		//everything was successful; set ID in word-object
		word.setId(id);
	}
	
	private int getNextWordId() throws SQLException
	{
		String sql = "SELECT CASE WHEN count(*) = 0 THEN 1 ELSE max(id) + 1 END FROM words; ";
		
		PreparedStatement ps = _db.getConnection().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		int nextId = rs.getInt(1);
		ps.close();
		return nextId;
	}
	
	@Requires("word != null")
	public void update(Word word) throws SQLException
	{
		String sql = "UPDATE words SET text = ? WHERE id = ?; ";
		
		PreparedStatement ps = _db.getConnection().prepareStatement(sql);
		ps.setString(1, word.getText());
		ps.setInt(2, word.getId());
		ps.execute();
		ps.close();
	}
	
	public void delete(int id) throws SQLException
	{
		String sql = "DELETE FROM words WHERE id = ?; ";
		
		PreparedStatement ps = _db.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.execute();
		ps.close();
	}
}