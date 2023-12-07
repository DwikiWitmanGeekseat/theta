package co.flexidev.theta.security;

public class Principal {

	private Long id;
	private String name;
	private String email;
	private String password;

	public Principal() {
	}

	public Principal(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Principal essence() {
		return new Principal(this.id, this.name, this.email);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
