package jp.mirageworld.spring.common.mapper;

import java.time.LocalDateTime;
import java.util.List;
import jp.mirageworld.spring.common.model.Users;
import jp.mirageworld.spring.common.model.UsersExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersMapper {
    @SelectProvider(type=UsersSqlProvider.class, method="countByExample")
    long countByExample(UsersExample example);

    @DeleteProvider(type=UsersSqlProvider.class, method="deleteByExample")
    int deleteWithVersionByExample(@Param("version") LocalDateTime version, @Param("example") UsersExample example);

    @DeleteProvider(type=UsersSqlProvider.class, method="deleteByExample")
    int deleteByExample(UsersExample example);

    @Delete({
        "delete from users",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteWithVersionByPrimaryKey(@Param("version") LocalDateTime version, @Param("key") Integer id);

    @Delete({
        "delete from users",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into users (id, username, ",
        "email, password, ",
        "enabled, locked, created, ",
        "updated, deleted, ",
        "hash, hash_exp)",
        "values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{enabled,jdbcType=BIT}, #{locked,jdbcType=BIT}, #{created,jdbcType=TIMESTAMP}, ",
        "#{updated,jdbcType=TIMESTAMP}, #{deleted,jdbcType=DATE}, ",
        "#{hash,jdbcType=VARCHAR}, #{hashExp,jdbcType=TIMESTAMP})"
    })
    int insert(Users record);

    @InsertProvider(type=UsersSqlProvider.class, method="insertSelective")
    int insertSelective(Users record);

    @SelectProvider(type=UsersSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="enabled", property="enabled", jdbcType=JdbcType.BIT),
        @Result(column="locked", property="locked", jdbcType=JdbcType.BIT),
        @Result(column="created", property="created", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updated", property="updated", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.DATE),
        @Result(column="hash", property="hash", jdbcType=JdbcType.VARCHAR),
        @Result(column="hash_exp", property="hashExp", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Users> selectByExampleWithRowbounds(UsersExample example, RowBounds rowBounds);

    @SelectProvider(type=UsersSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="enabled", property="enabled", jdbcType=JdbcType.BIT),
        @Result(column="locked", property="locked", jdbcType=JdbcType.BIT),
        @Result(column="created", property="created", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updated", property="updated", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.DATE),
        @Result(column="hash", property="hash", jdbcType=JdbcType.VARCHAR),
        @Result(column="hash_exp", property="hashExp", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Users> selectByExample(UsersExample example);

    @Select({
        "select",
        "id, username, email, password, enabled, locked, created, updated, deleted, hash, ",
        "hash_exp",
        "from users",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="enabled", property="enabled", jdbcType=JdbcType.BIT),
        @Result(column="locked", property="locked", jdbcType=JdbcType.BIT),
        @Result(column="created", property="created", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updated", property="updated", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.DATE),
        @Result(column="hash", property="hash", jdbcType=JdbcType.VARCHAR),
        @Result(column="hash_exp", property="hashExp", jdbcType=JdbcType.TIMESTAMP)
    })
    Users selectByPrimaryKey(Integer id);

    @UpdateProvider(type=UsersSqlProvider.class, method="updateByExample")
    int updateWithVersionByExample(@Param("version") LocalDateTime version, @Param("record") Users record, @Param("example") UsersExample example);

    @UpdateProvider(type=UsersSqlProvider.class, method="updateByExampleSelective")
    int updateWithVersionByExampleSelective(@Param("version") LocalDateTime version, @Param("record") Users record, @Param("example") UsersExample example);

    @UpdateProvider(type=UsersSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Users record, @Param("example") UsersExample example);

    @UpdateProvider(type=UsersSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Users record, @Param("example") UsersExample example);

    @Update({
        "update users",
        "set username = #{username,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "enabled = #{enabled,jdbcType=BIT},",
          "locked = #{locked,jdbcType=BIT},",
          "created = #{created,jdbcType=TIMESTAMP},",
          "updated = #{updated,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=DATE},",
          "hash = #{hash,jdbcType=VARCHAR},",
          "hash_exp = #{hashExp,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateWithVersionByPrimaryKey(@Param("version") LocalDateTime version, @Param("record") Users record);

    @UpdateProvider(type=UsersSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateWithVersionByPrimaryKeySelective(@Param("version") LocalDateTime version, @Param("record") Users record);

    @UpdateProvider(type=UsersSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Users record);

    @Update({
        "update users",
        "set username = #{username,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "enabled = #{enabled,jdbcType=BIT},",
          "locked = #{locked,jdbcType=BIT},",
          "created = #{created,jdbcType=TIMESTAMP},",
          "updated = #{updated,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=DATE},",
          "hash = #{hash,jdbcType=VARCHAR},",
          "hash_exp = #{hashExp,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Users record);
}