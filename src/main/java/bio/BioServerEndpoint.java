package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class BioServerEndpoint {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(10000, 20);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                InetAddress clientAddress = socket.getInetAddress();
                System.out.printf("accept client %s:%d\n", clientAddress, socket.getPort());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream inputStream = socket.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                            while (true) {
                                String data = bufferedReader.readLine();
                                if (null != data) {
                                    System.out.println(data);
                                } else {
                                    socket.close();
                                    System.out.println("client close ..");
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
