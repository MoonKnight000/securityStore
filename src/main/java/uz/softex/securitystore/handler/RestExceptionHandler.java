package uz.softex.securitystore.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.softex.securitystore.address.exception.AddressNotFound;
import uz.softex.securitystore.inputs.exceptions.InputsNotFound;
import uz.softex.securitystore.inputs.exceptions.PaymentTypeNotFound;
import uz.softex.securitystore.outputs.exeptions.OutputsNotFound;
import uz.softex.securitystore.payload.ApiResponse;
import uz.softex.securitystore.position.exceptions.PositionNotFound;
import uz.softex.securitystore.product.exception.ProductNotFoundException;
import uz.softex.securitystore.store.exceptions.StoreNotFound;
import uz.softex.securitystore.workers.exception.WorkersDtoNotFound;

import java.util.List;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse> handleProductNotFound(ProductNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Product not found", false));
    }
    @ExceptionHandler(StoreNotFound.class)
    public ResponseEntity<ApiResponse> handleStoreNotFound(StoreNotFound e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("store not found", false));
    }
    @ExceptionHandler(AddressNotFound.class)
    public ResponseEntity<ApiResponse> handleAddressNotFound(AddressNotFound e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("address not found", false));
    }
    @ExceptionHandler(WorkersDtoNotFound.class)
    public ResponseEntity<ApiResponse> handleWorkersDtoNotFound(WorkersDtoNotFound e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("worker not found", false));
    }
    @ExceptionHandler(PositionNotFound.class)
    public ResponseEntity<ApiResponse> handlePositionException(PositionNotFound e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("position not found", false));
    }
    @ExceptionHandler(PaymentTypeNotFound.class)
    public ResponseEntity<ApiResponse> handlePaymnetType(PaymentTypeNotFound e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("paymnet type not found", false));
    }
    @ExceptionHandler(InputsNotFound.class)
    public ResponseEntity<ApiResponse> handleInputsNotFound(InputsNotFound e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("unputs not found", false));
    }
    @ExceptionHandler(OutputsNotFound.class)
    public ResponseEntity<ApiResponse> handleOutputsNotFound(OutputsNotFound e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("outputs not found", false));
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("bu yerga keldi");
        System.out.println(ex);
        System.out.println(headers);
        System.out.println(request);
        String bodyOfResponse = "name is empty";
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        allErrors.forEach(i->{
            System.out.println(i.getDefaultMessage());
        });
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}