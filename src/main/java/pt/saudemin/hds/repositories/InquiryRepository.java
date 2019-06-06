package pt.saudemin.hds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.saudemin.hds.entities.Inquiry;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

}
