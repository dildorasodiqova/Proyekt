package uz.pdp.proyekt.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String str){
         super(str);
    }
}
