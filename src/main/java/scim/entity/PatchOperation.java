package scim.entity;
/**
 * 
 * @author AkshathaKadri
 *
 */
public class PatchOperation {

	private String op;
	private Object value;
	
	public PatchOperation() {
		value = new Object();
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
