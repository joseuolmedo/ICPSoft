package modelo;

public class Usuario{
	private String nombre, alias, contrasenia, nivel;
	private boolean estado;
	
	public Usuario(String nombre, String alias, String contrasenia, String nivel, boolean estado){
		this.nombre = nombre;
		this.alias = alias;
		this.contrasenia = contrasenia;
		this.nivel = nivel;
		this.estado = estado;
	}

	public String getNombre(){
		return nombre;
	}

	public void setNombre(String nombre){
		this.nombre = nombre;
	}

	public String getAlias(){
		return alias;
	}

	public void setAlias(String alias){
		this.alias = alias;
	}

	public String getContrasenia(){
		return contrasenia;
	}

	public void setContrasenia(String contrasenia){
		this.contrasenia = contrasenia;
	}

	public String getNivel(){
		return nivel;
	}

	public void setNivel(String nivel){
		this.nivel = nivel;
	}

	public boolean getEstado(){
		return estado;
	}

	public void setEstado(boolean estado){
		this.estado = estado;
	}
	
}
