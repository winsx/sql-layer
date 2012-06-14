/**
 * END USER LICENSE AGREEMENT (“EULA”)
 *
 * READ THIS AGREEMENT CAREFULLY (date: 9/13/2011):
 * http://www.akiban.com/licensing/20110913
 *
 * BY INSTALLING OR USING ALL OR ANY PORTION OF THE SOFTWARE, YOU ARE ACCEPTING
 * ALL OF THE TERMS AND CONDITIONS OF THIS AGREEMENT. YOU AGREE THAT THIS
 * AGREEMENT IS ENFORCEABLE LIKE ANY WRITTEN AGREEMENT SIGNED BY YOU.
 *
 * IF YOU HAVE PAID A LICENSE FEE FOR USE OF THE SOFTWARE AND DO NOT AGREE TO
 * THESE TERMS, YOU MAY RETURN THE SOFTWARE FOR A FULL REFUND PROVIDED YOU (A) DO
 * NOT USE THE SOFTWARE AND (B) RETURN THE SOFTWARE WITHIN THIRTY (30) DAYS OF
 * YOUR INITIAL PURCHASE.
 *
 * IF YOU WISH TO USE THE SOFTWARE AS AN EMPLOYEE, CONTRACTOR, OR AGENT OF A
 * CORPORATION, PARTNERSHIP OR SIMILAR ENTITY, THEN YOU MUST BE AUTHORIZED TO SIGN
 * FOR AND BIND THE ENTITY IN ORDER TO ACCEPT THE TERMS OF THIS AGREEMENT. THE
 * LICENSES GRANTED UNDER THIS AGREEMENT ARE EXPRESSLY CONDITIONED UPON ACCEPTANCE
 * BY SUCH AUTHORIZED PERSONNEL.
 *
 * IF YOU HAVE ENTERED INTO A SEPARATE WRITTEN LICENSE AGREEMENT WITH AKIBAN FOR
 * USE OF THE SOFTWARE, THE TERMS AND CONDITIONS OF SUCH OTHER AGREEMENT SHALL
 * PREVAIL OVER ANY CONFLICTING TERMS OR CONDITIONS IN THIS AGREEMENT.
 */

package com.akiban.server.api.dml.scan;

import com.akiban.server.rowdata.RowData;
import com.akiban.server.api.dml.ColumnSelector;

import java.util.Arrays;

public class LegacyScanRequest extends LegacyScanRange implements ScanRequest {
    private final int indexId;
    private final int scanFlags;
    private ScanLimit limit;

    @Override
    public int getIndexId() {
        return indexId;
    }

    @Override
    public int getScanFlags() {
        return scanFlags;
    }

    public LegacyScanRequest(int tableId,
                             RowData start,
                             ColumnSelector startColumns,
                             RowData end,
                             ColumnSelector endColumns,
                             byte[] columnBitMap,
                             int indexId,
                             int scanFlags,
                             ScanLimit limit)
    {
        super(tableId, start, startColumns, end, endColumns, columnBitMap);
        this.indexId = indexId;
        this.scanFlags = scanFlags;
        this.limit = limit;
    }

    @Override
    public String toString() {
        return String.format("Scan[ tableId=%d, indexId=%d, scanFlags=0x%02X, projection=%s start=<%s> end=<%s>",
                tableId, indexId, scanFlags, Arrays.toString(columnBitMap), start, end
        );
    }

    @Override
    public ScanLimit getScanLimit() {
        return limit;
    }

    @Override
    public void dropScanLimit()
    {
        limit = ScanLimit.NONE;
    }
}
