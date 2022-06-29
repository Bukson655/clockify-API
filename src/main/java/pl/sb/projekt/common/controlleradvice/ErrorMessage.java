package pl.sb.projekt.common.controlleradvice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorMessage {

    private int statusCode;
    private Date timeStamp;
    private String message;
    private String description;

}