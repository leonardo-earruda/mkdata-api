package com.mkdata.mkdataapi.domains.cliente;

import com.mkdata.mkdataapi.domains.cliente.assembler.ClienteAssembler;
import com.mkdata.mkdataapi.domains.cliente.dto.request.ClienteRequestDTO;
import com.mkdata.mkdataapi.domains.cliente.dto.response.ClienteResponseDTO;
import com.mkdata.mkdataapi.domains.cliente.enums.StatusCliente;
import com.mkdata.mkdataapi.exception_handler.exceptions.PersonTypeWithWrongDocumentException;
import com.mkdata.mkdataapi.util.FilterPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteAssembler clienteAssembler;
    private final SmartValidator validator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO criar(@RequestBody ClienteRequestDTO clienteRequestDTO) throws PersonTypeWithWrongDocumentException {
        extracted(clienteRequestDTO);
        Cliente cliente = clienteService.create(clienteRequestDTO);
        return clienteAssembler.toResponseModel(cliente);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDTO atualizar(@RequestBody ClienteRequestDTO clienteRequestDTO, @PathVariable UUID id) throws PersonTypeWithWrongDocumentException {
        extracted(clienteRequestDTO);
        Cliente cliente = clienteService.update(clienteRequestDTO, id);
        return clienteAssembler.toResponseModel(cliente);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDTO findById(@PathVariable UUID id) {
        Cliente clienteById = clienteService.findById(id);
        return clienteAssembler.toResponseModel(clienteById);
    }

    @GetMapping("/pageable")
    @ResponseStatus(HttpStatus.OK)
    public Page<ClienteResponseDTO> findPageable(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) StatusCliente customerStatus,
                                                 FilterPageable filterPageable) {
        return clienteService.findByPageable(name.toUpperCase(), customerStatus, filterPageable.listByPage());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable UUID id) {
        clienteService.deleteById(id);
    }

    private void extracted(ClienteRequestDTO clienteRequestDTO) throws PersonTypeWithWrongDocumentException {
        Method currentMethod = new Object() {
        }.getClass().getEnclosingMethod();
        validateModel(clienteRequestDTO, clienteRequestDTO.getInterface(), currentMethod);
    }

    private void validateModel(ClienteRequestDTO clienteRequestDTO, Object validationGroup, Method currentMethod) throws
            PersonTypeWithWrongDocumentException {
        BeanPropertyBindingResult erros = new BeanPropertyBindingResult(clienteRequestDTO, clienteRequestDTO.getClass().getName());
        validator.validate(clienteRequestDTO, erros, validationGroup);
        if (!erros.getAllErrors().isEmpty()) {
            throw new PersonTypeWithWrongDocumentException("Número de documento inválido.");
        }
    }

}
