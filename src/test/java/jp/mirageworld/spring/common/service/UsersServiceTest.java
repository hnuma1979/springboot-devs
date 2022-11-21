package jp.mirageworld.spring.common.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.mirageworld.spring.common.mapper.UsersMapper;
import jp.mirageworld.spring.common.model.Users;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class UsersServiceTest {

    private static final String DOMAIN = "@example.com";

    private static final String UUID1 = UUID.randomUUID().toString();
    private static final String UUID2 = UUID.randomUUID().toString();

    @Autowired
    UsersService service;

    @Autowired
    UsersMapper mapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    Users users = new Users();
    int orgId;

    @BeforeEach
    public void before() {
        log.debug("START:TEST");
        // 一度しかINSERTされないためリセット
        users = new Users();
        // テスト用 USERNAME 重複を避けるために UUID
        users.setUsername(UUID1);             
        users.setEmail(   UUID1 + DOMAIN);
        users.setPassword(UUID1);
        users.setEnabled(true);
        assertEquals(service.insert(users), 1, "登録");
        users = service.findByUsername(UUID1).get();
        orgId = users.getId();
    }

    @Test
    public void testInsert() {
        assertTrue(passwordEncoder.matches(UUID1, users.getPassword()), "暗号化テスト（insert）");
    }

    @Test
    public void testFindByUsername() {
        assertTrue(service.findByUsername(UUID1).isPresent(), "存在するケース");
        assertTrue(service.findByUsername(UUID2).isEmpty()  , "存在しないケース");
    }

    @Test
    public void testFindByEmail() {
        assertTrue(service.findByEmail(UUID1+DOMAIN).isPresent(), "存在するケース");
        assertTrue(service.findByEmail(UUID2+DOMAIN).isEmpty()  , "存在しないケース");
    }

    @Test
    public void testUpdate1() {

        // 更新系（パスワード変更なし）
        users.setUsername(UUID2);
        users.setEmail(UUID2+DOMAIN);

        assertEquals(service.update(users), 1, "更新系（パスワード変更なし）");
        users = service.findById(orgId).get();
        assertEquals(users.getUsername(),   UUID2, "usaname 更新");
        assertEquals(users.getEmail(),      UUID2+DOMAIN, "email   更新");
        assertTrue(passwordEncoder.matches(UUID1, users.getPassword()), "パスワード変更なし");
    }

    @Test
    public void testUpdate2() {

        // 更新系（パスワード変更あり）
        users.setUsername(UUID2);
        users.setEmail(UUID2+DOMAIN);
        users.setPassword(UUID2);

        assertEquals(service.update(users), 1, "更新系（パスワード変更あり）");
        users = service.findById(orgId).get();
        assertEquals(users.getUsername(),   UUID2, "usaname 更新");
        assertEquals(users.getEmail(),      UUID2+DOMAIN, "email   更新");
        assertTrue(passwordEncoder.matches(UUID2, users.getPassword()), "パスワード変更あり");
    }

    @Test
    public void testDelete1() {
        assertEquals(service.delete(users), 1, "論理削除：実行");
        assertEquals(service.delete(users), 0, "論理削除：未実行");

        assertTrue(service.findById(orgId).isPresent(), "ID検索時は論理削除でもみつかる");
        assertTrue(service.findByUsername(UUID1).isEmpty(), "論理削除では検索されない");
        assertTrue(service.findByEmail(UUID1+DOMAIN).isEmpty(), "論理削除では検索されない");
    }

    @Test
    public void testDelete2() {
        assertEquals(service.delete(users.getId()), 1, "論理削除：実行");
        assertEquals(service.delete(users.getId()), 0, "論理削除：未実行");

        assertTrue(service.findById(orgId).isPresent(), "ID検索時は論理削除でもみつかる");
        assertTrue(service.findByUsername(UUID1).isEmpty(), "論理削除では検索されない");
        assertTrue(service.findByEmail(UUID1+DOMAIN).isEmpty(), "論理削除では検索されない");
    }


    @AfterEach
    public void afuterClass() {
        service.findById(orgId).ifPresent(
            users->mapper.deleteByPrimaryKey(users.getId())
        );
        log.debug("END:TEST");
    }
}
