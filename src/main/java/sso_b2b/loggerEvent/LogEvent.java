/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sso_b2b.loggerEvent;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vasiliy.andricov
 */
@Entity
@Table(name = "t_log_event")
@XmlRootElement
public class LogEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "client_id", nullable = true, unique = false, length = 255)
    private String client_id;
    @Column(name = "details_json", nullable = true, unique = false, columnDefinition = "text")
    private String details_json;
    @Column(name = "error", nullable = true, unique = false, length = 255)
    private String error;
    @Column(name = "ip_address", nullable = true, unique = false, length = 255)
    private String ip_address;
    @Column(name = "realm_id", nullable = true, unique = false, length = 255)
    private String realm_id;
    @Column(name = "session_id", nullable = true, unique = false, length = 255)
    private String session_id;
    @Column(name = "event_time", nullable = false, unique = false, columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date event_time;
    @Column(name = "resource_path", nullable = true, unique = false, length = 255)
    private String resource_path;
    @Column(name = "user_id", nullable = true, unique = false, length = 255)
    private String user_id;
    @Column(name = "operation_type", nullable = true, unique = false, length = 255)
    private String operation_type;
    @Column(name = "token_id", nullable = true, unique = false, length = 255)
    private String token_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getDetails_json() {
        return details_json;
    }

    public void setDetails_json(String details_json) {
        this.details_json = details_json;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getRealm_id() {
        return realm_id;
    }

    public void setRealm_id(String realm_id) {
        this.realm_id = realm_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public Date getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Date event_time) {
        this.event_time = event_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getResource_path() {
        return resource_path;
    }

    public void setResource_path(String resourse_path) {
        this.resource_path = resourse_path;
    }

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogEvent)) {
            return false;
        }
        LogEvent other = (LogEvent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LogEvent{" + "id=" + id + '}';
    }

}
