package dam2.add.p22.modelo;

import java.util.Collections;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Provincia implements Comparable<Provincia> {

	@SerializedName("id")
	@Expose
	private int id; 
	
	@SerializedName("nm")
	@Expose
	private String nombre;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Provincia(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	@Override
	public int compareTo(Provincia o) {
		if (o.getId() > id) {
			return -1;
		} else if (o.getId() > id) {
			return 0;
		} else {
			return 1;
		}
	} 

}
