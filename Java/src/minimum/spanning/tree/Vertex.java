package minimum.spanning.tree;

public class Vertex {
	
	public int index, key = Integer.MAX_VALUE;
	public boolean visited = false;
	public Vertex parent;
	
	@Override
	public String toString() {
		String text = "Vertex = " + Main.toCharacter(index) + ", Parent = ";
		
		if (parent == null) {
			text += null;
		}
		else {
			text += Main.toCharacter(parent.index);
		}
		
		return text + ", Key = " + key;
	}
	
}