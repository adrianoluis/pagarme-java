package me.pagar.util;

import me.pagar.PagarMe;
import org.bouncycastle.crypto.tls.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class PagarMeSSLSocketFactory extends SSLSocketFactory {

	static {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}

	public PagarMeSSLSocketFactory() {
		System.setProperty("https.protocols", "TLSv1.2");
	}

	private java.security.cert.Certificate getRequiredCert(final CertificateFactory cf)
			throws IOException, CertificateException {
		PemReader reader = null;
		try {
			// read local cert copy
			final InputStream certFile = Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream("pagarme.crt");

			reader = new PemReader(new InputStreamReader(certFile));
			final PemObject pem = reader.readPemObject();
			final ByteArrayInputStream bais = new ByteArrayInputStream(pem.getContent());
			// will always return 1
			final java.security.cert.X509Certificate cert =
					(java.security.cert.X509Certificate) cf.generateCertificates(bais).iterator().next();
			// ensure it is valid
			cert.checkValidity();
			return cert;
		} finally {
			if (null != reader) {
				reader.close();
			}
		}
	}

	public Socket createSocket(Socket s, final String host, final int port, final boolean autoClose) throws IOException {
		if (s == null) {
			s = new Socket();
		}

		if (!s.isConnected()) {
			s.connect(new InetSocketAddress(host, port));
		}

		final TlsClientProtocol tlsClientProtocol = new TlsClientProtocol(
				s.getInputStream(), s.getOutputStream(),
				new SecureRandom());

		return _createSSLSocket(host, tlsClientProtocol);
	}

	public String[] getDefaultCipherSuites() {
		return null;
	}

	public String[] getSupportedCipherSuites() {
		return null;
	}

	public Socket createSocket(String host, int port) {
		throw new UnsupportedOperationException();
	}

	public Socket createSocket(InetAddress host, int port) {
		throw new UnsupportedOperationException();
	}

	public Socket createSocket(String host, int port, InetAddress localHost, int localPort) {
		throw new UnsupportedOperationException();
	}

	public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) {
		throw new UnsupportedOperationException();
	}

	private SSLSocket _createSSLSocket(final String host, final TlsClientProtocol tlsClientProtocol) {
		return new SSLSocket() {
			private java.security.cert.Certificate[] peerCerts;

			public InputStream getInputStream() {
				return tlsClientProtocol.getInputStream();
			}

			public OutputStream getOutputStream() {
				return tlsClientProtocol.getOutputStream();
			}

			public synchronized void close() throws IOException {
				tlsClientProtocol.close();
			}

			public void addHandshakeCompletedListener(HandshakeCompletedListener listener) {
			}

			public boolean getEnableSessionCreation() {
				return false;
			}

			public String[] getEnabledCipherSuites() {
				return null;
			}

			public String[] getEnabledProtocols() {
				return null;
			}

			public boolean getNeedClientAuth() {
				return false;
			}

			public SSLSession getSession() {
				return new SSLSession() {

					public int getApplicationBufferSize() {
						return 0;
					}

					public String getCipherSuite() {
						throw new UnsupportedOperationException();
					}

					public long getCreationTime() {
						throw new UnsupportedOperationException();
					}

					public byte[] getId() {
						throw new UnsupportedOperationException();
					}

					public long getLastAccessedTime() {
						throw new UnsupportedOperationException();
					}

					public java.security.cert.Certificate[] getLocalCertificates() {
						throw new UnsupportedOperationException();
					}

					public Principal getLocalPrincipal() {
						throw new UnsupportedOperationException();
					}

					public int getPacketBufferSize() {
						throw new UnsupportedOperationException();
					}

					public X509Certificate[] getPeerCertificateChain() {
						return null;
					}

					public java.security.cert.Certificate[] getPeerCertificates() {
						return peerCerts;
					}

					public String getPeerHost() {
						throw new UnsupportedOperationException();
					}

					public int getPeerPort() {
						return 0;
					}

					public Principal getPeerPrincipal() {
						return null;
					}

					public String getProtocol() {
						throw new UnsupportedOperationException();
					}

					public SSLSessionContext getSessionContext() {
						throw new UnsupportedOperationException();
					}

					public Object getValue(String name) {
						throw new UnsupportedOperationException();
					}

					public String[] getValueNames() {
						throw new UnsupportedOperationException();
					}

					public void invalidate() {
						throw new UnsupportedOperationException();
					}

					public boolean isValid() {
						throw new UnsupportedOperationException();
					}

					public void putValue(String name, Object value) {
						throw new UnsupportedOperationException();
					}

					public void removeValue(String arg0) {
						throw new UnsupportedOperationException();
					}
				};
			}

			public String[] getSupportedProtocols() {
				return null;
			}

			public boolean getUseClientMode() {
				return false;
			}

			public boolean getWantClientAuth() {
				return false;
			}

			public void removeHandshakeCompletedListener(HandshakeCompletedListener listener) {
			}

			public void setEnableSessionCreation(boolean flag) {
			}

			public void setEnabledCipherSuites(String[] suites) {
			}

			public void setEnabledProtocols(String[] protocols) {
			}

			public void setNeedClientAuth(boolean need) {
			}

			public void setUseClientMode(boolean mode) {
			}

			public void setWantClientAuth(boolean want) {
			}

			public String[] getSupportedCipherSuites() {
				return null;
			}

			public void startHandshake() throws IOException {
				tlsClientProtocol.connect(new DefaultTlsClient() {

					@SuppressWarnings("unchecked")
					public Hashtable<Integer, byte[]> getClientExtensions() throws IOException {
						Hashtable<Integer, byte[]> clientExtensions = super.getClientExtensions();
						if (clientExtensions == null) {
							clientExtensions = new Hashtable<Integer, byte[]>();
						}

						// Add host_name
						final byte[] hostName = host.getBytes();

						final ByteArrayOutputStream baos = new ByteArrayOutputStream();
						final DataOutputStream dos = new DataOutputStream(baos);
						dos.writeShort(hostName.length + 3);
						dos.writeByte(0);
						dos.writeShort(hostName.length);
						dos.write(hostName);
						dos.close();

						clientExtensions.put(ExtensionType.server_name, baos.toByteArray());
						return clientExtensions;
					}

					public TlsAuthentication getAuthentication() {
						return new TlsAuthentication() {

							public void notifyServerCertificate(
									org.bouncycastle.crypto.tls.Certificate serverCertificate) throws IOException {
								try {
									final CertificateFactory cf = CertificateFactory.getInstance("X.509");
									final java.security.cert.Certificate requiredCert = getRequiredCert(cf);

									final List<java.security.cert.Certificate> certs = new LinkedList<java.security.cert.Certificate>();
									boolean trustedCertificate = false;
									for (org.bouncycastle.asn1.x509.Certificate c : serverCertificate.getCertificateList()) {
										java.security.cert.Certificate cert =
												cf.generateCertificate(new ByteArrayInputStream(c.getEncoded()));
										certs.add(cert);

										if (cert instanceof java.security.cert.X509Certificate) {
											java.security.cert.X509Certificate x509 =
													(java.security.cert.X509Certificate) cert;
											x509.checkValidity();

											if (null == requiredCert ||
													!PagarMe.isSslPinningEnabled() ||
													x509.equals(requiredCert)) {
												trustedCertificate = true;
												break;
											}
										}
									}

									if (!trustedCertificate) {
										throw new IOException("Invalid certificate chain. Possible SSL proxy cert being used.");
									}

									peerCerts = certs.toArray(new java.security.cert.Certificate[0]);
								} catch (Exception e) {
									throw new IOException(e);
								}
							}

							public TlsCredentials getClientCredentials(CertificateRequest certificateRequest) {
								return null;
							}
						};
					}
				});
			}
		};
	}
}
