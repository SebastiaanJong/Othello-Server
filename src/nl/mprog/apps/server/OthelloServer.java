package nl.mprog.apps.server;

import java.io.IOException;

import nl.mprog.apps.server.Network.Connect;
import nl.mprog.apps.server.Network.Data;
import nl.mprog.apps.server.Network.Move;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class OthelloServer {
	
	Server server;
	PlayerQueue playerQueue;
	GameStorage games;
	
	public OthelloServer() throws IOException{
		
		playerQueue = new PlayerQueue();
		games = new GameStorage();
		server = new Server();
		server.start();
		server.bind(54555);
		
		System.out.println("Started server on TCP port 54555");
		
		Network.register(server);
		
		server.addListener(new Listener() {
			
			   public void received (Connection connection, Object object) {
				   
				  if (object instanceof Connect) {
					  
					  Connect data = (Connect) object;
					  
					  Player player = new Player();
					  
					  player.player_id = data.id;
					  player.connection_id = connection.getID();
					  
					  System.out.println("A player with android device ID " + data.id + " and IP " + connection.getRemoteAddressTCP() + " connected.");
					  
					  Game game = playerQueue.attemptGameCreate(player);
					  
					  if(game == null) return;
					  
					  System.out.println("Created game with game_id: " + game.game_id);
					  	  
					  games.addGame(game.game_id, game);
					  
					  Data response = new Data();
					  response.key = 1;
					  response.value = game.game_id;
					  
					  server.sendToTCP(game.player_one.connection_id, response);
					  server.sendToTCP(game.player_two.connection_id, response);

					  return;
				  }
			      if (object instanceof Move) {
			    	  Move move = (Move) object;
			      }
			      
			   }
		});
	}

	public static void main(String[] args) throws IOException {
		new OthelloServer();
	}

}