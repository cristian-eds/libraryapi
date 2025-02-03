package io.github.cristian_eds.libraryapi.controller.common;

import io.github.cristian_eds.libraryapi.controller.dto.ErroCampo;
import io.github.cristian_eds.libraryapi.controller.dto.ErroResposta;
import io.github.cristian_eds.libraryapi.exceptions.OperacaoNaoPermitida;
import io.github.cristian_eds.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodAgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors.stream().map(campo -> new ErroCampo(campo.getField(), campo.getDefaultMessage())).toList();
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação",listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitida.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitida(OperacaoNaoPermitida e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public  ErroResposta handleErrosNaoTratados(RuntimeException e) {
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno, por favor aguarde e tente novamente mais tarde",
                List.of());
    }
}
