package jp.mirageworld.spring.common.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import jp.mirageworld.spring.common.exception.ValidationException;
import jp.mirageworld.spring.common.mapper.UsersMapper;
import jp.mirageworld.spring.common.model.Users;
import jp.mirageworld.spring.common.model.UsersExample;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsersService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsersMapper usersMapper;

    /**
     * テスト以外では直接呼ばないこと。
     * 
     * @param usersMapper
     */
    @Autowired
    public UsersService(UsersMapper usersMapper) {
        log.debug("INIT");
        this.usersMapper = usersMapper;
    }

    /**
     * 主キーをもとに検索する。
     * 
     * @param id
     * @return
     */
    public Optional<Users> findById(int id) {
        return Optional.ofNullable(usersMapper.selectByPrimaryKey(id));
    }

    /**
     * カラム名をもとに検索する。
     * 
     * @param username
     * @return
     */
    public Optional<Users> findByUsername(String username) {
        UsersExample example = new UsersExample();
        example.or().andUsernameEqualTo(username).andDeletedIsNull();
        return usersMapper.selectByExample(example).stream().findFirst();
    }

    /**
     * カラム名をもとに検索する。
     * 
     * @param email
     * @return
     */
    public Optional<Users> findByEmail(String email) {
        UsersExample example = new UsersExample();
        example.or().andEmailEqualTo(email).andDeletedIsNull();
        return usersMapper.selectByExample(example).stream().findFirst();
    }

    /**
     * インサート。
     * 
     * @param users
     * @return
     */
    public int insert (Users users) {
        log.debug("insert(users) : START");
        try {
            // ユーザー名は重複してはいけない
            existsUsernameThrow(users.getUsername(), null, "errors.username.exists");
            // メールアドレスは重複してはいけない
            existsEmailThrow(users.getEmail(), null, "errors.email.exists");

            Users insertUser = new Users();
            BeanUtils.copyProperties(users, insertUser);

            // 入力されたパスワードを変換する
            encodePassword(insertUser);

            LocalDateTime now =  LocalDateTime.now();
        
            // 更新時
            insertUser.setCreated(now);
            insertUser.setUpdated(now);

            return usersMapper.insert(insertUser);
        } finally {
                log.debug("insert(users) : END");
        }
    }

    /**
     * アップデート。
     * 
     * @param users
     * @return
     */
    public int update(Users users) {
        log.debug("update(user) : START");
        try {
            // ユーザー名は重複してはいけない
            existsUsernameThrow(users.getUsername(),    users.getId(), "errors.username.exists");
            // メールアドレスは重複してはいけない
            existsEmailThrow(users.getEmail(),          users.getId(), "errors.email.exists");

            Users databaseUser = findById(users.getId()).get();
            Users updateUser = new Users();

            BeanUtils.copyProperties(users, updateUser);

            String inpPassword = users.getPassword();
            String savPassword = databaseUser.getPassword();

            if (!StringUtils.hasLength(inpPassword)) {
                log.debug("パスワード未入力");
                // 更新前の値を設定
                updateUser.setPassword(savPassword);
            } else if (Objects.equals(inpPassword, savPassword)) {
                log.debug("同じ情報が設定されている（暗号化済）");
                // 更新前の値を設定
                updateUser.setPassword(savPassword);
            } else if (passwordEncoder.matches(inpPassword, savPassword)) {
                log.debug("同じ情報が設定されている（暗号化前）");
                // 更新前の値を設定
                updateUser.setPassword(savPassword);
            } else {
                log.info("暗号化");
                encodePassword(updateUser);
            }

            LocalDateTime now =  LocalDateTime.now();
            
            // 更新時
            updateUser.setCreated(null); // 作成日時は更新しない
            updateUser.setUpdated(now);

            UsersExample usersExample = defaultUsersExample(users.getId());

            return  usersMapper.updateByExampleSelective(updateUser, usersExample);
        } finally {
            log.debug("update(user) : END");
        }
    }

    /**
     * デリート。
     * 
     * @param users
     * @return
     */
    public int delete(Users users) {
        return delete(users.getId());
    }

    public int delete(int id) {
        log.debug("delete(int) : START");
        try {
            UsersExample    usersExample    = defaultUsersExample(id);
            Optional<Users> usersOptional   = findById(id);
            if (usersOptional.isPresent()) {
                
                Users users = new Users();
                LocalDateTime   updated = LocalDateTime.now();
                LocalDate       deleted = LocalDate.now();

                users.setId(id);
                users.setEnabled(false);    
                users.setUpdated(updated);
                users.setDeleted(deleted);

                return  usersMapper.updateByExampleSelective(users, usersExample);
            }
            return 0;
        } finally {
            log.debug("delete(int) : END");
        }
    }

    /**
     * ユーザー名重複チェック
     * 
     * @param username
     * @param id    新規登録時は null 許容
     * @param message
     */
    private void existsUsernameThrow(String username , Integer id, String message)  {
        Assert.notNull(username, "username not exists");
        Optional<Users> users = findByUsername(username);
        if (users.isEmpty()) {
            // 新規登録
            return;
        } else if (Objects.equals(id, users.get().getId())) {
            // 更新（自分）
            return;
        }
        log.warn("EXISTS ID : {}", users.get().getId());
        throw new ValidationException("username", message);
    }

    /**
     * email重複チェック
     * 
     * @param email
     * @param id    新規登録時は null 許容
     * @param message
     */
    private void existsEmailThrow(String email, Integer id, String message)  {
        Assert.notNull(email, "email not exists");
        Optional<Users> users = findByEmail(email);
        if (users.isEmpty()) {
            // 新規登録
            return;
        } else if (Objects.equals(id, users.get().getId())) {
            // 更新（自分）
            return;
        }
        log.warn("EXISTS ID : {}", users.get().getId());
        throw new ValidationException("email", message);
    }

    /**
     * パスワードの暗号化
     * 
     * @param users
     */
    private void encodePassword(Users users) {
        Assert.notNull(users, "users is null");
        // 入力されたパスワードを変換する
        String rawPassword = users.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);

        log.trace("raw => {}, enc => {}", rawPassword, encPassword);
        users.setPassword(encPassword);
    }

    /**
    * 更新条件
    *
    * 1. 同一ID であること
    * 2. 有効状態のアカウント専用であること
    *
    * @param id
    */
    private UsersExample defaultUsersExample(int id) {
        UsersExample usersExample = new UsersExample();
        usersExample.createCriteria().andIdEqualTo(id).andEnabledEqualTo(true).andDeletedIsNull();
        return  usersExample;
    }
}