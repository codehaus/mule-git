package org.mule.providers.jgroups;

import org.mule.providers.AbstractConnector;
import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.InitialisationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.JChannel;

public class JGroupsConnector extends AbstractConnector
{
    private String flags = "UDP(mcast_addr=224.0.0.35;mcast_port=45566;ip_ttl=32;):PING(timeout=2000):FD(timeout=5000):pbcast.NAKACK:pbcast.STABLE(stability_delay=1000):VERIFY_SUSPECT(timeout=1500):MERGE2(max_interval=10000;min_interval=5000):UNICAST(timeout=3600):FRAG:pbcast.GMS(join_timeout=3000;join_retry_timeout=2000;shun=true)";
    private String extraFlags = "";
    private boolean primary = false;

    /**
     * logger used by this class
     */
    private static Log logger = LogFactory.getLog(JGroupsConnector.class);

    public JGroupsConnector()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.providers.UMOConnector#getProtocol()
     */
    public String getProtocol()
    {
        return "jgroups";
    }

    public JChannel createChannel(String groupName) throws InitialisationException
    {
        try {
            JChannel channel = new JChannel(flags + extraFlags);
            //channel.setOpt(channel.GET_STATE_EVENTS, Boolean.TRUE);
            return channel;
        } catch (Exception e) {
        logger.error(e);
            throw new InitialisationException(e, this);
        }
    }

    protected void doDispose()
    {
        // template method, nothing to do
    }


    protected void doInitialise() throws InitialisationException
    {
        // template method, nothing to do
    }

    protected void doConnect() throws Exception
    {
        // template method, nothing to do
    }

    protected void doDisconnect() throws Exception
    {
        // template method, nothing to do
    }

    protected void doStart() throws UMOException
    {
        // template method, nothing to do
    }

    protected void doStop() throws UMOException
    {
        // template method, nothing to do
    }

    /**
     * @return Returns the flags string.
     */
    public String getFlags()
    {
        return flags;
    }

    /**
     * @param flags The flags to set.
     */
    public void setFlags(String flags)
    {
        this.flags = flags;
    }

    /**
     * @return Returns the extra flags.
     */
    public String getExtraFlags()
    {
        return extraFlags;
    }

    /**
     * @param flags The flags to set.
     */
    public void setExtraFlags(String extraFlags)
    {
        this.extraFlags = extraFlags;
    }

    /**
     * @return Returns the primary flag.
     */
    public boolean getPrimary()
    {
        return primary;
    }

    /**
     * @param primary 
     */
    public void setPrimary(boolean primary)
    {
        this.primary = primary;
    }

}
