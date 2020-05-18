package com.meetings.repository;

import com.meetings.entity.Meeting;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, UUID> {
  @Query(
      "SELECT COUNT(*) FROM #{#entityName} t WHERE ?1 BETWEEN t.fromAt AND t.toAt OR ?2 BETWEEN t.fromAt AND t.toAt")
  Long countBetween(final Date from, final Date to);
}
