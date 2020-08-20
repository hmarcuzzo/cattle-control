package br.edu.utfpr.cm.dacom.service;


import java.net.URLConnection;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.edu.utfpr.cm.dacom.entity.DataFile;
import br.edu.utfpr.cm.dacom.exception.AnyPersistenceException;
import br.edu.utfpr.cm.dacom.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cm.dacom.repository.DataFileRepository;

@Service
@RequiredArgsConstructor
public class DataFileService implements ICRUDService<DataFile> {

    private final DataFileRepository dataFileRepository;

    public List<DataFile> readAll() {
        return List.copyOf(dataFileRepository.findAll());
    }

    public DataFile readById(Long anId) throws EntityNotFoundException {
        return dataFileRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
    }

    public DataFile create(DataFile aDataFile) throws EntityAlreadyExistsException, AnyPersistenceException {

        if (dataFileRepository
        		.findAll()
        		.stream()
        		.anyMatch(currentDataFile -> currentDataFile.getNome().equals(aDataFile.getNome()))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            return dataFileRepository.save(aDataFile);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

	@Override
	public void update(DataFile entity) {
		// TODO Implementação obrigatório por causa da interface ICRUDService
		
		
	}

	@Override
	public void delete(Long anId) {
		// TODO Implementação obrigatório por causa da interface ICRUDService	
	}
	
	
    public Boolean hasArquivoAnexo(DataFile entity) {
    	return (entity.getArquivoAnexo()==null || entity.getArquivoAnexo().length==0?false:true);
    }
    
	public Integer getTamanhoArquivoAnexo(DataFile entity) {
		return (this.hasArquivoAnexo(entity)?entity.getArquivoAnexo().length:0);
	}
	
	public String getMimeType(DataFile entity) {
		if (entity.getNomeArquivoAnexo()==null)
			return null;
		else 
			return URLConnection.guessContentTypeFromName(entity.getNomeArquivoAnexo());
	}
}
