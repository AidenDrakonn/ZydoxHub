package me.drakonn.zydoxhub.utils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;

public class Ping {

    public Ping() {
    }

    public static int maxpl(String adress, int port, int timeout) {
        int max;
        try {
            Socket socket = new Socket();
            socket.setSoTimeout(120);
            socket.connect(new InetSocketAddress(adress, port), timeout);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-16BE"));
            dataOutputStream.write(new byte[]{-2, 1});
            int packetId = inputStream.read();
            if (packetId == -1) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            if (packetId != 255) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Invalid packet ID (" + packetId + ").");
            }

            int length = inputStreamReader.read();
            if (length == -1) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            if (length == 0) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Invalid string length.");
            }

            char[] chars = new char[length];
            if (inputStreamReader.read(chars, 0, length) != length) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            String string = new String(chars);
            String[] data;
            if (string.startsWith("§")) {
                data = string.split("\u0000");
                socket.close();
                max = Integer.parseInt(data[5]);
            } else {
                data = string.split("§");
                socket.close();
                max = Integer.parseInt(data[2]);
            }

            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
        } catch (SocketException var14) {
            max = 0;
        } catch (IOException var15) {
            max = 0;
        }

        return max;
    }

    public static int onpl(String adress, int port, int timeout) {
        int max;
        try {
            Socket socket = new Socket();
            socket.setSoTimeout(120);
            socket.connect(new InetSocketAddress(adress, port), timeout);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-16BE"));
            dataOutputStream.write(new byte[]{-2, 1});
            int packetId = inputStream.read();
            if (packetId == -1) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            if (packetId != 255) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Invalid packet ID (" + packetId + ").");
            }

            int length = inputStreamReader.read();
            if (length == -1) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            if (length == 0) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Invalid string length.");
            }

            char[] chars = new char[length];
            if (inputStreamReader.read(chars, 0, length) != length) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            String string = new String(chars);
            String[] data;
            if (string.startsWith("§")) {
                data = string.split("\u0000");
                socket.close();
                max = Integer.parseInt(data[4]);
            } else {
                data = string.split("§");
                socket.close();
                max = Integer.parseInt(data[1]);
            }

            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
        } catch (SocketException var14) {
            max = 0;
        } catch (IOException var15) {
            max = 0;
        }

        return max;
    }

    public static boolean online(String adress, int port, int timeout) {
        boolean on = false;

        try {
            Socket socket = new Socket();
            socket.setSoTimeout(120);
            socket.connect(new InetSocketAddress(adress, port), timeout);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-16BE"));
            dataOutputStream.write(new byte[]{-2, 1});
            int packetId = inputStream.read();
            if (packetId == -1) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            if (packetId != 255) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Invalid packet ID (" + packetId + ").");
            }

            int length = inputStreamReader.read();
            if (length == -1) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            if (length == 0) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Invalid string length.");
            }

            char[] chars = new char[length];
            if (inputStreamReader.read(chars, 0, length) != length) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            String string = new String(chars);
            if (string.startsWith("§")) {
                socket.close();
                on = true;
            } else {
                socket.close();
                on = true;
            }

            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
        } catch (SocketException var13) {
            on = false;
        } catch (IOException var14) {
            on = false;
        }

        return on;
    }

    public static String motd(String adress, int port, int timeout) {
        String motd = "";

        try {
            Socket socket = new Socket();
            socket.setSoTimeout(120);
            socket.connect(new InetSocketAddress(adress, port), timeout);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-16BE"));
            dataOutputStream.write(new byte[]{-2, 1});
            int packetId = inputStream.read();
            if (packetId == -1) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            if (packetId != 255) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Invalid packet ID (" + packetId + ").");
            }

            int length = inputStreamReader.read();
            if (length == -1) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            if (length == 0) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Invalid string length.");
            }

            char[] chars = new char[length];
            if (inputStreamReader.read(chars, 0, length) != length) {
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                throw new IOException("Premature end of stream.");
            }

            String string = new String(chars);
            String[] data;
            if (string.startsWith("§")) {
                data = string.split("\u0000");
                socket.close();
                motd = data[3];
            } else {
                data = string.split("§");
                socket.close();
                motd = data[0];
            }

            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();
        } catch (SocketException var14) {
            motd = "";
        } catch (IOException var15) {
            motd = "";
        }

        return motd;
    }
}
