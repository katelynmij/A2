/**
 * 
 * Visitor interface.
 * 
 */
public interface UserTypeVisitor {
	public void visit (User user);
	public void visit(UserGroup userGroup);
	public boolean validate(String id);
}