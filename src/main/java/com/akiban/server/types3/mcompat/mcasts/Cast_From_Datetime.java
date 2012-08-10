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

package com.akiban.server.types3.mcompat.mcasts;

import com.akiban.server.types3.TCastBase;
import com.akiban.server.types3.TCast;
import com.akiban.server.types3.TExecutionContext;
import com.akiban.server.types3.mcompat.mtypes.MDatetimes;
import com.akiban.server.types3.mcompat.mtypes.MApproximateNumber;
import com.akiban.server.types3.mcompat.mtypes.MNumeric;
import com.akiban.server.types3.mcompat.mtypes.MString;
import com.akiban.server.types3.pvalue.PValueSource;
import com.akiban.server.types3.pvalue.PValueTarget;
import com.akiban.server.types3.texpressions.Constantness;

import static com.akiban.server.types3.mcompat.mcasts.MNumericCastBase.*;

public class Cast_From_Datetime
{
    public static final TCast TO_TINYINT = new FromInt64ToInt8(MDatetimes.DATETIME, MNumeric.TINYINT, false, Constantness.UNKNOWN);

    public static final TCast TO_UNSIGNED_TINYINT = new FromInt64ToUnsignedInt8(MDatetimes.DATETIME, MNumeric.TINYINT_UNSIGNED, false, Constantness.UNKNOWN);
    
    public static final TCast TO_SMALLINT = new FromInt64ToInt16(MDatetimes.DATETIME, MNumeric.SMALLINT, false, Constantness.UNKNOWN);
    
    public static final TCast TO_UNSIGNED_SMALINT = new FromInt64ToUnsignedInt16(MDatetimes.DATETIME, MNumeric.SMALLINT_UNSIGNED, false, Constantness.UNKNOWN);
    
    public static final TCast TO_MEDIUMINT = new FromInt64ToInt32(MDatetimes.DATETIME, MNumeric.MEDIUMINT, false, Constantness.UNKNOWN);
    
    public static final TCast TO_UNSIGNED_MEDIUMINT = new FromInt64ToUnsignedInt32(MDatetimes.DATETIME, MNumeric.MEDIUMINT_UNSIGNED, false, Constantness.UNKNOWN);
    
    public static final TCast TO_INT = new FromInt64ToInt32(MDatetimes.DATETIME, MNumeric.INT, false, Constantness.UNKNOWN);
    
    public static final TCast TO_UNSIGNED_INT = new FromInt64ToUnsignedInt32(MDatetimes.DATETIME, MNumeric.INT_UNSIGNED, false, Constantness.UNKNOWN);
    
    public static final TCast TO_BIGINT = new FromInt64ToInt64(MDatetimes.DATETIME, MNumeric.BIGINT, false, Constantness.UNKNOWN);
    
    public static final TCast TO_UNSIGNED_BIGINT = new FromInt64ToInt64(MDatetimes.DATETIME, MNumeric.BIGINT_UNSIGNED, false, Constantness.UNKNOWN);
    
    public static final TCast TO_DOUBLE = new FromInt64ToDouble(MDatetimes.DATETIME, MApproximateNumber.DOUBLE, false, Constantness.UNKNOWN);
    
    public static final TCast TO_DECIMAL = new FromInt64ToDecimal(MDatetimes.DATETIME, MNumeric.DECIMAL, false, Constantness.UNKNOWN);
    
    public static final TCast TO_DATE = new TCastBase(MDatetimes.DATETIME, MDatetimes.DATE, Constantness.UNKNOWN)
    {

        @Override
        public void doEvaluate(TExecutionContext context, PValueSource source, PValueTarget target)
        {
            target.putInt32(MDatetimes.encodeDate(MDatetimes.decodeDatetime(source.getInt64())));
        }
    };
    
    public static final TCast TO_TIME = new TCastBase(MDatetimes.DATETIME, MDatetimes.TIME, Constantness.UNKNOWN)
    {

        @Override
        public void doEvaluate(TExecutionContext context, PValueSource source, PValueTarget target)
        {
            target.putInt32(MDatetimes.encodeTime(MDatetimes.decodeDatetime(source.getInt64())));
        }
    };
    
    public static final TCast TO_TIMESTAMP = new TCastBase(MDatetimes.DATETIME, MDatetimes.TIMESTAMP, Constantness.UNKNOWN)
    {

        @Override
        public void doEvaluate(TExecutionContext context, PValueSource source, PValueTarget target)
        {
            target.putInt32(MDatetimes.encodeTimestamp(MDatetimes.decodeDatetime(source.getInt64()),
                                                       context.getCurrentTimezone(),
                                                       context));
        }
    };
}
