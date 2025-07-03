package model;

// import java.math.BigDecimal; // Remova esta linha

public class Jogos {
	private int id;
	private String nome;
	private String genero;
	private String plataforma;
	private int ano;
	private double preco; // Alterado para double
	private String desenvolvedora;
	private User usuario;

	public Jogos() {
		this(0);
	}

	public Jogos(int id) {
		this.id = id;
		this.nome = "";
		this.genero = "";
		this.plataforma = "";
		this.desenvolvedora = "";
		this.ano = 0;
		this.preco = 0.0; // Inicializar para 0.0
	}

	// Getters e Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public double getPreco() { // Retorno alterado para double
		return preco;
	}

	public void setPreco(double preco) { // Parâmetro alterado para double
		this.preco = preco;
	}

	public String getDesenvolvedora() {
		return desenvolvedora;
	}

	public void setDesenvolvedora(String desenvolvedora) {
		this.desenvolvedora = desenvolvedora;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Jogos{" + "id=" + id + ", nome='" + nome + '\'' + ", genero='" + genero + '\'' + ", plataforma='"
				+ plataforma + '\'' + ", ano=" + ano + ", preco=" + preco + ", desenvolvedora='" + desenvolvedora + '\''
				+ ", usuario=" + (usuario != null ? usuario.getName() : "N/A") + '}';
	}
}