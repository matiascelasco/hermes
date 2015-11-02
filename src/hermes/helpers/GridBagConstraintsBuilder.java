package hermes.helpers;

import java.awt.GridBagConstraints;
import java.awt.Insets;


public class GridBagConstraintsBuilder {

	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;
	private Integer fill;
	private Integer anchor;
	private Double weightx;
	private Double weighty;
	private Insets insets;
	
	public GridBagConstraintsBuilder x(int x) {
		this.x = x;
		return this;
	}
	
	public GridBagConstraintsBuilder y(int y) {
		this.y = y;
		return this;
	}
	
	public GridBagConstraintsBuilder width(int width) {
		this.width = width;
		return this;
	}
	
	public GridBagConstraintsBuilder height(int height) {
		this.height = height;
		return this;
	}
	
	public GridBagConstraintsBuilder size(int size) {
		this.width = size;
		this.height = size;
		return this;
	}
	
	public GridBagConstraintsBuilder size(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}
	
	public GridBagConstraintsBuilder at(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public GridBagConstraintsBuilder fill(int fill) {
		this.fill = fill;
		return this;
	}
	
	public GridBagConstraintsBuilder anchor(int anchor) {
		this.anchor = anchor;
		return this;
	}
	
	public GridBagConstraintsBuilder insets(Insets insets) {
		this.insets = insets;
		return this;
	}
	
	public GridBagConstraintsBuilder weightX(double weightx) {
		this.weightx = weightx;
		return this;
	}

	public GridBagConstraintsBuilder weightY(double weighty) {
		this.weighty = weighty;
		return this;
	}
	
	public GridBagConstraints build(){
		if (x == null || y == null || width == null || height == null){
			throw new IllegalStateException("All attributes (x, y, width and height) are required");
		}
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		
		if (weightx != null){
			constraints.weightx = weightx;
		}
		if (weighty != null){
			constraints.weightx = weighty;
		}
		if (fill != null){
			constraints.fill = fill;
		}
		if (anchor != null){
			constraints.anchor = anchor;
		}
		if (insets != null){
			constraints.insets = insets;
		}
		return constraints;
	}
	
}
