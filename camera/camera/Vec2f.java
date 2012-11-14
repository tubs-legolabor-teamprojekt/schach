package camera;

public class Vec2f {

	float x = 0.f;
	float y = 0.f;
	
	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2f() {
	}
	/*
	 * Berechnet aus 2 Punkten seinen Richtungsvektor und setzt ihn als seine x,y werte.
	 * @param x1,y1,x2,y2 Punkte, aus denen RV berechnet wird
	 */
	public void calcVec2f(float x1, float y1, float x2, float y2) {
		this.x = x2-x1;
		this.y = y2-y1;
	}
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
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
