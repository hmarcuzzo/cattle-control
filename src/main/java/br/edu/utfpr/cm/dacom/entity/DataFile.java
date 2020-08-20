package br.edu.utfpr.cm.dacom.entity;

import java.io.Serializable;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class DataFile implements Serializable {

	@Id 
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
	
    
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    @NotNull
    private String nome;
    
    
    @Size(min = 0, max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.")
    private String descricao;
 
    private String nomeArquivoAnexo;
    
    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] arquivoAnexo;
       
    @Builder
    public static DataFile create (Long id, String nome, String descricao) {
        DataFile instance = new DataFile();

        instance.setId(id);
        instance.setNome(nome);
        instance.setDescricao(descricao);
        return instance;
    }
}
