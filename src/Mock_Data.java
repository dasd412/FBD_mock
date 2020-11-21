
public class Mock_Data {

	
	private String type;
	private double tcPercant;
	private double mcdcPercent;
	
	private double scoreSon;
	private double scoreMom;
	

	public Mock_Data(String type, double tcPercant, double mcdcPercent, double scoreSon, double scoreMom
		) {
	
		this.type = type;
		this.tcPercant = tcPercant;
		this.mcdcPercent = mcdcPercent;
		this.scoreSon = scoreSon;
		this.scoreMom = scoreMom;

	}

	public String getType() {
		return type;
	}

	public double getTcPercant() {
		return tcPercant;
	}

	public double getMcdcPercent() {
		return mcdcPercent;
	}

	public double getScoreSon() {
		return scoreSon;
	}

	public double getScoreMom() {
		return scoreMom;
	}

	@Override
	public String toString() {
		return "Mock_Data [type=" + type + ", tcPercant=" + tcPercant + ", mcdcPercent=" + mcdcPercent + ", scoreSon="
				+ scoreSon + ", scoreMom=" + scoreMom + "]";
	}

	
	
	
	
	
}
