package model;

public class User {
	private int id;
	private String name;
	private String gender;
	private String email;
	private String senha; 

	public User() {
		this(0);
	}

	public User(int id) {
		this.id = id;
		setName("");
		setGender("");
		setEmail("");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSenha() { 
		return senha;
	}

	public void setSenha(String senha) { 
		this.senha = senha;
	}
}