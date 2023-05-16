package logic;

public class Client {

	// Instance variables **********************************************

	private String ip;
	private String hostName;
	private ClientConnection status;

	// Constructors ****************************************************

	/**
	 * @param ip
	 * @param hostName
	 * @param status
	 */
	public Client(String ip, String hostName, ClientConnection status) {
		this.ip = ip;
		this.hostName = hostName;
		this.status = status;
	}

	// Instance methods ************************************************

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * setter
	 * @param String ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName The hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the status
	 */
	public ClientConnection getStatus() {
		return status;
	}

	/**
	 * @param status The status to set
	 */
	public void setStatus(ClientConnection status) {
		this.status = status;
	}
}
//End of Client class