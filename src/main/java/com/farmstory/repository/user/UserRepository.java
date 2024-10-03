package com.farmstory.repository.user;

import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.entity.Comment;
import com.farmstory.entity.User;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String>, UserRepositoryCustom {
    public int countByUserUid(String userUid);
    public int countByUserNick(String userNick);
    public int countByUserEmail(String userEmail);
    public int countByUserHp(String userHp);
    public int countByUserPass(String userPass);

    User findByUserEmail(String userEmail);
    User findByUserUid(String userUid);

    @Modifying
    @Query("UPDATE User u SET u.userLeaveDate = :leaveDate WHERE u.userUid = :userUid")
    int updateByUserLeaveDate(@Param("userUid") String userUid, @Param("leaveDate") LocalDateTime leaveDate);

    Page<Tuple> selectUserAllForList(PageRequestDTO pageRequestDTO, Pageable pageable);
}
