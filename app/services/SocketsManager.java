package services;

import java.util.concurrent.CopyOnWriteArrayList;
import play.mvc.WebSocket.Out;

/**
 * Created by tzachit on 17/11/14.
 */

public class SocketsManager implements ISocketsManager {

    private CopyOnWriteArrayList<Out> _sockets;

    public SocketsManager(){
        this._sockets = new CopyOnWriteArrayList<Out>();
    }

    @Override
    public void add(Out out) {
        this._sockets.add(out);
    }

    @Override
    public void remove(Out out) {
        this._sockets.remove(out);
    }

    @Override
    public <T> void notifyAll(T message) {
        for (Out out : this._sockets){
            out.write(message);
        }
    }
}
