package br.edu.utfpr.cm.dacom.view;



import java.io.ByteArrayInputStream;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


import lombok.RequiredArgsConstructor;
	
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.edu.utfpr.cm.dacom.entity.DataFile;
import br.edu.utfpr.cm.dacom.exception.AnyPersistenceException;
import br.edu.utfpr.cm.dacom.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cm.dacom.service.DataFileService;



@Component
@RequestScope
@RequiredArgsConstructor
public class DataFileView{

	@Autowired
    private final DataFileService dataFileService;
    
    private DataFile arquivoDeDados = new DataFile();
    
    private UploadedFile arquivoUpload;
    
    // Delegação de Getters and Setters das propriedades de arquivoDeDados que serão usadas na tela 
    public Long getId() {
		return arquivoDeDados.getId();
	}

	public String getDescricao() {
		return arquivoDeDados.getDescricao();
	}
	
	public String getNomeArquivoAnexo() {
		return arquivoDeDados.getNomeArquivoAnexo();
	}

	public String getNome() {
		return arquivoDeDados.getNome();
	}
	
	public void setDescricao(String descricao) {
		arquivoDeDados.setDescricao(descricao);
	}

	public void setNome(String nome) {
		arquivoDeDados.setNome(nome);
	}
	    
	// Tratamento do upload e download do arquivo anexo
	public UploadedFile getArquivoUpload() {
		return arquivoUpload;
	}

	public void setArquivoUpload(UploadedFile umArquivoUpload) {
		this.arquivoUpload = umArquivoUpload;
	}
	
	public StreamedContent downloadArquivoAnexo(DataFile umArquivoDeDados) {
		String tipoArquivo = dataFileService.getMimeType(umArquivoDeDados);
		StreamedContent umArquivoDownload = new DefaultStreamedContent(new ByteArrayInputStream(umArquivoDeDados.getArquivoAnexo()), 
				                                                       tipoArquivo, 
				                                                       umArquivoDeDados.getNomeArquivoAnexo());
		
		return umArquivoDownload;
	}
	
	
	// Ações disponibilizadas na tela
    public String create() {
        this.arquivoDeDados = DataFile.builder()
        						.nome(arquivoDeDados.getNome())
        						.descricao(arquivoDeDados.getDescricao())
        						.build();
        
        if (this.getArquivoUpload()!=null) {
        	this.arquivoDeDados.setNomeArquivoAnexo(this.getArquivoUpload().getFileName());
        	this.arquivoDeDados.setArquivoAnexo(this.getArquivoUpload().getContents());
        }
        
        FacesContext.getCurrentInstance()
			.getExternalContext()
			.getFlash()
			.setKeepMessages(true);
        
        try {
            dataFileService.create(this.arquivoDeDados);
            FacesContext.getCurrentInstance()
            			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
            												"Info", 
            												String.format("Arquivo [%s] criado com sucesso!", 
            												this.arquivoDeDados.getNome())));  
            
            return "index.xhtml?faces-redirect=true";
        } catch (EntityAlreadyExistsException e) {
            FacesContext.getCurrentInstance()
            			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            												"Erro", 
            												"Já existe um arquivo com esse nome!"));
            return "index.xhtml?indice=1";

        } catch (AnyPersistenceException e) {
            FacesContext.getCurrentInstance()
            			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            												"Erro", 
            												"Erro na gravação dos dados!"));
            return "index.xhtml";
        }
    }
    
    public List<DataFile> listarTodosArquivosDeDados() {
        return dataFileService.readAll();
    }

    // Funções auxiliares
	public void setIdArquivoSelecionado(long idArquivoSelecionado) {
		this.arquivoDeDados = dataFileService.readById(idArquivoSelecionado);
	}
	
	public Integer getTamanhoArquivoAnexo() {
		return dataFileService.getTamanhoArquivoAnexo(this.arquivoDeDados);
	}
	
	public Boolean hasArquivoAnexo(DataFile umArquivoDeDados) {
		return dataFileService.hasArquivoAnexo(umArquivoDeDados);
	}
	
}
