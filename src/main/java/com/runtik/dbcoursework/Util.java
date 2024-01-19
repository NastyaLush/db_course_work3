package com.runtik.dbcoursework;

import com.runtik.dbcoursework.dto.Order;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.springframework.data.domain.Sort;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
    public static List<SortField<?>> getSortedFields(Sort sort, TableImpl table){
        List<SortField<?>> sortFields = new ArrayList<>();
        if(sort == null){
            return sortFields;
        }
        sort.forEach( (order)-> {
                          Field<?> field = table.field(order.getProperty());
                          if (field != null) {
                              SortOrder sortOrder = order.getDirection().name().equals("ASC") ? SortOrder.ASC : SortOrder.DESC;
                              SortField<?> sortField = field.sort(sortOrder);
                              sortFields.add(sortField);
                          }
                      }
        );


            return sortFields;
    }
    public static Condition getFilterFields(String[] sort, TableImpl table){
        Condition condition = DSL.trueCondition();
        if(sort == null){
            return condition;
        }
        for(int i=0;i< sort.length-1;i+=2){
            Field<?> field = table.field(sort[i]);
            if(field!= null) {
                if (field.getType() == String.class) {
                    condition=condition.and(field.like(sort[i + 1]));
                } else if (field.getType() == Integer.class) {
                    Field<Integer> field2 = table.field(sort[i]);
                    condition=condition.and(field2.equal(Integer.valueOf(sort[i + 1])));
                } else if (field.getType() == LocalDateTime.class) {
                    Field<LocalDateTime> field2 = table.field(sort[i]);
                    condition=condition.and(field2.eq(LocalDateTime.parse(sort[i + 1], DateTimeFormatter.ISO_LOCAL_DATE)));
                }
            }
        }
        return condition;
    }

}
