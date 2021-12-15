package bg.mycompany.eventbuddy.web;

import bg.mycompany.eventbuddy.model.view.CommentForEventViewModel;
import bg.mycompany.eventbuddy.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentControllerRest {

    private final CommentService commentService;

    public CommentControllerRest(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/events/{eventId}/comments")
    public ResponseEntity<List<CommentForEventViewModel>> getCommentsForEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(commentService.getCommentsForEvent(eventId));
    }

}
