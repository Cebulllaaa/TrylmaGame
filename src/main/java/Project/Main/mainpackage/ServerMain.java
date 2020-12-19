package Project.Main.mainpackage;

import java.util.Scanner;

import Project.server.main.Server;
import Project.server.main.ServerActivities;
import Project.server.main.ThreadConnectionServer;

/**
 * Hello world!
 *
 */
public class ServerMain 
{	

	Server server;
	static int port_server;
	ThreadConnectionServer connection;
	boolean end =false;
	boolean correct_move = true;
    public static void main( String[] args )
    {
        port_server = Integer.parseInt(args[0]);
       System.out.println("Rozpoczynanie tworzenia serwera");
       ServerMain main = new ServerMain();
       main.create_server();
        
    }
    public void create_server() {
    	server = new Server(port_server);
    	server.create_Server();
    	System.out.println("Utworzono serwer przechodze do interfejsu serwera");
    	this.interaction();
    }
    public void interaction() {
    	String cmd;
    	Scanner scan;
    	System.out.println("Aby otworzyc serwer i zaczac rozsylac ID wcisnij cokolwiek");
    	scan = new Scanner(System.in);
    	cmd = scan.nextLine();
    	connection = new ThreadConnectionServer(server);
    	connection.start();
    	System.out.println("Aby skonczyc wpuszczanie graczy i wyslac im informacje wcisnij cokolwiek");
    	cmd = scan.nextLine();
   
    	if(server.command_ms.game.Players.size() <2) {
    		System.out.println("przyjeto za mala ilosc graczy");
    		System.out.println(server.command_ms.game.Players.size());
    		System.exit (0);
    	}
    	if(server.command_ms.game.Players.size() > 6) {
    		System.out.println("przyjeto za duza ilosc graczy");
    		System.exit (0);
    	}
    	if(server.command_ms.game.Players.size()  == 5) {
    		System.out.println("Gra nie jest przeznaczona dla 5 graczy");
    		System.exit (0);
    	}
    	System.out.println("Przyjeto " + Integer.toString(server.command_ms.game.Players.size()) + " graczy");
    	
    	server.command_ms.activiti = ServerActivities.SEND_GAME_INFORMATION;
    	System.out.println("Aby skonczyc wysylanie informacji wcisnij cokolwiek");
    	scan.nextLine();
    	
    	server.command_ms.activiti = ServerActivities.SEND_START_GAME;
       	System.out.println("Aby skonczyc wysylanie informacji o starcie gry wcisnij cokolwiek");
    	scan.nextLine();
	
    	server.command_ms.game.create_Queue();
    	in_game();

    	
    	
   
    }
    public void in_game() {
		System.out.println("Rozpoczynam gre");
    	Scanner sc = new Scanner(System.in);
    	while(true) {
    		server.command_ms.activiti=ServerActivities.SEND_BOARD;
        	System.out.println("Aby skonczyc wysylanie informacji o tablicy wcisnij cokolwiek");
        	sc.nextLine();
    		server.command_ms.activiti=ServerActivities.SEND_WHOSE_TURN;
        	System.out.println("Aby skonczyc wysylanie informacji o turze wcisnij cokolwiek");
        	sc.nextLine();
    		System.out.println(server.command_ms.getCommand());
    		server.command_ms.activiti=ServerActivities.LISTEN;
    		server.command_ms.setCommand("");
    		while(server.command_ms.getCommand().isEmpty()) {
    			
    		}
    		// sprawdzamy czy poprawny ruch TODO
    		if(correct_move) {
    			server.command_ms.game.increase_Queue();
    		}
    		//sprawdzamy czy ktos skonczyl
    		if(end) {
    			end_game();
    		}
    		
    	}
    }
    public void end_game() {
    	System.out.println("Koncze gre");
    	server.command_ms.activiti=ServerActivities.SEND_END_GAME;
    	System.out.println("Aby skonczyc wysylanie informacji wcisnij cokolwiek");
    	server.connection_iterator=1;
    	System.out.println("Aby skonczyc wysylanie informacji wcisnij cokolwiek");
    }
}
