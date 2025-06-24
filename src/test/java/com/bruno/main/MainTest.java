package com.bruno.main;

import com.bruno.Main;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    void shouldStartAndStopServer() throws Exception {
        Server server = Main.startServer();

        assert server.isStarted();

        server.stop();
        assert server.isStopped();
    }
}