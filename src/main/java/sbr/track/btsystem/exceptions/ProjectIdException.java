package sbr.track.btsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 3000267413178440645L;

    public ProjectIdException(String message) {
        super(message);
    }
    
}