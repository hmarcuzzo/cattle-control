package br.com.cattle_control.starter.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.persistence.Entity;


@Entity
@Data
@NoArgsConstructor
@Table(name = "Cattle")
public class Cattle implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Cattle_ID")
    private Integer id;

    @Column(name = "Cattle_numbering")
    private Integer numbering;

    @Column(name = "Cattle_weight")
    private Float weight;

    @Column(name = "Cattle_price")
    private Float price;

    @ManyToOne
    @JoinColumn(name= "Id_Farm")
    private Farm farm;

    @Column(name = "Cattle_Info")
    @Length(min = 0, max= 1000, message = "*A descrição deve ter no máximo 1000 caracteres")
    private String info;

    @Column(name = "Cattle_Deleted")
    private Boolean deleted;

    @Builder 
	public static Cattle create (
            Integer id,
			Integer numbering,
			Float  weight,
            Float price,
            Farm farm,
			String  info,
			Boolean deleted) {
		
    	
    	Cattle instance = new Cattle();
    	
    	instance.setId(id);
        instance.setWeight(weight);
        instance.setPrice(price);
        instance.setFarm(farm);
    	instance.setNumbering(numbering);
    	instance.setInfo(info);
        instance.setDeleted(deleted);
        
        return instance;
    }
}