package br.com.cattle_control.starter.model;

import java.io.Serializable;
import java.util.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;


@Entity
@Data
@NoArgsConstructor
@Table(name = "Action")
public class Action implements Serializable {
    /**
    *
    */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Action_ID")
    // @OnDelete(action = OnDeleteAction.CASCADE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name= "id_buyer")
    private People people_buyer;

    @ManyToOne
    @JoinColumn(name= "id_seller")
    private People people_seller;

    @ManyToOne
    @JoinColumn(name= "id_deliveryman")
    private People people_deliveryman;

    @Column(name = "Action_amount")
    private Integer amount;

    @Column(name = "Action_value")
    private Double value;

    @Column(name = "Action_date")
    private String date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "People_ID")
    private List<Cattle> cattle;

    @ManyToOne
    @JoinColumn(name= "id_payment_type")
    private PaymentType payment_type;

    @Column(name = "Action_divided")
    private Integer divided;

    @Column(name = "Action_deadline")
    private String deadline;

    @ManyToOne
    @JoinColumn(name= "id_place")
    private Place place;

    @Column(name = "Action_district")
    private String district;

    @Column(name = "Action_reference")
    private String reference;

    @Column(name = "Action_number")
    private Integer number;

    @Column(name = "Action_info")
    private String info;

    @Column(name = "Action_Deleted")
    private Boolean deleted;



    @Builder 
	public static Action create (
            Integer id,
            People  people_buyer,
            People  people_seller,
            People  people_deliveryman,
			Integer amount,
            Double  value,
            List<Cattle> cattle,
            String  date,
            PaymentType payment_type,
            Integer divided,
            String  deadline,
            Place   place,
            String  district,
            String  reference,
            Integer number,
            String  info,
            Boolean deleted) {
		
    	
    	Action instance = new Action();
    	
    	instance.setId(id);
        instance.setPeople_buyer(people_buyer);
        instance.setPeople_seller(people_seller);
        instance.setPeople_deliveryman(people_deliveryman);
        instance.setAmount(amount);
        instance.setValue(value);
        instance.setDate(date);
        if(cattle == null){
            cattle = new ArrayList<>();
            instance.setCattle(cattle);
        }
        else{
            instance.setCattle(cattle);
        } 
        instance.setPayment_type(payment_type);
        instance.setDivided(divided);
        instance.setDeadline(deadline);
        instance.setPlace(place);
        instance.setDistrict(district);
        instance.setReference(reference);
        instance.setNumber(number);
        instance.setInfo(info);
        instance.setDeleted(deleted);
        
        return instance;
    }
    
}
