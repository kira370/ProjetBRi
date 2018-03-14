package bri;


import java.io.*;
import java.net.*;


public class ServeurBRi implements Runnable {
	private ServerSocket listen_socket;
	private Class <? extends Runnable> serverClass;
	
	// Cree un serveur TCP - objet de la classe ServerSocket
	public ServeurBRi(int port, Class <? extends Runnable> c) {
		try {
			listen_socket = new ServerSocket(port);
			serverClass = c;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Le serveur ecoute et accepte les connections.
	// pour chaque connection, il cree un ServiceInversion, 
	// qui va la traiter.
	public void run() {
		try {
			//TODO : on veut que ServiceProgrammeur & ServiceAmateur extend ServiceBri, et on veut ici d�finir quel ServiceBri sera lanc� par le serveur selon le TypeUser
			while(true){
				//TODO : on veut lancer la classe de service donn�e en argument dans le constructeur, PAS LE SERVICE DIRECTEMENT ! ! !
				new Thread(new ServiceProgBri(listen_socket.accept())).start();
				System.out.println("nouvelle connexion sur " + serverClass);
			}
		}
		catch (IOException e) { 
			try {this.listen_socket.close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'�coute :"+e);
		}
	}

	 // restituer les ressources --> finalize
	protected void finalize() throws Throwable {
		try {this.listen_socket.close();} catch (IOException e1) {}
	}

	// lancement du serveur
	public void lancer() {
		(new Thread(this)).start();		
	}
}
