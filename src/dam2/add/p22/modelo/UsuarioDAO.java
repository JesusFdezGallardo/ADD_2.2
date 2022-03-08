package dam2.add.p22.modelo;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dam2.add.p22.HibernateManager;
import dam2.add.p22.Main;

public class UsuarioDAO {

	// INSERT
	public static void anadir_usuario(Usuario a) {
		// Transaction tx = null;
		Session session = HibernateManager.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction(); // Se crea una transaccion
		int i = (int) session.save(a);// Guarda el objeto creado en la BBDD.
		System.out.println("Usuario creado con idUsuario: " + i);
		tx.commit(); // Materializa la transaccion
		session.close();
	}

	// SELECT
	// Recupera un objeto cuyo id se pasa como parametro
	public static Usuario recuperar_usuario_nombre(int id) {
		Session session = HibernateManager.getSessionFactory().openSession();
		session.beginTransaction();
		Usuario a = (Usuario) session.get(Usuario.class, id);
		if (a == null) {
			System.out.println("No existe el usuario");
		} else {
			System.out.println("Usuario: " + a.getUsuario() + "|| Contraseña: " + a.getPass());
		}
		session.close();
		return a;
	}
	
	/*
	 * Recuperar usuario por nombre desde bbdd
	 */
	public static Usuario obtenerUsuario(String nombre) {
		Session session = HibernateManager.getSessionFactory().openSession();
		session.beginTransaction();
		Query q = session.createQuery("SELECT a FROM Usuario a WHERE usuario like '" + nombre + "'");
		List<Usuario> results = q.list();
		Usuario usuario = results.get(0);
//		System.out.println(usuario.getUsuario());
		session.close();
		return usuario;
	}
	
	// UPDATE
	public static void bloquear_usuario(int id) {
		Session session = HibernateManager.getSessionFactory().openSession();
		session.beginTransaction();
		Usuario a = recuperar_usuario_nombre(id);
		if(a == null) {
			System.out.println("No existe el album");
		}
		else {
			a.setBloqueado(true);
			session.update(a);// Modifica el objeto con Id indicado
			session.getTransaction().commit(); // Materializa la transaccion
		} 
		session.close();
	}

	/*
	 * Desbloquear usuario
	 */
	public static void desbloquear_usuario(int id) {
		Session session = HibernateManager.getSessionFactory().openSession();
		session.beginTransaction();
		Usuario a = recuperar_usuario_nombre(id);
		if(a == null) {
			System.out.println("No existe el Usuario");
		}
		else {
			a.setBloqueado(false);
			session.update(a);
			session.getTransaction().commit(); 
		} 
		session.close();
	}

	// Borrar un objeto cuyo id se pasa como parametro
	public static void borrar_usuario(int id) {
		Session session = HibernateManager.getSessionFactory().openSession();
		session.beginTransaction();
		Usuario a = recuperar_usuario_nombre(id);// new Album(id);
		if (a == null) {
			System.out.println("No existe el Usuario");
		} else {
			session.delete(a);
			System.out.println("Objeto borrado");
			session.getTransaction().commit(); // Materializa la transaccion
		}
		session.close();
	}

	//Lista todos los usuarios
	public static List<Usuario> getListaUsuarios() {
		Session session = HibernateManager.getSessionFactory().openSession();
		session.beginTransaction();
		Query q = session.createQuery("SELECT a FROM Usuario a");
		List<Usuario> results = q.list();

//		for(Usuario cadena : results) {
//			System.out.println(" - " + results.toString());
//		}
		session.close();
		return results;
	}

	//lista usuarios bloqueados
	public static List<Usuario> getListaUsuariosBloqueados() {
		Session session = HibernateManager.getSessionFactory().openSession();
		session.beginTransaction();
		Query q = session.createQuery("SELECT a FROM Usuario a WHERE bloqueado = 1");
		List<Usuario> results = q.list();
		for(Usuario cadena : results) {
//			System.out.println(" - " + results.toString());
		}
		session.close();
		return results;
	}
	
	public static boolean comprobarUsuarioExiste(Usuario usuario) {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>(getListaUsuarios());
		Session session = HibernateManager.getSessionFactory().openSession();
		session.beginTransaction();
		for (int i = 0; i < listaUsuarios.size(); i++) {
//			System.out.println(listaUsuarios.get(i).getUsuario());
			if (listaUsuarios.get(i).getUsuario().equalsIgnoreCase(usuario.getUsuario())) {
//				System.out.println("Usuario existe");
				return true;
			} else {
//				System.out.println("Usuario incorrecto");
			}
		}
		session.close();
		return false;
	}

	public static boolean comprobarLogin(Usuario usuario) {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>(getListaUsuarios());
		Session session = HibernateManager.getSessionFactory().openSession();
		session.beginTransaction();
		for (int i = 0; i < listaUsuarios.size(); i++) {
//			System.out.println(listaUsuarios.get(i).getUsuario());
			if (listaUsuarios.get(i).getUsuario().equalsIgnoreCase(usuario.getUsuario())) {
				if (listaUsuarios.get(i).getPass().equalsIgnoreCase(usuario.getPass())) {
//					System.out.println("Usuario coincide");
					return true;
				} else {
//					System.out.println("Pass no coincide");
				}
			} else {
//				System.out.println("No coincide el usuario");
			}
		}
		session.close();
		return false;
	}

	//Comprobar admin
	public static boolean comprobarUsuarioAdmin(Usuario usuario) {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>(getListaUsuarios());
		Session session = HibernateManager.getSessionFactory().openSession();
		session.beginTransaction();
		for (int i = 0; i < listaUsuarios.size(); i++) {
			if (listaUsuarios.get(i).getUsuario().equalsIgnoreCase(usuario.getUsuario())) {
				if (usuario.isAdmin()) {
				System.out.println("Usuario es admin");
				return true;
				}
			} 
		}
		session.close();
		return false;
	}
	
	//Comprobar bloqueado
	public static boolean comprobarUsuarioBloq(Usuario usuario) {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>(getListaUsuarios());
		Session session = HibernateManager.getSessionFactory().openSession();
		session.beginTransaction();
		for (int i = 0; i < listaUsuarios.size(); i++) {
			if (listaUsuarios.get(i).getUsuario().equalsIgnoreCase(usuario.getUsuario())) {
				if (usuario.isBloqueado()) {
				System.out.println("El usuario esta bloqueado");
				return true;
				}
			} 
		}
		session.close();
		return false;
	}
	
	public static void main(String[] args) {
		Usuario a = new Usuario("laura", "laura123", false, false, "Melilla");
		UsuarioDAO.anadir_usuario(a);
////		UsuarioDAO.recuperar_usuario_nombre(1);
//		UsuarioDAO.modificar_usuario(1);
//		UsuarioDAO.borrar_usuario(2);
//		UsuarioDAO.consulta("SELECT a FROM Usuario a");
//		UsuarioDAO.obtenerUsuario("laura"); 
//		UsuarioDAO.getListaUsuarios();
//		UsuarioDAO. modificar_usuario(a.getId());
//		UsuarioDAO.desbloquear_usuario(1);
//			boolean existe = UsuarioDAO.comprobarUsuarioAdmin(a);
//			if (existe) {
//				System.out.println("Correcto");
//			} else {
//				System.out.println("No");
//			}

	}

}
