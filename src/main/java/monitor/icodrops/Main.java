package monitor.icodrops;

import monitor.icodrops.Telegram.MonitorBot;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new MonitorBot().start();
        System.out.println("Bot started");
    }

}
