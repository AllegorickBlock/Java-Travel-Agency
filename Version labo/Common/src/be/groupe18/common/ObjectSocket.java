package be.groupe18.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectSocket implements AutoCloseable{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ObjectSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public void write(Object object) throws IOException {
        this.out.reset();
        this.out.writeObject(object);
        this.out.flush();

    }

    public <T> T read() throws IOException, ClassNotFoundException {
        return (T) in.readObject();
    }

    @Override
    public void close() throws Exception {
        out.close();
        in.close();
        socket.close();
    }
}
