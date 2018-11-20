package BullAnalytics;

/*
 * Classe que recebe cada um dos dados
 * Criada para lidar com diferentes tipos de informação
 */


public class Info {
	//timestamp,open,high,low,close,volume
	private String timestamp;
	private double open;
	private double high;
	private double low;
	private double close;
	private int volume;
	
	public Info(String timestamp, double open, double high, double low, double close, int volume) {
		this.timestamp = timestamp;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
		
}
