package dev.kaira.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kaira.service.GameService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class InfoProvider extends HttpServlet {

    ObjectMapper mapper = new ObjectMapper();
    Logger logger = Logger.getLogger(InfoProvider.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        logger.info(request.getRequestURI());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        GameService service = new GameService();
        response.getOutputStream().println(mapper.writeValueAsString(service.getInfo()));
    }


}