package org.sqlcomponents.core.model.relational;

import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.Property;
import org.sqlcomponents.core.model.relational.enumeration.TableType;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Table  {

    private String tableName;
    private String sequenceName;
    private String categoryName;
    private String schemaName;
    private TableType tableType;
    private String remarks;
    private String categoryType;
    private String schemaType;
    private String nameType;
    private String selfReferencingColumnName;
    private String referenceGeneration;
    
    private List<Column> columns;
    private final Database database;

    public Table(final Database database) {
        this.database = database;
    }


    public boolean getHasPrimaryKey() {
        return this.getColumns().stream()
                .filter(column->column.getPrimaryKeyIndex() != 0).findFirst().isPresent();
    }

    public int getHighestPKIndex() {
        int highestPKIndex = 0;
        for (Column column : columns) {
            if (highestPKIndex < column.getPrimaryKeyIndex()) {
                highestPKIndex = column.getPrimaryKeyIndex();
            }
        }
        return highestPKIndex;
    }

    public List<String> getUniqueConstraintGroupNames() {
        List<String> uniqueConstraintGroupNames = new ArrayList<String>() ;
        String prevUniqueConstraintGroupName = null ;
        String uniqueConstraintGroupName = null ;
        for (Column column : columns) {
            uniqueConstraintGroupName = column.getUniqueConstraintName() ;
            if( uniqueConstraintGroupName!=null
                    && !uniqueConstraintGroupName.equals(prevUniqueConstraintGroupName)) {
                uniqueConstraintGroupNames.add(uniqueConstraintGroupName);
                prevUniqueConstraintGroupName = uniqueConstraintGroupName ;
            }
        }
        return uniqueConstraintGroupNames ;
    }

    @Override
    public String toString() {
        return tableName + "::" + tableType ;
    }
}