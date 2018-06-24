package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {
	
	private List<Person> people;
	
	private Connection con;
	
	public Database() {
		people = new LinkedList<Person>();
	}
	
	public void connect() throws Exception {
		if (con != null) return;
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}
		
		String url = "jdbc:postgresql://localhost:5432/swingtest";
		con = DriverManager.getConnection(url, "postgres", "Packers10");
		
		System.out.println("Connected: " + con);
	}
	
	public void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection");
			}
		}
	}
	
	public void save() throws SQLException {
		
		String checkSql = "select count(*) as count from people where id=?";
		PreparedStatement checkStmt = con.prepareStatement(checkSql);
		
		String insertSql = "insert into people (id, name, age, employment_status, "
				+ "tax_id, us_citizen, gender, occupation) values (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStmt = con.prepareStatement(insertSql);
		
		for (Person person: people) {
			int id = person.getId();
			String name = person.getName();
			String occupation = person.getOccupation();
			AgeCategory age = person.getAgeCategory();
			EmploymentCategory emp = person.getEmpCat();
			String tax = person.getTaxId();
			boolean isUs = person.isUsCitizen();
			Gender gender = person.getGender();
			
			checkStmt.setInt(1, id);
			
			ResultSet checkResult = checkStmt.executeQuery();
			
			checkResult.next();
			
			int count = checkResult.getInt(1);
			
			if (count == 0) {
				System.out.println("Inserting person with ID " + id);
				
				int col = 1;
				insertStmt.setInt(col, id);
				insertStmt.setString(col++, name);
				insertStmt.setString(col++, age.name());
				insertStmt.setString(col++, emp.name());
				insertStmt.setString(col++, tax);
				insertStmt.setBoolean(col++, isUs);
				insertStmt.setString(col++, gender.name());
				insertStmt.setString(col++, occupation);
				
				insertStmt.executeUpdate();
			} else {
				System.out.println("Updating person with ID " + id);
			}
		}
		
		insertStmt.close();
		checkStmt.close();
	}
	
	public void addPerson(Person person) {
		people.add(person);
	}
	
	public void removePerson(int index) {
		people.remove(index);
	}
	
	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}
	
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		Person[] persons = people.toArray(new Person[people.size()]);
		
		oos.writeObject(persons);
		
		oos.close();
	}
	
	public void loadFronFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			Person[] persons = (Person[])ois.readObject();
			
			people.clear();
			
			people.addAll(Arrays.asList(persons));
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ois.close();
	}
}
