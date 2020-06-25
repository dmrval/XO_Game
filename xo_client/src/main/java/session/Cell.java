package session;

import javafx.beans.property.SimpleObjectProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Cell implements Serializable {
    private Status status;

    public SimpleObjectProperty<Status> statusProperty(){
        return new SimpleObjectProperty<>(status);
    }
}
