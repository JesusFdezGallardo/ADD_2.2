package dam2.add.p22;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import dam2.add.p22.service.ApiService;
import dam2.add.p22.service.Login;
import dam2.add.p22.modelo.Provincia;
import dam2.add.p22.modelo.Usuario;
import dam2.add.p22.modelo.UsuarioDAO;

public class Main {

	final static String RUTALOG = "./ficheros/login.log";

	public static void main(String[] args) {

		Scanner entrada = new Scanner(System.in);

		boolean cerrarMenu = false;

		while (!cerrarMenu) {
			
			System.out.println("Bienvenido. Elija una opción");
			System.out.println("1- Nuevo usuario");
			System.out.println("2- Logearse como usuario");
			System.out.println("3- Cerrar Menú");

			int opcion = entrada.nextInt();

			switch (opcion) {

			case 1:
				menuNuevoUsuario();
				break;

			case 2:
				Scanner in = new Scanner(System.in);
				System.out.println("Introduce usuario");
				String user = in.nextLine();
				System.out.println("Introduce contraseña");
				String pass = in.nextLine();
				Usuario usuario = new Usuario(user, pass);
				//Compruebo si usuario existe en la BBDD
				boolean existe = UsuarioDAO.comprobarUsuarioExiste(usuario);

				if (existe) {
					//Compruebo si esta bloqueado
					Usuario usuarioID = UsuarioDAO.obtenerUsuario(usuario.getUsuario());
					boolean establoqueado = UsuarioDAO.comprobarUsuarioBloq(usuarioID);
					if (establoqueado) {
						System.out.println("Este usuario está bloqueado");
						break;
					} 
					menuLogin(usuario);
					break;
				} else {
					boolean nuevoUsuario = Login.comprobarPassIguales(usuario);
					if (nuevoUsuario) {
						//Obtengo provincias
						String provincia = menuApiProvincia();
						usuario.setProvincia(provincia);
						UsuarioDAO.anadir_usuario(usuario);
						break;
					} else {
						System.out.println("El usuario no se ha guardado");
						break;
					}
				}
			case 3:
				System.out.println("Cerrando menú");
				cerrarMenu = true;
				break;

			default:
				System.out.println("Solo números entre 1 y 3");
			}
		} // Cierra menu
	}

	public static void guardarLog(boolean estadoLog, Usuario usuario, int intentos) {

		Date fecha = new Date();

		BufferedWriter bw = null;

		try {

			bw = new BufferedWriter(new FileWriter(new File(RUTALOG), true));
			bw.write("Login " + estadoLog + " con fecha: " + fecha + ". Al usuario " + usuario.getUsuario()
					+ " le restaban " + intentos + " intentos.");
			bw.newLine();
		} catch (IOException errorDeFichero) {
			System.out.println("Ha habido problemas: " + errorDeFichero.getMessage());
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void menuNuevoUsuario() {

		List<Usuario> usuariosLista = UsuarioDAO.getListaUsuarios();
		Scanner e = new Scanner(System.in);
		System.out.println("Introduce un nuevo usuario.");
		String nuevoUser = e.nextLine();
		System.out.println("Introduce una contraseña");
		String nuevoPass = e.nextLine();
		System.out.println("Introduzca nuevamente la contraseñaa para confirmar");
		String nuevoPass2 = e.nextLine();
		boolean comprobacionPass = Login.comprobarPassIgual(nuevoPass, nuevoPass2);

		if (comprobacionPass) {
			Usuario usuarioNuevo = new Usuario();
			usuarioNuevo.setUsuario(nuevoUser);
			usuarioNuevo.setPass(nuevoPass);

			if (nuevoUser.contains("@")) {
				usuarioNuevo.setUsuario(nuevoUser.replace("@", ""));
				usuarioNuevo.setAdmin(true);
			} else {
				usuarioNuevo.setAdmin(false);
			}
			usuarioNuevo.setBloqueado(false);
			
			//Obtengo provincias
			String provincia = menuApiProvincia();
			usuarioNuevo.setProvincia(provincia);
			boolean existe = UsuarioDAO.comprobarUsuarioExiste(usuarioNuevo);
			if (existe) {
				System.out.println("El usuario no se puede agregar porque ya existe");
			} else {
				UsuarioDAO.anadir_usuario(usuarioNuevo);
				System.out.println("El usuario " + usuarioNuevo.getUsuario() + " ha sido registrado con éxito");
			}
		} else {
			System.out.println("Las contraseñaas no coinciden. No se guardará el usuario");
		}
	}

	public static void menuAdministrador() {

		List<Usuario> listaUsuariosBD = UsuarioDAO.getListaUsuariosBloqueados();
		// Imprimo usuarios guardados
		boolean exit = false;
		while (!exit) {
			System.out.println(
					"Elija un nombre de la siguiente lista para desbloquear un usuario. Escriba EXIT para salir");
			for (int i = 0; i < listaUsuariosBD.size(); i++) {
				System.out.println(listaUsuariosBD.get(i).getUsuario());
			}
			Scanner userDesb = new Scanner(System.in);
			String nombre = userDesb.nextLine();
			if (nombre.equalsIgnoreCase("EXIT")) {
				exit = true;
			} else {	
				Usuario u = new Usuario(nombre, "");
				boolean existe = UsuarioDAO.comprobarUsuarioExiste(u);

				if (existe) {
					Usuario usuarioBloq = UsuarioDAO.obtenerUsuario(nombre);
					UsuarioDAO.desbloquear_usuario(usuarioBloq.getId());
					System.out.println(nombre + " ha sido desbloqueado");
				}else {
					System.out.println("No existe el usuario que quieres desbloquear");
				}
				listaUsuariosBD = UsuarioDAO.getListaUsuariosBloqueados();
				
				if (listaUsuariosBD.size() == 0) {
					System.out.println("No hay mas usuarios bloqueados");
					exit = true; 
				}
			}
		}
	}

	public static boolean menuLogin(Usuario usuario) {
		Scanner in = new Scanner(System.in);
		int intentos = 3;
		while (intentos > 0) {
			boolean login = UsuarioDAO.comprobarLogin(usuario);
			guardarLog(login, usuario, intentos);
			if (login) {
				Usuario usuarioID = UsuarioDAO.obtenerUsuario(usuario.getUsuario());
				boolean esAdmin = UsuarioDAO.comprobarUsuarioAdmin(usuarioID);
				if (esAdmin) {
					Main.menuAdministrador();
					return true;
				} 
				System.out.println("Hola " + usuario.getUsuario());
				return true;
			} else {
				intentos--;
				if (intentos <= 0) {
					System.out.println("Has superado los intentos máximos permitidos");
					Usuario usuarioID = UsuarioDAO.obtenerUsuario(usuario.getUsuario());
					UsuarioDAO.bloquear_usuario(usuarioID.getId());
					return false;
				}
				System.out.println("La contraseña no coincide con el usuario. Introduce nuevamente la contraseñaa");
				System.out.println("intentos restantes " + intentos);
//				guardarLog(login, usuario, intentos);
				String passUsuario = in.nextLine();
				usuario.setPass(passUsuario);
			}

		}
		return false;
	}
	
	public static String menuApiProvincia() {
		String provinciaElegida= ""; 
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("Cargando provincias...");
		String cadenaJson = ApiService.leerUrl();

		Provincia[] provincias = new Gson().fromJson(cadenaJson, Provincia[].class);
		ArrayList<Provincia> listaProvincias = new ArrayList<Provincia>();
		
		//Relleno ArrayList
		for (Provincia provincia : provincias) {
			listaProvincias.add(provincia);
		}
		
		//Ordeno
		Collections.sort(listaProvincias);
		
		//Imprimio 
		for (int i = 0; i < listaProvincias.size(); i++) {
			System.out.println(listaProvincias.get(i).getId() +" -- "+listaProvincias.get(i).getNombre());
		}
		System.out.println("Elija una provincia seleccionando su número");
		int opcion = entrada.nextInt();
		
		provinciaElegida = ApiService.obtenerProvincia(listaProvincias, opcion); 
		System.out.println(provinciaElegida);
		return provinciaElegida;
	}
	 
}
