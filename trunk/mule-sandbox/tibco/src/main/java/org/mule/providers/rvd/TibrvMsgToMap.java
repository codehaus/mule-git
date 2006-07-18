package org.mule.providers.rvd;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.mule.transformers.AbstractTransformer;
import org.mule.umo.transformer.TransformerException;

import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgField;
import com.tibco.tibrv.TibrvXml;

/**
 * Takes in a tibrvMessage and converts it to a Map. Additionally tibco specific
 * types will be converted into java types.
 *
 * @author <a href="mailto:ross.paul@mlb.com">Ross Paul</a>
 * @version $Revision: 1.1 $
 */
public class TibrvMsgToMap extends AbstractTransformer {

    public TibrvMsgToMap() {
        super();
        registerSourceType(TibrvMsg.class);
    }

    protected Object doTransform(Object src, String encoding) throws TransformerException {
        TibrvMsg msg = (TibrvMsg) src;
        Map map = new HashMap();

        try {
            TibrvMsgField field = null;
            int fields = msg.getNumFields();
            for (int i = 0; i < fields; i++) {
                field = msg.getFieldByIndex(i);
                Object data = field.data;
                if (data instanceof TibrvXml) {
                    data = new String(((TibrvXml) data).getBytes(), encoding);
                }
                map.put(field.name, data);
            }
            return map;
        } catch( TibrvException e ){ throw new TransformerException( this, e );
        } catch( UnsupportedEncodingException e ){ throw new TransformerException( this, e ); }
    }
}