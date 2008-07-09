package org.mule.module.sxc;

import java.io.ByteArrayOutputStream;

import org.apache.commons.io.IOUtils;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

public class SxcFilterTestCase extends FunctionalTestCase {
	int finished = 0;
	
	public void testBasicXPath() throws Exception {
		final MuleClient client = new MuleClient();
		
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtils.copy(getClass().getResourceAsStream("/purchase-order.xml"), out);
		
		MuleMessage res = client.send("vm://in", out.toByteArray(), null);
		assertEquals(Boolean.TRUE, res.getPayload());
	}

    public void testAndFilter() throws Exception {
        final MuleClient client = new MuleClient();
        
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(getClass().getResourceAsStream("/purchase-order.xml"), out);
        
        MuleMessage res = client.send("vm://and-filter", out.toByteArray(), null);
        
        assertEquals(Boolean.TRUE, res.getPayload());
    }
    public void testOrFilter() throws Exception {
        final MuleClient client = new MuleClient();
        
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(getClass().getResourceAsStream("/purchase-order.xml"), out);
        
        MuleMessage res = client.send("vm://or-filter", out.toByteArray(), null);
        
        assertEquals(Boolean.TRUE, res.getPayload());
    }
    public void testNotFilter() throws Exception {
        final MuleClient client = new MuleClient();
        
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(getClass().getResourceAsStream("/purchase-order.xml"), out);
        
        MuleMessage res = client.send("vm://not-filter", out.toByteArray(), null);
        
        assertEquals(Boolean.TRUE, res.getPayload());
    }
    
	public void xtestBenchmark() throws Exception {
        final MuleClient client = new MuleClient();
        
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(getClass().getResourceAsStream("/purchase-order.xml"), out);
	        
		System.out.println("Warmup");
        fire(client, out, 1500);
		
		System.out.println("Running....");
        
        fire(client, out, 1000);
		
		Thread.sleep(1000);
	}

    private void fire(final MuleClient client, final ByteArrayOutputStream out, final int count) throws InterruptedException
    {
        long time = System.currentTimeMillis();
        finished = 0;
        for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < count; j++) {
						try {
							client.send("vm://in", out.toByteArray(), null);
						} catch (MuleException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					finished++;
				}
			}).start();
		}

        while (finished < 10) {
            Thread.sleep(100);
        }
        System.out.println("elapsed " + (System.currentTimeMillis() - time));
        
    }
	
	@Override
	protected String getConfigResources() {
		return "xpath-filter-conf.xml";
	}

}
