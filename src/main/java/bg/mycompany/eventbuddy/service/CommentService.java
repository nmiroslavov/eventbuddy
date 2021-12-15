package bg.mycompany.eventbuddy.service;

import bg.mycompany.eventbuddy.model.service.CommentAddServiceModel;
import bg.mycompany.eventbuddy.model.view.CommentForEventViewModel;

import java.util.List;

public interface CommentService {

    List<CommentForEventViewModel> getCommentsForEvent(Long eventId);

    CommentForEventViewModel createComment(CommentAddServiceModel commentAddServiceModel);
}
