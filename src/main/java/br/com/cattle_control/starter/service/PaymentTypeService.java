package br.com.cattle_control.starter.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.github.adminfaces.template.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
 

import br.com.cattle_control.starter.model.PaymentType;
import br.com.cattle_control.starter.exception.AnyPersistenceException;
import br.com.cattle_control.starter.exception.EntityAlreadyExistsException;
import br.com.cattle_control.starter.repository.PaymentTypeRepository;


@Service
@RequiredArgsConstructor
public class PaymentTypeService implements ICRUDService<PaymentType> {
    private final PaymentTypeRepository paymentTypeRepository;

    public List<PaymentType> readAll() {
        return paymentTypeRepository
                            .findAll()
                            .stream()
                            .filter(currentPaymentType -> currentPaymentType.getDeleted().equals(false))
                            .collect(Collectors.toList());
    }

    public List<String> readAllPaymentTypeName() {
        return paymentTypeRepository
                            .findAll()
                            .stream()
                            .filter(currentPaymentType -> currentPaymentType.getDeleted().equals(false))
                            .map(PaymentType::getPaymentType_name)
                            .collect(Collectors.toList());
    }

    public PaymentType readById(Integer anId) throws EntityNotFoundException {
        return paymentTypeRepository
        .findAll()
        .stream()
        .filter(currentPaymentType -> currentPaymentType.getDeleted().equals(false) && currentPaymentType.getId().equals(anId))
        .findFirst()
        .orElseThrow(EntityNotFoundException::new);
    }

    public List<String> getPaymentTypeNames(String query) {
        return paymentTypeRepository
                            .findAll()
                            .stream()
                            .filter(currentPaymentType -> currentPaymentType.getPaymentType_name().toLowerCase().contains(query.toLowerCase()) && currentPaymentType.getDeleted().equals(false))
                            .map(PaymentType::getPaymentType_name)
                            .collect(Collectors.toList());
    }

    public PaymentType create(PaymentType anPaymentType) throws EntityAlreadyExistsException, AnyPersistenceException {

        if (paymentTypeRepository
        		.findAll()
                .stream()
                .filter(currentPaymentType -> currentPaymentType.getDeleted().equals(false))
        		.anyMatch(currentPaymentType -> currentPaymentType.getPaymentType_name().toUpperCase().equals(anPaymentType.getPaymentType_name().toUpperCase()))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            return paymentTypeRepository.save(anPaymentType);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public PaymentType update(PaymentType entity) throws EntityAlreadyExistsException, AnyPersistenceException {
		if (paymentTypeRepository
        		.findAll()
                .stream()
                .filter(currentPaymentType -> currentPaymentType.getDeleted().equals(false) && !currentPaymentType.getId().equals(entity.getId()))
        		.anyMatch(currentPaymentType -> currentPaymentType.getPaymentType_name().toUpperCase().equals(entity.getPaymentType_name().toUpperCase()))) {
            throw new EntityAlreadyExistsException();
        }
		
		try {
            return paymentTypeRepository.save(entity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
	}

    @Override
	public void delete(Long anId) {
		// TODO Implementação obrigatório por causa da interface ICRUDService	
    }

    public PaymentType findByPaymentTypeName(String paymentTypeName) {
        return paymentTypeRepository.findAll()
                .stream()
                .filter(currentPaymentType -> currentPaymentType.getPaymentType_name().toLowerCase().equals(paymentTypeName.toLowerCase()) && currentPaymentType.getDeleted().equals(false))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Método de pagamento com o nome " + paymentTypeName + " não foi encontrado"));
    }
}
