package org.mule.providers.rvd;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.transformers.AbstractTransformer;
import org.mule.umo.transformer.TransformerException;

import com.tibco.tibrv.TibrvMsg;

/**
 * This transformer will convert any object into a TibrvMsg.  If the object
 * is a map, the TibrvMsg will utilize the mappings in the message. Otherwise,
 * the transformer will utilize a key of <code>RVDConnector.CONTENT_FIELD</code>
 * ("content") to label the object.
 *
 * @author <a href="mailto:ross.paul@mlb.com">Ross Paul</a>
 * @version $Revision: $
 */
public class ObjectToTibrvMsg extends AbstractTransformer
{
    private static transient Log logger = LogFactory.getLog( ObjectToTibrvMsg.class );

    protected Object doTransform(Object src, String encoding) throws TransformerException
    {
        if( src instanceof TibrvMsg )
            return src;

        TibrvMsg msg = null;
        try
        {
            msg = new TibrvMsg();

            if( src instanceof Map )
            {
                logger.debug( "Src is map, adding key value pairs" );
                Map map = (Map)src;
                String key;
                for( Iterator i = map.keySet().iterator(); i.hasNext(); )
                {
                    key = i.next().toString();
                    msg.add( key, map.get( key ));
                }
            }
            else
            {
                logger.debug( "adding src with fieldname: " +
                              RVDConnector.CONTENT_FIELD );
                msg.add( RVDConnector.CONTENT_FIELD, src );
            }
        }catch( Exception e ){ throw new TransformerException( this, e );}
        return msg;
    }
}