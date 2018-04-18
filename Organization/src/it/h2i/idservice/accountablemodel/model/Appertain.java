package it.h2i.idservice.accountablemodel.model;
// Generated 4-apr-2018 17.24.09 by Hibernate Tools 3.2.2.GA


import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="appertain"
    ,catalog="organization"
)
public class Appertain  implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1351332253379263301L;
	private AppertainId id;
     private User user;
     private Organization organization;
     private Role role;
   



	public Appertain() {
	
    }

    public Appertain(AppertainId id, User user, Organization organization, Role role) {
       this.id = id;
       this.user = user;
       this.organization = organization;
       this.role = role;
       
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="appertainIduser", column=@Column(name="appertain_iduser", nullable=false) ), 
        @AttributeOverride(name="appertainIdorganization", column=@Column(name="appertain_idorganization", nullable=false) ) } )
    public AppertainId getId() {
        return this.id;
    }
    
    public void setId(AppertainId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="appertain_iduser", nullable=false, insertable=false, updatable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="appertain_idorganization", nullable=false, insertable=false, updatable=false)
    public Organization getOrganization() {
        return this.organization;
    }
    
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="appertain_idrole", nullable=false)
    public Role getRole() {
        return this.role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }

    




}


