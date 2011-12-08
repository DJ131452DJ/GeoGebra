package geogebra3D.kernel3D;

import geogebra.common.kernel.Construction;
import geogebra.common.kernel.Matrix.Coords;
import geogebra.common.kernel.arithmetic.NumberValue;
import geogebra.common.kernel.geos.GeoElement;
import geogebra.common.kernel.kernelND.GeoPointND;
import geogebra.common.kernel.kernelND.GeoVectorND;

/**
 * @author ggb3D
 *
 */
public class AlgoQuadricPointVectorNumber extends AlgoQuadricPointNumber {
	
	/**
	 * @param c construction
	 * @param label 
	 * @param origin 
	 * @param direction 
	 * @param r 
	 */
	public AlgoQuadricPointVectorNumber(Construction c, String label, GeoPointND origin, GeoVectorND direction, NumberValue r, AlgoQuadricComputer computer) {
		super(c,label,origin,(GeoElement) direction, r,computer);
	}
	
	protected Coords getDirection(){
		return ((GeoVectorND) getSecondInput()).getCoordsInD(3);
	}
	
    final public String toString() {
    	return app.getPlain(getClassName()+"FromQuadricPointAVectorBNumberC",getOrigin().getLabel(),getSecondInput().getLabel(),getNumber().getLabel());

    }




	


}
