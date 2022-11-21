package jp.mirageworld.spring.common.mapper;

import java.util.List;
import java.util.Map;
import jp.mirageworld.spring.common.model.Users;
import jp.mirageworld.spring.common.model.UsersExample.Criteria;
import jp.mirageworld.spring.common.model.UsersExample.Criterion;
import jp.mirageworld.spring.common.model.UsersExample;
import org.apache.ibatis.jdbc.SQL;

public class UsersSqlProvider {

    public String countByExample(UsersExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("users");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(UsersExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("users");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Users record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("users");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getUsername() != null) {
            sql.VALUES("username", "#{username,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.VALUES("email", "#{email,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        sql.VALUES("enabled", "#{enabled,jdbcType=BIT}");
        
        sql.VALUES("locked", "#{locked,jdbcType=BIT}");
        
        if (record.getCreated() != null) {
            sql.VALUES("created", "#{created,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdated() != null) {
            sql.VALUES("updated", "#{updated,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDeleted() != null) {
            sql.VALUES("deleted", "#{deleted,jdbcType=DATE}");
        }
        
        if (record.getHash() != null) {
            sql.VALUES("hash", "#{hash,jdbcType=VARCHAR}");
        }
        
        if (record.getHashExp() != null) {
            sql.VALUES("hash_exp", "#{hashExp,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(UsersExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("username");
        sql.SELECT("email");
        sql.SELECT("password");
        sql.SELECT("enabled");
        sql.SELECT("locked");
        sql.SELECT("created");
        sql.SELECT("updated");
        sql.SELECT("deleted");
        sql.SELECT("hash");
        sql.SELECT("hash_exp");
        sql.FROM("users");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Users record = (Users) parameter.get("record");
        UsersExample example = (UsersExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("users");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getUsername() != null) {
            sql.SET("username = #{record.username,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{record.password,jdbcType=VARCHAR}");
        }
        
        sql.SET("enabled = #{record.enabled,jdbcType=BIT}");
        
        sql.SET("locked = #{record.locked,jdbcType=BIT}");
        
        if (record.getCreated() != null) {
            sql.SET("created = #{record.created,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdated() != null) {
            sql.SET("updated = #{record.updated,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDeleted() != null) {
            sql.SET("deleted = #{record.deleted,jdbcType=DATE}");
        }
        
        if (record.getHash() != null) {
            sql.SET("hash = #{record.hash,jdbcType=VARCHAR}");
        }
        
        if (record.getHashExp() != null) {
            sql.SET("hash_exp = #{record.hashExp,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("users");
        
        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("username = #{record.username,jdbcType=VARCHAR}");
        sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        sql.SET("password = #{record.password,jdbcType=VARCHAR}");
        sql.SET("enabled = #{record.enabled,jdbcType=BIT}");
        sql.SET("locked = #{record.locked,jdbcType=BIT}");
        sql.SET("created = #{record.created,jdbcType=TIMESTAMP}");
        sql.SET("updated = #{record.updated,jdbcType=TIMESTAMP}");
        sql.SET("deleted = #{record.deleted,jdbcType=DATE}");
        sql.SET("hash = #{record.hash,jdbcType=VARCHAR}");
        sql.SET("hash_exp = #{record.hashExp,jdbcType=TIMESTAMP}");
        
        UsersExample example = (UsersExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Users record) {
        SQL sql = new SQL();
        sql.UPDATE("users");
        
        if (record.getUsername() != null) {
            sql.SET("username = #{username,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.SET("email = #{email,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        sql.SET("enabled = #{enabled,jdbcType=BIT}");
        
        sql.SET("locked = #{locked,jdbcType=BIT}");
        
        if (record.getCreated() != null) {
            sql.SET("created = #{created,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdated() != null) {
            sql.SET("updated = #{updated,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDeleted() != null) {
            sql.SET("deleted = #{deleted,jdbcType=DATE}");
        }
        
        if (record.getHash() != null) {
            sql.SET("hash = #{hash,jdbcType=VARCHAR}");
        }
        
        if (record.getHashExp() != null) {
            sql.SET("hash_exp = #{hashExp,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, UsersExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}