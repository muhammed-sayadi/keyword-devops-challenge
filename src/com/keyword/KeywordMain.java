package com.keyword;

import com.keyword.service.KeywordHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Main class for running the application
 */
public class KeywordMain {

    private static Logger logger = LoggerFactory.getLogger(KeywordMain.class);

    private static final int port = 8080;

    public static void main(String[] args) {

        Server server = new Server();
        ServerConnector http = new ServerConnector(server);
        http.setPort(port);
        server.setConnectors(new Connector[]{http});

        server.setHandler(new KeywordHandler());

        try {
            server.start();
            logger.info("Server initialized");
        } catch (Exception e) {
            logger.error("Exception while initializing server " + e.getMessage());
            System.exit(1);
        }



    }
}
