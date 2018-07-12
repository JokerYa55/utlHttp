/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sso_b2b.loggerEvent;

import java.util.Date;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sso_b2b.interfaces.restLoggerInterface;

/**
 *
 * @author vasiliy.andricov
 */
public class logEventAPI implements restLoggerInterface {

    Logger log = Logger.getLogger(getClass().getName());
    private String url;
    // Заголовки ответов
    private final String HEADER_CACHE_CONTROL_NAME = "Cache-Control";
    private final String HEADER_CACHE_CONTROL_VAL = "no-store";
    private final String HEADER_PRAGMA_NAME = "Pragma";
    private final String HEADER_PRAGMA_VAL = "no-cache";

    public logEventAPI(String url) {
        this.url = url;
    }

    @Override
    public void addEventLog(String p_realm, String p_client_id, Date event_time, String p_ip, String p_resource_path, String p_oper_type, String p_session_id, String p_user_id, String p_token_id, String p_error, String p_detail_json) {
        Client client = ClientBuilder.newBuilder().build();
        log.info(String.format("host = %s", this.url));        
        WebTarget target = client.target(String.format("%s/api/realms/%s/log/event", this.url, p_realm));
        log.info(String.format("url = %s", target.getUri()));
        LogEvent event = new LogEvent();
        event.setClient_id(p_client_id);
        event.setDetails_json(p_detail_json);
        event.setError(p_error);
        event.setIp_address(p_ip);
        event.setOperation_type(p_oper_type);
        event.setRealm_id(p_realm);
        event.setResource_path(p_resource_path);
        event.setSession_id(p_session_id);
        event.setToken_id(p_token_id);
        event.setUser_id(p_user_id);
        event.setEvent_time(event_time);
        Response res = target.request().
                //header("Authorization", "Bearer " + getUserToken()).
                header(HEADER_CACHE_CONTROL_NAME, HEADER_CACHE_CONTROL_VAL).
                header(HEADER_PRAGMA_NAME, HEADER_PRAGMA_VAL).
                post(Entity.entity(event, MediaType.APPLICATION_JSON));
        log.info("res = " + res.getStatus());
    }

}
