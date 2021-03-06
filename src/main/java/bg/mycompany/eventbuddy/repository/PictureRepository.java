package bg.mycompany.eventbuddy.repository;

import bg.mycompany.eventbuddy.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    Picture findByPublicId(String publicId);
}
