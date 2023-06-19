package logic;

/**
 * class that Represents the Client connection.
 */
public class Client {

	private String ip;
	private String hostName;
	private ClientConnection status;

	

	/**
	 * Constructs an client object with the specified parameters.
	 * @param ip
	 * @param hostName
	 * @param status
	 */
	public Client(String ip, String hostName, ClientConnection status) {
		this.ip = ip;
		this.hostName = hostName;
		this.status = status;
	}

	
	/**
	 * Returns the ip of the client.
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip of the client.
	 * @param String ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Returns the hostName of the client.
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Sets the host name of the client.
	 * @param hostName The hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * Returns the status connection of the client.
	 * @return the status
	 */
	public ClientConnection getStatus() {
		return status;
	}

	/**
	 * Sets the status of the clients connection.
	 * @param status The status to set
	 */
	public void setStatus(ClientConnection status) {
		this.status = status;
	}
}
//End of Client class