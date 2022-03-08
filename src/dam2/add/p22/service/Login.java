package dam2.add.p22.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import dam2.add.p22.modelo.Usuario;
import dam2.add.p22.modelo.UsuarioDAO;

public class Login {

	public static boolean comprobarPassIguales(Usuario usuario) {

		Scanner entrada = new Scanner(System.in);

		System.out.println("El usuario no existe. Introduzca dos contraseñas iguales para registrarse");
		String pass1 = entrada.nextLine();
		System.out.println("Introduce contraseña nuevamente");
		String pass2 = entrada.nextLine();

		if (pass1.equalsIgnoreCase(pass2)) {
			usuario = new Usuario(usuario.getUsuario(), pass1);
			System.out
					.println("El usuario " + usuario.getUsuario() + usuario.getPass() + " se ha registrado con éxito");
			return true;
		} else {
			System.out.println("Las dos contraseñas no coinciden. No se guarda el nuevo usuario");
			return false;
		}
	}

	public static boolean comprobarPassIgual(String pass1, String pass2) {
		if (pass1.equalsIgnoreCase(pass2)) {
			return true;
		} else {
			return false;
		}
	}

}
