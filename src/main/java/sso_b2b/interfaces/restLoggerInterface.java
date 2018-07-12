/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sso_b2b.interfaces;

import java.util.Date;

/**
 *
 * @author vasiliy.andricov
 */
public interface restLoggerInterface {
    public void addEventLog(String p_realm, String p_client_id, Date event_time, String p_ip, String p_resourse_path, String p_oper_type, String p_session_id, String p_user_id, String p_token_id,String p_error, String p_detail_json);
}
