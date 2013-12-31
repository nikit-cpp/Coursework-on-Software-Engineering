package engine.lexer;

public final class Token {
	public final String value;
	public final Tag tag;
	
	public Token(String value, Tag tag){
		this.value=value;
		this.tag=tag;
	}

	public Token(String s) {
		this.value=s;
		this.tag=Tag.WORD;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (tag != other.tag)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Token [value=" + value + ", tag=" + tag + "]";
	}
}
