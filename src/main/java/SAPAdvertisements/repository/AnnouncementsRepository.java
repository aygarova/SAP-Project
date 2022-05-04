package SAPAdvertisements.repository;

import SAPAdvertisements.models.Announcements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AnnouncementsRepository extends JpaRepository<Announcements, Integer> {
    Announcements findByAnnouncementNumber(String announcementNumber);

    // find with status and between datefrom and dateto
    @Query(value = "SELECT * FROM announcements a WHERE status IN (:status) and a.activate_from >= :start and a.activate_to <= :end", nativeQuery = true)
    List<Announcements> getByStatusAndTimePeriod(@Param("status") List<String> status, @Param("start") Date startTime, @Param("end") Date endTime);

    // find new announcements with datefrom between two date
    @Query(value = "SELECT * FROM announcements a WHERE  a.activate_from BETWEEN :start and :end", nativeQuery = true)
    List<Announcements> getByDateCreated(@Param("start") Date startTime, @Param("end") Date endTime);

    // find with status
    @Query(value = "SELECT * FROM announcements a WHERE status IN (:status)", nativeQuery = true)
    List<Announcements> getByStatus(@Param("status") List<String> status);

    // find all favorites
    @Query(value = "select a.* from public.announcements a inner join public.wishlist w on w.announcement_id = a.announcement_id", nativeQuery = true)
    List<Announcements> findAllFavorites();

    @Query("SELECT a FROM Announcements a WHERE a.announcementNumber IN (:announcementNumber)")
    Announcements getAnnouncementsByNumber(String announcementNumber);

    @Query("SELECT a FROM Announcements a WHERE a.announcementNumber IN (:announcementNumber)")
    Announcements deleteDyAnnouncementNumber(String announcementNumber);
}