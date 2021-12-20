package bg.mycompany.eventbuddy.service.impl;

import bg.mycompany.eventbuddy.model.entity.Comment;
import bg.mycompany.eventbuddy.model.entity.Event;
import bg.mycompany.eventbuddy.model.entity.User;
import bg.mycompany.eventbuddy.model.service.CommentAddServiceModel;
import bg.mycompany.eventbuddy.model.view.CommentForEventViewModel;
import bg.mycompany.eventbuddy.repository.CommentRepository;
import bg.mycompany.eventbuddy.service.CommentService;
import bg.mycompany.eventbuddy.service.EventService;
import bg.mycompany.eventbuddy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EventService eventService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, EventService eventService, ModelMapper modelMapper, UserService userService) {
        this.commentRepository = commentRepository;
        this.eventService = eventService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public List<CommentForEventViewModel> getCommentsForEvent(Long eventId) {
        Event currentEvent = eventService.findEventById(eventId);

        return currentEvent
                .getComments()
                .stream()
                .map(this::mapCommentToViewModel).collect(Collectors.toList());
    }

    @Override
    public CommentForEventViewModel createComment(CommentAddServiceModel commentAddServiceModel) {
        Event event = eventService.findEventById(commentAddServiceModel.getEventId());
        Comment currentComment = new Comment();
        currentComment.setEvent(event);
        currentComment.setCreatedDateTime(LocalDateTime.now());
        currentComment.setTextContent(commentAddServiceModel.getTextContent());
        User user = userService.findByUsername(commentAddServiceModel.getUsername());
        currentComment.setAuthor(user);

        Comment savedComment = commentRepository.save(currentComment);

        return mapCommentToViewModel(savedComment);
    }


    @Override
    public CommentForEventViewModel mapCommentToViewModel(Comment currentComment) {
        CommentForEventViewModel commentForEventViewModel = new CommentForEventViewModel();
        commentForEventViewModel.setCommentId(currentComment.getId());
        commentForEventViewModel.setUser(currentComment.getAuthor().getUsername());
        commentForEventViewModel.setTextContent(currentComment.getTextContent());
        commentForEventViewModel
                .setCreationDateTime(currentComment
                        .getCreatedDateTime()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        return commentForEventViewModel;
    }
}
