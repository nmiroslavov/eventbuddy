package bg.mycompany.eventbuddy.model.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentAddBindingModel {
    @NotNull
    @Size(min = 3)
    private String textContent;

    public CommentAddBindingModel() {
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
