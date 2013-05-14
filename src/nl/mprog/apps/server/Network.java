package nl.mprog.apps.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	static public final int port = 54555;

    // This registers objects that are going to be sent over the network.
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Move.class);
        kryo.register(Connect.class);
        kryo.register(Error.class);
        kryo.register(Data.class);
    }

    static public class Move {
        public int x;
        public int y;
        public String game_id;
        public int color_id;
        public String player_id;
    }
    
    static public class Connect {
    	public String id;
    }
    
    static public class Data {
    	public int key;
    	public String value;
    }
    
    static public class Error {
    	public int error;
    	public String message;
    }
}
