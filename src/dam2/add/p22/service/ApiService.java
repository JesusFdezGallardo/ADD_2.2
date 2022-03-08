package dam2.add.p22.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BeanComparator;

import com.google.gson.Gson;

import dam2.add.p22.Main;
import dam2.add.p22.modelo.Provincia;

public class ApiService {

	public static void main(String[] args) {

	}

	public static String leerUrl() {
		String output = "";
		try {
			URL url = new URL("https://raw.githubusercontent.com/IagoLast/pselect/master/data/provincias.json");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() != 200) {
				// si la respuesta del servidor es distinta al codigo 200 lanzaremos una
				// Exception
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			// creamos un StringBuilder para almacenar la respuesta del web service
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = br.read()) != -1) {
				sb.append((char) cp);
			}
			// en la cadena output almacenamos toda la respuesta del servidor
			output = sb.toString();
			// System.out.println(output);

			conn.disconnect();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return output;
	}

	public static String obtenerProvincia(ArrayList<Provincia> listaProvincias, int opcion) {
		String provincia = "";
		
		for (int i = 0; i < listaProvincias.size(); i++) {
			if (listaProvincias.get(i).getId() == opcion) {
				provincia = listaProvincias.get(i).getNombre();
				return provincia;
			}
		}
		provincia = "No ha elegido una provincia válida";
		return provincia;
	}

}
