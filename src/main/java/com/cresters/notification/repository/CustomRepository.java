package com.cresters.notification.repository;


import com.cresters.notification.util.ChecksumImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * @author Stephen Obi <stephen@genuinesols.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * <p>
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 2021/02/13 1:55:04 AM
 */

@SuppressWarnings({"unused", "DuplicatedCode"})
@Slf4j
@RequiredArgsConstructor
public class CustomRepository {

    protected final EntityManager em;
    private BigDecimal pendingSettlementAmount;
    private BigDecimal settledAmount;
    private Long settledCount;
    private Long transactionCount;
    private BigDecimal totalFees;

    public boolean isValidId(String tableName, long id) {

        String sqlQuery = "select e from " + tableName + " e where e.id = " + id;
        return !em.createQuery(sqlQuery).getResultList().isEmpty();
    }

    public boolean isUnique(String tableName, String columnName, Object value) {
        String sqlQuery = "select e from  " + tableName + " e where LOWER(e." + columnName + ") = LOWER(:value)";
        return em.createQuery(sqlQuery)
                .setParameter("value", value)
                .getResultList()
                .isEmpty();
    }

    public boolean isUnique(String tableName, String columnName, Object value, String businessCode, boolean businessContext) {

        if (!businessContext) {
            return this.isUnique(tableName, columnName, value);
        }

        String sqlQuery = "select e from  " + tableName + " e where LOWER(e." + columnName + ") = LOWER(:value) AND e.businessCode  = " + businessCode;
        return em.createQuery(sqlQuery)
                .setParameter("value", value)
                .getResultList()
                .isEmpty();
    }

    public boolean isExist(String tableName, String columnName, Object value) {
        String sqlQuery = MessageFormat.format("select e from  {0} e where LOWER(e.{1}) = LOWER(:value)", tableName, columnName);
        return !em.createQuery(sqlQuery)
                .setParameter("value", value)
                .getResultList()
                .isEmpty();
    }

    protected <T> void validate(Class<T> type, T obj) {
        if (ChecksumImplementation.class.isAssignableFrom(type)) {
            try {
                ChecksumImplementation.class.getMethod("validate").invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}