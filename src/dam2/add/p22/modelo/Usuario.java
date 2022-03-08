package dam2.add.p22.modelo;

import java.io.*;

public class Usuario {
	
	private int id;
	private String usuario;
	private String pass;
	private boolean admin; 
	private boolean bloqueado;
	private String provincia;
	
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Usuario() {
		
	}
	
	public Usuario(int id, String usuario, String pass, boolean admin, boolean bloqueado, String provincia) {
		this.id = id;
		this.usuario = usuario;
		this.pass = pass;
		this.admin = admin;
		this.bloqueado = bloqueado;
		this.provincia = provincia;
	}
	
	public Usuario(String usuario, String pass) {
		super();
		this.usuario = usuario;
		this.pass = pass;
	}

	public Usuario(String usuario, String pass, boolean admin, boolean bloqueado, String provincia) {
		this.usuario = usuario;
		this.pass = pass;
		this.admin = admin;
		this.bloqueado = bloqueado;
		this.provincia = provincia;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Usuario(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", usuario=" + usuario + ", pass=" + pass + ", admin=" + admin + ", bloqueado="
				+ bloqueado + ", provincia=" + provincia + "]";
	}


}
