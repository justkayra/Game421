package dev.kaira.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kaira.service.GameService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PlayerDataReceiver extends HttpServlet {

    ObjectMapper mapper = new ObjectMapper();
    GameService service;

    Logger logger = Logger.getLogger(PlayerDataReceiver.class.getName());

    public PlayerDataReceiver() throws IOException {
        service = new GameService();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info(request.getRequestURI());
        Map<String, Object> answer = null;
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);

            //it could be straightforward if we use form-based values, but we made it in accordance of modern trends i.e. JSON
            String mapText = jb.toString();
            Map<String, String> map = new ObjectMapper().readValue(mapText, Map.class);

            String actionType = map.get("type");
            if (actionType.equals("start")) {
                answer = service.doStart();
            } else if (actionType.equals("play")) {
                answer = service.play( map.get("token"),  map.get("item"));
            } else if (actionType.equals("reset")) {
                answer = service.reset();
            }
        } catch (Exception e) {
            answer = new HashMap<>();
            answer.put("error", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.getOutputStream().println(mapper.writeValueAsString(answer));
    }

}