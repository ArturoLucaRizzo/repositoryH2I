package it.h2i.idservice.accountablemodel.model;
// Generated 4-apr-2018 17.24.09 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Transaction generated by hbm2java
 */
@Entity
@Table(name="transaction"
    ,catalog="organization"
)
public class Transaction  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = -5388758586558668331L;
	private Integer idtransaction;
     private Date date;
     private Function function;

    public Transaction() {
    }

	
    public Transaction(Date date) {
        this.date = date;
    }
    public Transaction(Date date, Function function) {
       this.date = date;
       this.function = function;
    }
    
    @OneToOne
    @JoinColumn(name = "idtransaction")
     public Function getFunction() {
		return function;
	}


	public void setFunction(Function function) {
		this.function = function;
	}


	@Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idtransaction", unique=true, nullable=false)
    public Integer getIdtransaction() {
        return this.idtransaction;
    }
    
    public void setIdtransaction(Integer idtransaction) {
        this.idtransaction = idtransaction;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date", nullable=false, length=10)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }





}


