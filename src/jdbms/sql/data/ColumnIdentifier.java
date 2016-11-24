package jdbms.sql.data;

public class ColumnIdentifier {
	private String name;
	private String type;
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public ColumnIdentifier(String name, String type) {
		this.name = name;
		this.type = type;
	}
}
