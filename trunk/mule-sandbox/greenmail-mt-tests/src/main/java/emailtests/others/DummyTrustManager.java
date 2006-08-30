/**
 * 
 */
package emailtests.others;

import java.security.cert.X509Certificate;

/**
 * @author <a href="mailto:stephen.fenech@symphonysoft.com">Stephen Fenech</a>
 *
 */
/*public class DummyTrustManager implements X509TrustManager {
	public boolean isClientTrusted( X509Certificate[] cert) {
	      return true;
	    }

	    public boolean isServerTrusted( X509Certificate[] cert) {
	      return true;
	    }

	    public X509Certificate[] getAcceptedIssuers() {
	      return new X509Certificate[ 0];
	    }

		
}*/

import javax.net.ssl.X509TrustManager;


/**
 * DummyTrustManager - NOT SECURE
 */
public class DummyTrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] cert, String authType) {
	// everything is trusted
    }

    public void checkServerTrusted(X509Certificate[] cert, String authType) {
	// everything is trusted
    }

    public X509Certificate[] getAcceptedIssuers() {
	return new X509Certificate[0];
    }
}
