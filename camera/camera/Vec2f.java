package camera;

public class Vec2f {

	float x = 0.f;
	float y = 0.f;
	
	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float getDot(Vec2f v) {
		return (this.x * v.getX())+(this.y * v.getY());
	}
	
	public float getLength() {
		return (float) Math.sqrt(this.x*this.x + this.y*this.y);
	}
	
	public float getAngle(Vec2f v) {
		return (float) Math.acos(v.getDot(this) / (this.getLength() * v.getLength()));
	}
	
	
}
