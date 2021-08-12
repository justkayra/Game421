package dev.kaira.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kaira.service.TossAdviserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class TossAdviser extends HttpServlet  {

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(InfoProvider.class.getName());
    private TossAdviserService service = new TossAdviserService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        logger.info(request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> answer = new HashMap<>();
        answer.put("item", service.getRandomItem().getName());
        response.getOutputStream().println(mapper.writeValueAsString(answer));
    }


}