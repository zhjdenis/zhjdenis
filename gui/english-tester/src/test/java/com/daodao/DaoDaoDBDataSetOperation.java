/**
 * 下午3:09:16
 */
package com.daodao;

import org.dbunit.operation.DatabaseOperation;

/**
 * @author HZHOU wrap the operation in dbunit
 * 
 */
public enum DaoDaoDBDataSetOperation {
    INSERT(DatabaseOperation.INSERT), DELETE(DatabaseOperation.DELETE_ALL), REFRESH(DatabaseOperation.REFRESH);
    private DatabaseOperation op;

    private DaoDaoDBDataSetOperation(DatabaseOperation op)
    {
        this.op = op;
    }

    /**
     * @return the op
     */
    public DatabaseOperation getOp()
    {
        return op;
    }

}
