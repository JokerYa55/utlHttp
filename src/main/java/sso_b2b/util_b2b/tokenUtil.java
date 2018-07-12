/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sso_b2b.util_b2b;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.keycloak.representations.idm.UserRepresentation;

/**
 *
 * @author vasiliy.andricov
 */
public class tokenUtil {

    private String token;
    private JSONArray json_arr = null;
    private JSONObject json_token = null;
    private JSONObject json_role = null;
    private String user_id = null;
    private String client_id = null;
    private UserRepresentation user = null;

    public tokenUtil(String token) throws ParseException {
        this.token = token;
        this.json_arr = getDecodedJwt(token);
        this.json_token = parseToken((String) this.json_arr.get(1));
        this.json_role = (JSONObject) json_token.get("resource_access");
        this.user_id = (String) json_token.get("sub");
        this.client_id = (String) json_token.get("aud");
    }

    public UserRepresentation getUser() {
        return user;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public boolean verifyToken() throws ParseException {
        System.out.println("verifyToken");
        boolean res = false;
        res = verifyTokenByRest();
        return res;
    }

    public boolean isUserInRole(String role, String client) {
        System.out.println("isUserInRole");
        boolean result = false;
        try {
            System.out.println("this.json_role = " + this.json_role.toJSONString());
            JSONArray role_arr = (JSONArray) ((JSONObject) this.json_role.get(client)).get("roles");
            System.out.println("role_arr = " + role_arr.toJSONString());
            for (Object item : role_arr) {
                System.out.println("item = " + item);
                if (((String) item).equals(role)) {
                    result = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("error = " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("result = " + result);
        return result;
    }

    private JSONObject parseToken(String token) throws ParseException {
        System.out.println("parseToken");
        JSONObject result = null;
        JSONParser parser = new JSONParser();
        result = (JSONObject) parser.parse(token);
        return result;
    }

    private JSONObject getUserRole() {
        return (JSONObject) json_token.get("resource_access");
    }

    private boolean verifyTokenByRest() throws ParseException {
        System.out.println("verifyTokenByRest");
        boolean result = false;
        String url_sso = (String) json_token.get("iss");
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(String.format("%s/protocol/openid-connect/userinfo", url_sso));
        System.out.println("url = " + target.getUri());
        Response res = target.request().header("Authorization", "Bearer " + token).get();
        System.out.println("res = " + res.getStatus());
        if (res.getStatus() == 200) {
            System.out.println(res.readEntity(String.class));
            //user = res.readEntity(UserRepresentation.class);
            result = true;
        }
        return result;
    }

    public static JSONArray getDecodedJwt(String jwt) {
        System.out.println("getDecodedJwt");
        JSONArray result = new JSONArray();
        JSONParser parser = new JSONParser();
        String[] parts = jwt.split("[.]");
        try {
            int index = 0;
            for (String part : parts) {
                //System.out.println("parts = " + part);
                if (index >= 2) {
                    break;
                }
                index++;
                byte[] partAsBytes = part.getBytes("UTF-8");
                String decodedPart = new String(java.util.Base64.getUrlDecoder().decode(partAsBytes), "UTF-8");
                //System.out.println("decode_part = " + decodedPart);
                JSONObject temp_part = (JSONObject) parser.parse(decodedPart);
                result.add(decodedPart);
                //result += decodedPart + "<endPart>";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldnt decode jwt", e);
        }
        return result;
    }

}
