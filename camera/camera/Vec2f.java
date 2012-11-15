package camera;

public class Vec2f {

	private float x = 0.f;
	private float y = 0.f;

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
	public void calcVec2f(Vec2f v1, Vec2f v2) {
		this.x = v2.getX()-v1.getX();
		this.y = v2.getY()-v1.getY();
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
		float radian = (float) (Math.acos(v.getDot(this) / (this.getLength() * v.getLength())));
		return (float)Math.toDegrees(radian);
	}

	public String toString() {
		return "x: "+this.x+"  y: "+this.y;
	}


}
