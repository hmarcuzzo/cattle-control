package br.edu.utfpr.cm.dacom.service;

import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import br.edu.utfpr.cm.dacom.entity.DataFile;
import br.edu.utfpr.cm.dacom.service.DataFileService;

@SpringBootTest
public class DataFileServiceTest {
	
	@Autowired
    DataFileService dataFileService;

	@DisplayName("Testar quando há arquivo anexo.")
	@Test
	void testHasArquivoAnexo_True() {
		DataFile mockDataFile = Mockito.mock(DataFile.class);
		
		Mockito.when(mockDataFile.getArquivoAnexo()).thenReturn("Isto é um teste!".getBytes());
		
		assertEquals(true, dataFileService.hasArquivoAnexo(mockDataFile));		
	}
		
	@DisplayName("Testar quando há arquivo anexo com tamannho zero.")
	@Test
	void testHasArquivoAnexo_Zero() {
		DataFile mockDataFile = Mockito.mock(DataFile.class);
		
		Mockito.when(mockDataFile.getArquivoAnexo()).thenReturn("".getBytes());
		
		assertEquals(false, dataFileService.hasArquivoAnexo(mockDataFile));		
	}
	
	@DisplayName("Testar quando não há arquivo anexo.")
	@Test
	void testHasArquivoAnexo_False() {
		DataFile mockDataFile = Mockito.mock(DataFile.class);
		
		Mockito.when(mockDataFile.getArquivoAnexo()).thenReturn(null);
		
		assertEquals(false, dataFileService.hasArquivoAnexo(mockDataFile));		
	}
	
	@DisplayName("Testar tamanho do arquivo quando há arquivo anexo.")
	@Test
	void testGetTamanhoArquivoAnexo_17() {
		DataFile mockDataFile = Mockito.mock(DataFile.class);
		
		Mockito.when(mockDataFile.getArquivoAnexo()).thenReturn("Isto é um teste!".getBytes());
		
		assertEquals(17, dataFileService.getTamanhoArquivoAnexo(mockDataFile));		
	}
	
	@DisplayName("Testar tamanho do arquivo quando há arquivo anexo com tamannho zero.")
	@Test
	void testGetTamanhoArquivoAnexo_zero() {
		DataFile mockDataFile = Mockito.mock(DataFile.class);
		
		Mockito.when(mockDataFile.getArquivoAnexo()).thenReturn("".getBytes());
		
		assertEquals(0, dataFileService.getTamanhoArquivoAnexo(mockDataFile));		
	}
	
	@DisplayName("Testar tamanho do arquivo quando não há arquivo anexo.")
	@Test
	void testHasArquivoAnexo_Null() {
		DataFile mockDataFile = Mockito.mock(DataFile.class);
		
		Mockito.when(mockDataFile.getArquivoAnexo()).thenReturn(null);
		
		assertEquals(0, dataFileService.getTamanhoArquivoAnexo(mockDataFile));		
	}
	
	@DisplayName("Testar o mimetype quando há arquivo anexo com extensão pdf.")
	@Test
	void testGetMimeType_Pdf() {
		DataFile mockDataFile = Mockito.mock(DataFile.class);
		
		Mockito.when(mockDataFile.getNomeArquivoAnexo()).thenReturn("arquivo.pdf");
		
		assertTrue("application/pdf".equals(dataFileService.getMimeType(mockDataFile)));
	}
	
	@DisplayName("Testar o mimetype quando há arquivo anexo sem extensão.")
	@Test
	void testGetMimeType_SemExtensao() {
		DataFile mockDataFile = Mockito.mock(DataFile.class);
		
		Mockito.when(mockDataFile.getNomeArquivoAnexo()).thenReturn("arquivo");
		
		assertEquals(null, dataFileService.getMimeType(mockDataFile));
	}
	
	@DisplayName("Testar o mimetype quando não há arquivo.")
	@Test
	void testGetMimeType_Null() {
		DataFile mockDataFile = Mockito.mock(DataFile.class);
		
		Mockito.when(mockDataFile.getNomeArquivoAnexo()).thenReturn(null);
		
		assertEquals(null, dataFileService.getMimeType(mockDataFile));
	}
}
